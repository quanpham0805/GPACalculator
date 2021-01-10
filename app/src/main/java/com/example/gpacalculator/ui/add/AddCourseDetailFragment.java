package com.example.gpacalculator.ui.add;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.gpacalculator.R;
import com.example.gpacalculator.viewmodels.CourseDetailViewModel;

import java.util.ArrayList;
import java.util.List;

public class AddCourseDetailFragment extends Fragment {

    private CourseDetailViewModel courseDetailViewModel;
    private List<Integer> readAllYear = new ArrayList<>();
    private List<String> readAllTerm = new ArrayList<>();
    private List<String> readAllCourse = new ArrayList<>();
    private Spinner spinner_year, spinner_term, spinner_course;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_add_course_detail, container, false);

        // set up View Model
        courseDetailViewModel = new ViewModelProvider(this).get(CourseDetailViewModel.class);

        // setting up spinners with live data
        spinner_year = (Spinner) view.findViewById(R.id.spinner_year);
        spinner_term = (Spinner) view.findViewById(R.id.spinner_term);
        spinner_course = (Spinner) view.findViewById(R.id.spinner_course);

        // TODO: take data here

        Button btn = (Button) view.findViewById(R.id.button);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertDataToDatabase(view);
            }
        });


        return view;
    }

    private void insertDataToDatabase(final View view) {

    }

    private ArrayAdapter<String> setUpNullCaseAdapter(String msg, Context context) {
        List<String> nullCase = new ArrayList<>();
        nullCase.add(msg);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, nullCase);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return adapter;
    }

    private ArrayAdapter<String> setUpStringAdapter(Context context, List<String> res) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, res);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return adapter;
    }

    private ArrayAdapter<Integer> setUpIntegerAdapter(Context context, List<Integer> res) {
        ArrayAdapter<Integer> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, res);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return adapter;
    }

    private boolean isParsableInteger(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (final NumberFormatException e) {
            return false;
        }
    }
}
