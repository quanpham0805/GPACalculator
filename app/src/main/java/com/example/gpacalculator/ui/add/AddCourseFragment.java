package com.example.gpacalculator.ui.add;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.gpacalculator.R;
import com.example.gpacalculator.database.CourseEntity;
import com.example.gpacalculator.viewmodels.CourseViewModel;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class AddCourseFragment extends Fragment {

    private CourseViewModel courseViewModel;
    private List<String> readAllTerm = new ArrayList<>();
    private List<Integer> readAllYear = new ArrayList<>();
    private Spinner spinner_year, spinner_term;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_add_course, container, false);

        // set up View Model
        courseViewModel = new ViewModelProvider(this).get(CourseViewModel.class);

        // setting up spinner with year, term live data
        // TODO: change focus of the spinner maybe nicer?
        // TODO: might wanna clear stack instead of just navigate to grades
        spinner_year = (Spinner) view.findViewById(R.id.spinner_year);
        spinner_term = (Spinner) view.findViewById(R.id.spinner_term);
        courseViewModel.getAllYear().observe(getViewLifecycleOwner(), new Observer<List<Integer>>() {
            @Override
            public void onChanged(List<Integer> integers) {
                readAllYear = integers;
                if (readAllYear == null || readAllYear.size() == 0) {
                    spinner_year.setAdapter(setUpNullCaseAdapter("Empty, please add year first", getContext()));
                    spinner_term.setAdapter(setUpNullCaseAdapter("Empty, please add year then add term first", getContext()));
                } else {
                    spinner_year.setAdapter(setUpIntegerAdapter(getContext(), readAllYear));
                    spinner_year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            courseViewModel.getTermFromYear(Integer.parseInt(spinner_year.getSelectedItem().toString()))
                                    .observe(getViewLifecycleOwner(), new Observer<List<String>>() {
                                        @Override
                                        public void onChanged(List<String> strings) {
                                            readAllTerm = strings;

                                            if (readAllTerm == null || readAllTerm.size() == 0)
                                                spinner_term.setAdapter(setUpNullCaseAdapter("Empty, please add term first", getContext()));
                                            else
                                                spinner_term.setAdapter(setUpStringAdapter(getContext(), readAllTerm));
                                        }
                                    });
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }
            }
        });

        // set up add button
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
        String year = spinner_year.getSelectedItem().toString();
        String term = spinner_term.getSelectedItem().toString();
        EditText course = (EditText) view.findViewById(R.id.course_field);
        EditText credits = (EditText) view.findViewById(R.id.credits_field);
//        Toast.makeText(this.getContext(), "Later", Toast.LENGTH_SHORT).show();
        if (inputCheck(year, term, course, credits)) {
            final int mYear = Integer.parseInt(year);
            final String mTerm = term;
            final String mCourse = course.getText().toString();
            final Double mCredits = Double.parseDouble(credits.getText().toString());
            courseViewModel.getTermIdFromTermAndYear(mTerm, mYear).observe(this, new Observer<Integer>() {
                @Override
                public void onChanged(final Integer termId) {
                    courseViewModel.checkCourseExisted(mCourse, termId).observe(getViewLifecycleOwner(), new Observer<Boolean>() {
                        @Override
                        public void onChanged(Boolean aBoolean) {
                            if (aBoolean) {
                                Toast.makeText(getContext(), "Entry already existed", Toast.LENGTH_SHORT).show();
                            } else {
                                CourseEntity courseEntity = new CourseEntity(mCourse, mCredits, termId);
                                courseViewModel.insertCourse(courseEntity);

                                Toast.makeText(getContext(), "Added one", Toast.LENGTH_SHORT).show();

                                Bundle bundle = new Bundle();
                                bundle.putInt("year", mYear);
                                bundle.putString("term", mTerm);

                                // Navigate back to GradesCourseFragment
                                // However, to prevent making a loop, we need to consider 2 cases:
                                // If we arrive at this Add Course fragment from grades course fragment,
                                // we need to pop gradesCourseFragment as well.
                                // Otherwise we only need to pop addCourse

                                NavController navController = Navigation.findNavController(view);
                                String prevBackStack =  navController.getPreviousBackStackEntry().getDestination().toString();

                                if (prevBackStack.contains("gradesCourseFragment")) {
                                    navController.navigate(R.id.action_action_add_course_to_gradesCourseFragment_Course, bundle);
                                } else {
                                    navController.navigate(R.id.action_action_add_course_to_gradesCourseFragment_Home, bundle);
                                }
                            }
                        }
                    });
                }
            });
        } else {
            Toast.makeText(this.getContext(),
                    "Some fields are not correct",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private boolean inputCheck(String year, String term, EditText course, EditText credits) {
        // year is a valid year
        boolean a = isParsableInteger(year);
        // term is not empty or null
        boolean b = !(readAllTerm == null || readAllTerm.size() == 0);
        // course name is not empty
        boolean c = !(TextUtils.isEmpty(course.getText().toString()));
        // course credits is a valid decimal number
        boolean d = isParsableDouble(credits.getText().toString());
        return a && b && c && d;
//        Log.e(AddCourseFragment.class.getSimpleName(), String.valueOf(a && b && c && d));
    }

    private boolean isParsableDouble(String input) {
        try {
            Double.parseDouble(input);
            return true;
        } catch (final NumberFormatException e) {
            return false;
        }
    }

    private boolean isParsableInteger(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (final NumberFormatException e) {
            return false;
        }
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
}
