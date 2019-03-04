package com.example.chatandroidadvanced.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Entity(tableName = "participant_table")
public class Participant implements Serializable {


    //todo fields createdBy, avatar usw können nicht initialisiert werden bzw ohne @ignore funktioniert es nicht

    @SerializedName("idRoom")
    @Expose
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "roomID")
    private int id;

    @Ignore
    @ColumnInfo(name = "createdBy")
    private String createdBy;

    @Ignore
    @ColumnInfo(name = "createdDate")
    private String createdDate;

    @Ignore
    @ColumnInfo(name = "lastModifiedBy")
    private String lastModifiedBy;

    @Ignore
    @ColumnInfo(name = "lastModifiedDate")
    private String lastModifiedDate;

    @Ignore
    @ColumnInfo(name = "avatar")
    private String mAvatar;

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
        this.createdDate = null;
    }

    public Participant(@NonNull String email, @NonNull String firstName, @NonNull String lastName) {
        this.mEmail = email;
        this.mfirstName = firstName;
        this.mlastName = lastName;
    }

    public Participant(@NonNull String id,@NonNull String email, @NonNull String firstName, @NonNull String lastName,
                       @NonNull String createdDate,@NonNull String createdBy, @NonNull String lastModifiedDate, @NonNull String lastModifiedBy, @NonNull String avatar) {
        this.mID = id;
        this.mEmail = email;
        this.mfirstName = firstName;
        this.mlastName = lastName;
        this.createdDate = createdDate;
        this.createdBy = createdBy;
        this.lastModifiedDate = lastModifiedDate;
        this.lastModifiedBy = lastModifiedBy;
        this.mAvatar = avatar;
    }

    public String getEmail() {
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

    public String getmAvatar() {
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
    }
}