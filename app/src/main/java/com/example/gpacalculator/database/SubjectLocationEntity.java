package com.example.gpacalculator.database;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "subject_location")
public class SubjectLocationEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private int year;
    private String term;
    private String subject;
    private double credits;

    public SubjectLocationEntity(int id, int year, String term, String subject, double credits) {
        this.id = id;
        this.year = year;
        this.term = term;
        this.subject = subject;
        this.credits = credits;
    }

    @Ignore
    public SubjectLocationEntity(int year, String term, String subject, double credits) {
        this.year = year;
        this.term = term;
        this.subject = subject;
        this.credits = credits;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public double getCredits() {
        return credits;
    }

    public void setCredits(double credits) {
        this.credits = credits;
    }
}
