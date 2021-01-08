package com.example.gpacalculator.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.gpacalculator.database.CourseYearEntity;
import com.example.gpacalculator.database.CourseYearRepository;

import java.util.List;

public class CourseYearViewModel extends AndroidViewModel {

    private LiveData<List<CourseYearEntity>> readAllData;
    private LiveData<List<Integer>> readAllYear;
    private CourseYearRepository repository;

    public CourseYearViewModel(@NonNull Application application) {
        super(application);

        this.repository = new CourseYearRepository(application);
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

    public void insertYear(CourseYearEntity courseYearEntity) {
        repository.addYear(courseYearEntity);
    }
}
