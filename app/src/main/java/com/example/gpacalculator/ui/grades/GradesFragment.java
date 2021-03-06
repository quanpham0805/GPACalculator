package com.example.gpacalculator.ui.grades;

import android.os.Bundle;
import android.util.Pair;
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
import com.example.gpacalculator.database.CourseEntity;
import com.example.gpacalculator.database.CourseTermEntity;
import com.example.gpacalculator.database.CourseYearEntity;
import com.example.gpacalculator.viewmodels.CourseYearViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class GradesFragment extends Fragment implements RVAdapter.ListItemClickListener {

    private final List<Pair<Double, Double>> fGradesData = null;
    private Toast mToast;
    private RVAdapter mAdapter;
    private List<Integer> fYearData = new ArrayList<>();
    private CourseYearViewModel mYearViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_grades, container, false);

        // Setting the recyclerview
        mAdapter = new RVAdapter(this);
        RecyclerView recyclerView = view.findViewById(R.id.rv_grades);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(mAdapter);
//        mAdapter.updateDataInteger(fYearData);

        // Course Year ViewModel
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
                                                        mAdapter.updateDataInteger(fYearData, CalculateGPA.getYearGPA(courseYearEntities, courseTermEntities, courseEntities, courseDetailEntities));
                                                    }
                                                });
                                    }
                                });
                    }
                });

            }
        });


        // Setting the floating action button to move to add fragment
        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mToast != null) {
                    mToast.cancel();
                }
                mToast = Toast.makeText(view.getContext(), "Adding page", Toast.LENGTH_SHORT);
                mToast.show();

                Navigation.findNavController(view).navigate(R.id.action_nav_grades_to_action_add_year);
            }
        });

        return view;
    }


    // setting the click behavior for list item click
    // move to next fragment
    @Override
    public void onListItemClick(int clickedItemIndex, View view) {
        if (mToast != null) {
            mToast.cancel();
        }

        String toastMessage = "Item " + fYearData.get(clickedItemIndex) + " was clicked.";
        mToast = Toast.makeText(this.getContext(), toastMessage, Toast.LENGTH_SHORT);
        mToast.show();

        Bundle bundle = new Bundle();
        bundle.putInt("year", fYearData.get(clickedItemIndex));

        Navigation.findNavController(view).navigate(R.id.action_nav_grades_to_gradesTermFragment, bundle);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    // inflating the app bar menu of the vertical ellipsis
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.toolbar_drawer, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


    // app bar menu of the vertical ellipsis
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (this.getActivity() == null) {
            throw new RuntimeException("null returned from getActivity() in GradesFragment");
        }
        NavController navController = Navigation.findNavController(this.getActivity(), R.id.nav_host_fragment);
        return NavigationUI.onNavDestinationSelected(item, navController) || super.onOptionsItemSelected(item);
    }
}
