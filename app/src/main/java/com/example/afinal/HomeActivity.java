package com.example.afinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.navigation.NavigationView;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //variables
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    Button newTripFunc, bikeShare, moveCar,manageCar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);

        Toolbar toolbar = findViewById(R.id.HomeToolBar);
        setSupportActionBar(toolbar);

        //Hooks
        drawerLayout = findViewById(R.id.DrawerLayout);
        navigationView = findViewById(R.id.NavView);
        toolbar = findViewById(R.id.HomeToolBar);
        newTripFunc = findViewById(R.id.NewTrip);
        bikeShare = findViewById(R.id.BikeShare);
        moveCar = findViewById(R.id.MoveCar);
        manageCar = findViewById(R.id.ManageMyCar);


        //toolbar
        setSupportActionBar(toolbar);

        //navigation menu
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        newTripFunc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this,NewTripTest.class);
                startActivity(intent);
            }
        });


        bikeShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this,BikeShareIndexActivity.class);
                startActivity(intent);
            }
        });

        manageCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this,ManageMyCar.class);
                startActivity(intent);
            }
        });


        moveCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this,MoveCarActivity1.class);
                startActivity(intent);
            }
        });



    }

    public void onBackPressed(){

        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else{
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.NavLogout:
                Intent intent=new Intent(HomeActivity.this,LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.NavProfile:
                Intent intent2=new Intent(HomeActivity.this,UserProfile.class);
                startActivity(intent2);
                break;
        }
        return true;
    }


}