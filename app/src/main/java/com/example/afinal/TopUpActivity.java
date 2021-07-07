package com.example.afinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class TopUpActivity extends AppCompatActivity {

    EditText bank, amount;
    Button confirm;
    TextView test;

    private DatabaseHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_up);

        bank = (EditText) findViewById(R.id.bankaccount);
        amount = (EditText) findViewById(R.id.topupamount);
        confirm = (Button) findViewById(R.id.topupconfirm);
        test = findViewById(R.id.test);

        DB = new DatabaseHelper(this);

        Intent intent = getIntent();
        String restbalance = intent.getStringExtra("balance");
        String phone = intent.getStringExtra("phone");

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Amount = amount.getText().toString();
                int int_amount = Integer.parseInt(Amount);

                int int_restbalance = Integer.parseInt(restbalance);

                int totalrest = int_restbalance + int_amount;
                String str_totalrest = Integer.toString(totalrest);
                boolean topup = DB.topup(str_totalrest, phone);
                if(topup){
                    Toast.makeText(TopUpActivity.this, "Top up successfully, please log in again", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(TopUpActivity.this,LoginActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}