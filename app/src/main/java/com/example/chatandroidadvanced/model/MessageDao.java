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
public interface MessageDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert (Message message);


    @Delete
    void deleteMessage(Message message);

    @Query("DELETE FROM message_table")
    void deleteAll();

    @Query("SELECT * from message_table LIMIT 1")
    Message[] getAnyMessage();

    @Query("SELECT * from message_table ORDER BY id ASC")
    LiveData<List<Message>> getAllMessage();


    @Query("SELECT * FROM message_table WHERE receiverId LIKE :recId " + "OR senderId LIKE :recId ORDER BY id ASC")
    LiveData<List<Message>> getAllMessagebyReciverId(int recId);


    @Query("SELECT * FROM message_table WHERE receiverId LIKE :recId " + " OR senderId LIKE :recId ORDER BY id ASC")
    LiveData<List<Message>> getAllMessagebyReciverConvId(int recId);

    @Query("SELECT * FROM message_table WHERE receiverId LIKE :recId AND senderId LIKE :sendId " + " OR senderId LIKE :recId AND receiverId LIKE :sendId ORDER BY id ASC")
    LiveData<List<Message>> getAllMessagebyReciverSendId(int recId, int sendId);

    @Update
    void update(Message... messages);
}
