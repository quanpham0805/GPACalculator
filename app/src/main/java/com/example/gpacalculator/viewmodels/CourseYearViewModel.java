package com.example.gpacalculator.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.gpacalculator.database.CourseDetailEntity;
import com.example.gpacalculator.database.CourseEntity;
import com.example.gpacalculator.database.CourseTermEntity;
import com.example.gpacalculator.database.CourseYearEntity;
import com.example.gpacalculator.database.CourseYearRepository;

import java.util.ArrayList;
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

    public LiveData<Integer> getYearIdFromYear(int year) {
        return repository.getYearIdFromYear(year);
    }

    public LiveData<Boolean> checkYearExisted(int year) {
        return repository.getYearExisted(year);
    }

    public void insertYear(CourseYearEntity courseYearEntity) {
        repository.addYear(courseYearEntity);
    }

    public void deleteYearByYear(int year) {
        repository.deleteYearByYear(year);
    }

    public LiveData<List<CourseTermEntity>> getListTermFromListYear(List<Integer> year) {
        return repository.getListTermFromListYear(year);
    }

    public LiveData<List<CourseEntity>> getListCourseFromListTermListYear(List<String> term, List<Integer> year) {
        return repository.getListCourseFromListTermListYear(term, year);
    }

    public LiveData<List<CourseDetailEntity>> loadAllDetailFromListCourseListTermListYear(List<String> courses, List<String> term, List<Integer> year) {
        return repository.loadAllDetailFromListCourseListTermListYear(courses, term, year);
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

    public List<Integer> extractYear(List<CourseYearEntity> courseYearEntities) {
        List<Integer> courseYear = new ArrayList<>();
        for (CourseYearEntity i : courseYearEntities) {
            courseYear.add(i.getYear());
        }
        return courseYear;
    }
}
