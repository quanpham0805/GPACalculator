package com.example.gpacalculator.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface SubjectDetailDAO {
    @Query("SELECT * FROM subject_detail")
    public List<SubjectDetailEntity> loadAllDetail();

    @Insert
    void insertDetail(SubjectDetailEntity subjectDetailEntity);

    @Update
    void updateDetail(SubjectDetailEntity subjectDetailEntity);

    @Delete
    void deleteDetail(SubjectDetailEntity subjectDetailEntity);

}
