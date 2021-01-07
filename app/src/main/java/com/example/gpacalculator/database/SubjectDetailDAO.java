package com.example.gpacalculator.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

@Dao
public interface SubjectDetailDAO {
    @Query("SELECT * FROM subject_detail")
    List<SubjectDetailEntity> loadAllDetail();

    @Transaction
    @Query("SELECT * FROM subject_detail WHERE locationID = :locationID")
    List<SubjectDetailEntity> getCorrespondingSubject(int locationID);

    @Insert
    void insertDetail(SubjectDetailEntity subjectDetailEntity);

    @Update
    void updateDetail(SubjectDetailEntity subjectDetailEntity);

    @Delete
    void deleteDetail(SubjectDetailEntity subjectDetailEntity);

}
