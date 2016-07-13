package diegomezquita.treelife.Interfaces;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
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

import diegomezquita.treelife.Models.Container;
import diegomezquita.treelife.Models.Containers;
import diegomezquita.treelife.DataGetters.DataGetter;
import diegomezquita.treelife.R;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private String searchLocation;
    private Containers containersToShow;

    private ArrayList<String> containersType = new ArrayList<>(Arrays.asList("batteries", "clothes", "oil"));
    private ArrayList<Integer> containersTypeResources = new ArrayList<>(Arrays
            .asList(R.drawable.marker_icon_battery_red, R.drawable.marker_icon_clothes, R.drawable.marker_icon_oil_curve_black));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        Intent intent = getIntent();
        this.containersToShow = intent.getParcelableExtra(DataGetter.getExtraContainersRequested());
        this.searchLocation = intent.getStringExtra(DataGetter.getExtraSearchLocation());
        //this.searchLocation = "Calle avilés, gijón"; //intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        mapFragment.getMapAsync(this);

//        Intent intent = getIntent();
//        this.searchLocation = intent.getStringExtra(RecycleInMenuActivity.EXTRA_LOCATION);
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
//        Intent intent = getIntent();
//        ArrayList<Containers> containersByType = intent.getParcelableExtra(DataGetter.EXTRA_CONTAINERS_BY_TYPE);
//        String message = "Calle avilés, gijón"; //intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        List<Address> geocodeMatches = null;
        double latitude;
        double longitude;

        mMap = googleMap;

        // ***** START - DRAWABLE BLOCK *****
        ArrayList<Bitmap> iconsList = this.resizeMapIcons(100, 100); //new ArrayList<>(); // CALL THE FUNCTION THAT RETURNS A ARRAYLIST<BITMAP> WITH ALL THE ICONS SCALED
        // ***** END - DRAWABLE BLOCK *****

     /*   MarkerOptions markerOptions = new MarkerOptions()
                .position(new LatLng(43.54, -5.67))
                .icon(BitmapDescriptorFactory.defaultMarker(240))
                .draggable(true);
        Marker calle_aviles = mMap.addMarker(markerOptions);
     */


        // Containers containers = new Containers();
        // Iterate by Containers to create all markers for clothes containers
        Iterator<Container> iterator = containersToShow.getContainerList().iterator();
String a = "";
        while (iterator.hasNext()) {
            Container it = iterator.next();
            String type = it.getType();
            int typeIndex =containersType.indexOf(type);
            Bitmap iconX = iconsList.get(typeIndex);
            Integer indexResource = containersTypeResources.get(containersType.indexOf(it.getType()));
            Bitmap icon = iconsList.get(containersTypeResources.indexOf(indexResource));

            mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(it.getLatitude(), it.getLongitude()))
                            //       .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_icon_diamond))
                            // .icon(BitmapDescriptorFactory.fromBitmap(("marker_icon_diamond.png", 10, 10))) - DRAWABLE BLOCK (see above)
                    .icon(BitmapDescriptorFactory.fromBitmap(icon))
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
                    new Geocoder(this).getFromLocationName(this.searchLocation, 1);
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
//            mMap.addMarker(new MarkerOptions()
//                    .icon(BitmapDescriptorFactory.fromBitmap(containersTypeResources.get(0)))
//                    .position(location));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 16));
        }
    }

    public ArrayList<Bitmap> resizeMapIcons(int width, int height){

        ArrayList<Bitmap> resizedIconsList = new ArrayList<>();

        for (Integer typeResource : containersTypeResources) {
            BitmapDrawable bitmap_draw_icon = (BitmapDrawable) getResources().getDrawable(typeResource);
            Bitmap bitmap_icon = bitmap_draw_icon.getBitmap();
            resizedIconsList.add(Bitmap.createScaledBitmap(bitmap_icon, width, height, false));
        }

        return resizedIconsList;
    }
}