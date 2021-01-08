package com.example.gpacalculator.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CourseYearDao {

    @Query("SELECT * FROM course_year ORDER BY id ASC")
    LiveData<List<CourseYearEntity>> loadAllData();

    @Query("SELECT DISTINCT(year) FROM course_year ORDER BY id ASC")
    LiveData<List<Integer>> loadAllYear();

    @Query("SELECT EXISTS(SELECT 1 FROM course_year WHERE year = :year)")
    LiveData<Boolean> yearExisted(int year);


    @Insert
    void insertLocation(CourseYearEntity courseYearEntity);

    @Update
    void updateLocation(CourseYearEntity courseYearEntity);

    @Delete
    void deleteLocation(CourseYearEntity courseYearEntity);


}
