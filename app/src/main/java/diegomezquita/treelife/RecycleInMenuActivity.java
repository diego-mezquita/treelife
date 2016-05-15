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

import java.util.ArrayList;

public class RecycleInMenuActivity extends Activity {

    public final static String EXTRA_LOCATION = "com.diegomezquita.treelife.RECYCLE_IN_MENU_SEARCH_LOCATION";
    protected final static String EXTRA_CLOTHES_CONTAINERS_JSON = "com.diegomezquita.treelife.CLOTHES_CONTAINERS_JSON";
    protected final static String EXTRA_SEARCH_LOCATION = "com.diegomezquita.treelife.RECYCLE_IN_MENU_SEARCH_LOCATION";

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
        String location = "calle aviles, gijon";//editText.getText().toString();
        // Container types selected ordered alphabetically
     //   boolean checkBoxBatteries = ((CheckBox) this.findViewById(R.id.feedback_check_box_recycle_in_menu__what__batteries)).isChecked();
     //   boolean checkBoxClothes = ((CheckBox) this.findViewById(R.id.feedback_check_box_recycle_in_menu__what__clothes)).isChecked();
     //   boolean checkBoxGlass = ((CheckBox) this.findViewById(R.id.feedback_check_box_recycle_in_menu__what__glass)).isChecked();

        // ArrayList that stores the materials to be or not requested to Gijón Open Data
        ArrayList<Boolean> materials = new ArrayList<>();
        materials.add(((CheckBox) this.findViewById(R.id.feedback_check_box_recycle_in_menu__what__batteries)).isChecked());
        materials.add(((CheckBox) this.findViewById(R.id.feedback_check_box_recycle_in_menu__what__clothes)).isChecked());
        materials.add(((CheckBox) this.findViewById(R.id.feedback_check_box_recycle_in_menu__what__glass)).isChecked());

        boolean checkBoxOil = ((CheckBox) this.findViewById(R.id.feedback_check_box_recycle_in_menu__what__oil)).isChecked();
        boolean checkBoxPaper = ((CheckBox) this.findViewById(R.id.feedback_check_box_recycle_in_menu__what__paper)).isChecked();
        boolean checkBoxPlastic = ((CheckBox) this.findViewById(R.id.feedback_check_box_recycle_in_menu__what__plastic)).isChecked();

        /*DataGetter dataGetter = new DataGetter(checkBoxClothes);

        if(checkBoxClothes) {
            Containers clothesContainers = dataGetter.getClothesContainers();
        }*/

        DataGetter data_getter = new DataGetter(this, location, materials);
        data_getter.execute(urlClothes);

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


}
