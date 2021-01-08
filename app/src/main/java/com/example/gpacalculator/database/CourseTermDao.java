package com.example.gpacalculator.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

@Dao
public interface CourseTermDao {

    @Query("SELECT * FROM course_term ORDER BY id ASC")
    LiveData<List<CourseTermEntity>> loadAllData();

    @Query("SELECT DISTINCT(term) FROM course_term ORDER BY id ASC")
    LiveData<List<String>> loadAllTerm();

    @Query("SELECT DISTINCT(id) FROM course_year WHERE year = :year")
    LiveData<Integer> getYearIdFromYear(int year);

    @Query("SELECT DISTINCT(year) FROM course_year ORDER BY id ASC")
    LiveData<List<Integer>> loadAllYear();

    @Transaction
    @Query("SELECT EXISTS(SELECT 1 FROM course_term WHERE term = :term AND yearId = :yearId)")
    LiveData<Boolean> termExisted(String term, int yearId);

    @Insert
    void insertTerm(CourseTermEntity courseTermEntity);

}
