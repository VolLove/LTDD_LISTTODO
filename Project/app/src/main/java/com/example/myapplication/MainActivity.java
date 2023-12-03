package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.android.material.navigation.NavigationView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import Data.DatabaseQuery;
import Model.Job;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    public static Toolbar toolbar;
    public static NavigationView nav;
    public static ImageButton btnCreate;
    public static int indext = 0;
    public static int USER_ID = 1;
    public static ArrayList<Job> jobs;
    public static DatabaseQuery db;
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setColtrol();
        setEvent();
    }

    private void setEvent() {
        Intent intent = getIntent();
        USER_ID = intent.getIntExtra("id", 1);
        setSupportActionBar(toolbar);
        db = new DatabaseQuery(this);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.close_nav, R.string.open_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        nav.setNavigationItemSelectedListener(this);
        change(new HomeFragment());
        btnCreate.setVisibility(View.GONE);
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (indext == 1) {
                    change(new CreateFragment());
                }
                if (indext == 2) {
                    change(new CreateTypeFragment());
                }
            }
        });
    }

    private void setColtrol() {
        toolbar = findViewById(R.id.main_tool);
        nav = findViewById(R.id.nav_main);
        btnCreate = findViewById(R.id.btnCreate);
        drawerLayout = findViewById(R.id.mainDrawer);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.navhome) {
            change(new HomeFragment());
        }
        if (id == R.id.navtable) {
            change(new TableFragment());
        }

        if (id == R.id.navtableType) {
            change(new TableTypeFragment());
        }
        if (id == R.id.navlogout) {
            Intent intent = new Intent(MainActivity.this, MainActivity2.class);
            startActivity(intent);
            finish();
        }
        if (id == R.id.navclose) {
            finish();
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public void change(Fragment fragment) {
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.mainFragment, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public static Date getDateFromString(String dateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        try {
            return dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getStringFromDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String strDateTime = dateFormat.format(date);
        return strDateTime;
    }
}