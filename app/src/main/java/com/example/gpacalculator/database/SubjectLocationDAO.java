package com.example.gpacalculator.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface SubjectLocationDAO {

    @Query("SELECT * FROM subject_location")
    LiveData<List<SubjectLocationEntity>> loadAllLocations();

    @Insert
    void insertLocation(SubjectLocationEntity subjectLocationEntity);

    @Update
    void updateLocation(SubjectLocationEntity subjectLocationEntity);

    @Delete
    void deleteLocation(SubjectLocationEntity subjectLocationEntity);


}
