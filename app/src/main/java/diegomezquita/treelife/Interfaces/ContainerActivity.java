package diegomezquita.treelife.Interfaces;

import android.app.Activity;
import android.os.Bundle;

import diegomezquita.treelife.DatabaseAccess.DBHelper;
import diegomezquita.treelife.R;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class ContainerActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_container);

        DBHelper db = DBHelper.getInstance(getApplicationContext());

    }




}
