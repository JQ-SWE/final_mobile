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

    ImageView back;
    TextView bikenum, starttime, period, cost;
    Button calculate, checkout;

    //for popup
    Button confirm, topup;
    TextView balance, cost_2;
    Dialog mdialog;

    Timer timer;
    TimerTask timerTask;
    Double time = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bike_share_record);

        back = (ImageView) findViewById(R.id.BikeShare_record_back);
        bikenum = (TextView) findViewById(R.id.bikesharerecord_identifier);
        starttime = (TextView) findViewById(R.id.bikesharerecord_starttime);
        period = (TextView) findViewById(R.id.bikesharerecord_period);
        cost = (TextView) findViewById(R.id.bikesharerecord_cost);
        calculate = (Button) findViewById(R.id.bikesharerecord_calculate);
        checkout = (Button) findViewById(R.id.bikesharerecord_checkout);

        //for pop up
        confirm = (Button) findViewById(R.id.checkout_confirm);
        topup = (Button) findViewById(R.id.checkout_topup);
        balance = (TextView) findViewById(R.id.checkout_balance);
        cost_2 = (TextView) findViewById(R.id.checkout_cost);
        mdialog = new Dialog(this);

        Intent intent = getIntent();
        String Identifier = intent.getStringExtra("identifier");
        String Starttime = intent.getStringExtra("datetime");

        bikenum.setText(Identifier);
        starttime.setText(Starttime);

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

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), BikeShareRentAcitvity.class);
                startActivity(intent);
            }
        });

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
                cost.setText("$ " + COST);
                cost_2.setText("$ " + COST);
            }
        });

        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mdialog.setContentView(R.layout.bike_checkout);
                mdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        topup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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