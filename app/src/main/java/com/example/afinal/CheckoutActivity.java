package com.example.afinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class CheckoutActivity extends AppCompatActivity {

    TextView balance, Totalcost, rentalno, rentalhour;
    Button confirm;

    private DatabaseHelper DB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        balance = (TextView) findViewById(R.id.checkout_balance);
        Totalcost = (TextView) findViewById(R.id.checkout_cost);
        confirm = (Button) findViewById(R.id.checkout_confirm);
        rentalno = (TextView) findViewById(R.id.rentalno);
        rentalhour = (TextView) findViewById(R.id.rentalhour);

        DB = new DatabaseHelper(this);

        Intent intent = getIntent();
        String cost = intent.getStringExtra("cost");
        String restbalance = intent.getStringExtra("balance");
        String phone = intent.getStringExtra("phone");
        String rentalhours = intent.getStringExtra("time");

        Totalcost.setText(cost);
        balance.setText(restbalance);

        Cursor cursor = DB.getQuestion(phone);
        StringBuilder stringBuilder = new StringBuilder();
        while (cursor.moveToNext()) {
            stringBuilder.append(cursor.getString(8));
            rentalno.setText(stringBuilder);
        }

        Cursor cursor_rentalhour = DB.getrentalinfo(phone);
        StringBuilder stringBuilder_rentalhour = new StringBuilder();
        while (cursor_rentalhour.moveToNext()) {
            stringBuilder_rentalhour.append(cursor_rentalhour.getString(9));
            rentalhour.setText(stringBuilder_rentalhour);
        }

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int int_cost = Integer.parseInt(cost);
                int int_restbalance = Integer.parseInt(restbalance);

                int totalrest = int_restbalance - int_cost;
                String str_totalrest = Integer.toString(totalrest);

                //rentalno
                String originno = rentalno.getText().toString();
                int originnum = Integer.parseInt(originno);
                int finalnum = originnum + 1;
                String rentalnum = Integer.toString(finalnum);

                //rentalhour
                String originhour = rentalhour.getText().toString();
                int origintime= Integer.parseInt(originhour);
                int plustime = Integer.parseInt(rentalhours);
                int finaltime = origintime + plustime;
                int hours = ((finaltime % 86400) / 3600);
                String rentaltime = Integer.toString(hours);

                boolean topup = DB.topup(str_totalrest, phone);
                boolean numberplus = DB.updaterentalno(rentalnum, phone);
                boolean timeplus = DB.updaterentalhour(rentaltime, phone);
                if(topup){
                    if(numberplus){
                        if(timeplus){
                            Toast.makeText(CheckoutActivity.this, "Checkout successfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(CheckoutActivity.this,CitiBikeActivity.class);
                            intent.putExtra("phone", phone);
                            startActivity(intent);
                        }
                    }
                }else{
                    Toast.makeText(CheckoutActivity.this, "Checkout failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}