package com.example.afinal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;


public class SignUpActivity extends AppCompatActivity {

    private EditText phonenumber, password, repassword, certificatenumber, name, securityquestion, securityanswer;
    private Button signup, signin;
    private Spinner certificatetype, location;
    private DatabaseHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        certificatetype = (Spinner) findViewById(R.id.spinner_certificatetype);
        certificatenumber = (EditText) findViewById(R.id.et_signup_certificatenumber);
        name = (EditText) findViewById(R.id.et_signup_name);
        phonenumber = (EditText) findViewById(R.id.et_signup_phone);
        password = (EditText) findViewById(R.id.et_signup_password);
        repassword = (EditText) findViewById(R.id.et_signup_repassword);
        location = (Spinner) findViewById(R.id.spinner_city);
        securityquestion = (EditText) findViewById(R.id.et_signup_question);
        securityanswer = (EditText) findViewById(R.id.et_signup_answer);
        signin = (Button) findViewById(R.id.btnsignin);
        signup = (Button) findViewById(R.id.btnsignup);
        DB = new DatabaseHelper(this);

        //sign up function
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = phonenumber.getText().toString();
                String pass = password.getText().toString();
                String repass = repassword.getText().toString();
                String certtype = certificatetype.getSelectedItem().toString();
                String certnumber = certificatenumber.getText().toString();
                String Name = name.getText().toString();
                String Location = location.getSelectedItem().toString();
                String question = securityquestion.getText().toString();
                String answer = securityanswer.getText().toString();

                int rentalno = 0;
                int rentalhour = 0;
                int balance = 0;

                if (phone.equals("") || pass.equals("") || repass.equals(""))
                    Toast.makeText(SignUpActivity.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                else {
                    if (pass.equals(repass)) {
                        Boolean checkuser = DB.checkUser(phone);
                        if (!checkuser) {
                            Boolean insert = DB.insertData(certtype, certnumber, Name, phone, Location, pass, question, answer, rentalno, rentalhour, balance);
                            if (insert) {
                                Toast.makeText(SignUpActivity.this, "Registered successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(SignUpActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(SignUpActivity.this, "User already exist! Please sign in", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(SignUpActivity.this, "Password not matching", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        //sign in function
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });


        //spinner for certificate type
        Spinner spinner_certificatetype = findViewById(R.id.spinner_certificatetype);
        ArrayAdapter<CharSequence> adapter_certificatetype = ArrayAdapter.createFromResource(this, R.array.spinner_certificatetype, android.R.layout.simple_spinner_item);
        adapter_certificatetype.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_certificatetype.setAdapter(adapter_certificatetype);

        //spinner for register city
        Spinner spinner_registercity = findViewById(R.id.spinner_city);
        ArrayAdapter<CharSequence> adapter_registercity = ArrayAdapter.createFromResource(this, R.array.spinner_city, android.R.layout.simple_spinner_item);
        adapter_registercity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_registercity.setAdapter(adapter_registercity);
    }
}