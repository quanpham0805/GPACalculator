package com.example.gpacalculator.ui.delete;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.gpacalculator.R;
import com.example.gpacalculator.viewmodels.CourseDetailViewModel;

import java.util.ArrayList;
import java.util.List;

public class DeleteCourseDetailFragment extends Fragment {

    private CourseDetailViewModel courseDetailViewModel;
    private List<Integer> readAllYear = new ArrayList<>();
    private List<String> readAllTerm = new ArrayList<>();
    private List<String> readAllCourse = new ArrayList<>();
    private List<String> readAllDetail = new ArrayList<>();
    private Spinner spinner_year, spinner_term, spinner_course, spinner_detail;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_delete_course_detail, container, false);

        // set up view model
        courseDetailViewModel = new ViewModelProvider(this).get(CourseDetailViewModel.class);

        // setting up spinners with live data
        spinner_year = view.findViewById(R.id.spinner_year);
        spinner_term = view.findViewById(R.id.spinner_term);
        spinner_course = view.findViewById(R.id.spinner_course);
        spinner_detail = view.findViewById(R.id.spinner_course_detail);

        courseDetailViewModel.getAllYear().observe(getViewLifecycleOwner(), new Observer<List<Integer>>() {
            @Override
            public void onChanged(List<Integer> integers) {
                readAllYear = integers;
                if (readAllYear == null || readAllYear.size() == 0) {
                    spinner_year.setAdapter(setUpNullCaseAdapter("EMPTY", getContext()));
                    spinner_term.setAdapter(setUpNullCaseAdapter("EMPTY", getContext()));
                    spinner_course.setAdapter(setUpNullCaseAdapter("EMPTY", getContext()));
                    spinner_detail.setAdapter(setUpNullCaseAdapter("EMPTY", getContext()));
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

        // set up delete button
        Button btn = view.findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteDataFromDatabase(view);
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
                            spinner_detail.setAdapter(setUpNullCaseAdapter("EMPTY", getContext()));
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
                    public void onChanged(List<String> strings) {
                        readAllCourse = strings;
                        if (readAllCourse == null || readAllCourse.size() == 0) {
                            spinner_course.setAdapter(setUpNullCaseAdapter("EMPTY", getContext()));
                            spinner_detail.setAdapter(setUpNullCaseAdapter("EMPTY", getContext()));
                        } else {
                            spinner_course.setAdapter(setUpStringAdapter(getContext(), readAllCourse));
                            spinner_course.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    spinnerCourseSelectedListener();
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });
                        }
                    }
                });
    }

    private void spinnerCourseSelectedListener() {
        courseDetailViewModel.getMarkNameFromCourseTermYear(spinner_course.getSelectedItem().toString(),
                spinner_term.getSelectedItem().toString(),
                Integer.parseInt(spinner_year.getSelectedItem().toString()))
                .observe(getViewLifecycleOwner(), new Observer<List<String>>() {
                    @Override
                    public void onChanged(List<String> strings) {
                        readAllDetail = strings;
                        if (readAllDetail == null || readAllDetail.size() == 0) {
                            spinner_detail.setAdapter(setUpNullCaseAdapter("EMPTY", getContext()));
                        } else {
                            spinner_detail.setAdapter(setUpStringAdapter(getContext(), readAllDetail));
                        }
                    }
                });
    }

    private void deleteDataFromDatabase(final View view) {
        if (inputCheck()) {
            final int mYear = Integer.parseInt(spinner_year.getSelectedItem().toString());
            final String mTerm = spinner_term.getSelectedItem().toString();
            final String mCourse = spinner_course.getSelectedItem().toString();
            final String mDetail = spinner_detail.getSelectedItem().toString();

            courseDetailViewModel.deleteDetail(mDetail, mCourse, mTerm, mYear);

            NavController navController = Navigation.findNavController(view);
            if (navController.popBackStack()) {
                Toast.makeText(getContext(), "Deleted one", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this.getContext(),
                    "Some fields are not correct",
                    Toast.LENGTH_SHORT).show();
        }

    }

    private boolean inputCheck() {
        // year is not empty or null
        boolean a = !(readAllYear == null || readAllYear.size() == 0);
        // term is not empty or null
        boolean b = !(readAllTerm == null || readAllTerm.size() == 0);
        // course is not empty or null
        boolean c = !(readAllCourse == null || readAllCourse.size() == 0);
        // detail is not empty or null
        boolean d = !(readAllDetail == null || readAllDetail.size() == 0);

        return a && b && c && d;
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
