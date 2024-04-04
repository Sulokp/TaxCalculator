package sprint1;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import java.awt.Color;

public class Signin extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField Name;
    private JTextField Email;
    private JPasswordField password;
    private JPasswordField confirmpass;
    private JLabel lblConfirmPassword;
    private JLabel lblNewLabel_2;
    private JLabel lblNewLabel_3;

    // Database connection
    private Connect database = new Connect();

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Signin frame = new Signin();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void NewScreen() {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Signin frame = new Signin();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public Signin() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 292, 461);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(205, 177, 204));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        Name = new JTextField();
        Name.setBackground(new Color(128, 255, 255));
        Name.setBounds(62, 86, 96, 19);
        contentPane.add(Name);
        Name.setColumns(10);

        Email = new JTextField();
        Email.setBackground(new Color(128, 255, 255));
        Email.setBounds(62, 132, 96, 19);
        contentPane.add(Email);
        Email.setColumns(10);

        password = new JPasswordField();
        password.setBackground(new Color(128, 255, 255));
        password.setBounds(62, 176, 96, 19);
        contentPane.add(password);

        confirmpass = new JPasswordField();
        confirmpass.setBackground(new Color(128, 255, 255));
        confirmpass.setBounds(62, 219, 96, 19);
        contentPane.add(confirmpass);

        JLabel lblNewLabel = new JLabel("Name");
        lblNewLabel.setBounds(62, 71, 45, 13);
        contentPane.add(lblNewLabel);

        JLabel lblEmail = new JLabel("Email");
        lblEmail.setBounds(62, 116, 45, 13);
        contentPane.add(lblEmail);

        JLabel lblNewLabel_1 = new JLabel("Password");
        lblNewLabel_1.setBounds(62, 162, 96, 13);
        contentPane.add(lblNewLabel_1);

        lblConfirmPassword = new JLabel("Confirm password");
        lblConfirmPassword.setBounds(62, 205, 89, 13);
        contentPane.add(lblConfirmPassword);

        lblNewLabel_2 = new JLabel("Tax Calculator");
        lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 15));
        lblNewLabel_2.setBounds(95, 25, 121, 25);
        contentPane.add(lblNewLabel_2);

        lblNewLabel_3 = new JLabel("If you already have an account  please ");
        lblNewLabel_3.setBounds(49, 312, 179, 13);
        contentPane.add(lblNewLabel_3);

        JButton btnNewButton = new JButton("Confirm");
        btnNewButton.setBackground(new Color(0, 0, 160));
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                registerUser();
            }
        });
        btnNewButton.setBounds(62, 263, 96, 21);
        contentPane.add(btnNewButton);

        JButton btnNewButton_1 = new JButton("Login");
        btnNewButton_1.setBackground(new Color(0, 0, 160));
        btnNewButton_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Login.NewScreen();
            }
        });
        btnNewButton_1.setBounds(86, 335, 85, 21);
        contentPane.add(btnNewButton_1);
    }

    // Method to register a new user
    private void registerUser() {
        String name = Name.getText().trim();
        String email = Email.getText().trim();
        String pass = new String(password.getPassword()).trim();
        String confirmPass = new String(confirmpass.getPassword()).trim();

        // Validation checks
        if (name.isEmpty() || email.isEmpty() || pass.isEmpty() || confirmPass.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please fill in all fields", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!pass.equals(confirmPass)) {
            JOptionPane.showMessageDialog(null, "Passwords do not match", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Additional validation for name field
        if (!name.matches("[a-zA-Z\\s]+")) {
            JOptionPane.showMessageDialog(null, "Name can only contain alphabets and spaces", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Save user data to database
        try (Connection connection = database.connect()) {
            String query = "INSERT INTO users (name, email, password) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, name);
            statement.setString(2, email);
            statement.setString(3, pass);
            statement.executeUpdate();

            JOptionPane.showMessageDialog(null, "User registered successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
            clearFields(); // Clear input fields after successful registration
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error registering user", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    // Method to clear input fields after registration
    private void clearFields() {
        Name.setText("");
        Email.setText("");
        password.setText("");
        confirmpass.setText("");
    }
}