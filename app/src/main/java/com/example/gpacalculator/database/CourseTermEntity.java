package com.example.gpacalculator.database;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(
        tableName = "course_term",
        foreignKeys = @ForeignKey(entity = CourseYearEntity.class, parentColumns = "id", childColumns = "yearId", onDelete = CASCADE)
)
public class CourseTermEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String term;

    // foreign key
    private int yearId;

    public CourseTermEntity(int id, String term, int yearId) {
        this.id = id;
        this.term = term;
        this.yearId = yearId;
    }

    @Ignore
    public CourseTermEntity(String term, int yearId) {
        this.term = term;
        this.yearId = yearId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public int getYearId() {
        return yearId;
    }

    public void setYearId(int yearId) {
        this.yearId = yearId;
    }
}
