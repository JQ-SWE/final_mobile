package com.example.afinal;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class BikeShareRentAcitvity extends AppCompatActivity {

    ImageView btScan, back;
    Button confirm;
    EditText identifier;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bike_share_rent_acitvity);

        btScan = (ImageButton) findViewById(R.id.btn_scan);
        back = (ImageView) findViewById(R.id.BikeShare_rent_back);
        confirm = (Button) findViewById(R.id.bikeconfirm);
        identifier = (EditText) findViewById(R.id.et_bikeidentifier);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String num = identifier.getText().toString();
                Intent intent = new Intent(getApplicationContext(), BikeShareRecordActivity.class);
                intent.putExtra("identifier", num);
                startActivity(intent);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CitiBikeActivity.class);
                startActivity(intent);
            }
        });

        btScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator intentIntegrator = new IntentIntegrator(
                        BikeShareRentAcitvity.this
                );
                //set prompt text
                intentIntegrator.setPrompt("For flash use volume up key");
                //set beep
                intentIntegrator.setBeepEnabled(true);
                //locked orientation
                intentIntegrator.setOrientationLocked(true);
                //set capture activity
                intentIntegrator.setCaptureActivity(Capture.class);
                //initiate scan
                intentIntegrator.initiateScan();
            }
        });
    }

    @Override
    public void onActivityReenter(int resultCode, Intent data) {
        super.onActivityReenter(resultCode, data);
        //initialize intent result
        IntentResult intentResult = IntentIntegrator.parseActivityResult(
                resultCode, resultCode, data
        );
        //check condition
        if (intentResult.getContents() != null) {
            //when result content is not null
            //initialize alert dialog
            AlertDialog.Builder builder = new AlertDialog.Builder(
                    BikeShareRentAcitvity.this
            );
            //set title
            builder.setTitle("Result");
            //set message
            builder.setMessage(intentResult.getContents());
            //set positive button
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //dismiss dialog
                    dialog.dismiss();
                }
            });
            //show alert dialog
            builder.show();
        }else{
            //when result content is null
            //display toast
            Toast.makeText(getApplicationContext(), "Oops...you did not scan anything", Toast.LENGTH_SHORT).show();
        }

    }
}