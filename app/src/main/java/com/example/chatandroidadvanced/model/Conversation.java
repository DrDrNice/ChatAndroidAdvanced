package com.example.chatandroidadvanced.model;

import android.widget.ImageView;

import java.io.Serializable;

public class Conversation implements Serializable {

    private ImageView image;
    private String id;
    private String firstName;
    private String lastName;
    private String status;
    private String content;
    private String time;

    public Conversation(String id, String firstName, String lastName, String content, String time){
        this.image = downLoadImage();
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.status = "I am using ChatApp!";
        this.content = content;
        this.time = time;
    }

    private ImageView downLoadImage() {
        //todo use glide to download an image
        return null;
    }

    public ImageView getImage() {
        return image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
