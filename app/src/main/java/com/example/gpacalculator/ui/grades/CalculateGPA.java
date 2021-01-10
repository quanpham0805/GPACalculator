package com.example.gpacalculator.ui.grades;

import android.util.Pair;

import com.example.gpacalculator.database.CourseDetailEntity;
import com.example.gpacalculator.database.CourseEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static java.lang.Math.round;

public class CalculateGPA {

    private List<CourseDetailEntity> listCourseDetail;
    private List<CourseEntity> course;

    public CalculateGPA(List<CourseEntity> course, List<CourseDetailEntity> listCourseDetail) {
        this.course = course;
        this.listCourseDetail = listCourseDetail;
    }

    public class GradeAndWeight {
        public double grades, weight;

        public GradeAndWeight(double grades, double weight) {
            this.grades = grades;
            this.weight = weight;
        }
    }

    public double FourToHundred(double grade) {
        if (grade == 4) return 95;
        else if (3.9 <= grade) return 87;
        else if (3.7 <= grade) return 82;
        else if (3.3 <= grade) return 78;
        else if (3.0 <= grade) return 74.5;
        else if (2.7 <= grade) return 71;
        else if (2.3 <= grade) return 68;
        else if (2.0 <= grade) return 64.5;
        else if (1.7 <= grade) return 61;
        else if (1.3 <= grade) return 58;
        else if (1.0 <= grade) return 54.5;
        else if (0.7 <= grade) return 51;
        else return 0;

    }

    public double HundredToFour(double grade) {
        if (90 <= grade) return 4;
        else if (85 <= grade) return 3.9;
        else if (80 <= grade) return 3.7;
        else if (77 <= grade) return 3.3;
        else if (73 <= grade) return 3;
        else if (70 <= grade) return 2.7;
        else if (67 <= grade) return 2.3;
        else if (63 <= grade) return 2;
        else if (60 <= grade) return 1.7;
        else if (57 <= grade) return 1.3;
        else if (53 <= grade) return 1;
        else if (50 <= grade) return 0.7;
        else return 0;
    }


    // must give back gpa that is in the same order as the courses.
    public List<Pair<Double, Double>> getCourseGPA() {

        // first: %, second: 4.0
        HashMap<Integer, Pair<GradeAndWeight, GradeAndWeight>> myMap = new HashMap<>();
        GradeAndWeight keyHolderFirst, keyHolderSecond;

        for (CourseDetailEntity i : listCourseDetail) {
            Pair<GradeAndWeight, GradeAndWeight> tempEntry = myMap.get(i.getCourseId());
            if (i.getCourseScale() == 4) {
                keyHolderFirst = new GradeAndWeight((FourToHundred(i.getCourseMark()) * i.getCourseWeight()) / 100.0, i.getCourseWeight());
                keyHolderSecond =
                        new GradeAndWeight((i.getCourseMark() * i.getCourseWeight()) / 100.0, i.getCourseWeight());
            } else {
                keyHolderFirst =
                        new GradeAndWeight((i.getCourseMark() * i.getCourseWeight()) / 100.0, i.getCourseWeight());
                keyHolderSecond = new GradeAndWeight((HundredToFour(i.getCourseMark()) * i.getCourseWeight()) / 100.0, i.getCourseWeight());
            }

            if (tempEntry != null) {
                keyHolderFirst = new GradeAndWeight(tempEntry.first.grades + keyHolderFirst.grades, tempEntry.first.weight + keyHolderFirst.weight);
                keyHolderSecond = new GradeAndWeight(tempEntry.second.grades + keyHolderSecond.grades, tempEntry.second.weight + keyHolderSecond.weight);
                myMap.remove(i.getCourseId());
            }
            myMap.put(i.getCourseId(), Pair.create(keyHolderFirst, keyHolderSecond));
        }

        List<Pair<Double, Double>> courseGPA = new ArrayList<>();
        for (CourseEntity i : course) {
            Pair<GradeAndWeight, GradeAndWeight> tempEntry = myMap.get(i.getId());
            if (tempEntry == null) {
                courseGPA.add(Pair.create((double)-1, (double)-1));
            } else {
                double grades = tempEntry.first.grades;
                double weight = tempEntry.first.weight / 100.0;
                double gpa1 = (weight == 0 ? -1 : (round((grades/weight) * 100.0) / 100.0));
                grades = tempEntry.second.grades;
                weight = tempEntry.second.weight / 100.0;
                double gpa2 = (weight == 0 ? -1 : (round((grades/weight) * 10.0) / 10.0));
                courseGPA.add(Pair.create(gpa1, gpa2));
            }
        }
        return courseGPA;
    }
}
