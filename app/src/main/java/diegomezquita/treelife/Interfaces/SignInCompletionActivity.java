package diegomezquita.treelife.Interfaces;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.FileNotFoundException;
import java.io.IOException;

import diegomezquita.treelife.R;
import diegomezquita.treelife.Models.User;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class SignInCompletionActivity extends Activity {
    // TODO control all validations - take a look at the notebook
    public final static String EXTRA_USER = "com.diegomezquita.treelife.USER_USER";

    // Attributes from previous step
    private String userEmail;
    private String userPassword;
    private Uri userAvatarPath;

    // Attributes needed to get the avatar picture
    private static Bitmap Image = null;
    private static Bitmap rotateImage = null;
    private ImageView imageView;
    private static final int GALLERY = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sign_in_completion);

        Intent intent = getIntent();
        this.userEmail = intent.getStringExtra(SignInActivity.EXTRA_EMAIL);
        this.userPassword = intent.getStringExtra(SignInActivity.EXTRA_PASSWORD);
        this.userAvatarPath = Uri.parse("android.resource://diegomezquita.treelife/drawable/user_default_avatar");

        this.imageView = (ImageView) findViewById(R.id.ImageViewAvatar);
        this.imageView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //imageView.setImageBitmap(null);
                if (Image != null)
                    Image.recycle();
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), GALLERY);
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == GALLERY && resultCode != 0) {
            this.userAvatarPath = data.getData();
            try {
                Image = MediaStore.Images.Media.getBitmap(this.getContentResolver(), this.userAvatarPath);
                if (getOrientation(getApplicationContext(), this.userAvatarPath) != 0) {
                    Matrix matrix = new Matrix();
                    matrix.postRotate(getOrientation(getApplicationContext(), this.userAvatarPath));
                    if (rotateImage != null)
                        rotateImage.recycle();
                    rotateImage = Bitmap.createBitmap(Image, 0, 0, Image.getWidth(), Image.getHeight(), matrix, true);
                    this.imageView.setImageBitmap(rotateImage);
                } else {
                    this.imageView.setImageBitmap(Image);
                    String s = "asass";
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static int getOrientation(Context context, Uri photoUri) {
        Cursor cursor = context.getContentResolver().query(photoUri,
                new String[] { MediaStore.Images.ImageColumns.ORIENTATION },null, null, null);

        if (cursor.getCount() != 1) {
            return -1;
        }
        cursor.moveToFirst();
        return cursor.getInt(0);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    public void signInCompletionStart(View view) {
        // User name
        EditText editName = (EditText) findViewById(R.id.EditTextName);
        String userName = editName.getText().toString();

        // Create the user && add it to the database
        User.getInstance(userName, this.userEmail, this.userPassword,
                this.userAvatarPath.toString(), getApplicationContext());

        // this.showAlertWithData(user.getUserName(), user.getUserEmail(), user.getUserPassword());

        Intent intent = new Intent(this, UserActivity.class);
        startActivity(intent);
    }

    private void showAlertWithData(String title, String message, String button) {
        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setButton(button, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.hide();
            }
        });

        alertDialog.show();
    }
}
