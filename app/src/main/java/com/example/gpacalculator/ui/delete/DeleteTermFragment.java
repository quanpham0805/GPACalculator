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
import com.example.gpacalculator.viewmodels.CourseTermViewModel;

import java.util.ArrayList;
import java.util.List;

public class DeleteTermFragment extends Fragment {

    private CourseTermViewModel courseTermViewModel;
    private List<String> readAllTerm = new ArrayList<>();
    private List<Integer> readAllYear = new ArrayList<>();
    private Spinner spinner_year, spinner_term;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_delete_term, container, false);

        // set up View Model
        courseTermViewModel = new ViewModelProvider(this).get(CourseTermViewModel.class);

        // setting up spinner with year and term live data
        spinner_year = view.findViewById(R.id.spinner_year);
        spinner_term = view.findViewById(R.id.spinner_term);
        courseTermViewModel.getAllYear().observe(getViewLifecycleOwner(), new Observer<List<Integer>>() {
            @Override
            public void onChanged(List<Integer> integers) {
                readAllYear = integers;
                if (readAllYear == null || readAllYear.size() == 0) {
                    spinner_year.setAdapter(setUpNullCaseAdapter("EMPTY", getContext()));
                    spinner_term.setAdapter(setUpNullCaseAdapter("EMPTY", getContext()));
                } else {
                    spinner_year.setAdapter(setUpIntegerAdapter(getContext(), readAllYear));
                    spinner_year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            courseTermViewModel.getTermFromYear(Integer.parseInt(spinner_year.getSelectedItem().toString()))
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

    private void deleteDataFromDatabase(final View view) {
        String year = spinner_year.getSelectedItem().toString();
        String term = spinner_term.getSelectedItem().toString();
        if (inputCheck(year, term)) {
            final int mYear = Integer.parseInt(year);
            final String mTerm = term;
            courseTermViewModel.deleteTermByTermAndYear(mTerm, mYear);

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

    private boolean inputCheck(String year, String term) {
        // year is a valid year
        boolean a = isParsableInteger(year);
        // term is not empty or null
        boolean b = !(readAllTerm == null || readAllTerm.size() == 0);
        return a && b;
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
