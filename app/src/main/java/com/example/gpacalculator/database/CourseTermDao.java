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

    @Query("SELECT DISTINCT(year) FROM course_year ORDER BY id ASC")
    LiveData<List<Integer>> loadAllYear();

    @Query("SELECT DISTINCT(id) FROM course_year WHERE year = :year")
    LiveData<Integer> getYearIdFromYear(int year);

    @Transaction
    @Query("SELECT term FROM course_term WHERE yearId = (SELECT DISTINCT(id) FROM course_year WHERE year = :year)")
    LiveData<List<String>> getTermFromYear(int year);

    @Transaction
    @Query("SELECT * FROM course_term WHERE yearId = (SELECT DISTINCT(id) FROM course_year WHERE year = :year)")
    LiveData<List<CourseTermEntity>> getListTermFromYear(int year);

    @Transaction
    @Query("SELECT * FROM course WHERE termId IN (SELECT id FROM course_term WHERE term in (:term) AND yearId = (SELECT id FROM course_year WHERE year = :year))")
    LiveData<List<CourseEntity>> getListCourseFromListTermYear(List<String> term, int year);

    @Transaction
    @Query("DELETE FROM course_term WHERE term = :term AND yearId = (SELECT DISTINCT(id) FROM course_year WHERE year = :year)")
    void deleteTermByTermAndYear(String term, int year);

    @Query("SELECT term FROM course_term WHERE yearId = :yearId")
    LiveData<List<String>> getTermFromYearId(int yearId);

    @Transaction
    @Query("SELECT EXISTS(SELECT 1 FROM course_term WHERE term = :term AND yearId = :yearId)")
    LiveData<Boolean> termExisted(String term, int yearId);

    @Transaction
    @Query("SELECT * FROM course_detail WHERE courseId IN (SELECT id FROM course WHERE course IN (:courses) AND termId = (SELECT DISTINCT(id) FROM course_term WHERE term IN (:term) AND yearId = (SELECT DISTINCT(id) FROM course_year WHERE year = :year)))")
    LiveData<List<CourseDetailEntity>> loadAllDetailFromListCourseListTermYear(List<String> courses, List<String> term, int year);

    @Insert
    void insertTerm(CourseTermEntity courseTermEntity);

}
