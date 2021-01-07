package com.example.gpacalculator.ui.add;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.example.gpacalculator.R;
import com.example.gpacalculator.database.SubjectLocationEntity;
import com.example.gpacalculator.viewmodels.GradesViewModel;

public class AddYearFragment extends Fragment {

    private GradesViewModel gradesViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_add_year, container, false);

//        TextView textView = view.findViewById(R.id.text_add_year);
//        textView.setText("This is add year fragment");

        gradesViewModel = new ViewModelProvider(this).get(GradesViewModel.class);

        Button btn = view.findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertDataToDatabase(view);
            }
        });

        return view;
    }

    private void insertDataToDatabase (View view) {
        EditText year = (EditText) view.findViewById(R.id.year_field);

        if (year != null && isParsable(year.getText().toString())) {
            int mYear = Integer.parseInt(year.getText().toString());
            SubjectLocationEntity subjectLocationEntity = new SubjectLocationEntity(mYear, "", "", -1);
            gradesViewModel.insertLocation(subjectLocationEntity);

            Toast.makeText(this.getContext(), "Added one", Toast.LENGTH_SHORT).show();
            Navigation.findNavController(view).navigate(R.id.action_action_add_year_to_nav_grades);
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
