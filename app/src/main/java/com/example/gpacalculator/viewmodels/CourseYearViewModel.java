package com.example.gpacalculator.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.gpacalculator.database.CourseYearDao;
import com.example.gpacalculator.database.CourseYearEntity;
import com.example.gpacalculator.database.MainDatabase;
import com.example.gpacalculator.database.CourseYearRepository;

import java.util.List;

public class CourseYearViewModel extends AndroidViewModel {

    private LiveData<List<CourseYearEntity>> readAllData;
    private LiveData<List<Integer>> readAllYear;
    private CourseYearDao courseYearDao;
    private CourseYearRepository repository;

    public CourseYearViewModel(@NonNull Application application) {
        super(application);


        this.courseYearDao = MainDatabase.getInstance(application).courseYearDao();
        this.repository = new CourseYearRepository(this.courseYearDao);
        this.readAllData = this.repository.getReadAllData();
        this.readAllYear = this.repository.getReadAllYear();
    }

    public LiveData<List<CourseYearEntity>> getReadAllData() {
        return readAllData;
    }

    public LiveData<List<Integer>> getAllYear() {
        return readAllYear;
    }

    public LiveData<Boolean> checkYearExisted(int year) {
        return repository.getYearExisted(year);
    }

    public void insertLocation(CourseYearEntity courseYearEntity) {
        repository.addLocation(courseYearEntity);
    }
}
