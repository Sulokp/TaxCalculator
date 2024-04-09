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
import javax.swing.JCheckBox;
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
import java.util.Calendar;
import java.util.GregorianCalendar;


public class DashBoard extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private static int userId;
    private JComboBox<String> fund = new JComboBox<>();
    private JComboBox<String> gender = new JComboBox<>();
    private JComboBox<String> disability = new JComboBox<>();
    private JComboBox<String> maritalstatus = new JComboBox<>();
    private JTextField monthlysalary;
    private JTextField Bonus;
    private JTextField annualsalary;
    private JTextField cittxt;
    private JTextField insurancetxt;
    private JTextField monthlysalary1;
    private JTextField bonus1;
    private JTextField annualsalary1;
    private JTextField cit1;
    private JTextField insurance1;
    private JCheckBox marriedcheck;
    private JCheckBox chckbxDisable;
    private JCheckBox chckbxFemale;
    private JCheckBox chckbxEpf;
    private double epfDeduction;
    

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    DashBoard frame = new DashBoard(userId);
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
                    DashBoard frame = new DashBoard(userId);
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public DashBoard(int userId) {
        this.userId = userId;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 511, 359);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane.setBounds(5, 5, 468, 276);
        contentPane.add(tabbedPane);

        JPanel newsPanel = new JPanel();
        tabbedPane.addTab("News", null, newsPanel, null);
        newsPanel.setLayout(null);

        JList<NewsItem> newsList = new JList<>(getLatestNews());
        JScrollPane scrollPane = new JScrollPane(newsList);
        scrollPane.setBounds(0, 0, 500, 300);
        newsPanel.add(scrollPane);

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
        if(userId==0) {
        JPanel taxCalculatorPanel = new JPanel();
        tabbedPane.addTab("Tax Calculator", null, taxCalculatorPanel, null);
        taxCalculatorPanel.setLayout(null);

        marriedcheck = new JCheckBox("Married");
        marriedcheck.setBounds(18, 6, 93, 21);
        taxCalculatorPanel.add(marriedcheck);

        chckbxDisable = new JCheckBox("Disable");
        chckbxDisable.setBounds(126, 6, 93, 21);
        taxCalculatorPanel.add(chckbxDisable);

        chckbxFemale = new JCheckBox("Female");
        chckbxFemale.setBounds(216, 6, 93, 21);
        taxCalculatorPanel.add(chckbxFemale);

        monthlysalary = new JTextField();
        monthlysalary.setBounds(10, 60, 96, 19);
        taxCalculatorPanel.add(monthlysalary);
        monthlysalary.setColumns(10);

        // Add ActionListener to calculate annual salary
        monthlysalary.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    // Get the value from monthlysalary field
                    double monthlySalaryValue = Double.parseDouble(monthlysalary.getText());
                    
                    // Calculate annual salary (monthly salary * 12)
                    double annualSalaryValue = monthlySalaryValue * 12;
                    
                    // Update the annualsalary field with the calculated value
                    annualsalary.setText(String.valueOf(annualSalaryValue));
                } catch (NumberFormatException ex) {
                    // Handle the case where the user enters non-numeric input
                    JOptionPane.showMessageDialog(null, "Please enter a valid numeric value for Monthly Salary.");
                }
            }
        });


        JLabel lblNewLabel_1 = new JLabel("Monthly Salary");
        lblNewLabel_1.setBounds(10, 45, 74, 13);
        taxCalculatorPanel.add(lblNewLabel_1);

        chckbxEpf = new JCheckBox("EPF");
        chckbxEpf.setBounds(311, 6, 93, 21);
        taxCalculatorPanel.add(chckbxEpf);

        Bonus = new JTextField();
        Bonus.setColumns(10);
        Bonus.setBounds(269, 60, 96, 19);
        taxCalculatorPanel.add(Bonus);

        JLabel lblNewLabel_2 = new JLabel("Bonus");
        lblNewLabel_2.setBounds(269, 45, 45, 13);
        taxCalculatorPanel.add(lblNewLabel_2);

        annualsalary = new JTextField();
        annualsalary.setBounds(10, 96, 96, 19);
        taxCalculatorPanel.add(annualsalary);
        annualsalary.setColumns(10);

        JLabel lblNewLabel_3 = new JLabel("Annual Salary");
        lblNewLabel_3.setBounds(10, 83, 74, 13);
        taxCalculatorPanel.add(lblNewLabel_3);

        cittxt = new JTextField();
        cittxt.setBounds(10, 130, 96, 19);
        taxCalculatorPanel.add(cittxt);
        cittxt.setColumns(10);

        JLabel lblNewLabel_5 = new JLabel("CIT");
        lblNewLabel_5.setBounds(10, 117, 45, 13);
        taxCalculatorPanel.add(lblNewLabel_5);

        insurancetxt = new JTextField();
        insurancetxt.setBounds(269, 96, 96, 19);
        taxCalculatorPanel.add(insurancetxt);
        insurancetxt.setColumns(10);

        JLabel lblNewLabel_6 = new JLabel("Insurance");
        lblNewLabel_6.setBounds(264, 83, 45, 13);
        taxCalculatorPanel.add(lblNewLabel_6);

        JButton btnNewButton_2_1_1 = new JButton("Reset Values");
        btnNewButton_2_1_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Reset text fields
                monthlysalary.setText("");
                Bonus.setText("");
                annualsalary.setText("");
                cittxt.setText("");
                insurancetxt.setText("");
                monthlysalary1.setText("");
                bonus1.setText("");
                annualsalary1.setText("");
                cit1.setText("");
                insurance1.setText("");

                // Reset checkboxes
                marriedcheck.setSelected(false);
                chckbxDisable.setSelected(false);
                chckbxFemale.setSelected(false);
                chckbxEpf.setSelected(false);

                // Display a message indicating that values have been reset
                JOptionPane.showMessageDialog(null, "Values have been reset.");
            }
        });

        btnNewButton_2_1_1.setBounds(263, 129, 102, 21);
        taxCalculatorPanel.add(btnNewButton_2_1_1);

        JButton btnNewButton_3 = new JButton("Calculate Tax");
        btnNewButton_3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                calculateTax();
            }
        });
        btnNewButton_3.setBounds(152, 153, 96, 21);
        taxCalculatorPanel.add(btnNewButton_3);}
        else {

        JPanel taxCalculatorPanel1 = new JPanel();
        tabbedPane.addTab("Tax Calculator", null, taxCalculatorPanel1, null);
        taxCalculatorPanel1.setLayout(null);

        JLabel lblNewLabel_1_1 = new JLabel("Monthly Salary");
        lblNewLabel_1_1.setBounds(10, 25, 74, 13);
        taxCalculatorPanel1.add(lblNewLabel_1_1);

     // Create the monthlysalary1 JTextField and set its properties
        monthlysalary1 = new JTextField();
        monthlysalary1.setColumns(10);
        monthlysalary1.setBounds(10, 42, 96, 19);
        taxCalculatorPanel1.add(monthlysalary1);

        // Add ActionListener to calculate annual salary based on monthly salary
        monthlysalary1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    // Get the value from monthlysalary1 field
                    double monthlySalaryValue = Double.parseDouble(monthlysalary1.getText());
                    
                    // Calculate annual salary (monthly salary * 12)
                    double annualSalaryValue = monthlySalaryValue * 12;
                    
                    // Update the annualsalary1 field with the calculated value
                    annualsalary1.setText(String.valueOf(annualSalaryValue));
                } catch (NumberFormatException ex) {
                    // Handle the case where the user enters non-numeric input
                    JOptionPane.showMessageDialog(null, "Please enter a valid numeric value for Monthly Salary.");
                }
            }
        });


        JLabel lblNewLabel_2_1 = new JLabel("Bonus");
        lblNewLabel_2_1.setBounds(270, 25, 45, 13);
        taxCalculatorPanel1.add(lblNewLabel_2_1);

        bonus1 = new JTextField();
        bonus1.setColumns(10);
        bonus1.setBounds(270, 42, 96, 19);
        taxCalculatorPanel1.add(bonus1);

        JLabel lblNewLabel_3_1 = new JLabel("Annual Salary");
        lblNewLabel_3_1.setBounds(10, 71, 74, 13);
        taxCalculatorPanel1.add(lblNewLabel_3_1);

        annualsalary1 = new JTextField();
        annualsalary1.setColumns(10);
        annualsalary1.setBounds(10, 85, 96, 19);
        taxCalculatorPanel1.add(annualsalary1);

        JLabel lblNewLabel_5_1 = new JLabel("CIT");
        lblNewLabel_5_1.setBounds(10, 114, 45, 13);
        taxCalculatorPanel1.add(lblNewLabel_5_1);

        cit1 = new JTextField();
        cit1.setColumns(10);
        cit1.setBounds(10, 126, 96, 19);
        taxCalculatorPanel1.add(cit1);

        JLabel lblNewLabel_6_1 = new JLabel("Insurance");
        lblNewLabel_6_1.setBounds(270, 71, 45, 13);
        taxCalculatorPanel1.add(lblNewLabel_6_1);

        insurance1 = new JTextField();
        insurance1.setColumns(10);
        insurance1.setBounds(270, 86, 96, 19);
        taxCalculatorPanel1.add(insurance1);

        JButton btnNewButton_2 = new JButton("History");
        btnNewButton_2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Query to fetch tax amount and date from user_data table
                String query = "SELECT date_saved, tax_amount FROM user_data";

                Connect connector = new Connect();
                Connection connection = connector.connect();
                if (connection != null) {
                    try {
                        PreparedStatement statement = connection.prepareStatement(query);

                        ResultSet resultSet = statement.executeQuery();
                        StringBuilder historyMessage = new StringBuilder();
                        while (resultSet.next()) {
                            java.sql.Date savedDate = resultSet.getDate("date_saved");
                            double taxAmount = resultSet.getDouble("tax_amount");
                            // Convert AD date to BS date
                            String bsDate = convertToBS(savedDate);
                            historyMessage.append("Date (Gregorian): ").append(savedDate).append(", Date (Bikram Sambat): ").append(bsDate).append(", Tax Amount: ").append(taxAmount).append("\n");
                        }
                        if (historyMessage.length() > 0) {
                            JOptionPane.showMessageDialog(null, "Tax History:\n" + historyMessage.toString());
                        } else {
                            JOptionPane.showMessageDialog(null, "No tax data found in the database.");
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
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to connect to the database.");
                }
            }
        });


        btnNewButton_2.setBounds(10, 215, 85, 21);
        taxCalculatorPanel1.add(btnNewButton_2);

        JButton btnNewButton_2_1 = new JButton("Reset Values");
        btnNewButton_2_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Reset text fields
                monthlysalary1.setText("");
                bonus1.setText("");
                annualsalary1.setText("");
                cit1.setText("");
                insurance1.setText("");

                // Display a message indicating that values have been reset
                JOptionPane.showMessageDialog(null, "Values have been reset.");
            }
        });

        btnNewButton_2_1.setBounds(264, 125, 102, 21);
        taxCalculatorPanel1.add(btnNewButton_2_1);

        JButton Refill = new JButton("Refill Values");
        Refill.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                refillValues();
            }
        });
        Refill.setBounds(346, 215, 96, 21);
        taxCalculatorPanel1.add(Refill);

        JButton btnNewButton_3_1 = new JButton("Calculate Tax");
        btnNewButton_3_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                calculateTax1();
            }
        });
        btnNewButton_3_1.setBounds(146, 144, 96, 21);
        taxCalculatorPanel1.add(btnNewButton_3_1);

        JPanel profilePanel = new JPanel();
        tabbedPane.addTab("User Profile", null, profilePanel, null);
        profilePanel.setLayout(null);

        fund.setModel(new DefaultComboBoxModel<>(new String[]{"EPF", "SSF"}));
        fund.setBounds(195, 22, 90, 21);
        profilePanel.add(fund);

        gender.setModel(new DefaultComboBoxModel<>(new String[]{"Male", "Female"}));
        gender.setBounds(195, 61, 90, 21);
        profilePanel.add(gender);

        maritalstatus.setModel(new DefaultComboBoxModel<>(new String[]{"Married", "Unmarried"}));
        maritalstatus.setBounds(195, 96, 90, 21);
        profilePanel.add(maritalstatus);

        disability.setModel(new DefaultComboBoxModel<>(new String[]{"Yes", "No"}));
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
        profilePanel.add(btnSave);}

        JButton btnNewButton = new JButton("Login");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Login.NewScreen();
            }
        });
        btnNewButton.setBounds(5, 291, 85, 21);
        contentPane.add(btnNewButton);

        JButton btnNewButton_1 = new JButton("SignIn");
        btnNewButton_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Signin.NewScreen();
            }
        });
        btnNewButton_1.setBounds(388, 291, 85, 21);
        contentPane.add(btnNewButton_1);
    }

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

    private NewsItem[] getLatestNews() {
        List<NewsItem> newsList = new ArrayList<>();
        Connect connector = new Connect();
        Connection connection = connector.connect();
        if (connection != null) {
            try {
                String query = "SELECT title, content FROM news ORDER BY date_published DESC LIMIT 5";
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
            return title;
        }
    }

    private void calculateTax() {
        try {
            // Get the values from text fields
            double monthlySalary = Double.parseDouble(monthlysalary.getText());
            double bonus = Double.parseDouble(Bonus.getText());
            double annualSalary = monthlySalary * 12;
            double totalTaxableAmount = annualSalary + bonus;
            double taxAmount = 0;
            boolean isMarried = marriedcheck.isSelected();
            boolean isFemale = chckbxFemale.isSelected();
            boolean isDisable = chckbxDisable.isSelected();
            // Check if EPF is checked
            boolean isEPFChecked = chckbxEpf.isSelected();
            double epfDeduction = 0; // Initialize EPF deduction
            
            if (isEPFChecked) {
                epfDeduction = totalTaxableAmount * 0.1; // Deduct 10% for EPF
                totalTaxableAmount -= epfDeduction;
            }

            // Calculate CIT limit based on EPF deduction
            double citLimit = isEPFChecked ? 300000 - (totalTaxableAmount * 0.1) : 300000;

            // Get the CIT value from the text field
            double citAmount = Double.parseDouble(cittxt.getText());

            // Check if CIT exceeds the limit
            if (citAmount > citLimit) {
                totalTaxableAmount += (citAmount - citLimit); 
                citAmount = citLimit; // Adjust CIT to meet the limit
            }

            // Check if the sum of EPF deduction and CIT is one-third of the annual salary plus bonus
            double oneThirdAmount = (annualSalary + bonus) / 3;
            if (epfDeduction + citAmount > oneThirdAmount) {
                // Adjust CIT to meet the one-third limit
                citAmount = oneThirdAmount - epfDeduction;
            }

            // Deduct CIT from the remaining taxable amount
            totalTaxableAmount -= citAmount;

            // Get the insurance amount from the text field
            double insuranceAmount = Double.parseDouble(insurancetxt.getText());

            // Check if insurance amount exceeds 40,000 and deduct if necessary
            if (insuranceAmount > 40000) {
                totalTaxableAmount += insuranceAmount - 40000;
                insuranceAmount = 40000; // Set insurance to the maximum limit
            }

            // Deduct insurance from the remaining taxable amount
            totalTaxableAmount -= insuranceAmount;

            // Calculate tax based on tax brackets
            if(isDisable && isMarried) {
            	if (totalTaxableAmount <= 600000) {
                    taxAmount = (totalTaxableAmount-(totalTaxableAmount * 0.5))* 0.01; // 1% tax
                } else if (totalTaxableAmount > 600000 && totalTaxableAmount <= 800000) {
                    taxAmount = ((totalTaxableAmount-(totalTaxableAmount * 0.5))* 0.01) + ((totalTaxableAmount - 600000) * 0.1); // 1% up to 600k, 10% beyond 600k
                } else if (totalTaxableAmount > 800000 && totalTaxableAmount <= 1100000) {
                    taxAmount = ((totalTaxableAmount-(totalTaxableAmount * 0.5)) * 0.01) + (200000 * 0.1) + ((totalTaxableAmount - 800000) * 0.2); // 1% up to 600k, 10% from 600k to 800k, 20% beyond 800k
                } else if (totalTaxableAmount > 1100000 && totalTaxableAmount <= 2000000) {
                    taxAmount = ((totalTaxableAmount-(totalTaxableAmount * 0.5))* 0.01) + (200000 * 0.1) + (300000 * 0.2) + ((totalTaxableAmount - 1100000) * 0.3); // 1% up to 600k, 10% from 600k to 800k, 20% from 800k to 1.1M, 30% beyond 1.1M
                } else if (totalTaxableAmount > 2000000 && totalTaxableAmount <= 5000000) {
                    taxAmount = ((totalTaxableAmount-(totalTaxableAmount * 0.5))* 0.01) + (200000 * 0.1) + (300000 * 0.2) + (900000 * 0.3) + ((totalTaxableAmount - 2000000) * 0.36); // 1% up to 600k, 10% from 600k to 800k, 20% from 800k to 1.1M, 30% from 1.1M to 2M, 36% beyond 2M
                } else if (totalTaxableAmount > 5000000) {
                    taxAmount = ((totalTaxableAmount-(totalTaxableAmount * 0.5))* 0.01) + (200000 * 0.1) + (300000 * 0.2) + (900000 * 0.3) + (3000000 * 0.36) + ((totalTaxableAmount - 5000000) * 0.39); // 1% up to 600k, 10% from 600k to 800k, 20% from 800k to 1.1M, 30% from 1.1M to 2M, 36% from 2M to 5M, 39% beyond 5M
                }
            }
            else if(isDisable) {
            	if (totalTaxableAmount <= 500000) {
                    taxAmount = (totalTaxableAmount-(totalTaxableAmount * 0.5))*0.01; // 1% tax
                } else if (totalTaxableAmount > 500000 && totalTaxableAmount <= 700000) {
                    taxAmount = ((totalTaxableAmount-(totalTaxableAmount * 0.5)) * 0.01) + ((totalTaxableAmount - 500000) * 0.1); // 1% up to 500k, 10% beyond 500k
                } else if (totalTaxableAmount > 700000 && totalTaxableAmount <= 1000000) {
                    taxAmount = ((totalTaxableAmount-(totalTaxableAmount * 0.5)) * 0.01) + (200000 * 0.1) + ((totalTaxableAmount - 700000) * 0.2); // 1% up to 500k, 10% from 500k to 700k, 20% beyond 700k
                } else if (totalTaxableAmount > 1000000 && totalTaxableAmount <= 2000000) {
                    taxAmount = ((totalTaxableAmount-(totalTaxableAmount * 0.5)) * 0.01) + (200000 * 0.1) + (300000 * 0.2) + ((totalTaxableAmount - 1000000) * 0.3); // 1% up to 500k, 10% from 500k to 700k, 20% from 700k to 1M, 30% beyond 1M
                } else if (totalTaxableAmount > 2000000 && totalTaxableAmount <= 5000000) {
                    taxAmount = ((totalTaxableAmount-(totalTaxableAmount * 0.5)) * 0.01) + (200000 * 0.1) + (300000 * 0.2) + (1000000 * 0.3) + ((totalTaxableAmount - 2000000) * 0.36); // 1% up to 500k, 10% from 500k to 700k, 20% from 700k to 1M, 30% from 1M to 2M, 36% beyond 2M
                } else if (totalTaxableAmount > 5000000) {
                    taxAmount = ((totalTaxableAmount-(totalTaxableAmount * 0.5)) * 0.01) + (200000 * 0.1) + (300000 * 0.2) + (1000000 * 0.3) + (3000000 * 0.36) + ((totalTaxableAmount - 5000000) * 0.39); // 1% up to 500k, 10% from 500k to 700k, 20% from 700k to 1M, 30% from 1M to 2M, 36% from 2M to 5M, 39% beyond 5M
                }
            }
            else if(isFemale && !isMarried) {
            	if (totalTaxableAmount <= 500000) {
                    taxAmount = (totalTaxableAmount-(totalTaxableAmount * 0.1))*0.01; // 1% tax
                } else if (totalTaxableAmount > 500000 && totalTaxableAmount <= 700000) {
                    taxAmount = ((totalTaxableAmount-(totalTaxableAmount * 0.1)) * 0.01) + ((totalTaxableAmount - 500000) * 0.1); // 1% up to 500k, 10% beyond 500k
                } else if (totalTaxableAmount > 700000 && totalTaxableAmount <= 1000000) {
                    taxAmount = ((totalTaxableAmount-(totalTaxableAmount * 0.1)) * 0.01) + (200000 * 0.1) + ((totalTaxableAmount - 700000) * 0.2); // 1% up to 500k, 10% from 500k to 700k, 20% beyond 700k
                } else if (totalTaxableAmount > 1000000 && totalTaxableAmount <= 2000000) {
                    taxAmount = ((totalTaxableAmount-(totalTaxableAmount * 0.1)) * 0.01) + (200000 * 0.1) + (300000 * 0.2) + ((totalTaxableAmount - 1000000) * 0.3); // 1% up to 500k, 10% from 500k to 700k, 20% from 700k to 1M, 30% beyond 1M
                } else if (totalTaxableAmount > 2000000 && totalTaxableAmount <= 5000000) {
                    taxAmount = ((totalTaxableAmount-(totalTaxableAmount * 0.1)) * 0.01) + (200000 * 0.1) + (300000 * 0.2) + (1000000 * 0.3) + ((totalTaxableAmount - 2000000) * 0.36); // 1% up to 500k, 10% from 500k to 700k, 20% from 700k to 1M, 30% from 1M to 2M, 36% beyond 2M
                } else if (totalTaxableAmount > 5000000) {
                    taxAmount = ((totalTaxableAmount-(totalTaxableAmount * 0.1)) * 0.01) + (200000 * 0.1) + (300000 * 0.2) + (1000000 * 0.3) + (3000000 * 0.36) + ((totalTaxableAmount - 5000000) * 0.39); // 1% up to 500k, 10% from 500k to 700k, 20% from 700k to 1M, 30% from 1M to 2M, 36% from 2M to 5M, 39% beyond 5M
                }
            }
            
            else if(isMarried) {
            	if (totalTaxableAmount <= 600000) {
                    taxAmount = totalTaxableAmount * 0.01; // 1% tax
                } else if (totalTaxableAmount > 600000 && totalTaxableAmount <= 800000) {
                    taxAmount = (600000 * 0.01) + ((totalTaxableAmount - 600000) * 0.1); // 1% up to 600k, 10% beyond 600k
                } else if (totalTaxableAmount > 800000 && totalTaxableAmount <= 1100000) {
                    taxAmount = (600000 * 0.01) + (200000 * 0.1) + ((totalTaxableAmount - 800000) * 0.2); // 1% up to 600k, 10% from 600k to 800k, 20% beyond 800k
                } else if (totalTaxableAmount > 1100000 && totalTaxableAmount <= 2000000) {
                    taxAmount = (600000 * 0.01) + (200000 * 0.1) + (300000 * 0.2) + ((totalTaxableAmount - 1100000) * 0.3); // 1% up to 600k, 10% from 600k to 800k, 20% from 800k to 1.1M, 30% beyond 1.1M
                } else if (totalTaxableAmount > 2000000 && totalTaxableAmount <= 5000000) {
                    taxAmount = (600000 * 0.01) + (200000 * 0.1) + (300000 * 0.2) + (900000 * 0.3) + ((totalTaxableAmount - 2000000) * 0.36); // 1% up to 600k, 10% from 600k to 800k, 20% from 800k to 1.1M, 30% from 1.1M to 2M, 36% beyond 2M
                } else if (totalTaxableAmount > 5000000) {
                    taxAmount = (600000 * 0.01) + (200000 * 0.1) + (300000 * 0.2) + (900000 * 0.3) + (3000000 * 0.36) + ((totalTaxableAmount - 5000000) * 0.39); // 1% up to 600k, 10% from 600k to 800k, 20% from 800k to 1.1M, 30% from 1.1M to 2M, 36% from 2M to 5M, 39% beyond 5M
                }
            }
            
            else {
                if (totalTaxableAmount <= 500000) {
                    taxAmount = totalTaxableAmount * 0.01; // 1% tax
                } else if (totalTaxableAmount > 500000 && totalTaxableAmount <= 700000) {
                    taxAmount = (500000 * 0.01) + ((totalTaxableAmount - 500000) * 0.1); // 1% up to 500k, 10% beyond 500k
                } else if (totalTaxableAmount > 700000 && totalTaxableAmount <= 1000000) {
                    taxAmount = (500000 * 0.01) + (200000 * 0.1) + ((totalTaxableAmount - 700000) * 0.2); // 1% up to 500k, 10% from 500k to 700k, 20% beyond 700k
                } else if (totalTaxableAmount > 1000000 && totalTaxableAmount <= 2000000) {
                    taxAmount = (500000 * 0.01) + (200000 * 0.1) + (300000 * 0.2) + ((totalTaxableAmount - 1000000) * 0.3); // 1% up to 500k, 10% from 500k to 700k, 20% from 700k to 1M, 30% beyond 1M
                } else if (totalTaxableAmount > 2000000 && totalTaxableAmount <= 5000000) {
                    taxAmount = (500000 * 0.01) + (200000 * 0.1) + (300000 * 0.2) + (1000000 * 0.3) + ((totalTaxableAmount - 2000000) * 0.36); // 1% up to 500k, 10% from 500k to 700k, 20% from 700k to 1M, 30% from 1M to 2M, 36% beyond 2M
                } else if (totalTaxableAmount > 5000000) {
                    taxAmount = (500000 * 0.01) + (200000 * 0.1) + (300000 * 0.2) + (1000000 * 0.3) + (3000000 * 0.36) + ((totalTaxableAmount - 5000000) * 0.39); // 1% up to 500k, 10% from 500k to 700k, 20% from 700k to 1M, 30% from 1M to 2M, 36% from 2M to 5M, 39% beyond 5M
                }}
            double netIncome = totalTaxableAmount - taxAmount;
            String message = "Total Taxable Amount: " + totalTaxableAmount + "\nTax Amount: " + taxAmount + "\nNet Income (After Tax): " + netIncome;
            JOptionPane.showMessageDialog(this, message);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter valid numeric values.");
        }
    }



    private void calculateTax1() {
        try {
            // Get the values from text fields
            double monthlySalary = Double.parseDouble(monthlysalary1.getText());
            double bonus = Double.parseDouble(bonus1.getText());
            double annualSalary = monthlySalary * 12;
            double totalTaxableAmount = annualSalary + bonus;
            double epfDeduction = 0;
            int isMarried = 0;
            int isMale = 0;
            int isDisable = 0;
            int isEPF = 0;

            Connect connector = new Connect();
            Connection connection = connector.connect();
            if (connection != null) {
                try {
                    String query = "SELECT isMarried, isMale, isDisable, isEPF FROM userprofile WHERE userid = ?";
                    PreparedStatement statement = connection.prepareStatement(query);
                    statement.setInt(1, userId);  // Assuming userId is the ID of the user whose profile you want to fetch

                    ResultSet resultSet = statement.executeQuery();
                    if (resultSet.next()) {
                        isMarried = resultSet.getInt("isMarried");
                        isMale = resultSet.getInt("isMale");
                        isDisable = resultSet.getInt("isDisable");
                        isEPF = resultSet.getInt("isEPF");
                        
                        
                        
                    } else {
                    	JOptionPane.showMessageDialog(this,"No user profile found for the specified user ID. Please save User Profile First." );
                    }

                    resultSet.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                } finally {
                    try {
                        connection.close();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            } else {
                System.out.println("Failed to connect to the database.");
            }

            double taxAmount = 0;
            double citLimit;
            if (isEPF==0) {
                citLimit = 300000 - (totalTaxableAmount * 0.1);
            } else {
                citLimit = 300000;
            }

            // Get the CIT value from the text field
            double citAmounttxt = Double.parseDouble(cit1.getText());
            double citAmount = citAmounttxt;

            // Check if CIT exceeds the limit
            if (citAmount > citLimit) {
                totalTaxableAmount += (citAmount - citLimit); 
                citAmount = citLimit; // Adjust CIT to meet the limit
            }

            // Check if the sum of 10% deduction and CIT is one-third of the annual salary plus bonus
            double oneThirdAmount = (annualSalary + bonus) / 3;
            if (epfDeduction + citAmount > oneThirdAmount) {
                // Adjust CIT to meet the one-third limit
                citAmount = oneThirdAmount - epfDeduction;
            }

            // Deduct CIT from the remaining taxable amount
            totalTaxableAmount -= citAmount;

            // Get the insurance amount from the text field
            double insuranceAmounttxt = Double.parseDouble(insurance1.getText());
            double insuranceAmount = insuranceAmounttxt;

            // Check if insurance amount exceeds 40,000 and deduct if necessary
            if (insuranceAmount > 40000) {
                totalTaxableAmount += insuranceAmount - 40000;
                insuranceAmount = 40000; // Set insurance to the maximum limit
            }

            // Deduct insurance from the remaining taxable amount
            totalTaxableAmount -= insuranceAmount;
            
            if(isDisable==0 && isMarried==0) {
            	if (totalTaxableAmount <= 600000) {
                    taxAmount = (totalTaxableAmount-(totalTaxableAmount * 0.5))* 0.01; // 1% tax
                } else if (totalTaxableAmount > 600000 && totalTaxableAmount <= 800000) {
                    taxAmount = ((totalTaxableAmount-(totalTaxableAmount * 0.5))* 0.01) + ((totalTaxableAmount - 600000) * 0.1); // 1% up to 600k, 10% beyond 600k
                } else if (totalTaxableAmount > 800000 && totalTaxableAmount <= 1100000) {
                    taxAmount = ((totalTaxableAmount-(totalTaxableAmount * 0.5)) * 0.01) + (200000 * 0.1) + ((totalTaxableAmount - 800000) * 0.2); // 1% up to 600k, 10% from 600k to 800k, 20% beyond 800k
                } else if (totalTaxableAmount > 1100000 && totalTaxableAmount <= 2000000) {
                    taxAmount = ((totalTaxableAmount-(totalTaxableAmount * 0.5))* 0.01) + (200000 * 0.1) + (300000 * 0.2) + ((totalTaxableAmount - 1100000) * 0.3); // 1% up to 600k, 10% from 600k to 800k, 20% from 800k to 1.1M, 30% beyond 1.1M
                } else if (totalTaxableAmount > 2000000 && totalTaxableAmount <= 5000000) {
                    taxAmount = ((totalTaxableAmount-(totalTaxableAmount * 0.5))* 0.01) + (200000 * 0.1) + (300000 * 0.2) + (900000 * 0.3) + ((totalTaxableAmount - 2000000) * 0.36); // 1% up to 600k, 10% from 600k to 800k, 20% from 800k to 1.1M, 30% from 1.1M to 2M, 36% beyond 2M
                } else if (totalTaxableAmount > 5000000) {
                    taxAmount = ((totalTaxableAmount-(totalTaxableAmount * 0.5))* 0.01) + (200000 * 0.1) + (300000 * 0.2) + (900000 * 0.3) + (3000000 * 0.36) + ((totalTaxableAmount - 5000000) * 0.39); // 1% up to 600k, 10% from 600k to 800k, 20% from 800k to 1.1M, 30% from 1.1M to 2M, 36% from 2M to 5M, 39% beyond 5M
                }
            }
            else if(isDisable==0) {
            	if (totalTaxableAmount <= 500000) {
                    taxAmount = (totalTaxableAmount-(totalTaxableAmount * 0.5))*0.01; // 1% tax
                } else if (totalTaxableAmount > 500000 && totalTaxableAmount <= 700000) {
                    taxAmount = ((totalTaxableAmount-(totalTaxableAmount * 0.5)) * 0.01) + ((totalTaxableAmount - 500000) * 0.1); // 1% up to 500k, 10% beyond 500k
                } else if (totalTaxableAmount > 700000 && totalTaxableAmount <= 1000000) {
                    taxAmount = ((totalTaxableAmount-(totalTaxableAmount * 0.5)) * 0.01) + (200000 * 0.1) + ((totalTaxableAmount - 700000) * 0.2); // 1% up to 500k, 10% from 500k to 700k, 20% beyond 700k
                } else if (totalTaxableAmount > 1000000 && totalTaxableAmount <= 2000000) {
                    taxAmount = ((totalTaxableAmount-(totalTaxableAmount * 0.5)) * 0.01) + (200000 * 0.1) + (300000 * 0.2) + ((totalTaxableAmount - 1000000) * 0.3); // 1% up to 500k, 10% from 500k to 700k, 20% from 700k to 1M, 30% beyond 1M
                } else if (totalTaxableAmount > 2000000 && totalTaxableAmount <= 5000000) {
                    taxAmount = ((totalTaxableAmount-(totalTaxableAmount * 0.5)) * 0.01) + (200000 * 0.1) + (300000 * 0.2) + (1000000 * 0.3) + ((totalTaxableAmount - 2000000) * 0.36); // 1% up to 500k, 10% from 500k to 700k, 20% from 700k to 1M, 30% from 1M to 2M, 36% beyond 2M
                } else if (totalTaxableAmount > 5000000) {
                    taxAmount = ((totalTaxableAmount-(totalTaxableAmount * 0.5)) * 0.01) + (200000 * 0.1) + (300000 * 0.2) + (1000000 * 0.3) + (3000000 * 0.36) + ((totalTaxableAmount - 5000000) * 0.39); // 1% up to 500k, 10% from 500k to 700k, 20% from 700k to 1M, 30% from 1M to 2M, 36% from 2M to 5M, 39% beyond 5M
                }
            }
            else if(isMale==1 && isMarried==1) {
            	if (totalTaxableAmount <= 500000) {
                    taxAmount = (totalTaxableAmount-(totalTaxableAmount * 0.1))*0.01; // 1% tax
                } else if (totalTaxableAmount > 500000 && totalTaxableAmount <= 700000) {
                    taxAmount = ((totalTaxableAmount-(totalTaxableAmount * 0.1)) * 0.01) + ((totalTaxableAmount - 500000) * 0.1); // 1% up to 500k, 10% beyond 500k
                } else if (totalTaxableAmount > 700000 && totalTaxableAmount <= 1000000) {
                    taxAmount = ((totalTaxableAmount-(totalTaxableAmount * 0.1)) * 0.01) + (200000 * 0.1) + ((totalTaxableAmount - 700000) * 0.2); // 1% up to 500k, 10% from 500k to 700k, 20% beyond 700k
                } else if (totalTaxableAmount > 1000000 && totalTaxableAmount <= 2000000) {
                    taxAmount = ((totalTaxableAmount-(totalTaxableAmount * 0.1)) * 0.01) + (200000 * 0.1) + (300000 * 0.2) + ((totalTaxableAmount - 1000000) * 0.3); // 1% up to 500k, 10% from 500k to 700k, 20% from 700k to 1M, 30% beyond 1M
                } else if (totalTaxableAmount > 2000000 && totalTaxableAmount <= 5000000) {
                    taxAmount = ((totalTaxableAmount-(totalTaxableAmount * 0.1)) * 0.01) + (200000 * 0.1) + (300000 * 0.2) + (1000000 * 0.3) + ((totalTaxableAmount - 2000000) * 0.36); // 1% up to 500k, 10% from 500k to 700k, 20% from 700k to 1M, 30% from 1M to 2M, 36% beyond 2M
                } else if (totalTaxableAmount > 5000000) {
                    taxAmount = ((totalTaxableAmount-(totalTaxableAmount * 0.1)) * 0.01) + (200000 * 0.1) + (300000 * 0.2) + (1000000 * 0.3) + (3000000 * 0.36) + ((totalTaxableAmount - 5000000) * 0.39); // 1% up to 500k, 10% from 500k to 700k, 20% from 700k to 1M, 30% from 1M to 2M, 36% from 2M to 5M, 39% beyond 5M
                }
            }
            
            else if(isMarried==1) {
            	if (totalTaxableAmount <= 600000) {
                    taxAmount = totalTaxableAmount * 0.01; // 1% tax
                } else if (totalTaxableAmount > 600000 && totalTaxableAmount <= 800000) {
                    taxAmount = (600000 * 0.01) + ((totalTaxableAmount - 600000) * 0.1); // 1% up to 600k, 10% beyond 600k
                } else if (totalTaxableAmount > 800000 && totalTaxableAmount <= 1100000) {
                    taxAmount = (600000 * 0.01) + (200000 * 0.1) + ((totalTaxableAmount - 800000) * 0.2); // 1% up to 600k, 10% from 600k to 800k, 20% beyond 800k
                } else if (totalTaxableAmount > 1100000 && totalTaxableAmount <= 2000000) {
                    taxAmount = (600000 * 0.01) + (200000 * 0.1) + (300000 * 0.2) + ((totalTaxableAmount - 1100000) * 0.3); // 1% up to 600k, 10% from 600k to 800k, 20% from 800k to 1.1M, 30% beyond 1.1M
                } else if (totalTaxableAmount > 2000000 && totalTaxableAmount <= 5000000) {
                    taxAmount = (600000 * 0.01) + (200000 * 0.1) + (300000 * 0.2) + (900000 * 0.3) + ((totalTaxableAmount - 2000000) * 0.36); // 1% up to 600k, 10% from 600k to 800k, 20% from 800k to 1.1M, 30% from 1.1M to 2M, 36% beyond 2M
                } else if (totalTaxableAmount > 5000000) {
                    taxAmount = (600000 * 0.01) + (200000 * 0.1) + (300000 * 0.2) + (900000 * 0.3) + (3000000 * 0.36) + ((totalTaxableAmount - 5000000) * 0.39); // 1% up to 600k, 10% from 600k to 800k, 20% from 800k to 1.1M, 30% from 1.1M to 2M, 36% from 2M to 5M, 39% beyond 5M
                }
            }
            
            else {
                if (totalTaxableAmount <= 500000) {
                    taxAmount = totalTaxableAmount * 0.01; // 1% tax
                } else if (totalTaxableAmount > 500000 && totalTaxableAmount <= 700000) {
                    taxAmount = (500000 * 0.01) + ((totalTaxableAmount - 500000) * 0.1); // 1% up to 500k, 10% beyond 500k
                } else if (totalTaxableAmount > 700000 && totalTaxableAmount <= 1000000) {
                    taxAmount = (500000 * 0.01) + (200000 * 0.1) + ((totalTaxableAmount - 700000) * 0.2); // 1% up to 500k, 10% from 500k to 700k, 20% beyond 700k
                } else if (totalTaxableAmount > 1000000 && totalTaxableAmount <= 2000000) {
                    taxAmount = (500000 * 0.01) + (200000 * 0.1) + (300000 * 0.2) + ((totalTaxableAmount - 1000000) * 0.3); // 1% up to 500k, 10% from 500k to 700k, 20% from 700k to 1M, 30% beyond 1M
                } else if (totalTaxableAmount > 2000000 && totalTaxableAmount <= 5000000) {
                    taxAmount = (500000 * 0.01) + (200000 * 0.1) + (300000 * 0.2) + (1000000 * 0.3) + ((totalTaxableAmount - 2000000) * 0.36); // 1% up to 500k, 10% from 500k to 700k, 20% from 700k to 1M, 30% from 1M to 2M, 36% beyond 2M
                } else if (totalTaxableAmount > 5000000) {
                    taxAmount = (500000 * 0.01) + (200000 * 0.1) + (300000 * 0.2) + (1000000 * 0.3) + (3000000 * 0.36) + ((totalTaxableAmount - 5000000) * 0.39); // 1% up to 500k, 10% from 500k to 700k, 20% from 700k to 1M, 30% from 1M to 2M, 36% from 2M to 5M, 39% beyond 5M
                }}
            double netIncome = totalTaxableAmount - taxAmount;
            String message = "Total Taxable Amount: " + totalTaxableAmount + "\nTax Amount: " + taxAmount + "\nNet Income (After Tax): " + netIncome;
            JOptionPane.showMessageDialog(this, message);
            saveFinancialData(userId, monthlySalary, bonus, annualSalary, insuranceAmount, citAmount, taxAmount);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter valid numeric values.");
        }
    }
private void saveFinancialData(int userId, double monthlySalary, double bonus, double annualSalary, double insuranceAmount, double citAmount, double taxAmount) {
    Connect connector = new Connect();
    Connection connection = connector.connect();
    if (connection != null) {
        try {
            String insertQuery = "INSERT INTO user_data (userid, monthly_salary, bonus, annual_salary, insurance, cit, tax_amount, date_saved) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
            insertStatement.setInt(1, userId); // Assuming userId is the ID of the user whose financial data is being saved
            insertStatement.setDouble(2, monthlySalary);
            insertStatement.setDouble(3, bonus);
            insertStatement.setDouble(4, annualSalary);
            insertStatement.setDouble(5, insuranceAmount);
            insertStatement.setDouble(6, citAmount);
            insertStatement.setDouble(7, taxAmount);
            insertStatement.setDate(8, java.sql.Date.valueOf(java.time.LocalDate.now())); // Set the current date as the date_saved

            int rowsAffected = insertStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Financial data saved successfully.");
            } else {
                System.out.println("Failed to save financial data.");
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
    } else {
        System.out.println("Failed to connect to the database.");
    }
}   
    private void saveOrUpdateUserProfile() {
        int isEPFValue = fund.getSelectedIndex();
        int isMaleValue = gender.getSelectedIndex();
        int isMarriedValue = maritalstatus.getSelectedIndex();
        int isDisableValue = disability.getSelectedIndex();

        Connect connector = new Connect();
        Connection connection = connector.connect();
        if (connection != null) {
            try {
                String query = "INSERT INTO userprofile (userid, isEPF, isMale, isMarried, isDisable, date) VALUES (?, ?, ?, ?, ?, CURRENT_DATE)";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setInt(1, userId);
                statement.setInt(2, isEPFValue);
                statement.setInt(3, isMaleValue);
                statement.setInt(4, isMarriedValue);
                statement.setInt(5, isDisableValue);
                statement.executeUpdate();
                JOptionPane.showMessageDialog(this, "User profile saved successfully.");
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

    private String convertToBS(java.sql.Date adDate) {
        int year = adDate.getYear() + 1900; // Get the year (adjusting for Java's date format)
        int month = adDate.getMonth() + 1; // Get the month (Java's month starts from 0)
        int day = adDate.getDate(); // Get the day of the month

        // Reversing Date
        year += 56;
        if (month <= 8) {
            month += 4;
            year++;
        } else {
            month -= 8;
        }

        if (day > 16) {
            day += 17;
        } else {
            day += 17;
            month--;
        }

        // Return the BS date in the desired format
        return year + "-" + month + "-" + day;
    }
    private void refillValues() {
        Connect connector = new Connect(); // Assuming this establishes a database connection
        Connection connection = connector.connect();
        
        if (connection != null) {
            try {
                String query = "SELECT * FROM user_data WHERE userid = ?";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setInt(1, userId); // Assuming userId is accessible in this method

                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    double monthlySalary = resultSet.getDouble("monthly_salary");
                    double bonus = resultSet.getDouble("bonus");
                    double annualSalary = resultSet.getDouble("annual_salary");
                    double insuranceAmount = resultSet.getDouble("insurance");
                    double citAmount = resultSet.getDouble("cit");

                    // Refill the text fields with fetched values
                    monthlysalary1.setText(String.valueOf(monthlySalary));
                    bonus1.setText(String.valueOf(bonus));
                    annualsalary1.setText(String.valueOf(annualSalary));
                    insurance1.setText(String.valueOf(insuranceAmount));
                    cit1.setText(String.valueOf(citAmount));
                } else {
                    JOptionPane.showMessageDialog(this, "User data not found in the database.");
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
        } else {
            JOptionPane.showMessageDialog(this, "Failed to connect to the database.");
        }
    }

}
