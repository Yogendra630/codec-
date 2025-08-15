package com.chatapp.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

public class Chat {
    private int id;
    private String name;
    private ChatType type;
    private int creatorId;
    private LocalDateTime createdAt;
    private LocalDateTime lastMessageTime;
    private List<Integer> participantIds;
    private String lastMessage;

    public enum ChatType {
        PRIVATE, GROUP
    }

    public Chat() {
        this.participantIds = new ArrayList<>();
        this.createdAt = LocalDateTime.now();
    }

    public Chat(String name, ChatType type, int creatorId) {
        this();
        this.name = name;
        this.type = type;
        this.creatorId = creatorId;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public ChatType getType() { return type; }
    public void setType(ChatType type) { this.type = type; }

    public int getCreatorId() { return creatorId; }
    public void setCreatorId(int creatorId) { this.creatorId = creatorId; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getLastMessageTime() { return lastMessageTime; }
    public void setLastMessageTime(LocalDateTime lastMessageTime) { this.lastMessageTime = lastMessageTime; }

    public List<Integer> getParticipantIds() { return participantIds; }
    public void setParticipantIds(List<Integer> participantIds) { this.participantIds = participantIds; }

    public String getLastMessage() { return lastMessage; }
    public void setLastMessage(String lastMessage) { this.lastMessage = lastMessage; }

    public void addParticipant(int userId) {
        if (!participantIds.contains(userId)) {
            participantIds.add(userId);
        }
    }

    public void removeParticipant(int userId) {
        participantIds.remove(Integer.valueOf(userId));
    }

    public boolean isParticipant(int userId) {
        return participantIds.contains(userId);
    }

    @Override
    public String toString() {
        return "Chat{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", creatorId=" + creatorId +
                ", participantCount=" + participantIds.size() +
                ", lastMessage='" + lastMessage + '\'' +
                '}';
    }
}
