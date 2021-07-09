package com.example.afinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.text.DateFormat;
import java.util.Calendar;

public class MoveCarActivity1 extends AppCompatActivity implements OnMapReadyCallback {

    Button request;
    TextView date;
    boolean isPermissionGranted; //ask for location permission
    GoogleMap MGoogleMap;
    MapView mapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_move_car1);

        request = (Button) findViewById(R.id.movecar_request);
        date = (TextView) findViewById(R.id.tv_date);
        mapView = findViewById(R.id.mapView);

        checkPermission();

        Calendar calendar = Calendar.getInstance();
        String curdate = DateFormat.getDateInstance().format(calendar.getTime());
        date.setText(curdate);

        request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MoveCarActivity1.this,MoveCarActivity2.class);
                startActivity(intent);
            }
        });
        if (isPermissionGranted) {
            if (checkGooglePlayServices()) {
                mapView.getMapAsync(this);
                mapView.onCreate(savedInstanceState);
//                SupportMapFragment supportMapFragment = SupportMapFragment.newInstance();
//                getSupportFragmentManager().beginTransaction().add(R.id.RoadConditionContainer, supportMapFragment).commit();
//                supportMapFragment.getMapAsync(this);
//                checkGooglePlayServices();
            } else {
                Toast.makeText(this, "GooglePlay Services Not Available", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean checkGooglePlayServices() {
        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        int result = googleApiAvailability.isGooglePlayServicesAvailable(this);
        if (result == ConnectionResult.SUCCESS) {
            return true;
        } else if (googleApiAvailability.isUserResolvableError(result)) {
            Dialog dialog = googleApiAvailability.getErrorDialog(this, result, 201, new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    Toast.makeText(MoveCarActivity1.this, "User Canceled Dialog", Toast.LENGTH_SHORT).show();
                }
            });
            dialog.show();
        }
        return false;
    }

    private void checkPermission() {
        Dexter.withContext(this).withPermission(Manifest.permission.ACCESS_FINE_LOCATION).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                isPermissionGranted = true;
                Toast.makeText(MoveCarActivity1.this,"Permission Granted", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package",getPackageName(),"");
                intent.setData(uri);
                startActivity(intent);
            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                permissionToken.continuePermissionRequest();
            }
        }).check();
    }



    private void checkMyPermission() {

        Dexter.withContext(this).withPermission(Manifest.permission.ACCESS_FINE_LOCATION).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                isPermissionGranted = true;
                Toast.makeText(MoveCarActivity1.this, "Permission grated", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getPackageName(), "");
                intent.setData(uri);
                startActivity(intent);
            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                permissionToken.continuePermissionRequest();
            }
        }).check();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        MGoogleMap = googleMap;
        //add marker
//        LatLng latLng = new LatLng(45, -104);
//        MarkerOptions markerOptions = new MarkerOptions();
//        markerOptions.title("My position");
//        markerOptions.position(latLng);
//        MGoogleMap.addMarker(markerOptions);
//        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 5);
//        googleMap.animateCamera(cameraUpdate);

        //gesture settings
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        googleMap.getUiSettings().setCompassEnabled(true);
        googleMap.getUiSettings().setZoomGesturesEnabled(true);
        googleMap.getUiSettings().setScrollGesturesEnabled(true);
        googleMap.getUiSettings().setRotateGesturesEnabled(true);
        //get current location
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        googleMap.setMyLocationEnabled(true);
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        // Creating a criteria object to retrieve provider
        Criteria criteria = new Criteria();

        // Getting the name of the best provider
        String provider = locationManager.getBestProvider(criteria, true);

        // Getting Current Location
        Location location = locationManager.getLastKnownLocation(provider);

        if (location != null) {
            // Getting latitude of the current location
            double latitude = location.getLatitude();

            // Getting longitude of the current location
            double longitude = location.getLongitude();

            // Creating a LatLng object for the current location
            LatLng latLng = new LatLng(latitude, longitude);

            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 17);
            googleMap.animateCamera(cameraUpdate);

        }
    }

    protected void onStart(){
        super.onStart();
        mapView.onStart();
    }

    protected void onResume(){
        super.onResume();
        mapView.onResume();
    }

    protected void onPause(){
        super.onPause();
        mapView.onPause();
    }

    protected void onStop(){
        super.onStop();
        mapView.onStop();
    }

    protected void onDestroy(){
        super.onDestroy();
        mapView.onDestroy();
    }

    public void onSaveInstanceState(@NonNull Bundle outState,@NonNull PersistableBundle outPersistence){
        super.onSaveInstanceState(outState,outPersistence);
        mapView.onSaveInstanceState(outState);
    }



    public void onLowMemory(){
        super.onLowMemory();
        mapView.onLowMemory();
    }
}