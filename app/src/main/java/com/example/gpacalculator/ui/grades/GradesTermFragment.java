package com.example.gpacalculator.ui.grades;

import android.os.Bundle;
import android.util.Log;
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
import com.example.gpacalculator.viewmodels.CourseTermViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class GradesTermFragment extends Fragment implements RVAdapter.ListItemClickListener {

    final String LOG_TAG = GradesTermFragment.class.getSimpleName();
    private Toast mToast;
    private RVAdapter mAdapter;
    private List<String> fTermData = new ArrayList<>();
    private CourseTermViewModel mTermViewModel;
    private int tYear;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_grades_term, container, false);

        tYear = getArguments().getInt("year");

        // Setting the recyclerview
        mAdapter = new RVAdapter(this);
        RecyclerView recyclerView = view.findViewById(R.id.rv_grades);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(mAdapter);


        // Course Term View Model here
        mTermViewModel = new ViewModelProvider(this).get(CourseTermViewModel.class);
        mTermViewModel.getListTermFromYear(tYear).observe(getViewLifecycleOwner(), new Observer<List<CourseTermEntity>>() {
            @Override
            public void onChanged(final List<CourseTermEntity> courseTermEntities) {
                fTermData = mTermViewModel.extractTermName(courseTermEntities);
                mTermViewModel.getListCourseFromListTermYear(fTermData, tYear).observe(getViewLifecycleOwner(), new Observer<List<CourseEntity>>() {
                    @Override
                    public void onChanged(final List<CourseEntity> courseEntities) {
                        mTermViewModel.loadAllDetailFromListCourseListTermYear(mTermViewModel.extractCourseName(courseEntities), fTermData, tYear)
                                .observe(getViewLifecycleOwner(), new Observer<List<CourseDetailEntity>>() {
                                    @Override
                                    public void onChanged(List<CourseDetailEntity> courseDetailEntities) {
                                        Log.e(LOG_TAG, courseDetailEntities.toString());
                                        mAdapter.updateDataString(fTermData, CalculateGPA.getTermGPA(courseTermEntities, courseEntities, courseDetailEntities));
                                    }
                                });
                    }
                });
            }
        });


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

                Navigation.findNavController(view).navigate(R.id.action_gradesTermFragment_to_action_add_term);
            }
        });

        return view;
    }


    // setting click behavior for list item click
    @Override
    public void onListItemClick(int clickedItemIndex, View view) {
        if (mToast != null) {
            mToast.cancel();
        }

        String toastMessage = "Item " + fTermData.get(clickedItemIndex) + " was clicked.";
        mToast = Toast.makeText(this.getContext(), toastMessage, Toast.LENGTH_SHORT);
        mToast.show();

        Bundle bundle = new Bundle();
        bundle.putString("term", fTermData.get(clickedItemIndex));
        bundle.putInt("year", tYear);

        Navigation
                .findNavController(view)
                .navigate(R.id.action_gradesTermFragment_to_gradesCourseFragment, bundle);
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
