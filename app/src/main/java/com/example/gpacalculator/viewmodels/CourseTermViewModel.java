package com.example.gpacalculator.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.gpacalculator.database.CourseDetailEntity;
import com.example.gpacalculator.database.CourseEntity;
import com.example.gpacalculator.database.CourseTermEntity;
import com.example.gpacalculator.database.CourseTermRepository;

import java.util.ArrayList;
import java.util.List;

public class CourseTermViewModel extends AndroidViewModel {

    private final LiveData<List<CourseTermEntity>> readAllData;
    private final LiveData<List<String>> readAllTerm;
    private final LiveData<List<Integer>> readAllYear;
    private final CourseTermRepository repository;

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

    public LiveData<List<String>> getTermFromYear(int year) {
        return repository.getTermFromYear(year);
    }

    public void deleteTermByTermAndYear(String term, int year) {
        repository.deleteTermByTermAndYear(term, year);
    }

    public void insertTerm(CourseTermEntity courseTermEntity) {
        repository.addTerm(courseTermEntity);
    }

    public LiveData<List<String>> getTermFromYearId(int yearId) {
        return repository.getTermFromYearId(yearId);
    }

    public List<String> extractCourseName(List<CourseEntity> courseEntity) {
        List<String> courses = new ArrayList<>();
        for (CourseEntity i : courseEntity) {
            courses.add(i.getCourse());
        }
        return courses;
    }

    public List<String> extractTermName(List<CourseTermEntity> courseTermEntities) {
        List<String> courseTerm = new ArrayList<>();
        for (CourseTermEntity i : courseTermEntities) {
            courseTerm.add(i.getTerm());
        }
        return courseTerm;
    }

    public LiveData<List<CourseTermEntity>> getListTermFromYear(int year) {
        return repository.getListTermFromYear(year);
    }

    public LiveData<List<CourseEntity>> getListCourseFromListTermYear(List<String> term, int year) {
        return repository.getListCourseFromListTermYear(term, year);
    }

    public LiveData<List<CourseDetailEntity>> loadAllDetailFromListCourseListTermYear(List<String> courses, List<String> term, int year) {
        return repository.loadAllDetailFromListCourseListTermYear(courses, term, year);
    }
}
