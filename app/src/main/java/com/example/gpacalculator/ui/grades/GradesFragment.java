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

public class GradesFragment extends Fragment implements RVAdapter.ListItemClickListener {

    private Toast mToast;
    private RVAdapter mAdapter;
    private ArrayList<String> tempdata = new ArrayList<String>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_grades, container, false);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rv_grades);
        recyclerView.setLayoutManager(new GridLayoutManager(view.getContext(), 2));

        mAdapter = new RVAdapter(this);
        if (tempdata.isEmpty()) {
            tempdata.add("2000");
            tempdata.add("2001");
            tempdata.add("2002");
            tempdata.add("2003");
            tempdata.add("2004");
        }

        mAdapter.updateData(tempdata);
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
                mAdapter.updateData(tempdata);
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

        Bundle bundle = new Bundle();
        bundle.putString("selected", tempdata.get(clickedItemIndex));

        Navigation.findNavController(view).navigate(R.id.action_nav_grades_to_gradesTermFragment, bundle);
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
