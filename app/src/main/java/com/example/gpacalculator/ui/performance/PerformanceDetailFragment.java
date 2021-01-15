package com.example.gpacalculator.ui.performance;

import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.gpacalculator.R;
import com.example.gpacalculator.database.CourseDetailEntity;
import com.example.gpacalculator.ui.grades.CalculateGPA;
import com.example.gpacalculator.viewmodels.CourseDetailViewModel;

import java.util.List;

public class PerformanceDetailFragment extends Fragment {

    private int tYear;
    private String tTerm, tCourse;
    private CourseDetailViewModel mCourseDetailViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_performance_detail, container, false);

        tYear = getArguments().getInt("year");
        tTerm = getArguments().getString("term");
        tCourse = getArguments().getString("course");
        final TextView tv_max = view.findViewById(R.id.tv_max);
        final TextView tv_predict = view.findViewById(R.id.tv_predict);

        mCourseDetailViewModel = new ViewModelProvider(this).get(CourseDetailViewModel.class);
        mCourseDetailViewModel.loadAllDetailFromCourseTermYear(tCourse, tTerm, tYear).observe(getViewLifecycleOwner(), new Observer<List<CourseDetailEntity>>() {
            @Override
            public void onChanged(List<CourseDetailEntity> courseDetailEntities) {
                Pair<Double, Double> getMax = CalculateGPA.getMax(CalculateGPA.rearrangeData(courseDetailEntities));
                if (getMax.first == -1) {
                    tv_max.setText("ERROR");
                } else {
                    tv_max.setText(getMax.first + "% / " + getMax.second);
                }

                Pair<Double, Double> getPrediction = CalculateGPA.predictGrade(CalculateGPA.rearrangeData(courseDetailEntities));
                if (getPrediction.first == -1) {
                    tv_predict.setText("ERROR");
                } else {
                    tv_predict.setText(getPrediction.first + "% / " + getPrediction.second);
                }
            }
        });

        return view;
    }
}
