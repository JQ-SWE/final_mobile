package com.example.afinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class UserProfile extends AppCompatActivity {
    private Button Update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        Update = findViewById(R.id.updateButton);
        Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveEdit();
            }
        });
    }
        private void moveEdit(){
        Intent intent = new Intent(UserProfile.this,ChangePasswordActivity.class);
        startActivity(intent);
    }
}