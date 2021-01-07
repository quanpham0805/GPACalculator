package com.example.gpacalculator.database;

import androidx.lifecycle.LiveData;

import java.util.List;

public class SubjectDetailRepository {

    private SubjectDetailDAO mSubjectDetailDAO;
    private LiveData<List<SubjectDetailEntity>> readAllData;

    SubjectDetailRepository (SubjectDetailDAO subjectDetailDAO) {
        this.mSubjectDetailDAO = subjectDetailDAO;
        this.readAllData = subjectDetailDAO.loadAllDetail();
    }

    void addDetail(SubjectDetailEntity subjectDetailEntity) {
        mSubjectDetailDAO.insertDetail(subjectDetailEntity);
    }
}
