package com.example.gpacalculator.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class CourseYearRepository {

    private CourseYearDao courseYearDao;
    private LiveData<List<CourseYearEntity>> readAllData;
    private LiveData<List<Integer>> readAllYear;


    // Since AsyncTask is deprecated, we use this instead
    private final Executor executor = Executors.newSingleThreadExecutor();

    public CourseYearRepository(Application application) {
        courseYearDao = MainDatabase.getInstance(application).courseYearDao();
        readAllData = courseYearDao.loadAllData();
        readAllYear = courseYearDao.loadAllYear();
    }

    public void addYear(final CourseYearEntity courseYearEntity) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                doInBackground(courseYearEntity);
            }
        });
    }

    public void deleteYearByYear(final int year) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                courseYearDao.deleteYearByYear(year);
            }
        });
    }

    // fake doInBackground
    protected void doInBackground(CourseYearEntity courseYearEntity) {
        courseYearDao.insertYear(courseYearEntity);
    }

    public LiveData<Integer> getYearIdFromYear(int year) {
        return courseYearDao.getYearIdFromYear(year);
    }

    public LiveData<List<CourseYearEntity>> getReadAllData() {
        return readAllData;
    }

    public LiveData<List<Integer>> getReadAllYear() {
        return readAllYear;
    }

    public LiveData<Boolean> getYearExisted(int year) {
        return courseYearDao.yearExisted(year);
    }
}
