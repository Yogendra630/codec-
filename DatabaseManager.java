package com.chatapp.database;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.Properties;
import java.io.FileInputStream;
import java.io.IOException;

public class DatabaseManager {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseManager.class);
    private static final String CONFIG_FILE = "database.properties";
    private static final String DEFAULT_URL = "jdbc:mysql://localhost:3306/chatapp?useSSL=false&serverTimezone=UTC";
    private static final String DEFAULT_USER = "root";
    private static final String DEFAULT_PASSWORD = "password";
    
    private static DatabaseManager instance;
    private Connection connection;
    private String url;
    private String username;
    private String password;

    private DatabaseManager() {
        loadConfiguration();
        initializeDatabase();
    }

    public static synchronized DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }

    private void loadConfiguration() {
        Properties props = new Properties();
        try (FileInputStream fis = new FileInputStream(CONFIG_FILE)) {
            props.load(fis);
            url = props.getProperty("db.url", DEFAULT_URL);
            username = props.getProperty("db.username", DEFAULT_USER);
            password = props.getProperty("db.password", DEFAULT_PASSWORD);
        } catch (IOException e) {
            logger.warn("Could not load database configuration file, using defaults: {}", e.getMessage());
            url = DEFAULT_URL;
            username = DEFAULT_USER;
            password = DEFAULT_PASSWORD;
        }
    }

    private void initializeDatabase() {
        try {
            createDatabaseIfNotExists();
            createTables();
            logger.info("Database initialized successfully");
        } catch (SQLException e) {
            logger.error("Failed to initialize database", e);
        }
    }

    private void createDatabaseIfNotExists() throws SQLException {
        String baseUrl = url.substring(0, url.lastIndexOf("/"));
        try (Connection conn = DriverManager.getConnection(baseUrl, username, password)) {
            String sql = "CREATE DATABASE IF NOT EXISTS chatapp";
            try (Statement stmt = conn.createStatement()) {
                stmt.execute(sql);
            }
        }
    }

    private void createTables() throws SQLException {
        try (Connection conn = getConnection()) {
            // Users table
            String createUsersTable = 
                "CREATE TABLE IF NOT EXISTS users (" +
                "    id INT AUTO_INCREMENT PRIMARY KEY," +
                "    username VARCHAR(50) UNIQUE NOT NULL," +
                "    email VARCHAR(100) UNIQUE NOT NULL," +
                "    password_hash VARCHAR(255) NOT NULL," +
                "    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                "    last_login TIMESTAMP NULL," +
                "    is_online BOOLEAN DEFAULT FALSE" +
                ")";

            // Chats table
            String createChatsTable = 
                "CREATE TABLE IF NOT EXISTS chats (" +
                "    id INT AUTO_INCREMENT PRIMARY KEY," +
                "    name VARCHAR(100) NOT NULL," +
                "    type ENUM('PRIVATE', 'GROUP') NOT NULL," +
                "    creator_id INT NOT NULL," +
                "    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                "    last_message_time TIMESTAMP NULL," +
                "    last_message TEXT," +
                "    FOREIGN KEY (creator_id) REFERENCES users(id) ON DELETE CASCADE" +
                ")";

            // Chat participants table
            String createParticipantsTable = 
                "CREATE TABLE IF NOT EXISTS chat_participants (" +
                "    chat_id INT NOT NULL," +
                "    user_id INT NOT NULL," +
                "    joined_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                "    PRIMARY KEY (chat_id, user_id)," +
                "    FOREIGN KEY (chat_id) REFERENCES chats(id) ON DELETE CASCADE," +
                "    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE" +
                ")";

            // Messages table
            String createMessagesTable = 
                "CREATE TABLE IF NOT EXISTS messages (" +
                "    id INT AUTO_INCREMENT PRIMARY KEY," +
                "    sender_id INT NOT NULL," +
                "    chat_id INT NOT NULL," +
                "    content TEXT NOT NULL," +
                "    type ENUM('TEXT', 'IMAGE', 'FILE', 'SYSTEM') DEFAULT 'TEXT'," +
                "    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                "    is_read BOOLEAN DEFAULT FALSE," +
                "    FOREIGN KEY (sender_id) REFERENCES users(id) ON DELETE CASCADE," +
                "    FOREIGN KEY (chat_id) REFERENCES chats(id) ON DELETE CASCADE" +
                ")";

            try (Statement stmt = conn.createStatement()) {
                stmt.execute(createUsersTable);
                stmt.execute(createChatsTable);
                stmt.execute(createParticipantsTable);
                stmt.execute(createMessagesTable);
            }
        }
    }

    public Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(url, username, password);
        }
        return connection;
    }

    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.error("Error closing database connection", e);
            }
        }
    }

    public void shutdown() {
        closeConnection();
    }
}
