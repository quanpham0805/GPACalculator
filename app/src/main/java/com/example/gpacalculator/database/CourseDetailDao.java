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

    @Insert
    void insertDetail(CourseDetailEntity courseDetailEntity);

//    @Update
//    void updateDetail(CourseDetailEntity courseDetailEntity);
//
//    @Delete
//    void deleteDetail(CourseDetailEntity courseDetailEntity);

}
