package com.example.afinal;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Timer;
import java.util.TimerTask;

public class BikeShareRecordActivity extends AppCompatActivity {

    TextView bikenum, starttime, period, cost;
    Button calculate, checkout;

    Timer timer;
    TimerTask timerTask;

    Double time = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bike_share_record);

        bikenum = (TextView) findViewById(R.id.bikesharerecord_identifier);
        starttime = (TextView) findViewById(R.id.bikesharerecord_starttime);
        period = (TextView) findViewById(R.id.bikesharerecord_period);
        cost = (TextView) findViewById(R.id.bikesharerecord_cost);
        calculate = (Button) findViewById(R.id.bikesharerecord_calculate);
        checkout = (Button) findViewById(R.id.bikesharerecord_checkout);

        Intent intent = getIntent();
        String balance = intent.getStringExtra("balance");
        bikenum.setText(intent.getStringExtra("identifier"));
        starttime.setText(intent.getStringExtra("datetime"));
        String phone = intent.getStringExtra("phone");

        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        time ++;
                        period.setText(getTimerText());
                    }
                });
            }
        };

        timer.scheduleAtFixedRate(timerTask, 0,1000);

        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timerTask.cancel();

                int COST;
                int rounded = (int) Math.round(time);
                int minutes = ((rounded % 86400) % 3600) / 60;
                int hours = ((rounded % 86400) / 3600);

                if((minutes - 30) >= 0 ){
                    COST = 3 * 2 * (hours+1);
                } else{
                    COST = 3 * 2 * hours + 3;
                }

                String totalcost = String.valueOf(COST);
                cost.setText(totalcost);
            }
        });

        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String totalcost = cost.getText().toString();
                int rounded = (int) Math.round(time);
                String round = Integer.toString(rounded);

                Intent intent = new Intent(BikeShareRecordActivity.this,CheckoutActivity.class);
                intent.putExtra("phone", phone);
                intent.putExtra("balance", balance);
                intent.putExtra("cost", totalcost);
                intent.putExtra("time", round);
                startActivity(intent);
            }

        });

    }
    private String getTimerText(){
        int rounded = (int) Math.round(time);
        int seconds = ((rounded % 86400) % 3600) % 60;
        int minutes = ((rounded % 86400) % 3600) / 60;
        int hours = ((rounded % 86400) / 3600);

        return formatTime(seconds, minutes, hours);
    }

    private String formatTime(int seconds, int minutes, int hours) {
        return String.format("%02d", hours) + " : " + String.format("%02d", minutes) + " : " + String.format("%02d", seconds);
    }

}