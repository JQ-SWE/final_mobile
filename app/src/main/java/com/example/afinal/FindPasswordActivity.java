package com.example.afinal;

import android.app.AppComponentFactory;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class FindPasswordActivity extends AppCompatActivity {

    private EditText answer, phone;
    private Button back, submit, getquestion;
    private TextView question;
    private DatabaseHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_password);

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
                if (!checkuser) {
                    Toast.makeText(FindPasswordActivity.this, "User not found", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(FindPasswordActivity.this, "User found", Toast.LENGTH_SHORT).show();
                    Cursor cursor = DB.getQuestion(Phone);
                    StringBuilder stringBuilder = new StringBuilder();
                    while (cursor.moveToNext()) {
                        stringBuilder.append(cursor.getString(6));
                        question.setText(stringBuilder);
                    }
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
                String Answer = answer.getText().toString();

                if (Answer.equals(""))
                    Toast.makeText(FindPasswordActivity.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                else {
                    Boolean checkanswer = DB.checkUserAnswer(Phone, Answer);
                    if (checkanswer == true) {
                        Toast.makeText(FindPasswordActivity.this, "Successful Authentication", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), ChangePasswordActivity.class);
                        intent.putExtra("phone",Phone);
                        startActivity(intent);
                    } else {
                        Toast.makeText(FindPasswordActivity.this, "Wrong Answer", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}