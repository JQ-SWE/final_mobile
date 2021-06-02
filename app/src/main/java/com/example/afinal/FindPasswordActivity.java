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

public class FindPasswordActivity extends AppCompatActivity {

    public EditText phone;
    private EditText certificateno, answer;
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
                    //String Question = DB.getQuestion(Phone);
                    question.setText("Question");
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
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
                    Boolean checkuserinfo = DB.checkCertnoAnswer(Phone, certno, Answer);
                    if (checkuserinfo == true) {
                        Toast.makeText(FindPasswordActivity.this, "Authentication Success", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), ChangePasswordActivity.class);
                        startActivity(intent);
                    }else {
                        Toast.makeText(FindPasswordActivity.this, "Authentication Failure", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }
}