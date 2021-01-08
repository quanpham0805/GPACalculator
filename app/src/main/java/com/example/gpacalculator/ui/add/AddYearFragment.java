package com.example.gpacalculator.ui.add;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.gpacalculator.R;
import com.example.gpacalculator.database.CourseYearEntity;
import com.example.gpacalculator.viewmodels.CourseYearViewModel;

public class AddYearFragment extends Fragment {

    private CourseYearViewModel courseYearViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_add_year, container, false);

//        TextView textView = view.findViewById(R.id.text_add_year);
//        textView.setText("This is add year fragment");

        courseYearViewModel = new ViewModelProvider(this).get(CourseYearViewModel.class);


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

    private void insertDataToDatabase (final View view) {
        EditText year = (EditText) view.findViewById(R.id.year_field);

        if (year != null && isParsable(year.getText().toString())) {
            final int mYear = Integer.parseInt(year.getText().toString());

            courseYearViewModel.checkYearExisted(mYear).observe(this, new Observer<Boolean>() {
                @Override
                public void onChanged(Boolean aBoolean) {
                    // existed
                    if (aBoolean)
                        Toast.makeText(getContext(), "Entry already existed", Toast.LENGTH_SHORT).show();
                    else {
                        CourseYearEntity courseYearEntity = new CourseYearEntity(mYear);
                        courseYearViewModel.insertYear(courseYearEntity);

                        Toast.makeText(getContext(), "Added one", Toast.LENGTH_SHORT).show();
                        Navigation.findNavController(view).navigate(R.id.action_action_add_year_to_nav_grades);
                    }
                }
            });

        } else {
            Toast.makeText(this.getContext(), "Invalid input", Toast.LENGTH_SHORT).show();
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
}
