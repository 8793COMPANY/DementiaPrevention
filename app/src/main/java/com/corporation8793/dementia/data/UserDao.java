package com.corporation8793.dementia.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDao {

    @Query("SELECT * FROM user")
    List<User> getAll();

    // String name, String region, String ageRange, String birthday
    @Query("SELECT * FROM user where name=:name")
    User findByName(String name);

    @Query("SELECT * FROM user where region=:region")
    User findByRegion(String region);

    @Query("SELECT * FROM user where ageRange=:ageRange")
    User findByAgeRange(String ageRange);

    @Query("SELECT * FROM user where birthday=:birthday")
    User findByBirthday(String birthday);

    @Insert
    void insert(User user);

    @Delete
    void delete(User user);

    @Update
    void update(User user);

    // 특정 필드 업데이트
    @Query("UPDATE user SET name = :name, region = :region, ageRange = :ageRange, birthday = :birthday WHERE uid = :id")
    void updateUserById(int id, String name, String region, String ageRange, String birthday);
}
