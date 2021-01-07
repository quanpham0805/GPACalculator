package com.example.gpacalculator.database;

import androidx.lifecycle.LiveData;

import java.util.List;

public class SubjectLocationRepository {

    private SubjectLocationDAO mSubjectLocationDAO;
    private LiveData<List<SubjectLocationEntity>> readAllData;

    SubjectLocationRepository(SubjectLocationDAO subjectLocationDAO) {
        this.mSubjectLocationDAO = subjectLocationDAO;
        this.readAllData = subjectLocationDAO.loadAllLocations();
    }

    public void addLocation (SubjectLocationEntity subjectLocationEntity) {
        mSubjectLocationDAO.insertLocation(subjectLocationEntity);
    }
}
