package com.example.notes;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import android.os.Bundle;
import android.widget.Toast;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer);
        initDrawer();
        getSupportFragmentManager().beginTransaction().replace(R.id.list_of_notes
                , new ListOfNotesFragment()).addToBackStack("list").commit();
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
        navigationView.setNavigationItemSelectedListener((NavigationView.OnNavigationItemSelectedListener) item -> {
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