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

    // Since AsyncTask is deprecated, we use this instead
    private final Executor executor = Executors.newSingleThreadExecutor();

    public CourseTermRepository(Application application) {
        courseTermDao = MainDatabase.getInstance(application).courseTermDao();
        readAllData = courseTermDao.loadAllData();
        readAllTerm = courseTermDao.loadAllTerm();
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

    public LiveData<List<CourseTermEntity>> getReadAllData() {
        return readAllData;
    }

    public LiveData<List<String>> getReadAllTerm() {
        return readAllTerm;
    }

    public LiveData<Boolean> getTermExisted(String term) {
        return courseTermDao.termExisted(term);
    }
}
