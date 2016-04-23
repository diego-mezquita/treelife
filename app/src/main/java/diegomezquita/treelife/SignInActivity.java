package diegomezquita.treelife;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class SignInActivity extends AppCompatActivity {

    public final static String EXTRA_NAME = "com.diegomezquita.treelife.USER_NAME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sign_in);

        Intent intent = getIntent();
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.sign_in_process);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);


    }

    public void signInStart (View view) {
        // User name
        EditText editName = (EditText) findViewById(R.id.EditTextName);
        String userName = editName.getText().toString();

        // User email
        EditText editEmail = (EditText) findViewById(R.id.EditTextEmail);
        String userEmail = editEmail.getText().toString();

        // User password
        EditText editPassword = (EditText) findViewById(R.id.EditTextPassword);
        String userPassword = editPassword.getText().toString();

        // User repeated password
        EditText editRepeatedPassword = (EditText) findViewById(R.id.EditTextPasswordRepeat);
        String userRepeatedPassword = editRepeatedPassword.getText().toString();

        showAlertWithData(userName, userEmail, userPassword, userRepeatedPassword);


//        intent.putExtra(EXTRA_NAME, userName);
//        startActivity(intent);
    }

    private void showAlertWithData(String userName, String email, String password, String repeatedPassword) {
        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Sign In User Data");
        String stringInfo = "Name: " + userName + "\nEmail: "+ email + "\nPassword: " + password + "\nRep Pass: " + repeatedPassword;
        alertDialog.setMessage(stringInfo);
        alertDialog.setButton("Exit", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.hide();
            }
        });

        alertDialog.show();
    }
}
