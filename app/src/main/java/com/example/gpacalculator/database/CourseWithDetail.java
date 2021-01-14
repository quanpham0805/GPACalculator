package com.example.gpacalculator.database;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class CourseWithDetail {

    @Embedded
    private CourseEntity CourseEntity;
    @Relation(
            parentColumn = "id",
            entityColumn = "courseId"
    )
    private List<CourseDetailEntity> CourseDetailEntity;

    public com.example.gpacalculator.database.CourseEntity getCourseEntity() {
        return CourseEntity;
    }

    public void setCourseEntity(com.example.gpacalculator.database.CourseEntity courseEntity) {
        CourseEntity = courseEntity;
    }

    public List<com.example.gpacalculator.database.CourseDetailEntity> getCourseDetailEntity() {
        return CourseDetailEntity;
    }

    public void setCourseDetailEntity(List<com.example.gpacalculator.database.CourseDetailEntity> courseDetailEntity) {
        CourseDetailEntity = courseDetailEntity;
    }
}
