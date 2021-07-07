package com.example.afinal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;


public class LoginActivity extends AppCompatActivity {

    private EditText phonenumber, password;
    private Button btnlogin;
    private TextView tvsignup;
    private TextView tvfindpassword;
    private DatabaseHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        phonenumber = (EditText) findViewById(R.id.et_login_phone);
        password = (EditText) findViewById(R.id.et_login_password);
        btnlogin = (Button) findViewById(R.id.btn_login);
        tvsignup = (TextView) findViewById(R.id.tv_login_signup);
        tvfindpassword = (TextView) findViewById(R.id.tv_login_findpassword);
        DB = new DatabaseHelper(this);

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = phonenumber.getText().toString();
                String pass = password.getText().toString();

                if (phone.equals("") || pass.equals(""))
                    Toast.makeText(LoginActivity.this, "Please enter all the field", Toast.LENGTH_SHORT).show();
                else {
                    Boolean checkuserpass = DB.checkPhoneNumberPassword(phone, pass);
                    if (checkuserpass == true) {
                        Toast.makeText(LoginActivity.this, "Sign in successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this,HomeActivity.class);
                        intent.putExtra("phone", phone);
                        startActivity(intent);
                    } else {
                        Toast.makeText(LoginActivity.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        tvfindpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), FindPasswordActivity.class);
                startActivity(intent);
            }
        });

        tvsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(intent);
            }
        });
    }
}