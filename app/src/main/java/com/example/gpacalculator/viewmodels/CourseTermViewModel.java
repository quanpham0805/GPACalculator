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
    private CourseTermRepository repository;

    public CourseTermViewModel(@NonNull Application application) {
        super(application);

        this.repository = new CourseTermRepository(application);
        this.readAllData = this.repository.getReadAllData();
        this.readAllTerm = this.repository.getReadAllTerm();
    }

    public LiveData<List<CourseTermEntity>> getReadAllData() {
        return readAllData;
    }

    public LiveData<List<String>> getAllTerm() {
        return readAllTerm;
    }

    public LiveData<Boolean> checkTermExisted(String term) {
        return repository.getTermExisted(term);
    }

    public void insertTerm(CourseTermEntity courseTermEntity) {
        repository.addTerm(courseTermEntity);
    }
}
