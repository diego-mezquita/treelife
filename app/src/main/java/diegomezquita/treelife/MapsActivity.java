package diegomezquita.treelife;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
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
    **     This function has two variables:                  **
    **    * Display map using LatLng variable/s          **
    **    *                   **

    @Override
    public void onMapReady(GoogleMap googleMap) {*/
        // Intent intent = getIntent();
        Intent intent = getIntent();
        Containers containers = intent.getParcelableExtra(DataGetter.EXTRA_CLOTHES_CONTAINERS_JSON);
        String message = "Calle avilés, gijón"; //intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        List<Address> geocodeMatches = null;
        double latitude;
        double longitude;

        mMap = googleMap;

        // ***** START - DRAWABLE BLOCK *****
        int icon_height = 100;
        int icon_width = 100;
        BitmapDrawable bitmap_draw_icon = (BitmapDrawable)getResources().getDrawable(R.drawable.marker_icon_diamond);
        Bitmap bitmap_icon = bitmap_draw_icon.getBitmap();
        Bitmap smallMarkerIcon = Bitmap.createScaledBitmap(bitmap_icon, icon_width, icon_height, false);
        // ***** END - DRAWABLE BLOCK *****

     /*   MarkerOptions markerOptions = new MarkerOptions()
                .position(new LatLng(43.54, -5.67))
                .icon(BitmapDescriptorFactory.defaultMarker(240))
                .draggable(true);
        Marker calle_aviles = mMap.addMarker(markerOptions);
     */

        // Iterate by Containers to create all markers for clothes containers
        Iterator<Container> iterator = containers.getContainerList().iterator();

        while (iterator.hasNext()) {
            Container it = iterator.next();

            mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(it.getLatitude(), it.getLongitude()))
                            //       .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_icon_diamond))
                            // .icon(BitmapDescriptorFactory.fromBitmap(("marker_icon_diamond.png", 10, 10))) - DRAWABLE BLOCK (see above)
                    .icon(BitmapDescriptorFactory.fromBitmap(smallMarkerIcon))
                    .title(it.getTitle())
                    .draggable(true));
        }

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
    }

    public Bitmap resizeMapIcons(String iconName,int width, int height){

        Bitmap imageBitmap = BitmapFactory.decodeResource(getResources(), getResources().getIdentifier(iconName, "drawable", getPackageName()));
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(imageBitmap, width, height, false);
        return resizedBitmap;
    }
}
