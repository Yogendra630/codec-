package com.chatapp.model;

import java.time.LocalDateTime;

public class Message {
    private int id;
    private int senderId;
    private int chatId;
    private String content;
    private MessageType type;
    private LocalDateTime timestamp;
    private boolean isRead;
    private String senderUsername;

    public enum MessageType {
        TEXT, IMAGE, FILE, SYSTEM
    }

    public Message() {}

    public Message(int senderId, int chatId, String content, MessageType type) {
        this.senderId = senderId;
        this.chatId = chatId;
        this.content = content;
        this.type = type;
        this.timestamp = LocalDateTime.now();
        this.isRead = false;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getSenderId() { return senderId; }
    public void setSenderId(int senderId) { this.senderId = senderId; }

    public int getChatId() { return chatId; }
    public void setChatId(int chatId) { this.chatId = chatId; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public MessageType getType() { return type; }
    public void setType(MessageType type) { this.type = type; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }

    public boolean isRead() { return isRead; }
    public void setRead(boolean read) { isRead = read; }

    public String getSenderUsername() { return senderUsername; }
    public void setSenderUsername(String senderUsername) { this.senderUsername = senderUsername; }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", senderId=" + senderId +
                ", chatId=" + chatId +
                ", content='" + content + '\'' +
                ", type=" + type +
                ", timestamp=" + timestamp +
                ", isRead=" + isRead +
                '}';
    }
}
