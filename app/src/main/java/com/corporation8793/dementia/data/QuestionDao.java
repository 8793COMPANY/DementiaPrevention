package com.corporation8793.dementia.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface QuestionDao {

    @Query("SELECT * FROM question")
    List<Question> findAll();

    // String type, String num, String contents, String question_type,
    // String score1, String score2, String score3
    @Query("SELECT * FROM question where type=:type")
    Question findByMessage(String type);

    @Query("SELECT * FROM question where num=:num")
    Question findByNum(String num);

    @Query("SELECT * FROM question where contents=:contents")
    Question findByContents(String contents);

    @Query("SELECT * FROM question where question_type=:question_type")
    Question findByQuestionType(String question_type);

    @Query("SELECT * FROM question where score1=:score1")
    Question findByScore1(String score1);

    @Query("SELECT * FROM question where score2=:score2")
    Question findByScore2(String score2);

    @Query("SELECT * FROM question where score3=:score3")
    Question findByScore3(String score3);

    @Query("DELETE FROM question")
    void deleteAll();

    @Insert
    void insert(Question question);

    @Delete
    void delete(Question question);
}
