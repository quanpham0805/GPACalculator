package com.example.gpacalculator.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CourseDao {

    @Query("SELECT * FROM course ORDER BY id ASC")
    LiveData<List<CourseEntity>> loadAllData();

    @Query("SELECT DISTINCT(course) FROM course ORDER BY id ASC")
    LiveData<List<String>> loadAllCourse();

    @Query("SELECT EXISTS(SELECT 1 FROM course WHERE course = :course)")
    LiveData<Boolean> courseExisted(String course);

    @Insert
    void insertCourse(CourseEntity courseEntity);


}
