package diegomezquita.treelife.Interfaces;

import android.app.Activity;
import android.os.Bundle;

import java.util.List;

import diegomezquita.treelife.DatabaseAccess.DBHelper;
import diegomezquita.treelife.Models.RecycleInAction;
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
    }

    public Container getContainer() {
        return container;
    }

    public void setContainer(Container container) {
        this.container = container;
    }


}
