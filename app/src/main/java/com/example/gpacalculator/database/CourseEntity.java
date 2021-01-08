package com.example.gpacalculator.database;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(
        tableName = "course",
        foreignKeys = @ForeignKey(entity = CourseTermEntity.class, parentColumns = "id", childColumns = "termId", onDelete = CASCADE)
)
public class CourseEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String course;
    private double credits;

    // foreign key
    private int termId;

    public CourseEntity(int id, String course, double credits, int termId) {
        this.id = id;
        this.course = course;
        this.credits = credits;
        this.termId = termId;
    }

    @Ignore
    public CourseEntity(String course, double credits, int termId) {
        this.course = course;
        this.credits = credits;
        this.termId = termId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getCredits() {
        return credits;
    }

    public void setCredits(double credits) {
        this.credits = credits;
    }

    public int getTermId() {
        return termId;
    }

    public void setTermId(int termId) {
        this.termId = termId;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }
}
