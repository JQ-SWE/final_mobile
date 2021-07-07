package com.example.afinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextClock;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Calendar;

public class MoveCarActivity1 extends AppCompatActivity {

    Button request;
    TextView date, location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_move_car1);

        request = (Button) findViewById(R.id.movecar_request);
        date = (TextView) findViewById(R.id.tv_date);
        location = (TextView) findViewById(R.id.et_movecar_location);

        Calendar calendar = Calendar.getInstance();
        String curdate = DateFormat.getDateInstance().format(calendar.getTime());
        date.setText(curdate);
        location.setText("location");

        request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MoveCarActivity1.this,MoveCarActivity2.class);
                startActivity(intent);
            }
        });
    }
}