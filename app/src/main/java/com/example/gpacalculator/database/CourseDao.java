package com.example.gpacalculator.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

@Dao
public interface CourseDao {

    @Query("SELECT * FROM course ORDER BY id ASC")
    LiveData<List<CourseEntity>> loadAllData();

    @Query("SELECT DISTINCT(course) FROM course ORDER BY id ASC")
    LiveData<List<String>> loadAllCourse();

    @Query("SELECT DISTINCT(term) FROM course_term ORDER BY id ASC")
    LiveData<List<String>> loadAllTerm();

    @Query("SELECT DISTINCT(year) FROM course_year ORDER BY id ASC")
    LiveData<List<Integer>> loadAllYear();

    @Transaction
    @Query("SELECT DISTINCT(id) FROM course_term WHERE term = :term AND yearId = (SELECT DISTINCT(id) FROM course_year WHERE year = :year)")
    LiveData<Integer> getTermIdFromTermAndYear(String term, int year);

    @Query("SELECT course FROM course WHERE termId = :termId ORDER BY id ASC")
    LiveData<List<String>> getCourseFromTermId(int termId);

    @Transaction
    @Query("SELECT term FROM course_term WHERE yearId = (SELECT DISTINCT(id) FROM course_year WHERE year = :year)")
    LiveData<List<String>> getTermFromYear(int year);

    @Query("SELECT EXISTS(SELECT 1 FROM course WHERE course = :course AND termId = :termId)")
    LiveData<Boolean> courseExisted(String course, int termId);

    @Insert
    void insertCourse(CourseEntity courseEntity);


}
