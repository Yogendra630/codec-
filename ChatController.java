package com.chatapp.controller;

import com.chatapp.client.ChatClient;
import com.chatapp.model.Chat;
import com.chatapp.model.Message;
import com.chatapp.model.User;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class ChatController {
    private static final Logger logger = LoggerFactory.getLogger(ChatController.class);
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
    
    @FXML private ListView<Chat> chatListView;
    @FXML private ListView<Message> messageListView;
    @FXML private TextArea messageInput;
    @FXML private Button sendButton;
    @FXML private Button newChatButton;
    @FXML private Label currentChatLabel;
    @FXML private VBox chatArea;
    
    private User currentUser;
    private Chat currentChat;
    private Stage stage;
    private ChatClient chatClient;
    private final ObservableList<Chat> chats;
    private final ObservableList<Message> messages;

    public ChatController() {
        this.chats = FXCollections.observableArrayList();
        this.messages = FXCollections.observableArrayList();
    }

    @FXML
    public void initialize() {
        setupUI();
        setupEventHandlers();
        loadSampleData();
    }

    private void setupUI() {
        chatListView.setItems(chats);
        messageListView.setItems(messages);
        
        // Set cell factories for custom display
        chatListView.setCellFactory(param -> new ListCell<Chat>() {
            @Override
            protected void updateItem(Chat chat, boolean empty) {
                super.updateItem(chat, empty);
                if (empty || chat == null) {
                    setText(null);
                } else {
                    setText(chat.getName());
                }
            }
        });
        
        messageListView.setCellFactory(param -> new ListCell<Message>() {
            @Override
            protected void updateItem(Message message, boolean empty) {
                super.updateItem(message, empty);
                if (empty || message == null) {
                    setText(null);
                } else {
                    String time = message.getTimestamp().format(TIME_FORMATTER);
                    String sender = message.getSenderUsername() != null ? 
                        message.getSenderUsername() : "Unknown";
                    setText(String.format("[%s] %s: %s", time, sender, message.getContent()));
                }
            }
        });
    }

    private void setupEventHandlers() {
        sendButton.setOnAction(e -> sendMessage());
        newChatButton.setOnAction(e -> createNewChat());
        
        // Enter key to send message
        messageInput.setOnKeyPressed(e -> {
            if (e.getCode().toString().equals("ENTER") && !e.isShiftDown()) {
                e.consume();
                sendMessage();
            }
        });
        
        // Chat selection
        chatListView.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> {
                if (newValue != null) {
                    selectChat(newValue);
                }
            }
        );
    }

    private void loadSampleData() {
        // Add sample chats
        Chat privateChat = new Chat("John Doe", Chat.ChatType.PRIVATE, 1);
        privateChat.setId(1);
        chats.add(privateChat);
        
        Chat groupChat = new Chat("Team Chat", Chat.ChatType.GROUP, 1);
        groupChat.setId(2);
        chats.add(groupChat);
        
        // Select first chat by default
        if (!chats.isEmpty()) {
            chatListView.getSelectionModel().select(0);
        }
    }

    private void createNewChat() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("New Chat");
        dialog.setHeaderText("Create a new chat");
        dialog.setContentText("Chat name:");
        
        dialog.showAndWait().ifPresent(chatName -> {
            if (!chatName.trim().isEmpty()) {
                Chat newChat = new Chat(chatName.trim(), Chat.ChatType.GROUP, currentUser.getId());
                newChat.setId(chats.size() + 1);
                chats.add(newChat);
                chatListView.getSelectionModel().select(newChat);
            }
        });
    }

    private void selectChat(Chat chat) {
        currentChat = chat;
        currentChatLabel.setText(chat.getName());
        messages.clear();
        
        // TODO: Load messages for this chat from database
        loadChatMessages(chat.getId());
    }

    private void loadChatMessages(int chatId) {
        // TODO: Implement loading messages from database
        // For now, add some sample messages
        if (chatId == 1) {
            messages.add(new Message(1, chatId, "Hello! How are you?", Message.MessageType.TEXT));
            messages.add(new Message(2, chatId, "I'm doing great, thanks!", Message.MessageType.TEXT));
        } else if (chatId == 2) {
            messages.add(new Message(1, chatId, "Welcome to the team chat!", Message.MessageType.TEXT));
            messages.add(new Message(3, chatId, "Thanks! Happy to be here.", Message.MessageType.TEXT));
        }
    }

    private void sendMessage() {
        String content = messageInput.getText().trim();
        if (content.isEmpty() || currentChat == null) {
            return;
        }
        
        Message message = new Message(currentUser.getId(), currentChat.getId(), content, Message.MessageType.TEXT);
        message.setSenderUsername(currentUser.getUsername());
        message.setTimestamp(java.time.LocalDateTime.now());
        
        // Add to local message list
        messages.add(message);
        
        // Send to server
        if (chatClient != null && chatClient.isConnected()) {
            chatClient.sendTextMessage(currentUser.getId(), currentChat.getId(), content);
        }
        
        // Clear input
        messageInput.clear();
        
        // Scroll to bottom
        messageListView.scrollTo(messages.size() - 1);
    }

    public void setUser(User user) {
        this.currentUser = user;
        connectToServer();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private void connectToServer() {
        chatClient = new ChatClient();
        chatClient.setMessageHandler(this::handleIncomingMessage);
        
        if (chatClient.connect()) {
            logger.info("Connected to chat server");
        } else {
            logger.error("Failed to connect to chat server");
            showAlert("Connection Error", "Failed to connect to chat server. Please check if the server is running.");
        }
    }

    private void handleIncomingMessage(Message message) {
        Platform.runLater(() -> {
            if (currentChat != null && message.getChatId() == currentChat.getId()) {
                messages.add(message);
                messageListView.scrollTo(messages.size() - 1);
            }
        });
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void cleanup() {
        if (chatClient != null) {
            chatClient.disconnect();
        }
    }
}
