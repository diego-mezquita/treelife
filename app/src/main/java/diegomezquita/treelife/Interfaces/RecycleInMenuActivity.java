package diegomezquita.treelife.Interfaces;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

import diegomezquita.treelife.LocationGetter.CurrentLocationGetter;
import diegomezquita.treelife.DataGetters.DataGetter;
import diegomezquita.treelife.R;

public class RecycleInMenuActivity extends Activity implements LocationListener {

    public final static String EXTRA_LOCATION = "com.diegomezquita.treelife.RECYCLE_IN_MENU_SEARCH_LOCATION";
    protected final static String EXTRA_CLOTHES_CONTAINERS_JSON = "com.diegomezquita.treelife.CLOTHES_CONTAINERS_JSON";
    protected final static String EXTRA_SEARCH_LOCATION = "com.diegomezquita.treelife.RECYCLE_IN_MENU_SEARCH_LOCATION";
    protected SeekBar seekBar;
    protected TextView seekBarTextView;
    protected String currentLocation = new String();

    protected LocationManager locationManager;
    protected LocationListener locationListener;
    protected Double locationLatitude;
    protected Double locationLongitude;
    protected Double locationSearchRatio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle_in_menu);

        this.startSeekBar();

        ActivityCompat.requestPermissions(RecycleInMenuActivity.this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                124);
    }

    public void displaySearchByAddress(View view) {
        /*Intent intent = new Intent(this, RecycleInMenuActivity.class);
        startActivity(intent);*/
    }

    public void displayHabitualContainers(View view) {
        Intent intent = new Intent(this, RecycleInMenuHabitualContainersActivity.class);
        startActivity(intent);
        this.overridePendingTransition(0, 0);
    }

    public void displayCreateContainers(View view) {
        Intent intent = new Intent(this, RecycleInMenuCreateContainerActivity.class);
        startActivity(intent);
        this.overridePendingTransition(0, 0);
    }

    public void executeSearch(View view) {
        Intent intent = new Intent(this, MapsActivity.class);
        String urlClothes = "http://opendata.gijon.es/descargar.php?id=7&tipo=JSON";

        // Getting info from the search form
        // Location specified
        EditText editText = (EditText) this.findViewById(R.id.edit_text_recycle_in_menu_where_location_feedback);
        String locationString = editText.getText().toString();
        // Container types selected ordered alphabetically
     //   boolean checkBoxBatteries = ((CheckBox) this.findViewById(R.id.feedback_check_box_recycle_in_menu__what__batteries)).isChecked();
     //   boolean checkBoxClothes = ((CheckBox) this.findViewById(R.id.feedback_check_box_recycle_in_menu__what__clothes)).isChecked();
     //   boolean checkBoxGlass = ((CheckBox) this.findViewById(R.id.feedback_check_box_recycle_in_menu__what__glass)).isChecked();

        // ArrayList that stores the materials to be or not requested to Gijón Open Data
        ArrayList<Boolean> materials = new ArrayList<>();
        materials.add(((CheckBox) this.findViewById(R.id.feedback_check_box_recycle_in_menu__what__batteries)).isChecked());
        materials.add(((CheckBox) this.findViewById(R.id.feedback_check_box_recycle_in_menu__what__clothes)).isChecked());
        materials.add(((CheckBox) this.findViewById(R.id.feedback_check_box_recycle_in_menu__what__oil)).isChecked());

        boolean checkBoxGlass = ((CheckBox) this.findViewById(R.id.feedback_check_box_recycle_in_menu__what__glass)).isChecked();
        boolean checkBoxPaper = ((CheckBox) this.findViewById(R.id.feedback_check_box_recycle_in_menu__what__paper)).isChecked();
        boolean checkBoxPlastic = ((CheckBox) this.findViewById(R.id.feedback_check_box_recycle_in_menu__what__plastic)).isChecked();

        this.locationSearchRangeFeedback();
        // So far I have the materials ArrayList and this.locationSearchRange ready to be used


        /*DataGetter dataGetter = new DataGetter(checkBoxClothes);

        if(checkBoxClothes) {
            Containers clothesContainers = dataGetter.getClothesContainers();
        }*/

        DataGetter dataGetter = new DataGetter(this, locationString, materials, this.locationSearchRatio);
        dataGetter.execute(urlClothes);

        //data_getter.getClothesContainers();
//
//        String hola = "HOla";
//
//        Containers containersRequested = data_getter.getContainersRequested();
//
//        intent.putExtra(EXTRA_CLOTHES_CONTAINERS_JSON, containersRequested);
//        intent.putExtra(EXTRA_SEARCH_LOCATION, location);
//        this.startActivity(intent);
    }

    // Method that get locationSearchRange from the view
    public void locationSearchRangeFeedback() {
        //Double rangeSearch = ((Radio) this.findViewById(R.id.feedback_check_box_recycle_in_menu__what__glass)).isChecked();
        RadioGroup rangeSearchRadioGroup = ((RadioGroup) findViewById(R.id.feedback_check_box_recycle_in_menu__scopes_group));
        int selectedRangeId = rangeSearchRadioGroup.getCheckedRadioButtonId();
        RadioButton rangeSearchRadioButton = (RadioButton) findViewById(selectedRangeId);

        if(rangeSearchRadioButton.getText().toString().equals("XX")) {
            this.locationSearchRatio = Double.parseDouble(this.seekBarTextView.getText().toString().split(" ")[0]);
        } else {
            this.locationSearchRatio = Double.parseDouble(rangeSearchRadioButton.getText().toString().split(" ")[0]);
        }

    }

    public void getLocationListener() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Intent displayGPSSettingIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivityForResult(displayGPSSettingIntent, 0);
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            String s = "s";
            return;
        }
        String ss = "";

        /*try {
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            if (location != null && location.getTime() > Calendar.getInstance().getTimeInMillis() -2 * 60 * 1000) {
                // lastKnownLocation valid -> use it
                String s = "";
            }
            else {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
            }
        } catch (SecurityException e) {
            e = e;
        }*/

        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        if (location != null && location.getTime() > Calendar.getInstance().getTimeInMillis() -2 * 60 * 1000) {
            // lastKnownLocation valid -> use it
            String s = "";
        }
        else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        }
    }

    public void getLocation() {
        try {
            this.locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

            // getting GPS status
            boolean isGPSEnabled = this.locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

            // getting network status
            boolean isNetworkEnabled = this.locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!isGPSEnabled && !isNetworkEnabled) {
                // no network provider is enabled
                Intent displayGPSSettingIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(displayGPSSettingIntent, 0);
            } else {
                this.getLocationFromProvider();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO get onActivityResult being triggered after startActivityForResult finishes execution
        String s = "ss";
        if (resultCode != 0) {
            // Permission not granted - GPS
            AlertDialog.Builder builder = new AlertDialog.Builder(RecycleInMenuActivity.this);
            builder.setMessage("Write your message here.");
            builder.setCancelable(true);

            builder.setPositiveButton(
                    "Yes",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

            builder.setNegativeButton(
                    "No",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

            AlertDialog alert = builder.create();
            alert.show();
        }

        else {
            this.getLocationFromProvider();
        }
    }

    public void getLocationFromProvider(){
        Location location = new Location("Service provider");

        // getting GPS status
        boolean isGPSEnabled = this.locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        // getting network status
        boolean isNetworkEnabled = this.locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if (isNetworkEnabled) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                this.locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
                Log.d("Network", "Network Enabled");
                if (this.locationManager != null) {
                    location = this.locationManager
                            .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    if (location != null) {
                        this.locationLatitude = location.getLatitude();
                        this.locationLongitude = location.getLongitude();
                    }
                }
            }
        }
        // if GPS Enabled get lat/long using GPS Services
        if (isGPSEnabled) {
            if (location == null) {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    this.locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
                    Log.d("GPS", "GPS Enabled");
                    if (this.locationManager != null) {
                        location = this.locationManager
                                .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        if (location != null) {
                            this.locationLatitude = location.getLatitude();
                            this.locationLongitude = location.getLongitude();
                        }
                    }
                }
            }
        }
    }

    public void getCurrentLocation(View view) {
        //this.getLocationListener();
        this.getLocation();
        CurrentLocationGetter currentLocation = new CurrentLocationGetter(this);
        currentLocation.execute(String.valueOf(this.locationLatitude), String.valueOf(this.locationLongitude));
    }

    public void locationAddress(String address) {
        this.currentLocation = address;
        EditText editTextWhere = (EditText) findViewById(R.id.edit_text_recycle_in_menu_where_location_feedback);
        editTextWhere.setText(address);
        //this.findViewById(R.id.edit_text_recycle_in_menu_where_location_feedback);
    }

    // Required methods due to implement LocationListener
    public void onLocationChanged(Location location) {
        if (location != null) {
            this.locationLatitude = location.getLatitude();
            this.locationLongitude = location.getLongitude();

            if ((ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                    && (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
                return;
            }

            locationManager.removeUpdates(this);
        }
    }

    public void startSeekBar() {
        this.seekBar = (SeekBar) findViewById(R.id.feedback_seek_bar_recycle_in_menu__custom_scoop);
        this.seekBarTextView = (TextView) findViewById(R.id.feedback_text_view_recycle_in_menu__custom_scoop);

        final RecycleInMenuActivity that = this;
        // Initialize the textview with '0'
        this.seekBarTextView.setText("0 m");

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progress = 0;

            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
                progress = progresValue;
                that.getSeekBarTextView().setText(progress /*+ "/" + seekBar.getMax()*/ + " m");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Toast.makeText(getApplicationContext(), "Started tracking seekbar", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                that.seekBarTextView.setText(progress /*+ "/" + seekBar.getMax()*/ + " m");
                Toast.makeText(getApplicationContext(), "Stopped tracking seekbar", Toast.LENGTH_SHORT).show();
            }
        });


    }

    public void onProviderEnabled(String arg0) {}

    public void onStatusChanged(String arg0, int arg1, Bundle arg2) {}

    public void onProviderDisabled(String arg0) {}

    public Double getLocationSearchRatio() {
        return locationSearchRatio;
    }

    public void setLocationSearchRatio(Double locationSearchRatio) {
        this.locationSearchRatio = locationSearchRatio;
    }


    public Double getLocationLatitude() {
        return locationLatitude;
    }

    public void setLocationLatitude(Double locationLatitude) {
        this.locationLatitude = locationLatitude;
    }

    public Double getLocationLongitude() {
        return locationLongitude;
    }

    public void setLocationLongitude(Double locationLongitude) {
        this.locationLongitude = locationLongitude;
    }

    public SeekBar getSeekBar() {
        return seekBar;
    }

    public void setSeekBar(SeekBar seekBar) {
        this.seekBar = seekBar;
    }

    public TextView getSeekBarTextView() {
        return seekBarTextView;
    }

    public void setSeekBarTextView(TextView seekBarTextView) {
        this.seekBarTextView = seekBarTextView;
    }

}
