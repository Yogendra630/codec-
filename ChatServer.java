package com.chatapp.server;

import com.chatapp.database.DatabaseManager;
import com.chatapp.model.Message;
import com.chatapp.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ChatServer {
    private static final Logger logger = LoggerFactory.getLogger(ChatServer.class);
    private static final int PORT = 8080;
    private static final int MAX_CLIENTS = 100;
    
    private ServerSocket serverSocket;
    private final Map<Integer, ClientHandler> clients;
    private final ExecutorService threadPool;
    private final ObjectMapper objectMapper;
    private final DatabaseManager databaseManager;
    private boolean isRunning;

    public ChatServer() {
        this.clients = new ConcurrentHashMap<>();
        this.threadPool = Executors.newFixedThreadPool(MAX_CLIENTS);
        this.objectMapper = new ObjectMapper();
        this.databaseManager = DatabaseManager.getInstance();
        this.isRunning = false;
    }

    public void start() {
        try {
            serverSocket = new ServerSocket(PORT);
            isRunning = true;
            logger.info("Chat server started on port {}", PORT);
            
            while (isRunning) {
                Socket clientSocket = serverSocket.accept();
                logger.info("New client connected: {}", clientSocket.getInetAddress());
                
                ClientHandler clientHandler = new ClientHandler(clientSocket, this);
                threadPool.execute(clientHandler);
            }
        } catch (IOException e) {
            if (isRunning) {
                logger.error("Error in server main loop", e);
            }
        } finally {
            stop();
        }
    }

    public void stop() {
        isRunning = false;
        logger.info("Stopping chat server...");
        
        // Close all client connections
        clients.values().forEach(ClientHandler::disconnect);
        clients.clear();
        
        // Shutdown thread pool
        threadPool.shutdown();
        
        // Close server socket
        if (serverSocket != null && !serverSocket.isClosed()) {
            try {
                serverSocket.close();
            } catch (IOException e) {
                logger.error("Error closing server socket", e);
            }
        }
        
        // Close database connection
        databaseManager.shutdown();
        
        logger.info("Chat server stopped");
    }

    public void addClient(int userId, ClientHandler clientHandler) {
        clients.put(userId, clientHandler);
        logger.info("Client {} added. Total clients: {}", userId, clients.size());
    }

    public void removeClient(int userId) {
        clients.remove(userId);
        logger.info("Client {} removed. Total clients: {}", userId, clients.size());
    }

    public void broadcastMessage(Message message, List<Integer> recipientIds) {
        String messageJson;
        try {
            messageJson = objectMapper.writeValueAsString(message);
        } catch (Exception e) {
            logger.error("Error serializing message", e);
            return;
        }

        for (Integer userId : recipientIds) {
            ClientHandler client = clients.get(userId);
            if (client != null && client.isConnected()) {
                client.sendMessage(messageJson);
            }
        }
    }

    public void sendPrivateMessage(int recipientId, String messageJson) {
        ClientHandler client = clients.get(recipientId);
        if (client != null && client.isConnected()) {
            client.sendMessage(messageJson);
        }
    }

    public boolean isClientOnline(int userId) {
        return clients.containsKey(userId) && clients.get(userId).isConnected();
    }

    public Set<Integer> getOnlineUsers() {
        return clients.keySet();
    }

    public static void main(String[] args) {
        ChatServer server = new ChatServer();
        
        // Add shutdown hook
        Runtime.getRuntime().addShutdownHook(new Thread(server::stop));
        
        try {
            server.start();
        } catch (Exception e) {
            logger.error("Failed to start server", e);
        }
    }
}
