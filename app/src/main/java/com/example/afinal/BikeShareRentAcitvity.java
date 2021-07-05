package com.example.afinal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

public class BikeShareRentAcitvity extends AppCompatActivity {

    Button btScan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bike_share_rent_acitvity);

        btScan = (Button) findViewById(R.id.btn_scan);

    }
}