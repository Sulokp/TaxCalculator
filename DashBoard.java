package taxcalculatorsprint1;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class DashBoard extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private static int userId; // User ID field
    private JComboBox fund = new JComboBox();
    private JComboBox gender = new JComboBox();
    private JComboBox disability = new JComboBox();
    private JComboBox maritalstatus = new JComboBox();

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    DashBoard frame = new DashBoard(userId); // Pass user ID to constructor
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void NewScreen(int userId) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    DashBoard frame = new DashBoard(userId); // Pass user ID to constructor
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
    // Inside the constructor of DashBoard class
    public DashBoard(int userId) { // Updated constructor to accept user ID
        this.userId = userId; // Set user ID
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 455, 359);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane.setBounds(5, 5, 426, 253);
        contentPane.add(tabbedPane);

        // News Panel
        JPanel newsPanel = new JPanel();
        tabbedPane.addTab("News", null, newsPanel, null);
        newsPanel.setLayout(null);

        JList<NewsItem> newsList = new JList<>(getLatestNews());
        JScrollPane scrollPane = new JScrollPane(newsList);
        scrollPane.setBounds(0, 0, 421, 226);
        newsPanel.add(scrollPane);

        // Add list selection listener to open a new tab with news content
        newsList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    NewsItem selectedNews = newsList.getSelectedValue();
                    if (selectedNews != null) {
                        String title = selectedNews.getTitle();
                        String content = selectedNews.getContent();
                        openNewsTab(title, content);
                    }
                }
            }
        });

        // Tax Calculator Panel
        JPanel taxCalculatorPanel = new JPanel();
        tabbedPane.addTab("Tax Calculator", null, taxCalculatorPanel, null);
        taxCalculatorPanel.setLayout(null);

        JLabel lblIncome = new JLabel("Income:");
        lblIncome.setBounds(50, 50, 100, 25);
        taxCalculatorPanel.add(lblIncome);

        JTextField txtIncome = new JTextField();
        txtIncome.setBounds(160, 50, 150, 25);
        taxCalculatorPanel.add(txtIncome);

        // Check if user ID is available to show User Profile tab
        if (userId != 0) {
            // User Profile Panel
            JPanel profilePanel = new JPanel();
            tabbedPane.addTab("User Profile", null, profilePanel, null);
            profilePanel.setLayout(null);

            fund.setModel(new DefaultComboBoxModel(new String[]{"EPF", "SSF"}));
            fund.setBounds(195, 22, 90, 21);
            profilePanel.add(fund);

            gender.setModel(new DefaultComboBoxModel(new String[]{"Male", "Female"}));
            gender.setBounds(195, 61, 90, 21);
            profilePanel.add(gender);

            maritalstatus.setModel(new DefaultComboBoxModel(new String[]{"Married", "Unmarried"}));
            maritalstatus.setBounds(195, 96, 90, 21);
            profilePanel.add(maritalstatus);

            disability.setModel(new DefaultComboBoxModel(new String[]{"Yes", "No"}));
            disability.setBounds(195, 135, 90, 21);
            profilePanel.add(disability);

            JLabel lblNewLabel = new JLabel("Fund type");
            lblNewLabel.setBounds(105, 26, 63, 13);
            profilePanel.add(lblNewLabel);

            JLabel lblGender = new JLabel("Gender");
            lblGender.setBounds(105, 65, 63, 13);
            profilePanel.add(lblGender);

            JLabel lblMaritalStatus = new JLabel("Marital Status");
            lblMaritalStatus.setBounds(105, 100, 63, 13);
            profilePanel.add(lblMaritalStatus);

            JLabel lblDisability = new JLabel("Disability");
            lblDisability.setBounds(105, 139, 63, 13);
            profilePanel.add(lblDisability);

            JButton btnSave = new JButton("Save");
            btnSave.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    saveOrUpdateUserProfile();
                }
            });
            btnSave.setBounds(146, 183, 85, 21);
            profilePanel.add(btnSave);
        }

        JButton btnNewButton = new JButton("Login");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Login.NewScreen();
            }
        });
        btnNewButton.setBounds(15, 268, 85, 21);
        contentPane.add(btnNewButton);

        JButton btnNewButton_1 = new JButton("SignIn");
        btnNewButton_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Signin.NewScreen();
            }
        });
        btnNewButton_1.setBounds(328, 268, 85, 21);
        contentPane.add(btnNewButton_1);
    }

    // Method to open a new tab with the news title and content
    private void openNewsTab(String title, String content) {
        JFrame newsFrame = new JFrame(title);
        JPanel newsPanel = new JPanel(new BorderLayout());
        JTextArea textArea = new JTextArea(content);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        newsPanel.add(scrollPane, BorderLayout.CENTER);
        newsFrame.getContentPane().add(newsPanel);
        newsFrame.setSize(400, 300);
        newsFrame.setVisible(true);
    }

    // Method to retrieve the latest news from the database
    private NewsItem[] getLatestNews() {
        List<NewsItem> newsList = new ArrayList<>();
        Connect connector = new Connect();
        Connection connection = connector.connect();
        if (connection != null) {
            try {
                String query = "SELECT title, content FROM news ORDER BY date_published DESC LIMIT 3"; // Assuming you want to display the latest 3 news
                PreparedStatement statement = connection.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    String title = resultSet.getString("title");
                    String content = resultSet.getString("content");
                    newsList.add(new NewsItem(title, content));
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                try {
                    connection.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
        return newsList.toArray(new NewsItem[0]);
    }

    // Custom class to hold title and content of news
    private class NewsItem {
        private String title;
        private String content;

        public NewsItem(String title, String content) {
            this.title = title;
            this.content = content;
        }

        public String getTitle() {
            return title;
        }

        public String getContent() {
            return content;
        }

        @Override
        public String toString() {
            return title; // Display only the title in the JList
        }
    }

    // Method to save or update user profile information
    private void saveOrUpdateUserProfile() {
        if (userId == 0) {
            // If no user ID is available, prompt the user to sign in
            JOptionPane.showMessageDialog(null, "Please sign in to save your profile data.");
            return;
        }

        int isEPFValue = fund.getSelectedIndex(); // 0 for "EPF", 1 for "SSF"
        int isMaleValue = gender.getSelectedIndex(); // 0 for "Male", 1 for "Female"
        int isMarriedValue = maritalstatus.getSelectedIndex(); // 0 for "Married", 1 for "Unmarried"
        int isDisableValue = disability.getSelectedIndex(); // 0 for "Yes", 1 for "No"

        Connect connector = new Connect();
        Connection connection = connector.connect();
        if (connection != null) {
            try {
                // Insert new user profile
                String query = "INSERT INTO userprofile (userid, isEPF, isMale, isMarried, isDisable, date_saved) VALUES (?, ?, ?, ?, ?, CURRENT_DATE)";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setInt(1, userId);
                statement.setInt(2, isEPFValue);
                statement.setInt(3, isMaleValue);
                statement.setInt(4, isMarriedValue);
                statement.setInt(5, isDisableValue);
                statement.executeUpdate();

                System.out.println("User profile saved successfully.");
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
    }

}
