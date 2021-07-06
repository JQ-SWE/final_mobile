package com.example.afinal;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.maps.android.clustering.ClusterManager;

import org.json.JSONException;

import java.io.InputStream;
import java.util.List;

public class SubwayActivity extends FragmentActivity implements OnMapReadyCallback{
    private GoogleMap mMap;
    private ClusterManager<MyItem> mClusterManager;
    private boolean mIsRestore;

    protected int getLayoutId() {
        return R.layout.map;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mIsRestore = savedInstanceState != null;
        setContentView(getLayoutId());
        setUpMap();
    }

    public void onMapReady(GoogleMap map) {
        if (mMap != null) {
            return;
        }
        mMap = map;

        startDemo(mIsRestore);
    }

    private void setUpMap() {
        ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMapAsync(this);
    }


    protected GoogleMap getMap() {
        return mMap;
    }

    protected void startDemo(boolean isRestore) {
        if (!isRestore) {
            getMap().moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(40, -73), 10));
        }

        mClusterManager = new ClusterManager<>(this, getMap());

        //item appearance
        RoadConditionRenderer renderer = new RoadConditionRenderer(this, getMap(), mClusterManager);
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
                final LayoutInflater inflater = LayoutInflater.from(SubwayActivity.this);
                final View view = inflater.inflate(R.layout.layout, null);
                final TextView textView = view.findViewById(R.id.textViewTitle);
                String text = (marker.getTitle() != null) ? marker.getTitle() : "Cluster Item";
                textView.setText(text);
                return view;
            }

            @Override
            public View getInfoContents(Marker marker) {
                return null;
            }
        });
        mClusterManager.getMarkerCollection().setOnInfoWindowClickListener(marker ->
                Toast.makeText(SubwayActivity.this,
                        "Info window clicked.",
                        Toast.LENGTH_SHORT).show());

        try {
            readItems();
        } catch (JSONException e) {
            Toast.makeText(this, "Problem reading list of markers.", Toast.LENGTH_LONG).show();
        }

        getMap().setOnCameraIdleListener(mClusterManager);

    }


    private void readItems() throws JSONException {
        InputStream inputStream = getResources().openRawResource(R.raw.subway_entrance);
        List<MyItem> items = new MyItemReader().read(inputStream);
        mClusterManager.addItems(items);
    }

}
