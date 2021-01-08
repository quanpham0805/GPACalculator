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
import androidx.recyclerview.widget.RecyclerView;

import com.example.gpacalculator.R;
import com.example.gpacalculator.viewmodels.CourseTermViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class GradesTermFragment extends Fragment implements RVAdapter.ListItemClickListener {

    private Toast mToast;
    private RVAdapter mAdapter;
    private List<String> fTermData = new ArrayList<>();
    private CourseTermViewModel mTermViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_grades_term, container, false);

        int tYear = getArguments().getInt("selected");

        // making fake data
//        if (fTermData.isEmpty()) {
//            String tempvar = getArguments().getString("selected");
//            switch (tempvar) {
//                case "2000":
//                    fTermData.add("1A");
//                    break;
//                case "2001":
//                    fTermData.add("2A");
//                    break;
//                case "2002":
//                    fTermData.add("3A");
//                    break;
//                case "2003":
//                    fTermData.add("4A");
//                    break;
//                case "2004":
//                    fTermData.add("5A");
//                    break;
//            }
//        }

        // Setting the recyclerview
        mAdapter = new RVAdapter(this);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rv_grades);
        recyclerView.setLayoutManager(new GridLayoutManager(view.getContext(), 2));
        recyclerView.setAdapter(mAdapter);


        // Course Term View Model here
        mTermViewModel = new ViewModelProvider(this).get(CourseTermViewModel.class);
        mTermViewModel.getYearIdFromYear(tYear).observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer tYearId) {
                mTermViewModel.getTermFromYearId(tYearId).observe(getViewLifecycleOwner(), new Observer<List<String>>() {
                    @Override
                    public void onChanged(List<String> mTerm) {
                        fTermData = mTerm;
                        mAdapter.updateDataString(mTerm);
                    }
                });
            }
        });


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

                Navigation.findNavController(view).navigate(R.id.action_gradesTermFragment_to_action_add_term);
            }
        });

        return view;
    }


    // setting click behavior for list item click
    // TODO: move to next fragment
    @Override
    public void onListItemClick(int clickedItemIndex, View view) {
        if (mToast != null) {
            mToast.cancel();
        }

        String toastMessage = "Item " + fTermData.get(clickedItemIndex) + " was clicked.";
        mToast = Toast.makeText(this.getContext(), toastMessage, Toast.LENGTH_SHORT);
        mToast.show();

        Bundle bundle = new Bundle();
        bundle.putString("selected", fTermData.get(clickedItemIndex));

//        Navigation
//                .findNavController(view)
//                .navigate(R.id.action_gradesTermFragment_to_gradesCourseFragment, bundle);
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
