package diegomezquita.treelife;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class RecycleInMenuCreateContainerActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle_in_menu_create_container);
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

}
