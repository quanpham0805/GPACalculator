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

    @Query("SELECT * FROM subject_location ORDER BY id ASC")
    LiveData<List<SubjectLocationEntity>> loadAllLocations();

    @Query("SELECT DISTINCT(year) FROM subject_location ORDER BY id ASC")
    LiveData<List<Integer>> loadAllYear();

    @Query("SELECT EXISTS(SELECT 1 FROM subject_location WHERE year = :year)")
    LiveData<Boolean> yearExisted(int year);


    @Insert
    void insertLocation(SubjectLocationEntity subjectLocationEntity);

    @Update
    void updateLocation(SubjectLocationEntity subjectLocationEntity);

    @Delete
    void deleteLocation(SubjectLocationEntity subjectLocationEntity);


}
