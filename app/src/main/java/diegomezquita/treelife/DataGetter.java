package diegomezquita.treelife;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.support.v4.app.ActivityCompat.startActivity;

/**
 * Created by diegomezquita on 19/03/16.
 *
 * Created to get data from an URL
 */
public class DataGetter extends AsyncTask<String, String, String> {
    public final static String EXTRA_CLOTHES_JSON = "com.diegomezquita.treelife.CLOTHES_JSON";
    protected final static String EXTRA_CLOTHES_CONTAINERS_JSON = "com.diegomezquita.treelife.CLOTHES_CONTAINERS_JSON";
    protected final static String EXTRA_SEARCH_LOCATION = "com.diegomezquita.treelife.RECYCLE_IN_MENU_SEARCH_LOCATION";
    // HttpURLConnection urlConnection;

    final RecycleInMenuActivity recycleInMenuActivity;
    final String searchLocation;
    /*final LinearLayout recycleInMenuActivityLinearLayout;
    final EditText recycleInMenuActivityEditText;

    public DataGetter(RecycleInMenuActivity activity, LinearLayout activityLinearLayout, EditText editText) {
        this.recycleInMenuActivity = activity;
        this.recycleInMenuActivityLinearLayout = activityLinearLayout;
        this.recycleInMenuActivityEditText = editText;
    }
    */

    public DataGetter(RecycleInMenuActivity activity, String location) {
        this.recycleInMenuActivity = activity;
        this.searchLocation = location;
    }

    @Override
    protected String doInBackground(String... params) {
        //------------------- INI ------------------------
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        String json_url = params[0];

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
            String buffer_value = buffer.toString();

            return buffer.toString();


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

        return null;

        //------------------- FIN ------------------------
    }

    protected void onPostExecute(String result) {
        String result_value = result;
        //Do something with the JSON string
        result = this.processJsonFromApi(result);
        Containers containers = this.GetContainersFromJsonString(result);
        super.onPostExecute(result);
        Intent intent = new Intent(this.recycleInMenuActivity, MapsActivity.class);

        intent.putExtra(EXTRA_CLOTHES_CONTAINERS_JSON, containers);
        intent.putExtra(EXTRA_SEARCH_LOCATION, this.searchLocation);
        this.recycleInMenuActivity.startActivity(intent);
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

        stringJson_value = resultJson;
        return resultJson;
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
}