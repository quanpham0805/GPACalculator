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
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gpacalculator.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class GradesCourseFragment extends Fragment implements RVAdapter.ListItemClickListener {

    private Toast mToast;
    private RVAdapter mAdapter;
    private ArrayList<String> tempdata = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_grades_course, container, false);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rv_grades);
        recyclerView.setLayoutManager(new GridLayoutManager(view.getContext(), 2));

        mAdapter = new RVAdapter(this);

        if (tempdata.isEmpty()) {
            String tempvar = getArguments().getString("selected");
            switch (tempvar) {
                case "1A":
                    tempdata.add("MATH 135");
                    break;
                case "2A":
                    tempdata.add("MATH 137");
                    break;
                case "3A":
                    tempdata.add("CS 145");
                    break;
                case "4A":
                    tempdata.add("SPCOM 223");
                    break;
                case "5A":
                    tempdata.add("ECON 101");
                    break;
            }
        }


        mAdapter.updateDataString(tempdata);
        recyclerView.setAdapter(mAdapter);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mToast != null) {
                    mToast.cancel();
                }
                mToast = Toast.makeText(view.getContext(), "Added one", Toast.LENGTH_SHORT);
                mToast.show();

                tempdata.add(Integer.toString(tempdata.size() + 1));
                mAdapter.updateDataString(tempdata);
            }
        });

        return view;
    }

    @Override
    public void onListItemClick(int clickedItemIndex, View view) {
        if (mToast != null) {
            mToast.cancel();
        }

        String toastMessage = "Item #" + clickedItemIndex + " clicked.";
        mToast = Toast.makeText(this.getContext(), toastMessage, Toast.LENGTH_SHORT);
        mToast.show();
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
