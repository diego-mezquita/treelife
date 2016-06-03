package diegomezquita.treelife;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;

public class RecycleInMenuCreateContainerActivity extends Activity {
    protected final static String EXTRA_NEW_CONTAINER = "com.diegomezquita.treelife.EXTRA_NEW_CONTAINER";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle_in_menu_create_container);

        ActivityCompat.requestPermissions(RecycleInMenuCreateContainerActivity.this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION},
                         124);
    }

    public void displaySearchByAddress(View view) {
        Intent intent = new Intent(this, RecycleInMenuActivity.class);
        startActivity(intent);
        this.overridePendingTransition(0, 0);
    }

    public void displayHabitualContainers(View view) {
        Intent intent = new Intent(this, RecycleInMenuHabitualContainersActivity.class);
        startActivity(intent);
        this.overridePendingTransition(0, 0);
    }

    public void displayCreateContainers(View view) {
        /*Intent intent = new Intent(this, RecycleInMenuCreateContainerActivity.class);
        startActivity(intent);*/
    }

    public void createNewContainer(View view) {
        Intent intent = new Intent(this, ContainerActivity.class);

        // Location specified
        EditText locationEditText = (EditText) this.findViewById(R.id.edit_text_container_creation_where_location_feedback);
        String locationString = locationEditText.getText().toString();

        // Title
        EditText titleEditText = (EditText) this.findViewById(R.id.edit_text_container_creation_where_location_feedback);
        String placeString = titleEditText.getText().toString();

        // Store in this.containerType the container type
        String typeString = this.containerTypeFeedback();

        // TODO: get lat lng
        // Get the lat and lng of the address specified

        //Creation of the container TODO constructor with lat-lng after complete TODO right before
        Container newContainer = new Container(locationString, placeString, typeString, getApplicationContext());

        // TODO: Saving the new container in the database - do it in Container class

        // Toast a message, kind of: "container created! thanks for collaborate"

        // Start activity ContainerActivity which displays the container view
        intent.putExtra(EXTRA_NEW_CONTAINER, newContainer);
        startActivity(intent);

    }

    // Method that get locationSearchRange from the view
    public String containerTypeFeedback() {
        //Double rangeSearch = ((Radio) this.findViewById(R.id.feedback_check_box_recycle_in_menu__what__glass)).isChecked();
        RadioGroup rangeSearchRadioGroup = ((RadioGroup) findViewById(R.id.feedback_radio_group_recycle_in_menu__type));
        int selectedRangeId = rangeSearchRadioGroup.getCheckedRadioButtonId();
        RadioButton containerTypeRadioButton = (RadioButton) findViewById(selectedRangeId);

        return containerTypeRadioButton.getText().toString().toLowerCase();
    }

}
