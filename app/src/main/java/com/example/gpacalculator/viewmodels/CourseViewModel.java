package com.example.gpacalculator.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.gpacalculator.database.CourseEntity;
import com.example.gpacalculator.database.CourseRepository;

import java.util.List;

public class CourseViewModel extends AndroidViewModel {

    private LiveData<List<CourseEntity>> readAllData;
    private LiveData<List<String>> readAllCourse;
    private CourseRepository repository;

    public CourseViewModel(@NonNull Application application) {
        super(application);

        this.repository = new CourseRepository(application);
        this.readAllData = this.repository.getReadAllData();
        this.readAllCourse = this.repository.getReadAllCourse();
    }

    public LiveData<List<CourseEntity>> getReadAllData() {
        return readAllData;
    }

    public LiveData<List<String>> getAllCourse() {
        return readAllCourse;
    }

    public LiveData<Boolean> checkCourseExisted(String course) {
        return repository.checkCourseExisted(course);
    }
}
