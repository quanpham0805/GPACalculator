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
    private LiveData<List<String>> readAllTerm;
    private LiveData<List<Integer>> readAllYear;
    private CourseRepository repository;

    public CourseViewModel(@NonNull Application application) {
        super(application);

        this.repository = new CourseRepository(application);
        this.readAllData = this.repository.getReadAllData();
        this.readAllCourse = this.repository.getReadAllCourse();
        this.readAllTerm = this.repository.getReadAllTerm();
        this.readAllYear = this.repository.getReadAllYear();
    }

    public LiveData<List<CourseEntity>> getReadAllData() {
        return readAllData;
    }

    public LiveData<List<String>> getAllCourse() {
        return readAllCourse;
    }

    public LiveData<List<String>> getAllTerm() {
        return readAllTerm;
    }

    public LiveData<List<Integer>> getAllYear() {
        return readAllYear;
    }

    public LiveData<Boolean> checkCourseExisted(String course, int termId) {
        return repository.checkCourseExisted(course, termId);
    }

    public LiveData<List<String>> getCourseFromTermId(int termId) {
        return repository.getCourseFromTermId(termId);
    }

    public void insertCourse(CourseEntity courseEntity) {
        repository.addCourse(courseEntity);
    }

    public LiveData<Integer> getTermIdFromTermAndYear(String term, int year) {
        return repository.getTermIdFromTermAndYear(term, year);
    }

    public LiveData<List<String>> getTermFromYear(int year) {
        return repository.getTermFromYear(year);
    }
}
