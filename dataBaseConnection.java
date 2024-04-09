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