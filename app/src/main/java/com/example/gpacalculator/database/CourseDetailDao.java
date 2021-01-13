package com.example.gpacalculator.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CourseDetailDao {
    @Query("SELECT * FROM course_detail")
    LiveData<List<CourseDetailEntity>> loadAllDetail();

    @Query("SELECT DISTINCT(year) FROM course_year ORDER BY id ASC")
    LiveData<List<Integer>> loadAllYear();

    @Transaction
    @Query("SELECT term FROM course_term WHERE yearId = (SELECT DISTINCT(id) FROM course_year WHERE year = :year)")
    LiveData<List<String>> getTermFromYear(int year);

    @Transaction
    @Query("SELECT course FROM course WHERE termId = (SELECT DISTINCT(id) FROM course_term WHERE term = :term AND yearId = (SELECT DISTINCT(id) FROM course_year WHERE year = :year))")
    LiveData<List<String>> getCourseFromTermAndYear(String term, int year);

    @Transaction
    @Query("SELECT id FROM course WHERE course = :course AND termId = (SELECT DISTINCT(id) FROM course_term WHERE term = :term AND yearId = (SELECT DISTINCT(id) FROM course_year WHERE year = :year))")
    LiveData<Integer> getCourseIdFromCourseAndTermAndYear(String course, String term, int year);

    @Insert
    void insertDetail(CourseDetailEntity courseDetailEntity);

    @Transaction
    @Query("SELECT * FROM course_detail WHERE courseId = (SELECT id FROM course WHERE course = :course AND termId = (SELECT DISTINCT(id) FROM course_term WHERE term = :term AND yearId = (SELECT DISTINCT(id) FROM course_year WHERE year = :year)))")
    LiveData<List<CourseDetailEntity>> loadAllDetailFromCourseTermYear(String course, String term, int year);

    @Query("SELECT EXISTS(SELECT 1 FROM course_detail WHERE courseMarkName = :courseDetail AND courseId = :courseId)")
    LiveData<Boolean> detailExisted(String courseDetail, int courseId);

//    @Update
//    void updateDetail(CourseDetailEntity courseDetailEntity);
//
//    @Delete
//    void deleteDetail(CourseDetailEntity courseDetailEntity);

}
