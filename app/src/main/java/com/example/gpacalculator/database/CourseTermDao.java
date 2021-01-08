package com.example.gpacalculator.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CourseTermDao {

    @Query("SELECT * FROM course_term ORDER BY id ASC")
    LiveData<List<CourseTermEntity>> loadAllData();

    @Query("SELECT DISTINCT(term) FROM course_term ORDER BY id ASC")
    LiveData<List<String>> loadAllTerm();

    @Query("SELECT EXISTS(SELECT 1 FROM course_term WHERE term = :term)")
    LiveData<Boolean> termExisted(String term);

    @Insert
    void insertTerm(CourseTermEntity courseTermEntity);

}
