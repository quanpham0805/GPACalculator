package com.example.gpacalculator.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class CourseDetailRepository {

    // Since AsyncTask is deprecated, we use this instead
    private final Executor executor = Executors.newSingleThreadExecutor();
    private final CourseDetailDao courseDetailDao;
    private final LiveData<List<CourseDetailEntity>> readAllData;

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

    public LiveData<List<CourseDetailEntity>> loadAllDetailFromCourseTermYear(String course, String term, int year) {
        return courseDetailDao.loadAllDetailFromCourseTermYear(course, term, year);
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

    public LiveData<Boolean> detailExisted(String courseDetail, int courseId) {
        return courseDetailDao.detailExisted(courseDetail, courseId);
    }

    public LiveData<List<String>> getMarkNameFromCourseTermYear(String course, String term, int year) {
        return courseDetailDao.getMarkNameFromCourseTermYear(course, term, year);
    }

    public void deleteDetail(final String name, final String course, final String term, final int year) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                courseDetailDao.deleteDetail(name, course, term, year);
            }
        });
    }
}
