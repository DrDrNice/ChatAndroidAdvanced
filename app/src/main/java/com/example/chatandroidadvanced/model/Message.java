package com.example.chatandroidadvanced.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Entity(tableName = "message_table", indices = {@Index(value = {"id"}, unique = true)})
public class Message implements Serializable {

    @SerializedName("roomId")
    @Expose
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "roomId")
    private int roomId;

    @SerializedName("content")
    @Expose
    @NonNull
    @ColumnInfo(name = "content")
    private String content;

    @SerializedName("conversationId")
    @Expose
    @NonNull
    @ColumnInfo(name = "conversationId")
    private String conversationId;


    @SerializedName("receiverId")
    @Expose
    @NonNull
    @ColumnInfo(name = "receiverId")
    private String receiverId;

    @SerializedName("senderId")
    @Expose
    @NonNull
    @ColumnInfo(name = "senderId")
    private String senderId;


    @Ignore
    @Expose
    private String createdBy;

    @Ignore
    @Expose
    private String createdDate;

    @SerializedName("id")
    @Expose
    private String id;

    @Ignore
    @Expose
    private String lastModifiedBy;

    @Ignore
    @Expose
    private String lastModifiedDate;

    public Message(String content, String receiverId, String senderId, String conversationId) {
        this.content = content;
        this.conversationId = conversationId;
        this.receiverId = receiverId;
        this.senderId = senderId;
    }
@Ignore
    public Message(int roomId, String content, String receiverId, String senderId, String conversationId) {
        this.roomId = roomId;
        this.content = content;
        this.conversationId = conversationId;
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

    public void setId(String id){this.id = id;}

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId){this.roomId = roomId;}

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
