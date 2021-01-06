package com.example.gpacalculator.database;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class SubjectLocationWithDetailEntity {

    @Embedded private SubjectLocationEntity subjectLocationEntity;
    @Relation(
            parentColumn = "id",
            entityColumn = "locationID"
    )
    private List<SubjectDetailEntity> subjectDetailEntities;

    public List<SubjectDetailEntity> getSubjectDetailEntities() {
        return this.subjectDetailEntities;
    }

    public void setSubjectDetailEntities(List<SubjectDetailEntity> subjectDetailEntities) {
        this.subjectDetailEntities = subjectDetailEntities;
    }

    public SubjectLocationEntity getSubjectLocationEntity() {
        return subjectLocationEntity;
    }

    public void setSubjectLocationEntity(SubjectLocationEntity subjectLocationEntity) {
        this.subjectLocationEntity = subjectLocationEntity;
    }
}
