package com.example.chatandroidadvanced.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "participant_table")
public class Participant {

    @PrimaryKey(autoGenerate = true)
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

    @NonNull
    @ColumnInfo(name = "email")
    public String mEmail;

    @NonNull
    @ColumnInfo(name = "firstName")
    public String mfirstName;

    @NonNull
    @ColumnInfo(name = "lastName")
    public String mlastName;

    public Participant(){

    }

    public Participant(@NonNull String email, String firstName, String lastName) {
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
