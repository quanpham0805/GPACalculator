package com.example.gpacalculator.database;

import androidx.lifecycle.LiveData;

import java.util.List;

public class CourseDetailRepository {

    private CourseDetailDao mCourseDetailDao;
    private LiveData<List<CourseDetailEntity>> readAllData;

    CourseDetailRepository(CourseDetailDao courseDetailDao) {
        this.mCourseDetailDao = courseDetailDao;
        this.readAllData = courseDetailDao.loadAllDetail();
    }

    void addDetail(CourseDetailEntity courseDetailEntity) {
        mCourseDetailDao.insertDetail(courseDetailEntity);
    }

    public LiveData<List<CourseDetailEntity>> getReadAllData() {
        return readAllData;
    }
}
