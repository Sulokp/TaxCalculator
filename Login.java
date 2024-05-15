package taxcalculatorsprint1;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField textField;
    private JPasswordField passwordField;

    /**
     * Launch the application.
     */
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
        setBounds(100, 100, 299, 446);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(205, 177, 204));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblNewLabel = new JLabel("Tax Calculator");
        lblNewLabel.setBounds(77, 24, 128, 37);
        lblNewLabel.setFont(new Font("Yu Gothic Medium", Font.BOLD, 14));
        contentPane.add(lblNewLabel);

        textField = new JTextField();
        textField.setBounds(57, 89, 96, 19);
        textField.setBackground(new Color(128, 255, 255));
        contentPane.add(textField);
        textField.setColumns(10);

        passwordField = new JPasswordField();
        passwordField.setBounds(58, 143, 95, 19);
        passwordField.setBackground(new Color(128, 255, 255));
        contentPane.add(passwordField);

        JLabel lblNewLabel_1 = new JLabel("Email");
        lblNewLabel_1.setBounds(57, 77, 45, 13);
        lblNewLabel_1.setFont(new Font("Sitka Text", Font.PLAIN, 11));
        contentPane.add(lblNewLabel_1);

        JLabel lblNewLabel_2 = new JLabel("Password");
        lblNewLabel_2.setBounds(57, 126, 61, 13);
        contentPane.add(lblNewLabel_2);

        JButton btnNewButton = new JButton("Login");
        btnNewButton.setBounds(57, 184, 96, 21);
        btnNewButton.setBackground(new Color(192, 192, 192));
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String email = textField.getText();
                String password = new String(passwordField.getPassword());
                int userId = isValidLogin(email, password);
                if (userId != -1) {
                    if (email.equals("admin") && password.equals("admin")) {
                        openAdminPanel();
                    } else {
                        openDashboard(userId);
                    }
                } else {
                    JOptionPane.showMessageDialog(Login.this, "Invalid email or password", "Login Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        contentPane.add(btnNewButton);

        JLabel lblNewLabel_3 = new JLabel("If you don't have an account, please");
        lblNewLabel_3.setBounds(20, 233, 233, 13);
        contentPane.add(lblNewLabel_3);

        JButton btnNewButton_1 = new JButton("SignUp");
        btnNewButton_1.setBounds(74, 257, 85, 21);
        btnNewButton_1.setBackground(new Color(192, 192, 192));
        btnNewButton_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Signin.NewScreen();
            }
        });
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
        return -1;
    }

    private void openDashboard(int userId) {
        DashBoard dashboard = new DashBoard(userId);
        dashboard.setVisible(true);
        dispose(); // Close the login window
    }

    private void openAdminPanel() {
        AdminPanel adminPanel = new AdminPanel();
        adminPanel.setVisible(true);
        dispose(); // Close the login window
    }

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
}
