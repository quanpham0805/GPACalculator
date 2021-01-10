package com.example.gpacalculator.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class CourseDetailRepository {

    private CourseDetailDao courseDetailDao;
    private LiveData<List<CourseDetailEntity>> readAllData;

    // Since AsyncTask is deprecated, we use this instead
    private final Executor executor = Executors.newSingleThreadExecutor();

    public CourseDetailRepository(Application application) {
        courseDetailDao = MainDatabase.getInstance(application).courseDetailDao();
        readAllData = courseDetailDao.loadAllDetail();
    }

    public void addDetail(final CourseDetailEntity courseDetailEntity) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                courseDetailDao.insertDetail(courseDetailEntity);
            }
        });
    }

    public LiveData<List<CourseDetailEntity>> getReadAllData() {
        return readAllData;
    }

    public LiveData<List<Integer>> loadAllYear() {
        return courseDetailDao.loadAllYear();
    }

    public LiveData<List<String>> getTermFromYear(int year) {
        return courseDetailDao.getTermFromYear(year);
    }

    public LiveData<List<String>> getCourseFromTermAndYear(String term, int year) {
        return courseDetailDao.getCourseFromTermAndYear(term, year);
    }

    public LiveData<Integer> getCourseIdFromCourseAndTermAndYear(String course, String term, int year) {
        return courseDetailDao.getCourseIdFromCourseAndTermAndYear(course, term, year);
    }
}
