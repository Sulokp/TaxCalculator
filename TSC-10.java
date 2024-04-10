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