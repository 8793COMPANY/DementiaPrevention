package com.corporation8793.dementia.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ChatDao {

    @Query("SELECT * FROM chat")
    List<Chat> findAll();

    // int type, String message
    @Query("SELECT * FROM chat where type=:type")
    Chat findByType(int type);

    @Query("SELECT * FROM chat where message=:message")
    Chat findByMessage(String message);

    @Insert
    void insert(Chat chat);

    @Delete
    void delete(Chat chat);
}
