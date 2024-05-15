package taxcalculatorsprint1;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class AdminPanel extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JPanel buttonPanel;
    private JScrollPane scrollPane;
    private JPanel newsPanel;
    private String selectedTitle; // Variable to store selected news title

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                AdminPanel frame = new AdminPanel();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public AdminPanel() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout());

        // Panel for buttons
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        contentPane.add(buttonPanel, BorderLayout.NORTH);

        JButton btnAddNews = new JButton("Add News");
        btnAddNews.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String title = JOptionPane.showInputDialog(AdminPanel.this, "Enter news title:");
                String content = JOptionPane.showInputDialog(AdminPanel.this, "Enter news content:");
                if (title != null && content != null) {
                    addNews(title, content);
                    refreshNewsDisplay();
                }
            }
        });
        buttonPanel.add(btnAddNews);

        JButton btnEditNews = new JButton("Edit Selected News");
        btnEditNews.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (selectedTitle != null) {
                    String newContent = JOptionPane.showInputDialog(AdminPanel.this, "Enter new content:");
                    if (newContent != null) {
                        editNews(selectedTitle, newContent);
                        refreshNewsDisplay();
                        selectedTitle = null; // Reset selected title after edit
                    }
                } else {
                    JOptionPane.showMessageDialog(AdminPanel.this, "Please select a news item to edit.");
                }
            }
        });
        buttonPanel.add(btnEditNews);

        JButton btnDeleteNews = new JButton("Delete Selected News");
        btnDeleteNews.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (selectedTitle != null) {
                    deleteNews(selectedTitle);
                    refreshNewsDisplay();
                    selectedTitle = null; // Reset selected title after delete
                } else {
                    JOptionPane.showMessageDialog(AdminPanel.this, "Please select a news item to delete.");
                }
            }
        });
        buttonPanel.add(btnDeleteNews);

        // Panel for news display
        newsPanel = new JPanel(new GridLayout(0, 1)); // GridLayout with one column
        scrollPane = new JScrollPane(newsPanel);
        contentPane.add(scrollPane, BorderLayout.CENTER);

        // Display latest news
        refreshNewsDisplay();
    }

    private void addNews(String title, String content) {
        Connect connector = new Connect();
        Connection connection = connector.connect();
        if (connection != null) {
            try {
                String query = "INSERT INTO news (title, content, date_published, date_added_to_db) VALUES (?, ?, CURDATE(), CURRENT_TIMESTAMP())";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, title);
                statement.setString(2, content);
                statement.executeUpdate();
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

    private void editNews(String title, String newContent) {
        Connect connector = new Connect();
        Connection connection = connector.connect();
        if (connection != null) {
            try {
                String query = "UPDATE news SET content = ? WHERE title = ?";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, newContent);
                statement.setString(2, title);
                statement.executeUpdate();
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

    private void deleteNews(String title) {
        Connect connector = new Connect();
        Connection connection = connector.connect();
        if (connection != null) {
            try {
                String query = "DELETE FROM news WHERE title = ?";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, title);
                statement.executeUpdate();
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

    private void refreshNewsDisplay() {
        newsPanel.removeAll(); // Clear previous news items

        Connect connector = new Connect();
        Connection connection = connector.connect();
        if (connection != null) {
            try {
                String query = "SELECT title FROM news ORDER BY date_published DESC LIMIT 5";
                PreparedStatement statement = connection.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    String title = resultSet.getString("title");

                    // Limit the view of news title to 30 characters with ellipsis if needed
                    if (title.length() > 30) {
                        title = title.substring(0, 27) + "...";
                    }

                    // Create a panel to hold each news item and its controls
                    final String finalTitle = title; // Declare a final variable to capture title
                    JPanel newsItemPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

                    // Create a label to display news title
                    JLabel newsLabel = new JLabel("<html><b>" + title + "</b></html>");
                    newsItemPanel.add(newsLabel);

                    // Create a checkbox for selection
                    JCheckBox selectCheckbox = new JCheckBox("Select");
                    newsItemPanel.add(selectCheckbox);

                    // Add ActionListener to handle selection
                    selectCheckbox.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            if (selectCheckbox.isSelected()) {
                                // Store the selected news title
                                selectedTitle = finalTitle;
                            }
                        }
                    });

                    // Add the news item panel to the main news panel
                    newsPanel.add(newsItemPanel);
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

        newsPanel.revalidate();
        newsPanel.repaint();
    }
}

