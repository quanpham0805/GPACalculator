package com.example.gpacalculator.ui.add;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.gpacalculator.R;
import com.example.gpacalculator.database.CourseTermEntity;
import com.example.gpacalculator.viewmodels.CourseTermViewModel;

import java.util.ArrayList;
import java.util.List;

public class AddTermFragment extends Fragment {

    private CourseTermViewModel courseTermViewModel;
    private List<Integer> readAllYear = new ArrayList<>();
    private final String LOG_TAG = AddTermFragment.class.getSimpleName();
    private Spinner spinner;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_add_term, container, false);

        // get view model
        courseTermViewModel = new ViewModelProvider(this).get(CourseTermViewModel.class);

        // setting up spinner with year live data
        spinner = (Spinner) view.findViewById(R.id.spinner);
        courseTermViewModel.getAllYear().observe(getViewLifecycleOwner(), new Observer<List<Integer>>() {
            @Override
            public void onChanged(List<Integer> integers) {
                readAllYear = integers;
                if (readAllYear == null || readAllYear.size() == 0) {
                    spinner.setAdapter(setUpNullCaseAdapter("Empty, please add year first", getContext()));
                } else {
                    ArrayAdapter<Integer> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, readAllYear);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner.setAdapter(adapter);
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

    private ArrayAdapter<String> setUpNullCaseAdapter(String msg, Context context) {
        List<String> nullCase = new ArrayList<>();
        nullCase.add(msg);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, nullCase);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return adapter;
    }

    private void insertDataToDatabase(final View view) {
        String year = spinner.getSelectedItem().toString();
        EditText term = (EditText) view.findViewById(R.id.term_field);

        if (inputCheck(year, term)) {
            final int mYear = Integer.parseInt(year);
            final String mTerm = term.getText().toString();

            courseTermViewModel.getYearIdFromYear(mYear).observe(this, new Observer<Integer>() {
                @Override
                public void onChanged(final Integer yearId) {
                    courseTermViewModel.checkTermExisted(mTerm, yearId).observe(getViewLifecycleOwner(), new Observer<Boolean>() {
                        @Override
                        public void onChanged(Boolean aBoolean) {
                            // existed
                            if (aBoolean) {
                                Toast.makeText(getContext(), "Entry already existed", Toast.LENGTH_SHORT).show();
                            } else {
                                CourseTermEntity courseTermEntity = new CourseTermEntity(mTerm, yearId);
                                courseTermViewModel.insertTerm(courseTermEntity);

                                Toast.makeText(getContext(), "Added one", Toast.LENGTH_SHORT).show();

                                Bundle bundle = new Bundle();
                                bundle.putInt("year", mYear);

                                // Navigate back to GradesTermFragment
                                // However, to prevent making a loop, we need to consider 2 cases:
                                // If we arrive at this Add Term fragment from grades term fragment,
                                // we need to pop gradesTermFragment as well.
                                // Otherwise we only need to pop AddTerm
                                NavController navController = Navigation.findNavController(view);
                                String prevBackStack = navController.getPreviousBackStackEntry().getDestination().toString();
//                                Log.e(LOG_TAG, prevBackStack);
                                if (prevBackStack.contains("gradesTermFragment")) {
                                    navController.navigate(R.id.action_action_add_term_to_gradesTermFragment_Term, bundle);
//                                    Log.e(LOG_TAG, "fml");
                                } else {
                                    navController.navigate(R.id.action_action_add_term_to_gradesTermFragment_Home, bundle);
//                                    Log.e(LOG_TAG, "new feature");
                                }
                            }
                        }
                    });
                }
            });

        } else {
            Toast.makeText(this.getContext(),
                    "Invalid input or empty year",
                    Toast.LENGTH_SHORT).show();
        }

    }

    private boolean inputCheck(String year, EditText term) {
        return (isParsable(year) && term != null
                && !TextUtils.isEmpty(term.getText().toString()));
    }

    private boolean isParsable(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (final NumberFormatException e) {
            return false;
        }
    }
}
