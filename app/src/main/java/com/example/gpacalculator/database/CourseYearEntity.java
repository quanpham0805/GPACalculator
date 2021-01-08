package com.example.gpacalculator.database;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "course_year")
public class CourseYearEntity {

    @PrimaryKey(autoGenerate = true) private int id;
    private int year;

    public CourseYearEntity(int id, int year) {
        this.id = id;
        this.year = year;
    }

    @Ignore
    public CourseYearEntity(int year) {
        this.year = year;
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
}
