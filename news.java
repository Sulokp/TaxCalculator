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
