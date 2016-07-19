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
import com.google.android.gms.maps.model.Marker;
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
    public final static String EXTRA_JSON_STRING_CONTAINER = "com.diegomezquita.treelife.CONTAINER";
    public final static String EXTRA_FROM = "com.diegomezquita.treelife.EXTRA_FROM";

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

        List<Address> geocodeMatches = null;
        double latitude;
        double longitude;

        mMap = googleMap;

        // ***** START - DRAWABLE BLOCK *****
        ArrayList<Bitmap> iconsList = this.resizeMapIcons(100, 100);
        // ***** END - DRAWABLE BLOCK *****

        // Iterate by Containers to create all markers for clothes containers
        Iterator<Container> iterator = containersToShow.getContainerList().iterator();

        while (iterator.hasNext()) {
            final Container it = iterator.next();
            String type = it.getType();
            int typeIndex = containersType.indexOf(type);
            Bitmap iconX = iconsList.get(typeIndex);
            Integer indexResource = containersTypeResources.get(containersType.indexOf(it.getType()));
            Bitmap icon = iconsList.get(containersTypeResources.indexOf(indexResource));
            String containerJson = it.getJsonStringFromContainer();

            mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(it.getLatitude(), it.getLongitude()))
                    .icon(BitmapDescriptorFactory.fromBitmap(icon))
                    .title(it.getTitle())
                    .snippet(containerJson)
                    .draggable(true));
        }

        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                String jsonString = marker.getSnippet();
                Intent intent = new Intent(MapsActivity.this, ContainerActivity.class);
                intent.putExtra(EXTRA_FROM, "mapsActivity");
                intent.putExtra(EXTRA_JSON_STRING_CONTAINER, jsonString);
                startActivity(intent);
            }
        });

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
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 16));
        }
    }

    public Container getContainerByMarker(Marker marker) {
        String title = marker.getTitle();
        String type = marker.getSnippet();

        return this.containersToShow.getContainerByTitleType(title, type);
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

    public static String getExtraJsonStringContainer() {
        return EXTRA_JSON_STRING_CONTAINER;
    }

    public static String getExtraFrom() {
        return EXTRA_FROM;
    }
}