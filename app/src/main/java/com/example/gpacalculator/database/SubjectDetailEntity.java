package com.example.gpacalculator.database;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(
        tableName = "subject_detail",
        foreignKeys = @ForeignKey(entity = SubjectLocationEntity.class, parentColumns = "id", childColumns = "locationID", onDelete = CASCADE)
)
public class SubjectDetailEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;

    // act as a foreign key
    private int locationID;

    private String subjectName;
    private String markNotes;
    private double mark;
    private double scale;
    private double weight;

    public SubjectDetailEntity(int id, int locationID, String subjectName, String markNotes, double mark, double scale, double weight) {
        this.id = id;
        this.locationID = locationID;
        this.subjectName = subjectName;
        this.markNotes = markNotes;
        this.mark = mark;
        this.scale = scale;
        this.weight = weight;
    }

    @Ignore
    public SubjectDetailEntity(int locationID, String subjectName, String markNotes, int mark, double scale, double weight) {
        this.locationID = locationID;
        this.subjectName = subjectName;
        this.markNotes = markNotes;
        this.mark = mark;
        this.scale = scale;
        this.weight = weight;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLocationID() {
        return locationID;
    }

    public void setLocationID(int locationID) {
        this.locationID = locationID;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getMarkNotes() {
        return markNotes;
    }

    public void setMarkNotes(String markNotes) {
        this.markNotes = markNotes;
    }

    public double getMark() {
        return mark;
    }

    public void setMark(double mark) {
        this.mark = mark;
    }

    public double getScale() {
        return scale;
    }

    public void setScale(double scale) {
        this.scale = scale;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
}
