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

@Entity(tableName = "conversation_table", indices = {@Index(value = {"Id"}, unique = true)})
public class Conversation implements Serializable {

    @SerializedName("roomId")
    @Expose
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "roomId")
    private int roomId;

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }



    @SerializedName("id")
    @Expose
    @NonNull
    @ColumnInfo(name = "Id")
    private String id;

    @SerializedName("topic")
    @Expose
    @NonNull
    @ColumnInfo(name = "topic")
    private String topic;

    public void setContent(@NonNull String content) {
        this.content = content;
    }

    @NonNull
    public String getContent() {
        return content;
    }

    //-------
    @SerializedName("content")
    @Expose
    @NonNull
    @ColumnInfo(name = "content")
    private String content;

    @NonNull
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(@NonNull String firstName) {
        this.firstName = firstName;
    }

    @NonNull
    public String getLastName() {
        return lastName;
    }

    public void setLastName(@NonNull String lastName) {
        this.lastName = lastName;
    }

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


    @SerializedName("email")
    @Expose
    @NonNull
    @ColumnInfo(name = "email")
    public String email;

    @SerializedName("firstName")
    @Expose
    @NonNull
    @ColumnInfo(name = "firstName")
    public String firstName;

    @SerializedName("lastName")
    @Expose
    @NonNull
    @ColumnInfo(name = "lastName")
    public String lastName;


    @Ignore
    @Expose
    private String lastModifiedBy;

    @Ignore
    @Expose
    private String lastModifiedDate;

    @Ignore
    @Expose
    private String createdBy;

    @NonNull
    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(@NonNull String receiverId) {
        this.receiverId = receiverId;
    }

    public void setSenderId(@NonNull String senderId) {
        this.senderId = senderId;
    }

    public void setEmail(@NonNull String email) {
        this.email = email;
    }

    @NonNull
    public String getSenderId() {
        return senderId;
    }

    @NonNull
    public String getEmail() {
        return email;
    }

    @Ignore
    @Expose
    private String createdDate;


    @Ignore
    public Conversation(String topic, String id) {
        this.topic = topic;
        this.id = id;
    }



    public Conversation(String topic, String id , String senderId, String receiverId, String email, String content , String firstName , String lastName) {
        this.topic = topic;
        this.id = id;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.email = email;
        this.content = content;
        this.firstName = firstName;
        this.lastName = lastName;
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

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public void setLastModifiedDate(String lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public int getRoomId(){return roomId;}

    public void setRoomId(){this.roomId = roomId;}

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