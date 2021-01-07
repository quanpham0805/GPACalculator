package com.example.gpacalculator.database;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class SubjectLocationRepository {

    private SubjectLocationDAO subjectLocationDAO;
    private LiveData<List<SubjectLocationEntity>> readAllData;
    private LiveData<List<Integer>> readAllYear;
    private boolean yearExisted;


    // Since AsyncTask is deprecated, we use this instead
    private final Executor executor = Executors.newSingleThreadExecutor();

    public SubjectLocationRepository(SubjectLocationDAO subjectLocationDAO) {
        this.subjectLocationDAO = subjectLocationDAO;
        this.readAllData = subjectLocationDAO.loadAllLocations();
        this.readAllYear = subjectLocationDAO.loadAllYear();
    }

    public void addLocation(final SubjectLocationEntity subjectLocationEntity) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                doInBackground(subjectLocationEntity);
            }
        });
    }

    public LiveData<List<SubjectLocationEntity>> getReadAllData() {
        return readAllData;
    }

    public LiveData<List<Integer>> getReadAllYear() {
        return readAllYear;
    }

    public SubjectLocationDAO getmSubjectLocationDAO() {
        return subjectLocationDAO;
    }


    // fake doInBackground
    protected void doInBackground(SubjectLocationEntity subjectLocationEntity) {
        subjectLocationDAO.insertLocation(subjectLocationEntity);
    }

    public LiveData<Boolean> getYearExisted(int year) {
        return subjectLocationDAO.yearExisted(year);
    }
}
