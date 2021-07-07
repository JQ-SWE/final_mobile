package com.example.afinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MoveCarActivity4 extends AppCompatActivity {

    ImageView photo;
    TextView license, location, message;
    Button backtohome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_move_car4);

        photo = (ImageView) findViewById(R.id.iv4_photo4);
        license = (TextView) findViewById(R.id.tv4_licenseno);
        location = (TextView) findViewById(R.id.tv4_location);
        message = (TextView) findViewById(R.id.tv4_message);
        backtohome = (Button) findViewById(R.id.btn4_back);

        Intent intent = getIntent();
        String License = intent.getStringExtra("license");
        String Location = intent.getStringExtra("location");
        String Message = intent.getStringExtra("message");
        Bitmap Image = (Bitmap)this.getIntent().getParcelableExtra("image");

        //photo.setImageBitmap(stringToBitmap(Image));
        license.setText(License);
        location.setText(Location);
        message.setText(Message);
        photo.setImageBitmap(Image);

        backtohome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MoveCarActivity4.this,HomeActivity.class);
                startActivity(intent);
            }
        });
    }

}