package diegomezquita.treelife;

import java.io.IOException;
import java.util.List;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import android.content.Intent;
//import android.widget.EditText;

import android.location.Address;
import android.location.Geocoder;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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

    /*
    ** This functio hash two variables:                  **
    **    * Display map using LatLng variable/s          **
    **    *                   **

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        List<Address> geocodeMatches = null;
        double latitude;
        double longitude;

        mMap = googleMap;

        // PLAYING WITH STATIC LOCATION DEFINED BY (LATITUDE, LONGITUDE)
        // Add some marker and move the camera
        LatLng gijon = new LatLng(43.32, -5.42);
        LatLng texas_state = new LatLng(29.8892575, -97.9401803);
        // mMap.addMarker(new MarkerOptions().position(gijon).title("Gijón"));
        // mMap.addMarker(new MarkerOptions().position(texas_state).title("Texas Statate University"));
        // mMap.addMarker(new MarkerOptions()
        //         .icon(BitmapDescriptorFactory.defaultMarker())
        //         .anchor(0.0f, 1.0f)
        //         .position(texas_state));
        // mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(texas_state, 16));

        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        // PLAYING WITH STATIC LOCATION DEFINED BY (LATITUDE, LONGITUDE)
        try {
            geocodeMatches =
                    new Geocoder(this).getFromLocationName(message, 1);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        if (!geocodeMatches.isEmpty())
        {
            latitude = geocodeMatches.get(0).getLatitude();
            longitude = geocodeMatches.get(0).getLongitude();

            LatLng location = new LatLng(latitude, longitude);

            mMap.addMarker(new MarkerOptions()
                 .icon(BitmapDescriptorFactory.defaultMarker())
                 .anchor(0.0f, 1.0f)
                 .position(location));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 16));
        }
    }*/
}
