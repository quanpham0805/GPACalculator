package com.example.gpacalculator.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class CourseTermRepository {

    private CourseTermDao courseTermDao;
    private LiveData<List<CourseTermEntity>> readAllData;
    private LiveData<List<String>> readAllTerm;
    private LiveData<List<Integer>> readAllYear;

    // Since AsyncTask is deprecated, we use this instead
    private final Executor executor = Executors.newSingleThreadExecutor();

    public CourseTermRepository(Application application) {
        courseTermDao = MainDatabase.getInstance(application).courseTermDao();
        readAllData = courseTermDao.loadAllData();
        readAllTerm = courseTermDao.loadAllTerm();
        readAllYear = courseTermDao.loadAllYear();
    }

    public LiveData<List<Integer>> getAllYear() {
        return readAllYear;
    }

    public void addTerm(final CourseTermEntity courseTermEntity) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                doInBackground(courseTermEntity);
            }
        });
    }

    // fake doInBackground
    protected void doInBackground(CourseTermEntity courseTermEntity) {
        courseTermDao.insertTerm(courseTermEntity);
    }

    public void deleteTermByTermAndYear(final String term, final int year) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                courseTermDao.deleteTermByTermAndYear(term, year);
            }
        });
    }

    public LiveData<List<CourseTermEntity>> getReadAllData() {
        return readAllData;
    }

    public LiveData<List<String>> getReadAllTerm() {
        return readAllTerm;
    }

    public LiveData<Boolean> getTermExisted(String term, int yearId) {
        return courseTermDao.termExisted(term, yearId);
    }

    public LiveData<Integer> getYearIdFromYear(int year) {
        return courseTermDao.getYearIdFromYear(year);
    }

    public LiveData<List<String>> getTermFromYearId(int yearId) {
        return courseTermDao.getTermFromYearId(yearId);
    }

    public LiveData<List<String>> getTermFromYear(int year) {
        return courseTermDao.getTermFromYear(year);
    }
}
