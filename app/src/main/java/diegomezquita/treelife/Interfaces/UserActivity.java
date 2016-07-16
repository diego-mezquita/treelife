package diegomezquita.treelife.Interfaces;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import diegomezquita.treelife.DatabaseAccess.DBHelper;
import diegomezquita.treelife.Models.Container;
import diegomezquita.treelife.Models.RecycleInAction;
import diegomezquita.treelife.R;
import diegomezquita.treelife.Models.User;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */

// TODO disable android device back button to not let the user get back to the sign in process
public class UserActivity extends Activity {
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_user);

        this.user = User.getInstance();
        this.displayUserInfo();
        this.displayHabitualContainers();

        DBHelper db = DBHelper.getInstance(getApplicationContext());

        //List<RecycleInAction> actions_list = this.user.getActionsList();

        // TODO display actions_list in the view - next line to remove
        //actions_list = this.getUser().getActionsList();
        this.displayActivity();
    }

    public void displayUserInfo() {
        try {
            ImageView avatar = (ImageView) findViewById(R.id.activity_user__header__avatar);
            Bitmap avatarImage = MediaStore.Images.Media.getBitmap(this.getContentResolver(),
                    Uri.parse(this.user.getUserProfilePictureUrl()));
            avatar.setImageBitmap(avatarImage);
            //Bitmap bmImg = BitmapFactory.decodeFile(this.user.getUserProfilePictureUrl());
            //avatar.setImageBitmap(bmImg);

            TextView userName = (TextView) findViewById(R.id.activity_user__header__name);
            userName.setText(this.user.getUserName());

            TextView userEmail = (TextView) findViewById(R.id.activity_user__header__email);
            userEmail.setText(this.user.getUserEmail());

            ImageView recycleIcon = (ImageView) findViewById(R.id.activity_user__header__recycle_in_icon);
            Bitmap recycleInBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.recycle_in_icon);
            recycleIcon.setImageBitmap(recycleInBitmap);


            // TODO display recent actions from user


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void displayHabitualContainers() {
        // Get habitual containers list from DB via DBHelper

        // By using the example in here:
        // http://stackoverflow.com/questions/17160113/how-to-create-a-variable-number-of-textviews-in-android
        // create programmatically the first 5
    }

    public void displayActivity() {
        LinearLayout activityLayout = (LinearLayout) findViewById(R.id.activity_user__activity);

        //this.user.getActionsList();

        // TODO this data is added to the db to be able to develop the listings
        // "Calle Avilés, 17, Gijón", 43.5375589, -5.6715278, "Colegio Virgen Reina", "oil"
        Container container_oil = new Container();
        container_oil.setTitle("Calle Avilés, 17, Gijón");
        container_oil.setPlace("Colegio Virgen Reina");
        container_oil.setType("oil");

        Container container_batteries = new Container();
        container_batteries.setTitle("Avenida Portugal, 44, Gijón");
        container_batteries.setPlace("Estación de servicio");
        container_batteries.setType("battery");

        List<RecycleInAction> recycleInActionList = new ArrayList<>();

        recycleInActionList.add(new RecycleInAction(container_batteries, getApplicationContext()));
        recycleInActionList.add(new RecycleInAction(container_oil, getApplicationContext()));
        recycleInActionList.add(new RecycleInAction(container_batteries, getApplicationContext()));
        recycleInActionList.add(new RecycleInAction(container_batteries, getApplicationContext()));
        recycleInActionList.add(new RecycleInAction(container_oil, getApplicationContext()));
        recycleInActionList.add(new RecycleInAction(container_oil, getApplicationContext()));
        recycleInActionList.add(new RecycleInAction(container_batteries, getApplicationContext()));

        DBHelper db = DBHelper.getInstance();

        int maxActionsToDisplay = 5;

        TextView[] textViews = new TextView[maxActionsToDisplay];
        TextView tempTextView;

        for (int i = 0; i < maxActionsToDisplay; i++) {
            tempTextView = new TextView(this);

            tempTextView.setText(recycleInActionList.get(i).getContainer().getPlace());

            activityLayout.addView(tempTextView);

            textViews[i] = tempTextView;
        }

    }

    public void goToRecycleIn(View view) {
        Intent intent = new Intent(this, RecycleInMenuActivity.class);
        startActivity(intent);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }



}