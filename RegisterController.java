package com.chatapp.controller;

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

public class RegisterController {
    private static final Logger logger = LoggerFactory.getLogger(RegisterController.class);
    
    @FXML private TextField usernameField;
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private PasswordField confirmPasswordField;
    @FXML private Button registerButton;
    @FXML private Button backButton;
    @FXML private Label statusLabel;
    
    private Stage stage;

    @FXML
    public void initialize() {
        registerButton.setOnAction(e -> handleRegister());
        backButton.setOnAction(e -> handleBack());
        
        // Enter key support
        confirmPasswordField.setOnKeyPressed(e -> {
            if (e.getCode().toString().equals("ENTER")) {
                handleRegister();
            }
        });
        
        statusLabel.setText("");
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private void handleRegister() {
        String username = usernameField.getText().trim();
        String email = emailField.getText().trim();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();
        
        // Validation
        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            showStatus("Please fill in all fields", false);
            return;
        }
        
        if (username.length() < 3) {
            showStatus("Username must be at least 3 characters", false);
            return;
        }
        
        if (!email.contains("@")) {
            showStatus("Please enter a valid email address", false);
            return;
        }
        
        if (password.length() < 6) {
            showStatus("Password must be at least 6 characters", false);
            return;
        }
        
        if (!password.equals(confirmPassword)) {
            showStatus("Passwords do not match", false);
            return;
        }
        
        // TODO: Implement actual user registration
        if (registerUser(username, email, password)) {
            showStatus("Registration successful! Please log in.", true);
            clearFields();
        } else {
            showStatus("Registration failed. Username or email may already exist.", false);
        }
    }

    private void handleBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/LoginView.fxml"));
            Parent root = loader.load();
            
            LoginController controller = loader.getController();
            controller.setStage(stage);
            
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Login - Chat Application");
            
        } catch (IOException e) {
            logger.error("Error loading login view", e);
            showStatus("Error loading login view", false);
        }
    }

    private boolean registerUser(String username, String email, String password) {
        // TODO: Implement actual database registration
        // For demo purposes, always return true
        logger.info("Registering user: {}", username);
        return true;
    }

    private void clearFields() {
        usernameField.clear();
        emailField.clear();
        passwordField.clear();
        confirmPasswordField.clear();
    }

    private void showStatus(String message, boolean isSuccess) {
        statusLabel.setText(message);
        statusLabel.setStyle(isSuccess ? "-fx-text-fill: green;" : "-fx-text-fill: red;");
        
        // Clear status after 5 seconds
        new Thread(() -> {
            try {
                Thread.sleep(5000);
                javafx.application.Platform.runLater(() -> statusLabel.setText(""));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();
    }
}
