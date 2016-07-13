package diegomezquita.treelife.DataGetters;

import android.content.Intent;
import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

import diegomezquita.treelife.Models.Container;
import diegomezquita.treelife.Models.Containers;
import diegomezquita.treelife.Interfaces.MapsActivity;
import diegomezquita.treelife.Interfaces.RecycleInMenuActivity;

/**
 * Created by diegomezquita on 19/03/16.
 *
 * Created to get data from an URL
 */
public class DataGetter extends AsyncTask<String, String, Containers> { // AsyncTask<'params',
    public final static String EXTRA_CLOTHES_JSON = "com.diegomezquita.treelife.CLOTHES_JSON";
    protected final static String EXTRA_CLOTHES_CONTAINERS_JSON = "com.diegomezquita.treelife.CLOTHES_CONTAINERS_JSON";
    protected final static String EXTRA_CONTAINERS_REQUESTED = "com.diegomezquita.treelife.EXTRA_CONTAINERS_BY_TYPE";
    protected final static String EXTRA_SEARCH_LOCATION = "com.diegomezquita.treelife.RECYCLE_IN_MENU_SEARCH_LOCATION";

    // URL to each container type data
    private String openDataClothesURL = "http://opendata.gijon.es/descargar.php?id=7&tipo=JSON";
    private String openDataBatteriesURL = "http://opendata.gijon.es/descargar.php?id=68&tipo=JSON";
    private String openDataOilURL = "http://opendata.gijon.es/descargar.php?id=6&tipo=JSON";
    private ArrayList<String> openDataURLs = new ArrayList<>(Arrays.asList(openDataBatteriesURL, openDataClothesURL, openDataOilURL));
    private ArrayList<String> containersType = new ArrayList<>(Arrays.asList("batteries", "clothes", "oil"));

    // HttpURLConnection urlConnection;

    private RecycleInMenuActivity recycleInMenuActivity = new RecycleInMenuActivity();
    private String searchLocation = "";
    private boolean clothesSelected;
    private Double locationSearchRatio;
    private ArrayList<Boolean> materials;

    private Containers containersRequested = new Containers();
    /*final LinearLayout recycleInMenuActivityLinearLayout;
    final EditText recycleInMenuActivityEditText;

    public DataGetter(RecycleInMenuActivity activity, LinearLayout activityLinearLayout, EditText editText) {
        this.recycleInMenuActivity = activity;
        this.recycleInMenuActivityLinearLayout = activityLinearLayout;
        this.recycleInMenuActivityEditText = editText;
    }
    */

    public DataGetter(boolean clothes) {
        this.clothesSelected = clothes;

    }

    public DataGetter(RecycleInMenuActivity activity, String location, boolean clothes, Double searchRange) {
        this.recycleInMenuActivity = activity;
        this.searchLocation = location;
        this.clothesSelected = clothes;
        this.locationSearchRatio = searchRange;

    }

    public DataGetter(RecycleInMenuActivity activity, String location, ArrayList<Boolean> materials, Double searchRange) {
        this.recycleInMenuActivity = activity;
        this.searchLocation = location;
        this.materials = materials;
        this.locationSearchRatio = searchRange;

    }

    @Override
    protected Containers doInBackground(String... params) {
        String result = "";
        ArrayList<Containers> containersByType = new ArrayList<>();

        try {
            for (int i = 0; i < this.materials.size(); i++) {
                if (this.materials.get(i)) {
                    result = this.getOpenDataFromURL(this.openDataURLs.get(i));
                    result = this.processJsonFromApi(result);
                    Containers containers = this.GetContainersFromJsonString(result);
                    containers.setContainerType(this.containersType.get(i));
                    //containersByType.add(containers);
                    this.containersRequested.concatContainers(containers);
                }
            }

            return this.containersRequested;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            int i = 0;
        }

        return this.containersRequested;
        //return null;
    }

