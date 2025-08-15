package com.chatapp.client;

import com.chatapp.model.Message;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
import java.util.function.Consumer;

public class ChatClient {
    private static final Logger logger = LoggerFactory.getLogger(ChatClient.class);
    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 8080;
    
    private Socket socket;
    private BufferedReader input;
    private PrintWriter output;
    private final ObjectMapper objectMapper;
    private Consumer<Message> messageHandler;
    private boolean isConnected;
    private Thread listenerThread;

    public ChatClient() {
        this.objectMapper = new ObjectMapper();
        this.isConnected = false;
    }

    public boolean connect() {
        try {
            socket = new Socket(SERVER_HOST, SERVER_PORT);
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new PrintWriter(socket.getOutputStream(), true);
            isConnected = true;
            
            startMessageListener();
            logger.info("Connected to chat server");
            return true;
        } catch (IOException e) {
            logger.error("Failed to connect to server", e);
            return false;
        }
    }

    public void disconnect() {
        isConnected = false;
        
        try {
            if (listenerThread != null) {
                listenerThread.interrupt();
            }
            if (input != null) input.close();
            if (output != null) output.close();
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        } catch (IOException e) {
            logger.error("Error closing connection", e);
        }
        
        logger.info("Disconnected from chat server");
    }

    private void startMessageListener() {
        listenerThread = new Thread(() -> {
            try {
                String messageJson;
                while (isConnected && (messageJson = input.readLine()) != null) {
                    handleIncomingMessage(messageJson);
                }
            } catch (IOException e) {
                if (isConnected) {
                    logger.error("Error in message listener", e);
                }
            }
        });
        listenerThread.setDaemon(true);
        listenerThread.start();
    }

    private void handleIncomingMessage(String messageJson) {
        try {
            Message message = objectMapper.readValue(messageJson, Message.class);
            if (messageHandler != null) {
                messageHandler.accept(message);
            }
        } catch (Exception e) {
            logger.error("Error parsing incoming message", e);
        }
    }

    public void sendMessage(Message message) {
        if (!isConnected) {
            logger.warn("Cannot send message: not connected to server");
            return;
        }

        try {
            String messageJson = objectMapper.writeValueAsString(message);
            output.println(messageJson);
            logger.debug("Message sent: {}", message.getContent());
        } catch (Exception e) {
            logger.error("Error sending message", e);
        }
    }

    public void sendTextMessage(int senderId, int chatId, String content) {
        Message message = new Message(senderId, chatId, content, Message.MessageType.TEXT);
        sendMessage(message);
    }

    public void setMessageHandler(Consumer<Message> messageHandler) {
        this.messageHandler = messageHandler;
    }

    public boolean isConnected() {
        return isConnected && socket != null && !socket.isClosed();
    }

    public String getServerHost() {
        return SERVER_HOST;
    }

    public int getServerPort() {
        return SERVER_PORT;
    }
}
