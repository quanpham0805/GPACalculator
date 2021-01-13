package com.example.gpacalculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // creating view
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // creating toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // finding the drawer layout
        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        // find the corresponding fragment using navigation
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        NavController navController = ((NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment)).getNavController();


        // Passing menu ID as a set of Ids
        // so we can dynamically add menu or
        // customize later at ease
        mAppBarConfiguration =
                new AppBarConfiguration.Builder
                        (R.id.nav_home, R.id.nav_calculator, R.id.nav_grades, R.id.nav_performance)
                        .setOpenableLayout(drawer)
                        .build();

        // make the burger icon to access the navigation bar
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);

        // set up the content by using the navigation on the app bar
        // i.e, giving the correct fragment correspond to the selected item
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();

    }
}