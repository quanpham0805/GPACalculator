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
    private LiveData<List<String>> readAllTerm;
    private LiveData<List<Integer>> readAllYear;

    // Since AsyncTask is deprecated, we use this instead
    private final Executor executor = Executors.newSingleThreadExecutor();

    public CourseRepository(Application application) {
        courseDao = MainDatabase.getInstance(application).courseDao();
        readAllData = courseDao.loadAllData();
        readAllCourse = courseDao.loadAllCourse();
        readAllTerm = courseDao.loadAllTerm();
        readAllYear = courseDao.loadAllYear();
    }

    public LiveData<Integer> getTermIdFromTermAndYear(String term, int year) {
        return courseDao.getTermIdFromTermAndYear(term, year);
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

    public LiveData<List<String>> getCourseFromTermId(int termId) {
        return courseDao.getCourseFromTermId(termId);
    }

    public LiveData<List<CourseEntity>> getReadAllData() {
        return readAllData;
    }

    public LiveData<List<String>> getReadAllCourse() {
        return readAllCourse;
    }

    public LiveData<Boolean> checkCourseExisted(String course, int termId) {
        return courseDao.courseExisted(course, termId);
    }

    public LiveData<List<String>> getReadAllTerm() {
        return readAllTerm;
    }

    public LiveData<List<Integer>> getReadAllYear() {
        return readAllYear;
    }

    public LiveData<List<String>> getTermFromYear(int year) {
        return courseDao.getTermFromYear(year);
    }

    public void deleteCourseByCourseAndTermAndYear(final String course, final String term, final int year) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                courseDao.deleteCourseByCourseAndTermAndYear(course, term, year);
            }
        });

    }
}
