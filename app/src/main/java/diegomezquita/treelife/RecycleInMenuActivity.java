package diegomezquita.treelife;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;

public class RecycleInMenuActivity extends Activity {

    public final static String EXTRA_LOCATION = "com.diegomezquita.treelife.RECYCLE_IN_MENU_SEARCH_LOCATION";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle_in_menu);
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
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.mainActivityLinearLayout);

        // Getting info from the search form
        // Location specified
        EditText editText = (EditText) this.findViewById(R.id.edit_text_recycle_in_menu_where_location_feedback);
        String location = editText.getText().toString();
        // Container types selected
        boolean checkBoxClothes = ((CheckBox) this.findViewById(R.id.feedback_check_box_recycle_in_menu__what__clothes)).isChecked();
        boolean checkBoxOil = ((CheckBox) this.findViewById(R.id.feedback_check_box_recycle_in_menu__what__oil)).isChecked();
        boolean checkBoxBatteries = ((CheckBox) this.findViewById(R.id.feedback_check_box_recycle_in_menu__what__batteries)).isChecked();
        boolean checkBoxGlass = ((CheckBox) this.findViewById(R.id.feedback_check_box_recycle_in_menu__what__glass)).isChecked();
        boolean checkBoxPaper = ((CheckBox) this.findViewById(R.id.feedback_check_box_recycle_in_menu__what__paper)).isChecked();
        boolean checkBoxPlastic = ((CheckBox) this.findViewById(R.id.feedback_check_box_recycle_in_menu__what__plastic)).isChecked();



        new DataGetter(this, location).execute(urlClothes);
    }


}