    protected String getOpenDataFromURL(String json_url) {
        //------------------- INI ------------------------
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        String buffer_value = new String();

        try {
            URL url = new URL(json_url);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            InputStream stream = connection.getInputStream();

            reader = new BufferedReader(new InputStreamReader(stream));

            StringBuffer buffer = new StringBuffer();
            String line;

            while((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            buffer_value = buffer.toString();

            //return buffer.toString();


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //return null;
        return buffer_value;

        //------------------- FIN ------------------------
    }

    @Override
    protected void onPostExecute(Containers containersRequested) {
        /*result = this.processJsonFromApi(result);
        Containers containers = this.GetContainersFromJsonString(result);
        super.onPostExecute(result);

        this.containersRequested = this.containersRequested.concatContainers(containers);
        this.CHANGE_NAME();*/


        Intent intent = new Intent(this.recycleInMenuActivity, MapsActivity.class);

        //intent.putExtra(EXTRA_CLOTHES_CONTAINERS_JSON, containersRequested);
        this.filterContainersBySearchRange();

        intent.putExtra(EXTRA_CONTAINERS_REQUESTED, this.containersRequested);
        intent.putExtra(EXTRA_SEARCH_LOCATION, this.searchLocation);
        this.recycleInMenuActivity.startActivity(intent);
    }

    public void filterContainersBySearchRange() {
        Location gpsLocation = createLocationFromLatLng(this.recycleInMenuActivity.getLocationLatitude(),
                this.recycleInMenuActivity.getLocationLongitude());
        ArrayList<Container> containersArray = this.containersRequested.getContainerList();

        Containers containersInRange = new Containers();

        int totalOut = 0;
        int totalIn = 0;
        for(int i = 0; i < containersArray.size(); i++){
            Container container = containersArray.get(i);
            Location containerLocation = createLocationFromLatLng(container.getLatitude(),
                    container.getLongitude());
            if(containerLocation.distanceTo(gpsLocation) <= this.locationSearchRatio) {
                containersInRange.getContainerList().add(container);
                totalIn++;
                Log.d("[CONTAINER INRANGE]",
                        "[STAYED] Distance to location = " + containerLocation.distanceTo(gpsLocation) + " <= " + this.locationSearchRatio);
            }
            else {
                totalOut++;
                Log.d("[CONTAINER OUTOFRANGE]",
                        "[REMOVED] Distance to location = " + containerLocation.distanceTo(gpsLocation) + " > " + this.locationSearchRatio);
            }
        }
        //total = total + total / 2;
        Log.d("[SUMMARY]",
                "[IN] " + totalIn + " - [OUT] " + totalOut);


        this.containersRequested.setContainerList(containersInRange.getContainerList());
        String s = "";
    }

    public Location createLocationFromLatLng(Double latitude, Double longitude) {
        Location location = new Location("GPS Location");
        location.setLatitude(latitude);
        location.setLongitude(longitude);

        return location;
    }

    protected void CHANGE_NAME() {
        Intent intent = new Intent(this.recycleInMenuActivity, MapsActivity.class);

        intent.putExtra(EXTRA_CLOTHES_CONTAINERS_JSON, containersRequested);
        intent.putExtra(EXTRA_SEARCH_LOCATION, this.searchLocation);
        this.recycleInMenuActivity.startActivity(intent);

       // return this.containersRequested;
    }

    //
    protected Containers getClothesContainers () {

        String containersClothesData = "";

        Containers containersClothesDataContainers = new Containers();
        Containers clothesContainers = new Containers();
        if (clothesSelected) {
            this.execute(this.getOpenDataClothesURL());
            containersClothesData = this.processJsonFromApi(containersClothesData);
            clothesContainers = this.GetContainersFromJsonString(containersClothesData);
        }

        return clothesContainers;





        /*// originally copied from onPostExecute function - result_value was result in the other method
        String result_value = this.openDataClothesURL;
        //Do something with the JSON string
        result = this.processJsonFromApi(result);
        Containers containers = this.GetContainersFromJsonString(result);
        super.onPostExecute(result);
        Intent intent = new Intent(this.recycleInMenuActivity, MapsActivity.class);

        intent.putExtra(EXTRA_CLOTHES_CONTAINERS_JSON, containers);
        intent.putExtra(EXTRA_SEARCH_LOCATION, this.searchLocation);
        this.recycleInMenuActivity.startActivity(intent);*/
    }

    protected String testHashSplit(String hash_data) {
        String[] locations = hash_data.split("titulo");
        String result = "";

        for(int i = 0; i < locations.length; i++) {
            result = result + locations[i] + "\n";
        }

        return result;
    }

    public String processJsonFromApi(String stringJson) {
        String stringJson_value = stringJson;
        int index = stringJson.indexOf(":");
        String resultJson = stringJson.substring(index + 1, stringJson.length() - 1);
        resultJson = this.prepareForGson(resultJson);

        stringJson_value = resultJson;
        return resultJson;
    }

    public String prepareForGson(String stringJson) {
        if (stringJson.contains("contenedorropa")) {
            return stringJson.replaceFirst("contenedorropa", "contenedor");
        }

        if (stringJson.contains("contenedorpilas")) {
            return stringJson.replaceFirst("contenedorpilas", "contenedor");
        }

        return stringJson.replaceFirst("contenedoraceite", "contenedor");
    }

    public Containers GetContainersFromJsonString(String stringJson) {
        Gson gson = new Gson();
        Containers containers = gson.fromJson(stringJson, Containers.class);

        return containers;
    }

    /*protected List<LatLng> processHashData(String hash_data) {
        List<LatLng> latLngs = new ArrayList<LatLng>();
        String[] locations = hash_data.split("titulo");
        // ArrayList<LatLng> arrayListLtnLng = new ArrayList<LatLng>(Arrays.asList(locations));

        for(int i = 0; i < locations.length; i++) {
            latLngs = latLngs.add() locations[i] + "\n";
        }

        return latLngs;
    }*/

    /*public List<LatLng> appendLatLng(String hash_data) {
        ArrayList


        double latDouble = Double.parseDouble(lat);
        double lngDouble = Double.parseDouble(lng);
        LatLng latLngToAppend = new LatLng(latDouble, lngDouble);

        List<LatLng> temp = new ArrayList<LatLng>(Arrays.asList(latLngs));
        temp.add(latLngToAppend);
        return temp;

    }*/

    public String getOpenDataClothesURL() {
        return openDataClothesURL;
    }

    public void setOpenDataClothesURL(String openDataClothesURL) {
        this.openDataClothesURL = openDataClothesURL;
    }

    public String getOpenDataBatteriesURL() {
        return openDataBatteriesURL;
    }

    public void setOpenDataBatteriesURL(String openDataBatteriesURL) {
        this.openDataBatteriesURL = openDataBatteriesURL;
    }

    public String getOpenDataOilURL() {
        return openDataOilURL;
    }

    public void setOpenDataOilURL(String openDataOilURL) {
        this.openDataOilURL = openDataOilURL;
    }

    public Containers getContainersRequested() {
        return containersRequested;
    }

    public void setContainersRequested(Containers containersRequested) {
        this.containersRequested = containersRequested;
    }

    public static String getExtraContainersRequested() {
        return EXTRA_CONTAINERS_REQUESTED;
    }

    public static String getExtraSearchLocation() {
        return EXTRA_SEARCH_LOCATION;
    }
}
