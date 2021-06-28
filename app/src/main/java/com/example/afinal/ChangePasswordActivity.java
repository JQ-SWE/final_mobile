package com.example.afinal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

public class ChangePasswordActivity extends AppCompatActivity {

    private TextView phone;
    private EditText password, repassword;
    private Button submit;
    private DatabaseHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        phone = (TextView) findViewById(R.id.tv_changepassword_phone);
        password = (EditText) findViewById(R.id.et_changepassword_password);
        repassword = (EditText) findViewById(R.id.et_changepassword_repassword);
        submit = (Button) findViewById(R.id.btn_changepassword_submit);
        DB = new DatabaseHelper(this);

        Intent intent = getIntent();
        phone.setText(intent.getStringExtra("userphone"));

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Phone = phone.getText().toString();
                String Password = password.getText().toString();
                String Repassword = repassword.getText().toString();

                if (!Password.equals(Repassword)) {
                    Toast.makeText(ChangePasswordActivity.this, "The re-password should be the same as the password!", Toast.LENGTH_SHORT).show();
                } else {
                    Boolean checkPasswordUpdate = DB.updatePassword(Phone, Password);
                    if(checkPasswordUpdate == true){
                        Toast.makeText(ChangePasswordActivity.this, "Your password has been changed successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(ChangePasswordActivity.this, "Password Update Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
