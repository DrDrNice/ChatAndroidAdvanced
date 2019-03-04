package com.example.chatandroidadvanced.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Entity(tableName = "participant_table")
public class Participant implements Serializable {


    @SerializedName("idRoom")
    @Expose
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "roomID")
    private int id;

    //todo are they needed??
    /*@PrimaryKey(autoGenerate = true)
    private String createdBy;

    @PrimaryKey(autoGenerate = true)
    private String createdDate;

    @PrimaryKey(autoGenerate = true)
    private String lastModifiedBy;

    @PrimaryKey(autoGenerate = true)
    private String lastModifiedDate;

    @PrimaryKey(autoGenerate = true)
    private String mAvatar;*/

    @SerializedName("id")
    @Expose
    @NonNull
    @ColumnInfo(name = "Id")
    public String mID;

    @SerializedName("email")
    @Expose
    @NonNull
    @ColumnInfo(name = "email")
    public String mEmail;

    @SerializedName("firstName")
    @Expose
    @NonNull
    @ColumnInfo(name = "firstName")
    public String mfirstName;

    @SerializedName("lastName")
    @Expose
    @NonNull
    @ColumnInfo(name = "lastName")
    public String mlastName;

    public Participant(){

    }

    public Participant(@NonNull String id,@NonNull String email, @NonNull String firstName, @NonNull String lastName) {
        this.mID = id;
        this.mEmail = email;
        this.mfirstName = firstName;
        this.mlastName = lastName;
    }

    @Ignore
    public Participant(@NonNull String email, @NonNull String firstName, @NonNull String lastName) {
        this.mEmail = email;
        this.mfirstName = firstName;
        this.mlastName = lastName;
    }

    @Ignore
    public Participant(int idRoom ,@NonNull String id,@NonNull String email, @NonNull String firstName, @NonNull String lastName) {
        this.id = idRoom;
        this.mID = id;
        this.mEmail = email;
        this.mfirstName = firstName;
        this.mlastName = lastName;
    }


    public String getmEmail() {
        return this.mEmail;
    }

    public String getfirstName() {
        return this.mfirstName;
    }

    public String getlastName() {
        return this.mlastName;
    }

    public String getIDServer() {
        return this.mID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    //todo are they needed
    /*public String getmAvatar() {
        return mAvatar;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public String getLastModifiedDate() {
        return lastModifiedDate;
    }*/
}
