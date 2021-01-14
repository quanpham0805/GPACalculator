package com.example.gpacalculator.ui.home;

import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.example.gpacalculator.R;
import com.example.gpacalculator.database.CourseDetailEntity;
import com.example.gpacalculator.database.CourseEntity;
import com.example.gpacalculator.database.CourseTermEntity;
import com.example.gpacalculator.database.CourseYearEntity;
import com.example.gpacalculator.ui.grades.CalculateGPA;
import com.example.gpacalculator.viewmodels.CourseYearViewModel;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private CourseYearViewModel mYearViewModel;
    private List<Integer> fYearData = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        final TextView textView = view.findViewById(R.id.cumulative_gpa_text);
        final ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.cumulative_gpa_bar);

        mYearViewModel = new ViewModelProvider(this).get(CourseYearViewModel.class);
        mYearViewModel.getReadAllData().observe(getViewLifecycleOwner(), new Observer<List<CourseYearEntity>>() {
            @Override
            public void onChanged(final List<CourseYearEntity> courseYearEntities) {
                fYearData = mYearViewModel.extractYear(courseYearEntities);
                mYearViewModel.getListTermFromListYear(fYearData).observe(getViewLifecycleOwner(), new Observer<List<CourseTermEntity>>() {
                    @Override
                    public void onChanged(final List<CourseTermEntity> courseTermEntities) {
                        mYearViewModel.getListCourseFromListTermListYear(mYearViewModel.extractTermName(courseTermEntities), fYearData)
                                .observe(getViewLifecycleOwner(), new Observer<List<CourseEntity>>() {
                                    @Override
                                    public void onChanged(final List<CourseEntity> courseEntities) {
                                        mYearViewModel.loadAllDetailFromListCourseListTermListYear(mYearViewModel.extractCourseName(courseEntities),
                                                mYearViewModel.extractTermName(courseTermEntities), fYearData)
                                                .observe(getViewLifecycleOwner(), new Observer<List<CourseDetailEntity>>() {
                                                    @Override
                                                    public void onChanged(List<CourseDetailEntity> courseDetailEntities) {
                                                        Pair<Double, Double> cGPA = CalculateGPA.getCGPA(courseYearEntities, courseTermEntities, courseEntities, courseDetailEntities);
                                                        if (cGPA.first == -1) {
                                                            textView.setText("NO DATA");
                                                            progressBar.setProgress(0);
                                                        } else {
                                                            textView.setText(String.valueOf(cGPA.first) + "% / " + String.valueOf(cGPA.second));
                                                            progressBar.setProgress((int) ((double) cGPA.first));
                                                        }
                                                    }
                                                });
                                    }
                                });
                    }
                });

            }
        });


        textView.setText("100%");
        progressBar.setProgress(100);

        return view;
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
