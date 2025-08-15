package com.chatapp.server;

import com.chatapp.model.Message;
import com.chatapp.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
import java.util.List;

public class ClientHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(ClientHandler.class);
    
    private final Socket clientSocket;
    private final ChatServer server;
    private final ObjectMapper objectMapper;
    private BufferedReader input;
    private PrintWriter output;
    private User user;
    private boolean isConnected;

    public ClientHandler(Socket socket, ChatServer server) {
        this.clientSocket = socket;
        this.server = server;
        this.objectMapper = new ObjectMapper();
        this.isConnected = false;
    }

    @Override
    public void run() {
        try {
            setupStreams();
            isConnected = true;
            logger.info("Client handler started for: {}", clientSocket.getInetAddress());
            
            String inputLine;
            while (isConnected && (inputLine = input.readLine()) != null) {
                handleMessage(inputLine);
            }
        } catch (IOException e) {
            if (isConnected) {
                logger.error("Error in client handler", e);
            }
        } finally {
            disconnect();
        }
    }

    private void setupStreams() throws IOException {
        input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        output = new PrintWriter(clientSocket.getOutputStream(), true);
    }

    private void handleMessage(String messageJson) {
        try {
            Message message = objectMapper.readValue(messageJson, Message.class);
            
            switch (message.getType()) {
                case TEXT:
                    handleTextMessage(message);
                    break;
                case SYSTEM:
                    handleSystemMessage(message);
                    break;
                default:
                    logger.warn("Unsupported message type: {}", message.getType());
            }
        } catch (Exception e) {
            logger.error("Error handling message", e);
        }
    }

    private void handleTextMessage(Message message) {
        // Store message in database
        // TODO: Implement message storage
        
        // Broadcast to chat participants
        // TODO: Get chat participants and broadcast
        logger.info("Text message from {}: {}", message.getSenderId(), message.getContent());
    }

    private void handleSystemMessage(Message message) {
        // Handle system messages like login, logout, etc.
        logger.info("System message: {}", message.getContent());
    }

    public void sendMessage(String messageJson) {
        if (isConnected && output != null) {
            output.println(messageJson);
        }
    }

    public void disconnect() {
        isConnected = false;
        
        if (user != null) {
            server.removeClient(user.getId());
            logger.info("Client {} disconnected", user.getUsername());
        }
        
        try {
            if (input != null) input.close();
            if (output != null) output.close();
            if (clientSocket != null && !clientSocket.isClosed()) {
                clientSocket.close();
            }
        } catch (IOException e) {
            logger.error("Error closing client connection", e);
        }
    }

    public boolean isConnected() {
        return isConnected && !clientSocket.isClosed();
    }

    public void setUser(User user) {
        this.user = user;
        if (user != null) {
            server.addClient(user.getId(), this);
        }
    }

    public User getUser() {
        return user;
    }
}
