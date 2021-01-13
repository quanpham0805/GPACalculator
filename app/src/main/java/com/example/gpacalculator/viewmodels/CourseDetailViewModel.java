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

    public CourseDetailViewModel(@NonNull Application application) {
        super(application);

        this.repository = new CourseDetailRepository(application);
    }

    public void addDetail(CourseDetailEntity courseDetailEntity) {
        repository.addDetail(courseDetailEntity);
    }

    public LiveData<List<Integer>> getAllYear() {
        return repository.loadAllYear();
    }

    public LiveData<List<String>> getTermFromYear(int year) {
        return repository.getTermFromYear(year);
    }

    public LiveData<List<String>> getCourseFromTermAndYear(String term, int year) {
        return repository.getCourseFromTermAndYear(term, year);
    }

    public LiveData<Integer> getCourseIdFromCourseAndTermAndYear(String course, String term, int year) {
        return repository.getCourseIdFromCourseAndTermAndYear(course, term, year);
    }

    public LiveData<List<CourseDetailEntity>> loadAllDetailFromCourseTermYear(String course, String term, int year) {
        return repository.loadAllDetailFromCourseTermYear(course, term, year);
    }

    public LiveData<Boolean> detailExisted(String courseDetail, int courseId) {
        return repository.detailExisted(courseDetail, courseId);
    }

    public LiveData<List<String>> getMarkNameFromCourseTermYear(String course, String term, int year) {
        return repository.getMarkNameFromCourseTermYear(course, term, year);
    }

    public void deleteDetail(String name, String course, String term, int year) {
        repository.deleteDetail(name, course, term, year);
    }
}
