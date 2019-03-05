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

    @Ignore
    @Expose
    private String lastModifiedBy;

    @Ignore
    @Expose
    private String lastModifiedDate;

    @Ignore
    @Expose
    private String createdBy;

    @Ignore
    @Expose
    private String createdDate;


    public Conversation(String topic, String id) {
        this.topic = topic;
        this.id = id;
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