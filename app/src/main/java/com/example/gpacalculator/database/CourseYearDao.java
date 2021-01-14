package com.example.gpacalculator.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

@Dao
public interface CourseYearDao {

    @Query("SELECT * FROM course_year ORDER BY id ASC")
    LiveData<List<CourseYearEntity>> loadAllData();

    @Query("SELECT DISTINCT(year) FROM course_year ORDER BY id ASC")
    LiveData<List<Integer>> loadAllYear();

    @Query("SELECT EXISTS(SELECT 1 FROM course_year WHERE year = :year)")
    LiveData<Boolean> yearExisted(int year);

    @Query("SELECT DISTINCT(id) FROM course_year WHERE year = :year")
    LiveData<Integer> getYearIdFromYear(int year);

    @Insert
    void insertYear(CourseYearEntity courseYearEntity);

    // delete
    @Query("DELETE FROM course_year WHERE year = :year")
    void deleteYearByYear(int year);

    @Transaction
    @Query("SELECT * FROM course_term WHERE yearId IN (SELECT id FROM course_year WHERE year IN (:year))")
    LiveData<List<CourseTermEntity>> getListTermFromListYear(List<Integer> year);

    @Transaction
    @Query("SELECT * FROM course WHERE termId IN (SELECT id FROM course_term WHERE term IN (:term) AND yearId IN (SELECT id FROM course_year WHERE year IN (:year)))")
    LiveData<List<CourseEntity>> getListCourseFromListTermListYear(List<String> term, List<Integer> year);

    @Transaction
    @Query("SELECT * FROM course_detail WHERE courseId IN (SELECT id FROM course WHERE course IN (:courses) AND termId IN (SELECT DISTINCT(id) FROM course_term WHERE term IN (:term) AND yearId IN (SELECT id FROM course_year WHERE year IN (:year))))")
    LiveData<List<CourseDetailEntity>> loadAllDetailFromListCourseListTermListYear(List<String> courses, List<String> term, List<Integer> year);

}
