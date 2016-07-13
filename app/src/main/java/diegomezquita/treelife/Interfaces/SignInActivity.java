package diegomezquita.treelife.Interfaces;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import diegomezquita.treelife.R;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class SignInActivity extends Activity {
    // TODO control all validations - take a look at the notebook

    public final static String EXTRA_EMAIL = "com.diegomezquita.treelife.USER_EMAIL";
    public final static String EXTRA_PASSWORD = "com.diegomezquita.treelife.USER_PASSWORD";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sign_in);

        Intent intent = getIntent();
        // Do it if necessary
        //RelativeLayout layout = (RelativeLayout) findViewById(R.id.sign_in_process);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);


    }

    public void signInStart (View view) {
        // User email
        EditText editEmail = (EditText) findViewById(R.id.EditTextEmail);
        String userEmail = editEmail.getText().toString();

        // User password
        EditText editPassword = (EditText) findViewById(R.id.EditTextPassword);
        String userPassword = editPassword.getText().toString();

        // User repeated password
        EditText editRepeatedPassword = (EditText) findViewById(R.id.EditTextPasswordRepeat);
        String userRepeatedPassword = editRepeatedPassword.getText().toString();

        boolean passwordCheck = this.comparePasswords(userPassword, userRepeatedPassword);

        if (passwordCheck) {
            Intent intent = new Intent(this, SignInCompletionActivity.class);

            intent.putExtra(EXTRA_EMAIL, userEmail);
            intent.putExtra(EXTRA_PASSWORD, userPassword);
            startActivity(intent);

        } else {
            String title = "Oooops";
            String message = "These passwords don't match.\nTry again!";
            String button = "Lets go!";
            this.showAlertWithData(title, message, button);

        }

    }

    private void showAlert(String title, String message, String button) {
        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setButton(button, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.hide();
            }
        });
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

    private boolean comparePasswords(String password, String repeatedPassword) {
        return password.equals(repeatedPassword);
    }
}
