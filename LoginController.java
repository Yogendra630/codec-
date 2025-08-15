package com.chatapp.controller;

import com.chatapp.Main;
import com.chatapp.model.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Button loginButton;
    @FXML private Button registerButton;
    @FXML private Label statusLabel;
    
    private Stage stage;

    @FXML
    public void initialize() {
        // Set up event handlers
        loginButton.setOnAction(e -> handleLogin());
        registerButton.setOnAction(e -> handleRegister());
        
        // Enter key support
        passwordField.setOnKeyPressed(e -> {
            if (e.getCode().toString().equals("ENTER")) {
                handleLogin();
            }
        });
        
        statusLabel.setText("");
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText();
        
        if (username.isEmpty() || password.isEmpty()) {
            showStatus("Please enter both username and password", false);
            return;
        }
        
        // TODO: Implement actual authentication
        // For now, just simulate successful login
        if (authenticateUser(username, password)) {
            User user = new User(username, "", ""); // Create user object
            openChatWindow(user);
        } else {
            showStatus("Invalid username or password", false);
        }
    }

    private void handleRegister() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/RegisterView.fxml"));
            Parent root = loader.load();
            
            RegisterController controller = loader.getController();
            controller.setStage(stage);
            
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Register - Chat Application");
            
        } catch (IOException e) {
            logger.error("Error loading register view", e);
            showStatus("Error loading register view", false);
        }
    }

    private boolean authenticateUser(String username, String password) {
        // TODO: Implement actual database authentication
        // For demo purposes, accept any non-empty credentials
        return !username.isEmpty() && !password.isEmpty();
    }

    private void openChatWindow(User user) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ChatView.fxml"));
            Parent root = loader.load();
            
            ChatController controller = loader.getController();
            controller.setUser(user);
            controller.setStage(stage);
            
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Chat - " + user.getUsername());
            
            logger.info("User {} logged in successfully", user.getUsername());
            
        } catch (IOException e) {
            logger.error("Error loading chat view", e);
            showStatus("Error loading chat view", false);
        }
    }

    private void showStatus(String message, boolean isSuccess) {
        statusLabel.setText(message);
        statusLabel.setStyle(isSuccess ? "-fx-text-fill: green;" : "-fx-text-fill: red;");
        
        // Clear status after 3 seconds
        new Thread(() -> {
            try {
                Thread.sleep(3000);
                javafx.application.Platform.runLater(() -> statusLabel.setText(""));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();
    }
}
