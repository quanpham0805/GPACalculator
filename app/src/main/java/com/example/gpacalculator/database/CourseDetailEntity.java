package com.example.gpacalculator.database;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(
        tableName = "course_detail",
        foreignKeys = @ForeignKey(entity = CourseEntity.class, parentColumns = "id", childColumns = "courseId", onDelete = CASCADE)
)
public class CourseDetailEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String courseMarkName;
    private String courseMarkNotes;
    private double courseMark;
    private double courseScale;
    private double courseWeight;

    // act as a foreign key
    private int courseId;

    public CourseDetailEntity(int id, String courseMarkName, String courseMarkNotes, double courseMark, double courseScale, double courseWeight, int courseId) {
        this.id = id;
        this.courseMarkName = courseMarkName;
        this.courseMarkNotes = courseMarkNotes;
        this.courseMark = courseMark;
        this.courseScale = courseScale;
        this.courseWeight = courseWeight;
        this.courseId = courseId;
    }

    @Ignore
    public CourseDetailEntity(String courseMarkName, String courseMarkNotes, double courseMark, double courseScale, double courseWeight, int courseId) {
        this.courseMarkName = courseMarkName;
        this.courseMarkNotes = courseMarkNotes;
        this.courseMark = courseMark;
        this.courseScale = courseScale;
        this.courseWeight = courseWeight;
        this.courseId = courseId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCourseMarkName() {
        return courseMarkName;
    }

    public void setCourseMarkName(String courseMarkName) {
        this.courseMarkName = courseMarkName;
    }

    public String getCourseMarkNotes() {
        return courseMarkNotes;
    }

    public void setCourseMarkNotes(String courseMarkNotes) {
        this.courseMarkNotes = courseMarkNotes;
    }

    public double getCourseMark() {
        return courseMark;
    }

    public void setCourseMark(double courseMark) {
        this.courseMark = courseMark;
    }

    public double getCourseScale() {
        return courseScale;
    }

    public void setCourseScale(double courseScale) {
        this.courseScale = courseScale;
    }

    public double getCourseWeight() {
        return courseWeight;
    }

    public void setCourseWeight(double courseWeight) {
        this.courseWeight = courseWeight;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }
}