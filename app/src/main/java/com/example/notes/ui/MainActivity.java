package com.example.notes.ui;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.Toast;

import com.example.notes.Note;
import com.example.notes.R;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    protected boolean isLandscape = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer);
        initDrawer();
        isLandscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
        if (isLandscape) {
            showLand();
        } else {
            showPort(ListOfNotesFragment.newInstance());
        }

    }

    private void showLand() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, ListOfNotesFragment.newInstance());
        fragmentTransaction.replace(R.id.fragment_container2, CurrentNoteFragment.newInstance(Note.current));
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void showPort(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void initDrawer() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.open, R.string.close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            switch (id) {
                case R.id.menu_about:
                    Toast.makeText(MainActivity.this, "about", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.menu_add:
                    Toast.makeText(MainActivity.this, "add", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.menu_search:
                    Toast.makeText(MainActivity.this, "search", Toast.LENGTH_SHORT).show();
                    return true;
            }
            return false;
        });
    }
}