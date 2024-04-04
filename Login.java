package sprint1;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class Login extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField textField;
    private JPasswordField passwordField;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Login frame = new Login();
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
                    Login frame = new Login();
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
    public Login() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 267, 446);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(205, 177, 204));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblNewLabel = new JLabel("Tax Calculator");
        lblNewLabel.setFont(new Font("Yu Gothic Medium", Font.BOLD, 14));
        lblNewLabel.setBounds(77, 24, 128, 37);
        contentPane.add(lblNewLabel);

        textField = new JTextField();
        textField.setBackground(new Color(128, 255, 255));
        textField.setBounds(57, 89, 96, 19);
        contentPane.add(textField);
        textField.setColumns(10);

        passwordField = new JPasswordField();
        passwordField.setBackground(new Color(128, 255, 255));
        passwordField.setBounds(58, 143, 95, 19);
        contentPane.add(passwordField);

        JLabel lblNewLabel_1 = new JLabel("Email");
        lblNewLabel_1.setFont(new Font("Sitka Text", Font.PLAIN, 11));
        lblNewLabel_1.setBounds(57, 77, 45, 13);
        contentPane.add(lblNewLabel_1);

        JLabel lblNewLabel_2 = new JLabel("Password");
        lblNewLabel_2.setBounds(57, 126, 61, 13);
        contentPane.add(lblNewLabel_2);

        JButton btnNewButton = new JButton("Login");
        btnNewButton.setBackground(new Color(0, 0, 160));
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String email = textField.getText();
                String password = new String(passwordField.getPassword());
                int userId = isValidLogin(email, password);
                if (userId != -1) {
                    DashBoard dashboard = new DashBoard(userId);
                    dashboard.setVisible(true);
                } else {
                    System.out.println("Invalid email or password");
                }
            }
        });
        btnNewButton.setBounds(57, 184, 96, 21);
        contentPane.add(btnNewButton);

        JLabel lblNewLabel_3 = new JLabel("If you don't have an account, please");
        lblNewLabel_3.setBounds(41, 233, 202, 13);
        contentPane.add(lblNewLabel_3);

        JButton btnNewButton_1 = new JButton("SignIn");
        btnNewButton_1.setBackground(new Color(0, 0, 160));
        btnNewButton_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Signin.NewScreen();
            }
        });
        btnNewButton_1.setBounds(74, 257, 85, 21);
        contentPane.add(btnNewButton_1);
    }

    // Method to validate login credentials and return user ID
    private int isValidLogin(String email, String password) {
        Connect connector = new Connect();
        Connection connection = connector.connect();
        if (connection != null) {
            try {
                String query = "SELECT id FROM users WHERE email = ? AND password = ?";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, email);
                statement.setString(2, password);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    // Return user ID if login is valid
                    return resultSet.getInt("id");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            } finally {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
        return -1; // Return -1 if login is invalid
    }
}