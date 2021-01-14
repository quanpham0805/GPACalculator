package com.example.gpacalculator.ui.grades;

import android.util.Pair;

import com.example.gpacalculator.database.CourseDetailEntity;
import com.example.gpacalculator.database.CourseEntity;
import com.example.gpacalculator.database.CourseTermEntity;
import com.example.gpacalculator.database.CourseYearEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static java.lang.Math.round;

public class CalculateGPA {

    public static double FourToHundred(double grade) {
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

    public static double HundredToFour(double grade) {
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

    public static GradeAndWeight storeProductGradeWeight(double grades, double weight) {
        return new GradeAndWeight(grades * weight / 100.0, weight);
    }

    // Then after that, we will calculate the gpa of each course.
    // The formula is SumOf(mark * weight) / SumOf(weight).
    // We calculated the sum while running the first loop to save some time.
    public static List<Pair<Double, Double>> getCourseGPA(List<CourseEntity> course, List<CourseDetailEntity> listCourseDetail) {

        HashMap<Integer, Pair<GradeAndWeight, GradeAndWeight>> myMap = new HashMap<>();
        GradeAndWeight keyHolderFirst, keyHolderSecond;
        Pair<GradeAndWeight, GradeAndWeight> tempEntry;

        // First iteration, map data by looping thru course detail and calculate SumOf.
        for (CourseDetailEntity i : listCourseDetail) {
            tempEntry = myMap.get(i.getCourseId());

            // the entry is stored using 4.0 scale. Hence, we need to convert 4.0 -> 100% for first holder.
            if (i.getCourseScale() == 4) {
                keyHolderFirst = storeProductGradeWeight(FourToHundred(i.getCourseMark()), i.getCourseWeight());
                keyHolderSecond = storeProductGradeWeight(i.getCourseMark(), i.getCourseWeight());
            }
            // the entry is stored using 100% scale. Hence, we need to convert 100% -> 4.0 for second holder.
            else {
                keyHolderFirst = storeProductGradeWeight(i.getCourseMark(), i.getCourseWeight());
                keyHolderSecond = storeProductGradeWeight(HundredToFour(i.getCourseMark()), i.getCourseWeight());
            }

            // if there is one entry already existed, we recalculate the SumOf, then delete
            if (tempEntry != null) {
                keyHolderFirst = new GradeAndWeight(tempEntry.first.grades + keyHolderFirst.grades, tempEntry.first.weight + keyHolderFirst.weight);
                keyHolderSecond = new GradeAndWeight(tempEntry.second.grades + keyHolderSecond.grades, tempEntry.second.weight + keyHolderSecond.weight);
                myMap.remove(i.getCourseId());
            }
            // store the data in hashmap.
            myMap.put(i.getCourseId(), Pair.create(keyHolderFirst, keyHolderSecond));
        }

        // Second iteration, calculating the gpa and store them into a list of pair of 2 types of gpa.
        List<Pair<Double, Double>> courseGPA = new ArrayList<>();
        for (CourseEntity i : course) {
            tempEntry = myMap.get(i.getId());

            // if there is no mark for a course, just put it -1 so the RecyclerView will give "NO DATA"
            // since GPA can't be negative, -1 is a good value.
            if (tempEntry == null) {
                courseGPA.add(Pair.create((double) -1, (double) -1));
            } else {
                double grades = tempEntry.first.grades;
                double weight = tempEntry.first.weight / 100.0;

                // if weight is equal to 0, all the marks are not counted. Hence we will put -1.
                // otherwise just do the formula. Here since we already calculated the sum, just take
                // the quotient.
                double gpa1 = (weight == 0 ? -1 : (round((grades / weight) * 100.0) / 100.0));
                grades = tempEntry.second.grades;
                weight = tempEntry.second.weight / 100.0;
                double gpa2 = (weight == 0 ? -1 : (round((grades / weight) * 10.0) / 10.0));
                courseGPA.add(Pair.create(gpa1, gpa2));
            }
        }
        // must give back gpa that is in the same order as the courses.
        return courseGPA;
    }

    // Description:
    // First iterate thru the list of course detail that is provided
    // We have to match which course the course details belong to by
    // using a map, where the key is the courseId, while the value is
    // the pair of GradeAndWeight class, where the first component of the
    // pair is grade in 100% scale, while the second is grade in 4.0 scale

    // Description:
    // First, we will use the getCourseGPA to get the GPA for all the courses.
    // Then, with the same logic of getCourseGPA, we now need to map the courses to the right term.
    // After that, we will calculate Term GPA. In this case, number of credits will be the weight of
    // each course.
    public static List<Pair<Double, Double>> getTermGPA(List<CourseTermEntity> courseTerm, List<CourseEntity> course, List<CourseDetailEntity> courseDetail) {
        return getTermGPAWithTotCredits(courseTerm, course, courseDetail).first;
    }

    public static Pair<List<Pair<Double, Double>>, List<Double>> getTermGPAWithTotCredits(List<CourseTermEntity> courseTerm, List<CourseEntity> course, List<CourseDetailEntity> courseDetail) {
        // using the getCourseGPA function, we now obtain the GPA for all the courses.
        List<Pair<Double, Double>> courseGPA = getCourseGPA(course, courseDetail);
        HashMap<Integer, Pair<GradeAndWeight, GradeAndWeight>> myMap = new HashMap<>();
        GradeAndWeight keyHolderFirst, keyHolderSecond;
        Pair<GradeAndWeight, GradeAndWeight> tempEntry;


        // First iteration, map course gpa to term.
        for (int i = 0; i < course.size(); i++) {
            tempEntry = myMap.get(course.get(i).getTermId());

            // if there is no grade data, just put whatever grade and weight = 0.
            // For simplicity, we will just put the same -1 and it won't matter at all
            // since it's a multiplication by 0.
            if (courseGPA.get(i).first == -1) {
                keyHolderFirst = storeProductGradeWeight(courseGPA.get(i).first, 0);
                keyHolderSecond = storeProductGradeWeight(courseGPA.get(i).second, 0);
            } else {
                // if there is grade data, we will just store it normally, note that weight = credits
                keyHolderFirst = storeProductGradeWeight(courseGPA.get(i).first, course.get(i).getCredits());
                keyHolderSecond = storeProductGradeWeight(courseGPA.get(i).second, course.get(i).getCredits());
            }

            // if there is one entry already exist, we recalculate the SumOf, then delete it.
            if (tempEntry != null) {
                keyHolderFirst = new GradeAndWeight(tempEntry.first.grades + keyHolderFirst.grades, tempEntry.first.weight + keyHolderFirst.weight);
                keyHolderSecond = new GradeAndWeight(tempEntry.second.grades + keyHolderSecond.grades, tempEntry.second.weight + keyHolderSecond.weight);
                myMap.remove(course.get(i).getTermId());
            }

            // store data in hashmap.
            myMap.put(course.get(i).getTermId(), Pair.create(keyHolderFirst, keyHolderSecond));
        }


        List<Pair<Double, Double>> termGPA = new ArrayList<>();
        List<Double> termCredits = new ArrayList<>();
        // Second iteration, get term gpa.
        for (CourseTermEntity i : courseTerm) {
            tempEntry = myMap.get(i.getId());

            // if there is no entry for a term, just put -1, same logic as the courseGPA function.
            if (tempEntry == null) {
                termGPA.add(Pair.create((double) -1, (double) -1));
                termCredits.add((double) -1);
            } else {
                double grades = tempEntry.first.grades;
                double weight = tempEntry.first.weight / 100.0;

                // if weight is equal to 0, all the marks are not counted. Hence we will put -1.
                // otherwise just do the formula. Here since we already calculated the sum, just take
                // the quotient.
                double gpa1 = (weight == 0 ? -1 : (round((grades / weight) * 100.0) / 100.0));
                grades = tempEntry.second.grades;
                weight = tempEntry.second.weight / 100.0;
                double gpa2 = (weight == 0 ? -1 : (round((grades / weight) * 10.0) / 10.0));
                termGPA.add(Pair.create(gpa1, gpa2));
                termCredits.add(weight);
            }
        }

        return Pair.create(termGPA, termCredits);

    }

    public static List<Pair<Double, Double>> getYearGPA(List<CourseYearEntity> courseYear, List<CourseTermEntity> courseTerm, List<CourseEntity> course, List<CourseDetailEntity> courseDetail) {
        return getYearGPAWithCredits(courseYear, courseTerm, course, courseDetail).first;
    }

    public static Pair<List<Pair<Double, Double>>, List<Double>> getYearGPAWithCredits(List<CourseYearEntity> courseYear, List<CourseTermEntity> courseTerm, List<CourseEntity> course, List<CourseDetailEntity> courseDetail) {
        Pair<List<Pair<Double, Double>>, List<Double>> termGPA = getTermGPAWithTotCredits(courseTerm, course, courseDetail);
        HashMap<Integer, Pair<GradeAndWeight, GradeAndWeight>> myMap = new HashMap<>();
        GradeAndWeight keyHolderFirst, keyHolderSecond;
        Pair<GradeAndWeight, GradeAndWeight> tempEntry;

        for (int i = 0; i < courseTerm.size(); i++) {
            tempEntry = myMap.get(courseTerm.get(i).getYearId());

            if (termGPA.first.get(i).first == -1) {
                keyHolderFirst = storeProductGradeWeight(termGPA.first.get(i).first, 0);
                keyHolderSecond = storeProductGradeWeight(termGPA.first.get(i).second, 0);
            } else {
                keyHolderFirst = storeProductGradeWeight(termGPA.first.get(i).first, termGPA.second.get(i));
                keyHolderSecond = storeProductGradeWeight(termGPA.first.get(i).second, termGPA.second.get(i));
            }

            if (tempEntry != null) {
                keyHolderFirst = new GradeAndWeight(tempEntry.first.grades + keyHolderFirst.grades, tempEntry.first.weight + keyHolderFirst.weight);
                keyHolderSecond = new GradeAndWeight(tempEntry.second.grades + keyHolderSecond.grades, tempEntry.second.weight + keyHolderSecond.weight);
                myMap.remove(courseTerm.get(i).getYearId());
            }

            myMap.put(courseTerm.get(i).getYearId(), Pair.create(keyHolderFirst, keyHolderSecond));
        }


        List<Pair<Double, Double>> yearGPA = new ArrayList<>();
        List<Double> yearCredits = new ArrayList<>();
        for (CourseYearEntity i : courseYear) {
            tempEntry = myMap.get(i.getId());

            if (tempEntry == null) {
                yearGPA.add(Pair.create((double) -1, (double) -1));
                yearCredits.add((double) -1);
            } else {
                double grades = tempEntry.first.grades;
                double weight = tempEntry.first.weight / 100.0;
                double gpa1 = (weight == 0 ? -1 : (round((grades / weight) * 100.0) / 100.0));
                grades = tempEntry.second.grades;
                weight = tempEntry.second.weight / 100.0;
                double gpa2 = (weight == 0 ? -1 : (round((grades / weight) * 10.0) / 10.0));
                yearGPA.add(Pair.create(gpa1, gpa2));
                yearCredits.add(weight);
            }
        }

        return Pair.create(yearGPA, yearCredits);
    }

    public static Pair<Double, Double> getCGPA(List<CourseYearEntity> courseYear, List<CourseTermEntity> courseTerm, List<CourseEntity> course, List<CourseDetailEntity> courseDetail) {
        Pair<List<Pair<Double, Double>>, List<Double>> yearGPA = getYearGPAWithCredits(courseYear, courseTerm, course, courseDetail);
        double sumHundred = 0;
        double sumFour = 0;
        double credits = 0;

        for (int i = 0; i < yearGPA.first.size(); i++) {
            if (yearGPA.first.get(i).first != -1) {
                sumHundred += yearGPA.first.get(i).first * yearGPA.second.get(i);
                sumFour += yearGPA.first.get(i).second * yearGPA.second.get(i);
                credits += yearGPA.second.get(i);
            }
        }

        if (credits == 0) {
            return Pair.create((double) -1, (double) -1);
        } else {
            return Pair.create((round((sumHundred / credits) * 100.0) / 100.0), (round((sumFour / credits) * 100.0) / 100.0));
        }
    }

    public static class GradeAndWeight {
        public double grades, weight;

        public GradeAndWeight(double grades, double weight) {
            this.grades = grades;
            this.weight = weight;
        }
    }
}
