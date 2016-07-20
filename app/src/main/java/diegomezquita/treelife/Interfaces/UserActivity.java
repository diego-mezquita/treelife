package diegomezquita.treelife.Interfaces;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.nearby.bootstrap.request.ContinueConnectRequest;

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

        List<RecycleInAction> actionsList = this.getUser().getActionsList();

        // TODO display actions_list in the view - next line to remove
        //actions_list = this.getUser().getActionsList();
        this.displayActivity(actionsList);
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

    public void displayActivity(List<RecycleInAction> actionsList) {
        LinearLayout activityLayout = (LinearLayout) findViewById(R.id.activity_user__activity);

        //this.user.getActionsList();

        // TODO this data is added to the db to be able to develop the listings
        // "Calle Avilés, 17, Gijón", 43.5375589, -5.6715278, "Colegio Virgen Reina", "oil"
        /*Container container_oil = new Container();
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
        recycleInActionList.add(new RecycleInAction(container_batteries, getApplicationContext()));*/


        //DBHelper db = DBHelper.getInstance();

        // List<RecycleInAction> recycleInActionList = db.getActionsByUser(this.getUser().getId());
        if(actionsList.size() != 0) {
            int maxActionsToDisplay = actionsList.size();

            TextView[] textViews = new TextView[maxActionsToDisplay];

            LinearLayout[] linearLayouts = new LinearLayout[maxActionsToDisplay];

            for (int i = 0; i < maxActionsToDisplay; i++) {
                Container container = actionsList.get(i).getContainer();
                String place = container.getPlace();
                String time = actionsList.get(i).getTime();
                // Uri uriToImage = container.getImagePathFromType();

                LinearLayout linearLayout = new LinearLayout(getApplicationContext());
                TextView placeTextView = new TextView(this);
                TextView timeTextView = new TextView(this);
                ImageView avatarImageView = this.setImageByType(container.getType());

                placeTextView.setText(place);
                timeTextView.setText(time);
                //avatarImageView.setImageURI(uriToImage);
                activityLayout.addView(linearLayout);

                linearLayout.addView(avatarImageView);
                LinearLayout textSubLinearLayout = new LinearLayout(getApplicationContext());
                textSubLinearLayout.setOrientation(LinearLayout.VERTICAL);
                textSubLinearLayout.addView(placeTextView);
                textSubLinearLayout.addView(timeTextView);

                linearLayout.addView(textSubLinearLayout);

                linearLayouts[i] = linearLayout;
            }
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

    public ImageView setImageByType(String type) {
        ImageView avatarImageView = new ImageView(getApplicationContext());
        Drawable typeImage = getResources().getDrawable(R.drawable.icon_container_green);

        switch(type) {
            case "clothes":
                typeImage = getResources().getDrawable(R.drawable.icon_clothes);
                break;
            case "batteries":
                typeImage = getResources().getDrawable(R.drawable.icon_battery_red);
                break;
            case "oil":
                typeImage = getResources().getDrawable(R.drawable.icon_oil_curve_black);
                break;
            case "paper":
                typeImage = getResources().getDrawable(R.drawable.icon_container_blue);
                break;
            case "plastic":
                typeImage = getResources().getDrawable(R.drawable.icon_container_yellow);
                break;
            case "glass":
                typeImage = getResources().getDrawable(R.drawable.icon_container_green);
                break;
        }

        // ***** START - DRAWABLE BLOCK *****
        typeImage = this.resizeIcon(typeImage, 100, 100);
        // ***** END - DRAWABLE BLOCK *****

        avatarImageView.setImageDrawable(typeImage);

        return avatarImageView;
    }

    public Drawable resizeIcon(Drawable typeImage, int width, int height){
        Bitmap bitmap = ((BitmapDrawable)typeImage).getBitmap();
        Bitmap bitmapResized = Bitmap.createScaledBitmap(bitmap, width, height, false);

        return new BitmapDrawable(getResources(), bitmapResized);
    }

}

