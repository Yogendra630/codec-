package com.chatapp;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SimpleMain extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Chat Application - Simple Demo");
        
        VBox root = new VBox(10);
        root.setStyle("-fx-padding: 20; -fx-alignment: center;");
        
        Label titleLabel = new Label("Chat Application");
        titleLabel.setStyle("-fx-font-size: 24; -fx-font-weight: bold;");
        
        Label infoLabel = new Label("This is a demonstration of the chat application.");
        infoLabel.setStyle("-fx-font-size: 14;");
        
        Label statusLabel = new Label("Status: Ready to run with full dependencies");
        statusLabel.setStyle("-fx-font-size: 12; -fx-text-fill: green;");
        
        Button runButton = new Button("Run Full Application");
        runButton.setOnAction(e -> showInstructions());
        
        root.getChildren().addAll(titleLabel, infoLabel, statusLabel, runButton);
        
        Scene scene = new Scene(root, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    private void showInstructions() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("How to Run Full Application");
        alert.setHeaderText("Setup Instructions");
        alert.setContentText(
            "To run the full chat application:\n\n" +
            "1. Install Maven (https://maven.apache.org/download.cgi)\n" +
            "2. Install MySQL and configure database.properties\n" +
            "3. Run: mvn clean compile\n" +
            "4. Start server: mvn exec:java -Dexec.mainClass=\"com.chatapp.server.ChatServer\"\n" +
            "5. Start client: mvn javafx:run\n\n" +
            "Or use the provided batch files:\n" +
            "- run-server.bat\n" +
            "- run-client.bat"
        );
        alert.showAndWait();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
