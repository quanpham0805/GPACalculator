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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gpacalculator.R;
import com.example.gpacalculator.viewmodels.CourseViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class GradesCourseFragment extends Fragment implements RVAdapter.ListItemClickListener {

    private Toast mToast;
    private RVAdapter mAdapter;
    private List<String> fCourseData = new ArrayList<>();
    private List<Double> fGradesData = new ArrayList<>();
    private CourseViewModel mCourseViewModel;
    private String tTerm;
    private int tYear;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_grades_course, container, false);

        tYear = getArguments().getInt("year");
        tTerm = getArguments().getString("term");

        // Setting the recyclerview
        mAdapter = new RVAdapter(this);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rv_grades);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(mAdapter);


        // Course View Model here
        mCourseViewModel = new ViewModelProvider(this).get(CourseViewModel.class);
        mCourseViewModel.getTermIdFromTermAndYear(tTerm, tYear).observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer newTermId) {
                mCourseViewModel.getCourseFromTermId(newTermId).observe(getViewLifecycleOwner(), new Observer<List<String>>() {
                    @Override
                    public void onChanged(List<String> mCourse) {
                        fCourseData = mCourse;
                        mAdapter.updateDataString(fCourseData, fGradesData);
                    }
                });
            }
        });


//        mAdapter.updateDataString(fCourseData);

        // Setting the floating button
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mToast != null) {
                    mToast.cancel();
                }
                mToast = Toast.makeText(view.getContext(), "Adding page", Toast.LENGTH_SHORT);
                mToast.show();

                Navigation.findNavController(view).navigate(R.id.action_gradesCourseFragment_to_action_add_course);
            }
        });

        return view;
    }

    @Override
    public void onListItemClick(int clickedItemIndex, View view) {
        if (mToast != null) {
            mToast.cancel();
        }

        String toastMessage = "Item " + fCourseData.get(clickedItemIndex) + " was clicked.";
        mToast = Toast.makeText(this.getContext(), toastMessage, Toast.LENGTH_SHORT);
        mToast.show();

        Bundle bundle = new Bundle();
        bundle.putInt("year", tYear);
        bundle.putString("term", tTerm);
        bundle.putString("course", fCourseData.get(clickedItemIndex));

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
