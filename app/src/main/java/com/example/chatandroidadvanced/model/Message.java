package com.example.chatandroidadvanced.model;

public class Message {

    private String content;
    private String conversationId;
    private String createdBy;
    private String createdDate;
    private String id;
    private String lastModifiedBy;
    private String lastModifiedDate;
    private String receiverId;
    private String senderId;

    public Message(String content, String conversationId, String createdBy, String lastModifiedBy, String receiverId, String senderId) {
        this.content = content;
        this.conversationId = conversationId;
        this.createdBy = createdBy;
        this.lastModifiedBy = lastModifiedBy;
        this.receiverId = receiverId;
        this.senderId = senderId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public String getId() {
        return id;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public String getLastModifiedDate() {
        return lastModifiedDate;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }
}
