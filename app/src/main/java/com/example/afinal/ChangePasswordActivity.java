package com.example.afinal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ChangePasswordActivity extends FindPasswordActivity {

    private EditText password, repassword, phone;
    private Button submit;
    private DatabaseHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        password = (EditText) findViewById(R.id.et_changepassword_password);
        repassword = (EditText) findViewById(R.id.et_changepassword_repassword);
        submit = (Button) findViewById(R.id.btn_changepassword_submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (password != repassword) {
                    Toast.makeText(ChangePasswordActivity.this, "The re-password should be the same as the password!", Toast.LENGTH_SHORT).show();
                } else {
                    String Phone = phone.getText().toString();
                    String Password = password.getText().toString();
                    boolean ischanged = DB.changepassword(Phone, Password);
                    Toast.makeText(ChangePasswordActivity.this, "Your password has been changed successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                }
            }
        });

    }
}