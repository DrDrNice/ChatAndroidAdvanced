package com.example.chatandroidadvanced.model;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface ParticipantDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert (Participant participant);

    @Delete
    void deleteParticipant(Participant participant);

    @Query("DELETE FROM participant_table")
    void deleteAll();

    @Query("SELECT * from participant_table LIMIT 1")
    Participant[] getAnyParticipant();

    @Query("SELECT * from participant_table ORDER BY id ASC")
    LiveData<List<Participant>> getAllParticipants();

    @Update
    void update(Participant... participant);
}
