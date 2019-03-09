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
public interface ConversationDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert (Conversation conversation);

    @Delete
    void deleteConversation(Conversation conversation);

    @Query("DELETE FROM conversation_table")
    void deleteAll();

    @Query("SELECT * from conversation_table LIMIT 1")
    Conversation[] getAnyConversation();

    @Query("SELECT * from conversation_table ORDER BY id DESC")
    LiveData<List<Conversation>> getAllConversations();

    @Query("SELECT * from conversation_table ORDER BY id ASC")
    List<Conversation> getAllConversationsList();

    @Update
    void update(Conversation... conversations);
}
