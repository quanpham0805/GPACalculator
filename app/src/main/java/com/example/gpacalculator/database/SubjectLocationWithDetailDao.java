package com.example.gpacalculator.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

@Dao
public interface SubjectLocationWithDetailDao {
    @Transaction
    @Query("SELECT * FROM subject_location")
    public List<SubjectLocationWithDetailEntity> getAllSubjectLocationWithDetail();


}
