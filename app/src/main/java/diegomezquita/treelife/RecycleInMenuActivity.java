package diegomezquita.treelife;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class RecycleInMenuActivity extends Activity {

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


}
