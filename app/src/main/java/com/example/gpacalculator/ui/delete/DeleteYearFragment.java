package com.example.gpacalculator.ui.delete;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.gpacalculator.viewmodels.CourseYearViewModel;

import java.util.ArrayList;
import java.util.List;

public class DeleteYearFragment extends Fragment {

    private CourseYearViewModel courseYearViewModel;
    private List<Integer> readAllYear = new ArrayList<>();
    private Spinner spinner_year;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_delete_year, container, false);

        // set up View Model
        courseYearViewModel = new ViewModelProvider(this).get(CourseYearViewModel.class);

        // setting up spinner with year live data
        spinner_year = view.findViewById(R.id.spinner_year);
        courseYearViewModel.getAllYear().observe(getViewLifecycleOwner(), new Observer<List<Integer>>() {
            @Override
            public void onChanged(List<Integer> integers) {
                readAllYear = integers;
                if (readAllYear == null || readAllYear.size() == 0) {
                    spinner_year.setAdapter(setUpNullCaseAdapter("EMPTY", getContext()));
                } else {
                    spinner_year.setAdapter(setUpIntegerAdapter(getContext(), readAllYear));
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
        if (isParsable(year)) {
            final int mYear = Integer.parseInt(year);
            courseYearViewModel.deleteYearByYear(mYear);

            NavController navController = Navigation.findNavController(view);
            if (navController.popBackStack()) {
                Toast.makeText(getContext(), "Deleted one", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this.getContext(), "Error", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isParsable(String input) {
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
