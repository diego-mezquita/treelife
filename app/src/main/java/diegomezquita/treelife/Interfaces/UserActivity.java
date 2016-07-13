package diegomezquita.treelife.Interfaces;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.IOException;

import diegomezquita.treelife.DatabaseAccess.DBHelper;
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

        DBHelper db = DBHelper.getInstance(getApplicationContext());

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

    public void goToRecycleIn(View view) {
        Intent intent = new Intent(this, RecycleInMenuActivity.class);
        startActivity(intent);
    }


}