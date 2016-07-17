package diegomezquita.treelife.Interfaces;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.widget.EditText;
import android.widget.FrameLayout;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import diegomezquita.treelife.R;

public class MainActivity extends AppCompatActivity {
    public final static String EXTRA_MESSAGE = "com.diegomezquita.treelife.MESSAGE";
    // public final static String EXTRA_CLOTHES_JSON = "com.diegomezquita.treelife.CLOTHES_JSON";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "prrrr, for you! :p", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /* Called when the user clicks the 'Send' button */
    /* ByDie: http://developer.android.com/intl/es/training/basics/firstapp/starting-activity.html  */
    public void sendMessage(View view) {
        Intent intent = new Intent(this, MapsActivity.class);
        /*EditText editText = (EditText) findViewById(R.id.edit_message);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);*/

        /*String urlClothes = "http://opendata.gijon.es/descargar.php?id=7&tipo=JSON";
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.mainActivityLinearLayout);
        EditText editText = (EditText) this.findViewById(R.id.edit_message);*/
        // Commented because the constructor doesn't exist anymore -
        // new DataGetter(this, linearLayout, editText).execute(urlClothes);
    }

    /*
    * Called when the user clicks on the 'Sign In' button
    * It's going the display the Sign In Activity
    * */
    public void goToSignIn(View view) {
        Intent intent = new Intent(this, SignInActivity.class);
        startActivity(intent);
    }

    /* Called when the user clicks the 'Clothes container' button */
    /* ByDie: trying to get the JSON open data info by URL (executeHttpGet function) */
    /* https://erjaimer.wordpress.com/2013/01/26/mandar-una-peticion-get-en-android/ */
    public void getClothes(View view) {

        // Call the get method that returns the clothes containers info JSON format
        String urlClothes = "http://opendata.gijon.es/descargar.php?id=7&tipo=JSON";
        // Other URLs:
        // Batteries container: http://opendata.gijon.es/descargar.php?id=68&tipo=JSON
        // Oil container: http://opendata.gijon.es/descargar.php?id=6&tipo=JSON

        FrameLayout fameLayout = (FrameLayout) findViewById(R.id.mainActivityFrameLayout);
        EditText editText = (EditText) this.findViewById(R.id.edit_message);
        // Commented because the constructor doesn't exist anymore -
        // new DataGetter(this, linearLayout, editText).execute(urlClothes);

    }

    public void goToRecycleIn(View view) {
        Intent intent = new Intent(this, RecycleInMenuActivity.class);
        startActivity(intent);
    }


    /* Connection to the network and getting the JSON from URL */
    /* ByDie: get the JSON open data info by URL */
    /* HttpClient: http://stackoverflow.com/questions/32153318/httpclient-wont-import-in-android-studio */
    public String executeHttpGet(String urlString) throws Exception
    {
        URL clothes = new URL(urlString);
        BufferedReader in = new BufferedReader(
                new InputStreamReader(
                        clothes.openStream()));

        String inputLine;
        String res = "";

        while ((inputLine = in.readLine()) != null) {
           // System.out.println(inputLine);
            res += in.readLine();
        }

        in.close();
        return res;
    }

}