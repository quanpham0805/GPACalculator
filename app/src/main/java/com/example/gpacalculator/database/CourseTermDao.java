package com.example.gpacalculator.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CourseTermDao {

    @Query("SELECT * FROM course_term ORDER BY id ASC")
    LiveData<List<CourseTermEntity>> readAllData();

    @Query("SELECT DISTINCT(term) FROM course_term ORDER BY id ASC")
    LiveData<List<String>> readAllTerm();

    @Insert
    void insertTerm(CourseTermEntity courseTermEntity);


}
