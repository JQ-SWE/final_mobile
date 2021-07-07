package com.example.afinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class UserProfile extends AppCompatActivity {
    private Button Update;
    private TextView balance, name, phone, passport, location;

    private DatabaseHelper DB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        Update = findViewById(R.id.updateButton);
        balance = findViewById(R.id.payment_label);
        name = findViewById(R.id.profilename);
        phone = findViewById(R.id.profilephone);
        passport = findViewById(R.id.profilepassport);
        location = findViewById(R.id.profilelocation);

        DB = new DatabaseHelper(this);


        Intent intent = getIntent();
        String Phone = intent.getStringExtra("phone");

        phone.setText(Phone);

        Cursor cursor = DB.getQuestion(Phone);
        StringBuilder stringBuilder = new StringBuilder();
        while (cursor.moveToNext()) {
            stringBuilder.append(cursor.getString(2));
            name.setText(stringBuilder);}

        Cursor cursor1 = DB.getQuestion(Phone);
        StringBuilder stringBuilder1 = new StringBuilder();
        while (cursor1.moveToNext()) {
            stringBuilder1.append(cursor1.getString(10));
            balance.setText(stringBuilder1);}

        Cursor cursor2 = DB.getQuestion(Phone);
        StringBuilder stringBuilder2 = new StringBuilder();
        while (cursor2.moveToNext()) {
            stringBuilder2.append(cursor2.getString(1));
            passport.setText(stringBuilder2);}

        Cursor cursor3 = DB.getQuestion(Phone);
        StringBuilder stringBuilder3 = new StringBuilder();
        while (cursor3.moveToNext()) {
            stringBuilder3.append(cursor3.getString(4));
            location.setText(stringBuilder3);}


        Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveEdit();
            }
        });
    }
        private void moveEdit(){
        Intent intent = new Intent(UserProfile.this,FindPasswordActivity.class);
        startActivity(intent);
    }
}