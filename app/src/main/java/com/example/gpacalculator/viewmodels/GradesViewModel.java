package com.example.gpacalculator.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.gpacalculator.database.MainDatabase;
import com.example.gpacalculator.database.SubjectLocationDAO;
import com.example.gpacalculator.database.SubjectLocationEntity;
import com.example.gpacalculator.database.SubjectLocationRepository;

import java.util.List;

public class GradesViewModel extends AndroidViewModel {

    private LiveData<List<SubjectLocationEntity>> readAllData;
    private SubjectLocationDAO subjectLocationDao;
    private SubjectLocationRepository repository;

    public GradesViewModel(@NonNull Application application) {
        super(application);


        this.subjectLocationDao = MainDatabase.getInstance(application).subjectLocationDAO();
        this.repository = new SubjectLocationRepository(this.subjectLocationDao);
        this.readAllData = this.repository.getReadAllData();
    }



    public void insertLocation(SubjectLocationEntity subjectLocationEntity) {
        repository.addLocation(subjectLocationEntity);
    }
}
