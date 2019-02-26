package com.example.chatandroidadvanced.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "participant_table")
public class Participant {

public Participant(){

}

    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    @ColumnInfo(name = "email")
    public String mEmail;

    @NonNull
    @ColumnInfo(name = "firstName")
    public String mfirstName;

    @NonNull
    @ColumnInfo(name = "lastName")
    public String mlastName;

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
}
