package com.example.gpacalculator.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.gpacalculator.database.CourseDetailEntity;
import com.example.gpacalculator.database.CourseEntity;
import com.example.gpacalculator.database.CourseRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class CourseViewModel extends AndroidViewModel {

    private LiveData<List<CourseEntity>> readAllData;
    private LiveData<List<String>> readAllCourse;
    private LiveData<List<String>> readAllTerm;
    private LiveData<List<Integer>> readAllYear;
    private CourseRepository repository;

    // Since AsyncTask is deprecated, we use this instead
    private final Executor executor = Executors.newSingleThreadExecutor();

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

    public LiveData<List<CourseDetailEntity>> loadAllDetailFromListCourseTermYear(List<String> courses, String term, int year) {
        return repository.loadAllDetailFromListCourseTermYear(courses, term, year);
    }

    public LiveData<List<CourseEntity>> getBigCourseFromTermId(int termId) {
        return repository.getBigCourseFromTermId(termId);
    }

    public List<String> extractCourse(List<CourseEntity> courseEntity) {
        List<String> courses = new ArrayList<>();
        for (CourseEntity i : courseEntity) {
            courses.add(i.getCourse());
        }
        return courses;
    }


    public void deleteCourseByCourseAndTermAndYear(String course, String term, int year) {
        repository.deleteCourseByCourseAndTermAndYear(course, term, year);
    }
}
