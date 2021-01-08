package com.example.gpacalculator.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CourseDao {

    @Query("SELECT * FROM course ORDER BY id ASC")
    LiveData<List<CourseEntity>> readAllData();

    @Query("SELECT DISTINCT(course) FROM course ORDER BY id ASC")
    LiveData<List<String>> readAllCourse();

    @Insert
    void insertCourse(CourseEntity courseEntity);


}
