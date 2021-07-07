package com.example.afinal;

import com.here.sdk.core.GeoCoordinates;
import com.here.sdk.mapview.MapCamera;
import com.here.sdk.mapview.MapScene;
import com.here.sdk.mapview.MapView;

public class TrafficLayer {
    private MapView mapView;

    public TrafficLayer(MapView mapView) {
        this.mapView = mapView;
        MapCamera camera = mapView.getCamera();
        double distanceInMeters = 1000 *10;
        camera.lookAt(new GeoCoordinates(40.730610, -73.935242), distanceInMeters);
        mapView.getMapScene().setLayerState(MapScene.Layers.TRAFFIC_FLOW, MapScene.LayerState.VISIBLE);
        mapView.getMapScene().setLayerState(MapScene.Layers.TRAFFIC_INCIDENTS, MapScene.LayerState.VISIBLE);
    }

}
