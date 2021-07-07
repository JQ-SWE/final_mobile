package com.example.afinal;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class BikeShareAccountActivity extends AppCompatActivity {

    ImageView back;
    TextView name, rentalno, rentalhour, balance;
    Button topup;
    private DatabaseHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bike_share_account);

        back = (ImageView) findViewById(R.id.BikeShare_account_back);
        name = (TextView) findViewById(R.id.bikeaccount_phone);
        rentalno = (TextView) findViewById(R.id.tv_numberOfRentals);
        rentalhour = (TextView) findViewById(R.id.tv_totalRentalHours);
        balance = (TextView) findViewById(R.id.tv_totalbalance);
        topup = (Button) findViewById(R.id.Account_topup);

        Intent intent = getIntent();
        String phone = intent.getStringExtra("phone");

        DB = new DatabaseHelper(this);

        Cursor cursor_name = DB.getQuestion(phone);
        StringBuilder stringBuilder_name = new StringBuilder();
        while (cursor_name.moveToNext()) {
            stringBuilder_name.append(cursor_name.getString(2));
            name.setText(stringBuilder_name);
        }

        Cursor cursor_rentalno = DB.getrentalinfo(phone);
        StringBuilder stringBuilder_rentalno = new StringBuilder();
        while (cursor_rentalno.moveToNext()) {
            stringBuilder_rentalno.append(cursor_rentalno.getString(8));
            rentalno.setText(stringBuilder_rentalno);
        }

        Cursor cursor_rentalhour = DB.getrentalinfo(phone);
        StringBuilder stringBuilder_rentalhour = new StringBuilder();
        while (cursor_rentalhour.moveToNext()) {
            stringBuilder_rentalhour.append(cursor_rentalhour.getString(9));
            rentalhour.setText(stringBuilder_rentalhour);
        }

        Cursor cursor_balance = DB.getrentalinfo(phone);
        StringBuilder stringBuilder_balance = new StringBuilder();
        while (cursor_balance .moveToNext()) {
            stringBuilder_balance.append(cursor_balance.getString(10));
            balance.setText(stringBuilder_balance);
        }

        String Totalbalance = balance.getText().toString();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CitiBikeActivity.class);
                startActivity(intent);
            }
        });

        topup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TopUpActivity.class);
                intent.putExtra("phone", phone);
                intent.putExtra("balance", Totalbalance);
                startActivity(intent);
            }
        });
    }
}