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

    TextView balance, Totalcost;
    Button confirm;

    private DatabaseHelper DB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        balance = (TextView) findViewById(R.id.checkout_balance);
        Totalcost = (TextView) findViewById(R.id.checkout_cost);
        confirm = (Button) findViewById(R.id.checkout_confirm);

        DB = new DatabaseHelper(this);

        Intent intent = getIntent();
        String cost = intent.getStringExtra("cost");
        String restbalance = intent.getStringExtra("balance");
        String phone = intent.getStringExtra("phone");

        Totalcost.setText(cost);
        balance.setText(restbalance);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int int_cost = Integer.parseInt(cost);
                int int_restbalance = Integer.parseInt(restbalance);

                int totalrest = int_restbalance - int_cost;
                String str_totalrest = Integer.toString(totalrest);
                boolean topup = DB.topup(str_totalrest, phone);
                if(topup){
                    Toast.makeText(CheckoutActivity.this, "Checkout successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(CheckoutActivity.this,CitiBikeActivity.class);
                    intent.putExtra("phone", phone);
                    startActivity(intent);
                }
            }
        });

    }
}