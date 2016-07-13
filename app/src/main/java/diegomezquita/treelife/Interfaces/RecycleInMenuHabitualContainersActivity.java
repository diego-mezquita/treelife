package diegomezquita.treelife.Interfaces;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import diegomezquita.treelife.R;

public class RecycleInMenuHabitualContainersActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle_in_menu_habitual_containers);
    }

    public void displaySearchByAddress(View view) {
        Intent intent = new Intent(this, RecycleInMenuActivity.class);
        startActivity(intent);
        this.overridePendingTransition(0, 0);
    }

    public void displayHabitualContainers(View view) {
        /*Intent intent = new Intent(this, RecycleInMenuHabitualContainersActivity.class);
        startActivity(intent);*/
    }

    public void displayCreateContainers(View view) {
        Intent intent = new Intent(this, RecycleInMenuCreateContainerActivity.class);
        startActivity(intent);
        this.overridePendingTransition(0, 0);
    }

}
