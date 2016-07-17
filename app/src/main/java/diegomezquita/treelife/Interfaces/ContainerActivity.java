package diegomezquita.treelife.Interfaces;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import diegomezquita.treelife.DataGetters.DataGetter;
import diegomezquita.treelife.DatabaseAccess.DBHelper;
import diegomezquita.treelife.Models.Container;
import diegomezquita.treelife.R;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class ContainerActivity extends Activity {
      private Container container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_container);

        DBHelper db = DBHelper.getInstance(getApplicationContext());

       // List<RecycleInAction> actionsList = db.getActionsByContainer(this.getContainer().getId());

        // TODO remove integer - debugger purpouse
        Integer i = 0;

        Intent intent = getIntent();
        //this.container = intent.getParcelableExtra(DataGetter.getExtraContainersRequested());
        //this.container = intent.getParcelableExtra(MapsActivity.getExtraContainer());
        //this.container = intent.getExtra(DataGetter.getExtraContainersRequested());

        String jsonString = this.getExtraJsonString(intent);

        // Get info from String JSON to Container
        this.container = this.getContainersFromJsonString(jsonString);

        // TODO start
        // test to see if the container received from MapsActivity is the correct one
        TextView tempTextViewPlace, tempTextViewTitle;

        tempTextViewPlace = new TextView(this);
        tempTextViewPlace.setText(this.container.getPlace());

        tempTextViewTitle = new TextView(this);
        tempTextViewTitle.setText(this.container.getTitle());

        LinearLayout activityLayout = (LinearLayout) findViewById(R.id.activity_container__activity);

        activityLayout.addView(tempTextViewPlace);
        activityLayout.addView(tempTextViewTitle);
        int x = 0;
        // TODO end

    }

    public Container getContainer() {
        return container;
    }

    public void setContainer(Container container) {
        this.container = container;
    }

    public Container getContainersFromJsonString(String stringJson) {
        Gson gson = new Gson();
        Container container = gson.fromJson(stringJson, Container.class);

        return container;
    }

    public String getExtraJsonString(Intent intent) {
        String extraFrom = intent.getStringExtra(MapsActivity.getExtraFrom());

        if(extraFrom.equals("mapsActivity")) {

            return intent.getStringExtra(MapsActivity.getExtraJsonStringContainer());
        }

        return intent.getStringExtra(RecycleInMenuCreateContainerActivity.getExtraNewContainerJsonString());
    }

}
