package com.example.gpacalculator.ui.grades;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gpacalculator.R;
import com.example.gpacalculator.database.CourseDetailEntity;
import com.example.gpacalculator.viewmodels.CourseDetailViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class GradesCourseDetailFragment extends Fragment {

    private Toast mToast;
    private CourseDetailRVAdapter mAdapter;
    private List<CourseDetailEntity> fDetailData = new ArrayList<>();
    private CourseDetailViewModel mCourseDetailViewModel;
    private int tYear;
    private String tTerm, tCourse;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_grades_course_details, container, false);

        tYear = getArguments().getInt("year");
        tTerm = getArguments().getString("term");
        tCourse = getArguments().getString("course");

        // Setting the recycler view
        mAdapter = new CourseDetailRVAdapter();
        RecyclerView recyclerView = view.findViewById(R.id.rv_detail);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(mAdapter);

        // Course Detail VM here
        mCourseDetailViewModel = new ViewModelProvider(this).get(CourseDetailViewModel.class);
        mCourseDetailViewModel.loadAllDetailFromCourseTermYear(tCourse, tTerm, tYear).observe(getViewLifecycleOwner(), new Observer<List<CourseDetailEntity>>() {
            @Override
            public void onChanged(List<CourseDetailEntity> courseDetailEntities) {
                fDetailData = courseDetailEntities;
                mAdapter.updateData(fDetailData);
            }
        });


        // setting the floating button
        // Setting the floating button
        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mToast != null) {
                    mToast.cancel();
                }
                mToast = Toast.makeText(view.getContext(), "Adding page", Toast.LENGTH_SHORT);
                mToast.show();

                Navigation.findNavController(view).navigate(R.id.action_gradesCourseDetailFragment_to_action_add_course_detail);
            }
        });

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
        if (this.getActivity() == null) {
            throw new RuntimeException("null returned from getActivity() in GradesCourseFragment");
        }
        NavController navController = Navigation.findNavController(this.getActivity(), R.id.nav_host_fragment);
        return NavigationUI.onNavDestinationSelected(item, navController) || super.onOptionsItemSelected(item);
    }
}
