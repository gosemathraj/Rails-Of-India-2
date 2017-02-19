package com.gosemathraj.railsofindia.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.gosemathraj.railsofindia.R;
import com.gosemathraj.railsofindia.models.TrainRoute;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private TrainRoute trainRoute;
    private SupportMapFragment mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        getIntentData();
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    private void drawPolyLine() {

        for(int i = 0;i < trainRoute.getRoute().size() - 1;i++){
            mMap.addPolyline(new PolylineOptions()
                    .add(new LatLng(trainRoute.getRoute().get(i).getLat(),trainRoute.getRoute().get(i).getLng()),
                            new LatLng(trainRoute.getRoute().get(i+1).getLat(),trainRoute.getRoute().get(i+1).getLng()))
                    .width(5)
                    .color(Color.RED));
        }
    }

    private void getIntentData() {

        if(getIntent() != null){
            trainRoute = (TrainRoute) getIntent().getSerializableExtra("trainRoute");
        }
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        /*LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
*/
        for(int i = 0;i < trainRoute.getRoute().size();i++){
            LatLng latlng = new LatLng(trainRoute.getRoute().get(i).getLat(),trainRoute.getRoute().get(i).getLng());
            mMap.addMarker(new MarkerOptions().position(latlng).title(trainRoute.getRoute().get(i).getFullname()));
        }
        mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(trainRoute.getRoute().get(0).getLat(),
                trainRoute.getRoute().get(0).getLng())));

        mMap.animateCamera( CameraUpdateFactory.zoomTo( 4.0f ) );

        drawPolyLine();
    }
}
