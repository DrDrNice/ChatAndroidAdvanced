package com.example.chatandroidadvanced.model;

import java.io.Serializable;

public class Conversation implements Serializable {

    private String createdBy;
    private String createdDate;
    private String id;
    private String lastModifiedBy;
    private String lastModifiedDate;
    private String topic;

    public Conversation(String createdBy, String lastModifiedBy, String topic) {
        this.createdBy = createdBy;
        this.lastModifiedBy = lastModifiedBy;
        this.topic = topic;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getCreatedBy() {
        return createdBy;
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

    public String getLastModifiedDate() {
        return lastModifiedDate;
    }

    public String getTopic() {
        return topic;
    }


}
