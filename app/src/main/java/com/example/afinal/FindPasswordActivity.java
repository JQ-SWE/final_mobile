package com.example.afinal;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class FindPasswordActivity {

    public EditText phone;
    private EditText certificateno, answer, phone;
    private Button back, submit, getquestion;
    private TextView question;
    private DatabaseHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_password);

        certificateno = (EditText) findViewById(R.id.et_retrieve_certificatenumber);
        answer = (EditText) findViewById(R.id.et_retrieve_securityanswer);
        phone = (EditText) findViewById(R.id.et_retrieve_phone);
        back = (Button) findViewById(R.id.btn_retrieve_back);
        submit = (Button) findViewById(R.id.btn_retrieve_submit);
        question = (TextView) findViewById(R.id.tv_retrieve_securityproblem);
        getquestion = (Button) findViewById(R.id.btn_retrieve_getquestion);

        DB = new DatabaseHelper(this);

        getquestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Phone = phone.getText().toString();

                Boolean checkuser = DB.checkUser(Phone);
                if (!checkuser){
                    Toast.makeText(FindPasswordActivity.this, "User not found", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(FindPasswordActivity.this, "User found", Toast.LENGTH_SHORT).show();
                    Cursor cursor = DB.getQuestion(Phone);
                    StringBuilder stringBuilder = new StringBuilder();
                    while(cursor.moveToNext()){
                        stringBuilder.append(cursor.getString(6));
                        question.setText(stringBuilder);
                    }
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ChangePasswordActivity.class);
                startActivity(intent);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Phone = phone.getText().toString();
                String certno = certificateno.getText().toString();
                String Answer = answer.getText().toString();

                if (certno.equals("")||Answer.equals(""))
                    Toast.makeText(FindPasswordActivity.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                else{
                    Cursor checkCerto = DB.getPhoneCertno(Phone);
                    Cursor checkAnswer = DB.getPhoneAnswer(Phone);
                    if (checkCerto.getString(1) != certno) {
                        Toast.makeText(FindPasswordActivity.this, certno, Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(FindPasswordActivity.this, "Authentication Success", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), ChangePasswordActivity.class);
                        startActivity(intent);
                    }

                    /*
                    if (!checkCerto) {
                        Toast.makeText(FindPasswordActivity.this, "Wrong Certificate Number", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        if (!checkAnswer){
                            Toast.makeText(FindPasswordActivity.this, "Wrong Security Answer", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(FindPasswordActivity.this, "Authentication Success", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), ChangePasswordActivity.class);
                            startActivity(intent);
                        }
                    }*/
                }
            }
        });

    }
}