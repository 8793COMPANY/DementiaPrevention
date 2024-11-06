package com.corporation8793.dementia.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DiagnoseListDao {

    @Query("SELECT * FROM diagnoselist")
    List<DiagnoseList> getAll();

    // String date, String resultText, int resultScore
    @Query("SELECT * FROM diagnoselist where date=:date")
    DiagnoseList findByDate(String date);

    @Query("SELECT * FROM diagnoselist where resultText=:resultText")
    DiagnoseList findByResultText(String resultText);

    @Query("SELECT * FROM diagnoselist where resultScore=:resultScore")
    DiagnoseList findByResultScore(String resultScore);

    @Insert
    void insert(DiagnoseList diagnoseList);

    @Delete
    void delete(DiagnoseList diagnoseList);
}
