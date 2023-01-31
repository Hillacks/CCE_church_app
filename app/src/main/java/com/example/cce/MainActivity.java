package com.example.cce;

import android.content.Intent;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.cce.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    ActivityMainBinding binding;
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new HomeFragment());



        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navigationView.setNavigationItemSelectedListener(this);

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.home:
                    replaceFragment(new HomeFragment());
                    getSupportActionBar().setTitle("Home");
                    break;
                case R.id.media:
                    replaceFragment(new MediaFragment());
                    getSupportActionBar().setTitle("Media");
                    break;
                case R.id.events:
                    replaceFragment(new EventsFragment());
                    getSupportActionBar().setTitle("Events");
                    break;
                case R.id.bible:
                    replaceFragment(new BibleFragment());
                    getSupportActionBar().setTitle("Bible");
                    break;
                case R.id.connect:
                    replaceFragment(new ConnectFragment());
                    getSupportActionBar().setTitle("Connect");
                    break;
            }
            return true;
        });

    }

    public boolean onOptionsItemSelected(@NonNull MenuItem items) {

        if (actionBarDrawerToggle.onOptionsItemSelected(items)) {
            return true;
        }

        return super.onOptionsItemSelected(items);
    }

    public boolean onNavigationItemSelected(@NonNull MenuItem items) {
        switch (items.getItemId()) {
            case R.id.sign_In:
                startActivity(new Intent(MainActivity.this, SignupActivity.class));
                break;
            case R.id.share:
                Intent shareIntent =   new Intent(android.content.Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT,"Insert Subject here");
                String app_url = " https://play.google.com/store/apps/details?id=my.example.javatpoint";
                shareIntent.putExtra(android.content.Intent.EXTRA_TEXT,app_url);
                startActivity(Intent.createChooser(shareIntent, "Share"));
                break;
            case R.id.notes:
                startActivity(new Intent(MainActivity.this, Notes.class));
                getSupportActionBar().setTitle("Notes");
                break;
            case R.id.search:
                Toast.makeText(this,"Search", Toast.LENGTH_SHORT).show();
                break;
            case R.id.trivia:
                startActivity(new Intent(MainActivity.this, TriviaFragment.class));
                getSupportActionBar().setTitle("Notes");
                break;
            case R.id.dark_theme:
                int nightMode = AppCompatDelegate.getDefaultNightMode();
                if (nightMode == AppCompatDelegate.MODE_NIGHT_YES) {
                    // Night mode is enabled
                } else {
                    // Night mode is disabled
                }

                break;
        }
        return true;
    }

    private void replaceFragment(Fragment fragment){

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.relativeLayout2, fragment);
        fragmentTransaction.commit();
    }


}