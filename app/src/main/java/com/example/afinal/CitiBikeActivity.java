package com.example.afinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.ClusterManager;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Locale;

public class CitiBikeActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final int REQUEST_CALL = 1;
    boolean isPermissionGranted; //ask for location permission
    GoogleMap MGoogleMap;
    Button service;
    Button rent;
    Button account;
    private boolean mIsRestore;
    private ClusterManager<MyItem> mClusterManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_citi_bike);
        service = findViewById(R.id.index_service);
        rent = findViewById(R.id.index_rent);
        account = findViewById(R.id.index_account);
        mIsRestore = savedInstanceState != null;

        checkMyPermission();

        //check if google play services available
        if (isPermissionGranted) {
            if (checkGooglePlayServices()) {
                SupportMapFragment supportMapFragment = SupportMapFragment.newInstance();
                getSupportFragmentManager().beginTransaction().add(R.id.CitiBikeContainer, supportMapFragment).commit();
                supportMapFragment.getMapAsync(this);
            } else {
                Toast.makeText(this, "GooglePlay Services Not Available", Toast.LENGTH_SHORT).show();
            }
        }

        rent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                String phone = intent.getStringExtra("phone");

                Intent intent1 = new Intent(CitiBikeActivity.this,BikeShareRentAcitvity.class);
                intent1.putExtra("phone", phone);
                startActivity(intent1);
            }
        });

        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                String phone = intent.getStringExtra("phone");

                Intent intent1 = new Intent(CitiBikeActivity.this,BikeShareAccountActivity.class);
                intent1.putExtra("phone", phone);
                startActivity(intent1);
            }
        });

        service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makePhoneCall();
            }
        });
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
                    Toast.makeText(CitiBikeActivity.this, "User Canceled Dialog", Toast.LENGTH_SHORT).show();
                }
            });
            dialog.show();
        }
        return false;
    }

    //check if location services permitted
    private void checkMyPermission() {

        Dexter.withContext(this).withPermission(Manifest.permission.ACCESS_FINE_LOCATION).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                isPermissionGranted = true;
                Toast.makeText(CitiBikeActivity.this, "Permission grated", Toast.LENGTH_SHORT).show();
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

    //write anything needed in this function (e.g. Marker, Zoom, Gesture)
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
            startCluster(mIsRestore);
        }
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        getMenuInflater().inflate(R.menu.map_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected (@NonNull MenuItem item){

        if (item.getItemId() == R.id.NormalMap) {
            MGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        }

        if (item.getItemId() == R.id.SatelliteMap) {
            MGoogleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        }

        if (item.getItemId() == R.id.MapHybrid) {
            MGoogleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        }

        if (item.getItemId() == R.id.MapTerrain) {
            MGoogleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        }

        return super.onOptionsItemSelected(item);
    }

    protected GoogleMap getMap() { return MGoogleMap; }

    protected void startCluster(boolean isRestore) {
        if (!isRestore) {
            getMap().moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(40.74177603, -74.00149746), 17));
        }
        mClusterManager = new ClusterManager<>(this, getMap());

        //item appearance
        CitiBikeRenderer renderer = new CitiBikeRenderer(this, getMap(), mClusterManager);
        mClusterManager.setRenderer(renderer);

        //set onClickListener
        mClusterManager.setOnClusterItemClickListener(item -> {
            // Listen for clicks on a cluster item here
            return false;
        });
        mClusterManager.setOnClusterClickListener(item -> {
            // Listen for clicks on a cluster here
            return false;
        });


        // Add a custom InfoWindowAdapter by setting it to the MarkerManager.Collection object from
        // ClusterManager rather than from GoogleMap.setInfoWindowAdapter
        mClusterManager.getMarkerCollection().setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                final LayoutInflater inflater = LayoutInflater.from(CitiBikeActivity.this);
                final View view = inflater.inflate(R.layout.layout, null);
                final TextView textView = view.findViewById(R.id.textViewTitle);
                String text = (marker.getTitle() != null) ? marker.getTitle() : "No Information";
                textView.setText(text);
                return view;
            }

            @Override
            public View getInfoContents(Marker marker) {
                return null;
            }
        });
//        mClusterManager.getMarkerCollection().setOnInfoWindowClickListener(marker ->
//                Toast.makeText(CitiBikeActivity.this,
//                        "Info window clicked.",
//                        Toast.LENGTH_SHORT).show());

        try {
            readItems();
        } catch (JSONException e) {
            Toast.makeText(this, "Problem reading list of markers.", Toast.LENGTH_LONG).show();
        }

        getMap().setOnCameraIdleListener(mClusterManager);

    }


    private void readItems() throws JSONException {
        InputStream inputStream = getResources().openRawResource(R.raw.bike_stations);
        List<MyItem> items = new BikeItemReader().read(inputStream);
        mClusterManager.addItems(items);
    }

    private void makePhoneCall(){
        String number = "13758128408";
        if(ContextCompat.checkSelfPermission(CitiBikeActivity.this,
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(CitiBikeActivity.this,
                    new String[] {Manifest.permission.CALL_PHONE}, REQUEST_CALL);
        }else{
            String dial = "tel:" + number;
            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQUEST_CALL){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                makePhoneCall();
            }else{
                Toast.makeText(this, "Permission denied...!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}