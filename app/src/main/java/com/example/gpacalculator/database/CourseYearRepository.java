package com.example.gpacalculator.database;

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

    public CourseYearRepository(CourseYearDao courseYearDao) {
        this.courseYearDao = courseYearDao;
        this.readAllData = courseYearDao.loadAllData();
        this.readAllYear = courseYearDao.loadAllYear();
    }

    public void addLocation(final CourseYearEntity courseYearEntity) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                doInBackground(courseYearEntity);
            }
        });
    }

    // fake doInBackground
    protected void doInBackground(CourseYearEntity courseYearEntity) {
        courseYearDao.insertLocation(courseYearEntity);
    }

    public LiveData<List<CourseYearEntity>> getReadAllData() {
        return readAllData;
    }

    public LiveData<List<Integer>> getReadAllYear() {
        return readAllYear;
    }

    public CourseYearDao getmCourseYearDao() {
        return courseYearDao;
    }

    public LiveData<Boolean> getYearExisted(int year) {
        return courseYearDao.yearExisted(year);
    }
}
