package com.example.afinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class MoveCarActivity2 extends AppCompatActivity {

    Button next;
    EditText license, location, message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_move_car2);

        next = (Button) findViewById(R.id.movecar2_submit);
        license = (EditText) findViewById(R.id.et_Licenseplatenumber);
        location = (EditText) findViewById(R.id.et_location);
        message = (EditText) findViewById(R.id.et_message);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String License = license.getText().toString();
                String Location = location.getText().toString();
                String Message = message.getText().toString();

                if (License.equals("") || Location.equals("") || Message.equals(""))
                    Toast.makeText(MoveCarActivity2.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                else{
                    Intent intent = new Intent(MoveCarActivity2.this,MoveCarActivity3.class);
                    intent.putExtra("license", License);
                    intent.putExtra("location", Location);
                    intent.putExtra("message", Message);
                    startActivity(intent);
                }
            }
        });

    }
}