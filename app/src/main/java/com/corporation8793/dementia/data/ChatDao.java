package com.corporation8793.dementia.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ChatDao {
    @Query("SELECT * FROM Chat")
    List<Chat> getAll();

    @Insert
    void insert(Chat chat);

    @Delete
    void delete(Chat chat);
}
