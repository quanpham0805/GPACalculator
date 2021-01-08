package com.example.gpacalculator.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class CourseRepository {

    private CourseDao courseDao;
    private LiveData<List<CourseEntity>> readAllData;
    private LiveData<List<String>> readAllCourse;

    // Since AsyncTask is deprecated, we use this instead
    private final Executor executor = Executors.newSingleThreadExecutor();

    public CourseRepository(Application application) {
        courseDao = MainDatabase.getInstance(application).courseDao();
        readAllData = courseDao.loadAllData();
        readAllCourse = courseDao.loadAllCourse();
    }

    public void addCourse(final CourseEntity courseEntity) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                doInBackground(courseEntity);
            }
        });
    }

    // fake doInBackground
    protected void doInBackground(CourseEntity courseEntity) {
        courseDao.insertCourse(courseEntity);
    }

    public LiveData<List<CourseEntity>> getReadAllData() {
        return readAllData;
    }

    public LiveData<List<String>> getReadAllCourse() {
        return readAllCourse;
    }

    public LiveData<Boolean> checkCourseExisted(String course) {
        return courseDao.courseExisted(course);
    }
}
