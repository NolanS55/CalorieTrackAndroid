package com.example.calorietracker;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import org.osmdroid.config.Configuration;
import org.osmdroid.views.MapView;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.overlay.Marker;

import java.io.File;

public class Map extends AppCompatActivity {

    private MapView map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);  // Only set once

        // Initialize OsmDroid configurations
        File osmdroidBasePath = new File(getCacheDir(), "osmdroid");
        Configuration.getInstance().setOsmdroidBasePath(osmdroidBasePath);
        Configuration.getInstance().setOsmdroidTileCache(new File(osmdroidBasePath, "tiles"));
        Configuration.getInstance().setUserAgentValue(getPackageName());
        Configuration.getInstance().setTileDownloadThreads((short) 4);
        Configuration.getInstance().setCacheMapTileCount((short) 12);

        // Initialize the map
        map = findViewById(R.id.map);
        map.setTileSource(TileSourceFactory.MAPNIK); // Use OpenStreetMap tiles
        map.setMultiTouchControls(true); // Enable touch gestures (pinch to zoom, etc.)

        // Set the accurate coordinates for Toronto
        GeoPoint startPoint = new GeoPoint(43.65107, -79.347015); // Toronto Coordinates

        // Log the coordinates to make sure they're correct
        Log.d("MapActivity", "Latitude: " + startPoint.getLatitude() + " Longitude: " + startPoint.getLongitude());

        // Set initial zoom and location
        map.getController().setZoom(12.0); // Set zoom level
        map.getController().setCenter(startPoint); // Center the map on Toronto

        // Add a marker at the location
        Marker marker = new Marker(map);
        marker.setPosition(startPoint);
        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        marker.setTitle("Toronto");
        map.getOverlays().add(marker); // Add marker to map
    }
}