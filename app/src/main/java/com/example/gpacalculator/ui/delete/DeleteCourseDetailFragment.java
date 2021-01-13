package com.example.gpacalculator.ui.delete;

import android.widget.Spinner;

import androidx.fragment.app.Fragment;

import com.example.gpacalculator.viewmodels.CourseDetailViewModel;

import java.util.ArrayList;
import java.util.List;

public class DeleteCourseDetailFragment extends Fragment {

    private CourseDetailViewModel courseDetailViewModel;
    private List<Integer> readAllYear = new ArrayList<>();
    private List<String> readAllTerm = new ArrayList<>();
    private List<String> readAllCourse = new ArrayList<>();
    // TODO: Pair of String? to distinguish between same name.
    private List<String> readAllDetail = new ArrayList<>();
    private Spinner spinner_year, spinner_term, spinner_course;



}
