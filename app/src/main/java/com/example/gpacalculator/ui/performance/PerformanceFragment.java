package com.example.gpacalculator.ui.performance;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import androidx.navigation.ui.NavigationUI;

import com.example.gpacalculator.R;
import com.example.gpacalculator.viewmodels.CourseViewModel;

import java.util.ArrayList;
import java.util.List;

public class PerformanceFragment extends Fragment {

    private CourseViewModel courseViewModel;
    private List<Integer> readAllYear = new ArrayList<>();
    private List<String> readAllTerm = new ArrayList<>();
    private List<String> readAllCourse = new ArrayList<>();
    private Spinner spinner_year, spinner_term, spinner_course;

    // The onCreateView is exactly the same as DeleteCourseFragment.
    // I should have created some base classes then extends it :(
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_performance, container, false);

        // set up View Model
        courseViewModel = new ViewModelProvider(this).get(CourseViewModel.class);

        // setting up spinner with year, term and course live data
        spinner_year = view.findViewById(R.id.spinner_year);
        spinner_term = view.findViewById(R.id.spinner_term);
        spinner_course = view.findViewById(R.id.spinner_course);
        courseViewModel.getAllYear().observe(getViewLifecycleOwner(), new Observer<List<Integer>>() {
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

        // set up delete button
        Button btn = view.findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seePerformance(view);
            }
        });


        return view;
    }

    private void spinnerYearSelectedListener() {

        courseViewModel.getTermFromYear(Integer.parseInt(spinner_year.getSelectedItem().toString()))
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
        courseViewModel
                .getTermIdFromTermAndYear(spinner_term.getSelectedItem().toString(),
                        Integer.parseInt(spinner_year.getSelectedItem().toString()))
                .observe(getViewLifecycleOwner(), new Observer<Integer>() {
                    @Override
                    public void onChanged(Integer newTermId) {
                        courseViewModel.getCourseFromTermId(newTermId)
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
                });
    }

    private void seePerformance(final View view) {
//        Toast.makeText(getContext(), "Performance is working", Toast.LENGTH_SHORT).show();
        String year = spinner_year.getSelectedItem().toString();
        String term = spinner_term.getSelectedItem().toString();
        String course = spinner_course.getSelectedItem().toString();
        if (inputCheck()) {
            final int mYear = Integer.parseInt(year);
            final String mTerm = term;
            final String mCourse = course;

            Bundle bundle = new Bundle();
            bundle.putInt("year", mYear);
            bundle.putString("term", mTerm);
            bundle.putString("course", mCourse);
            Navigation.findNavController(view).navigate(R.id.action_nav_performance_to_performanceDetailFragment, bundle);
        } else {
            Toast.makeText(this.getContext(),
                    "Some fields are not correct",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private boolean inputCheck() {
        // year is a valid year
        boolean a = !(readAllYear == null || readAllYear.size() == 0);
        // term is not empty or null
        boolean b = !(readAllTerm == null || readAllTerm.size() == 0);
        // course is not empty or null
        boolean c = !(readAllCourse == null || readAllCourse.size() == 0);
        return a && b && c;
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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.toolbar_drawer, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        NavController navController = Navigation.findNavController(this.getActivity(), R.id.nav_host_fragment);
        return NavigationUI.onNavDestinationSelected(item, navController) || super.onOptionsItemSelected(item);
    }
}
