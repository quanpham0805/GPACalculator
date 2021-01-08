package com.example.gpacalculator.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.gpacalculator.database.CourseTermEntity;
import com.example.gpacalculator.database.CourseTermRepository;

import java.util.List;

public class CourseTermViewModel extends AndroidViewModel {

    private LiveData<List<CourseTermEntity>> readAllData;
    private LiveData<List<String>> readAllTerm;
    private LiveData<List<Integer>> readAllYear;
    private CourseTermRepository repository;

    public CourseTermViewModel(@NonNull Application application) {
        super(application);

        this.repository = new CourseTermRepository(application);
        this.readAllData = this.repository.getReadAllData();
        this.readAllTerm = this.repository.getReadAllTerm();
        this.readAllYear = this.repository.getAllYear();
    }

    public LiveData<List<CourseTermEntity>> getReadAllData() {
        return readAllData;
    }

    public LiveData<List<String>> getAllTerm() {
        return readAllTerm;
    }

    public LiveData<List<Integer>> getAllYear() {
        return readAllYear;
    }

    public LiveData<Boolean> checkTermExisted(String term, int yearId) {
        return repository.getTermExisted(term, yearId);
    }

    public LiveData<Integer> getYearIdFromYear(int year) {
        return repository.getYearIdFromYear(year);
    }

    public void insertTerm(CourseTermEntity courseTermEntity) {
        repository.addTerm(courseTermEntity);
    }

    public LiveData<List<String>> getTermFromYearId(int yearId) {
        return repository.getTermFromYearId(yearId);
    }
}
