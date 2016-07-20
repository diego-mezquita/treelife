package diegomezquita.treelife.Interfaces;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import diegomezquita.treelife.DataGetters.DataGetter;
import diegomezquita.treelife.DatabaseAccess.DBHelper;
import diegomezquita.treelife.Models.Container;
import diegomezquita.treelife.Models.RecycleInAction;
import diegomezquita.treelife.Models.User;
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

        // TODO remove integer - debugger purpose
        Integer i = 0;

        Intent intent = getIntent();
        //this.container = intent.getParcelableExtra(DataGetter.getExtraContainersRequested());
        //this.container = intent.getParcelableExtra(MapsActivity.getExtraContainer());
        //this.container = intent.getExtra(DataGetter.getExtraContainersRequested());

        String jsonString = this.getExtraJsonString(intent);

        // Get info from String JSON to Container
        this.setContainer(this.getContainersFromJsonString(jsonString));

        // TODO start
        // test to see if the container received from MapsActivity is the correct one
        TextView tempTextViewPlace, tempTextViewTitle;

        tempTextViewPlace = (TextView) findViewById(R.id.activity_container__header__place);
        tempTextViewPlace.setText(this.getContainer().getPlace());

        tempTextViewTitle = (TextView) findViewById(R.id.activity_container__header__address);
        tempTextViewTitle.setText(this.getContainer().getTitle());

        this.setImageByType(this.getContainer().getType());

        /*String type = this.container.getType();
        String uriStringToImg = "android.resource://diegomezquita.treelife/marker_icon_" + type;
        avatarImageView.setImageURI(Uri.parse(uriStringToImg));*/

/*
        LinearLayout activityLayout = (LinearLayout) findViewById(R.id.activity_container__activity);

        activityLayout.addView(tempTextViewPlace);
        activityLayout.addView(tempTextViewTitle);
        int x = 0;*/
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

    public void recycleIn(View view) {
        new RecycleInAction(this.container, getApplicationContext());

        // Show dialog window: Recycle In completed.
        String alertTitle = "¡Recycle-In hecho!";
        //String alertMessage =  "El mundo es un lugar más limpio ahora.";
        //String alertButton = "Aceptar";
        //this.showAlertWithData(aletTitle, alertMessage, alertButton);
        Toast.makeText(getApplicationContext(), alertTitle, Toast.LENGTH_SHORT).show();

        // Navigate to user profile activity
        Intent intent = new Intent(this, UserActivity.class);
        startActivity(intent);
    }

    public void setImageByType(String type) {
        ImageView avatarImageView = (ImageView) findViewById(R.id.activity_container__header__type_icon);

        switch(type) {
            case "clothes":
                avatarImageView.setImageDrawable(getResources().getDrawable(R.drawable.icon_clothes));
                break;
            case "batteries":
                avatarImageView.setImageDrawable(getResources().getDrawable(R.drawable.icon_battery_red));
                break;
            case "oil":
                avatarImageView.setImageDrawable(getResources().getDrawable(R.drawable.icon_oil_curve_black));
                break;
            case "paper":
                avatarImageView.setImageDrawable(getResources().getDrawable(R.drawable.icon_container_blue));
                break;
            case "plastic":
                avatarImageView.setImageDrawable(getResources().getDrawable(R.drawable.icon_container_yellow));
                break;
            case "glass":
                avatarImageView.setImageDrawable(getResources().getDrawable(R.drawable.icon_container_green));
                break;
        }
    }

    private void showAlertWithData(String title, String message, String button) {
        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setButton(button, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.hide();
            }
        });

        alertDialog.show();
    }

}
