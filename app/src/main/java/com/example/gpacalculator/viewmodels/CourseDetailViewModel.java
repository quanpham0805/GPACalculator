package com.example.gpacalculator.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.gpacalculator.database.CourseDetailEntity;
import com.example.gpacalculator.database.CourseDetailRepository;

import java.util.List;

public class CourseDetailViewModel extends AndroidViewModel {

    private CourseDetailRepository repository;
    private LiveData<List<CourseDetailEntity>> readAllData;

    public CourseDetailViewModel(@NonNull Application application) {
        super(application);

        this.repository = new CourseDetailRepository(application);
    }

    public LiveData<List<Integer>> loadAllYear() {
        return repository.loadAllYear();
    }

    public LiveData<List<String>> getTermFromYear(int year) {
        return repository.getTermFromYear(year);
    }

    public LiveData<List<String>> getCourseFromTermAndYear(String term, int year) {
        return repository.getCourseFromTermAndYear(term, year);
    }
}
