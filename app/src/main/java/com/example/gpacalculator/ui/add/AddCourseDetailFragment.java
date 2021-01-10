package com.example.gpacalculator.ui.add;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.gpacalculator.R;
import com.example.gpacalculator.database.CourseDetailEntity;
import com.example.gpacalculator.viewmodels.CourseDetailViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AddCourseDetailFragment extends Fragment {

    private CourseDetailViewModel courseDetailViewModel;
    private List<Integer> readAllYear = new ArrayList<>();
    private List<String> readAllTerm = new ArrayList<>();
    private List<String> readAllCourse = new ArrayList<>();
    private Spinner spinner_year, spinner_term, spinner_course, spinner_scale;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_add_course_detail, container, false);

        // set up View Model
        courseDetailViewModel = new ViewModelProvider(this).get(CourseDetailViewModel.class);

        // setting up spinners with predefined options
        spinner_scale = (Spinner) view.findViewById(R.id.spinner_scale);
        spinner_scale.setAdapter(setUpStringAdapter(getContext(), Arrays.asList(new String[]{"4.0 scale", "100% scale"})));

        // setting up spinners with live data
        spinner_year = (Spinner) view.findViewById(R.id.spinner_year);
        spinner_term = (Spinner) view.findViewById(R.id.spinner_term);
        spinner_course = (Spinner) view.findViewById(R.id.spinner_course);

        courseDetailViewModel.getAllYear().observe(getViewLifecycleOwner(), new Observer<List<Integer>>() {
            @Override
            public void onChanged(List<Integer> integers) {
                readAllYear = integers;
                if (readAllYear == null || readAllYear.size() == 0) {
                    spinner_year.setAdapter(setUpNullCaseAdapter("EMPTY", getContext()));
                    spinner_term.setAdapter(setUpNullCaseAdapter("EMPTY", getContext()));
                    spinner_course.setAdapter(setUpNullCaseAdapter("EMPTY", getContext()));
                } else {
                    spinner_year.setAdapter(setUpIntegerAdapter(getContext(), readAllYear));
                    spinner_year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            spinnerYearSelectedListener();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }
            }
        });



        // setting up button
        Button btn = (Button) view.findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertDataToDatabase(view);
            }
        });


        return view;
    }

    private void spinnerYearSelectedListener() {

        courseDetailViewModel.getTermFromYear(Integer.parseInt(spinner_year.getSelectedItem().toString()))
                .observe(getViewLifecycleOwner(), new Observer<List<String>>() {
                    @Override
                    public void onChanged(List<String> strings) {
                        readAllTerm = strings;
                        if (readAllTerm == null || readAllTerm.size() == 0) {
                            spinner_term.setAdapter(setUpNullCaseAdapter("EMPTY", getContext()));
                            spinner_course.setAdapter(setUpNullCaseAdapter("EMPTY", getContext()));
                        } else {
                            spinner_term.setAdapter(setUpStringAdapter(getContext(), readAllTerm));
                            spinner_term.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    spinnerTermSelectedListener();
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });
                        }
                    }
                });

    }

    private void spinnerTermSelectedListener() {
        courseDetailViewModel.getCourseFromTermAndYear(spinner_term.getSelectedItem().toString(),
                Integer.parseInt(spinner_year.getSelectedItem().toString()))
                .observe(getViewLifecycleOwner(), new Observer<List<String>>() {
                    @Override
                    public void onChanged(List<String> mCourse) {
                        readAllCourse = mCourse;
                        if (readAllCourse == null || readAllCourse.size() == 0)
                            spinner_course.setAdapter(setUpNullCaseAdapter("EMPTY", getContext()));
                        else
                            spinner_course.setAdapter(setUpStringAdapter(getContext(), readAllCourse));
                    }
                });
    }

    private void insertDataToDatabase(final View view) {
        final String year = spinner_year.getSelectedItem().toString();
        final String term = spinner_term.getSelectedItem().toString();
        final String course = spinner_course.getSelectedItem().toString();
        final EditText title = (EditText) view.findViewById(R.id.course_title);
        final EditText grade = (EditText) view.findViewById(R.id.course_grade);
        final EditText weight = (EditText) view.findViewById(R.id.course_weight);
        String scale = spinner_scale.getSelectedItem().toString();
        final double mScale = (scale.contains("100") ? 100 : 4);
        if (inputCheck(title, grade, weight, mScale)) {
            courseDetailViewModel.getCourseIdFromCourseAndTermAndYear(course, term, Integer.parseInt(year))
                    .observe(this, new Observer<Integer>() {
                @Override
                public void onChanged(Integer courseId) {
                    CourseDetailEntity courseDetailEntity =
                            new CourseDetailEntity(
                                    title.getText().toString(),
                                    "",
                                    Double.parseDouble(grade.getText().toString()),
                                    mScale,
                                    Double.parseDouble(weight.getText().toString()),
                                    courseId);
                    courseDetailViewModel.addDetail(courseDetailEntity);

                    Toast.makeText(getContext(), "Added one", Toast.LENGTH_SHORT).show();

                    Bundle bundle = new Bundle();
                    bundle.putInt("year", Integer.parseInt(year));
                    bundle.putString("term", term);
                    bundle.putString("course", course);

                }
            });

        } else {
            Toast.makeText(this.getContext(),
                    "Some fields are not correct",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private boolean inputCheck(EditText title, EditText grade, EditText weight, double scale) {
        // year, term, courses are not empty
        boolean a = !(readAllYear == null || readAllYear.size() == 0);
        boolean b = !(readAllTerm == null || readAllTerm.size() == 0);
        boolean c = !(readAllCourse == null || readAllCourse.size() == 0);

        // title must not be empty, it can have duplicates though
        boolean d = !(TextUtils.isEmpty(title.getText().toString()));
        // grade and weight must be decimal numbers
        boolean e = isParsableDouble(grade.getText().toString());
        boolean f = isParsableDouble(weight.getText().toString());
        boolean g = a && b && c && d && e && f;
        if (g) {
            return inputValidate(
                    Double.parseDouble(grade.getText().toString()),
                    Double.parseDouble(weight.getText().toString()),
                    scale);
        } else return false;
    }

    private boolean inputValidate(double grade, double weight, double scale) {
        boolean a = ((0 <= weight) && (weight <= 100));
        boolean b = (((int) scale == 100) ? ((0 <= grade) && (grade <= 100)) : ((0 <= grade) && (grade <= 4)));
        return a && b;
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

    private boolean isParsableDouble(String input) {
        try {
            Double.parseDouble(input);
            return true;
        } catch (final NumberFormatException e) {
            return false;
        }
    }
}
