package de.teambluebaer.patientix.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import de.teambluebaer.patientix.R;
import de.teambluebaer.patientix.helper.Flasher;

/**
 * This class handle the login data from the MTRA.
 */

public class LoginActivity extends Activity {

    private Button buttonLogin;
    private EditText editName = null;
    private EditText editPassword = null;
    private String url;
    private String thisExeption;
    private String httpData;

    /**
     * In this method is defined what happens on create of the Activity:
     * Set Layout, remove titlebar and open the keyboard
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Titlebar removed
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_login);

        buttonLogin = (Button) findViewById(R.id.buttonBack);
        editName = (EditText) findViewById(R.id.editName);
        editPassword = (EditText) findViewById(R.id.editPassword);

        // Keyboard open when touch editfield
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    /**
     * When the start button is clicked, then opens the StartActivity
     * @param v
     */
    public void onClickLoginButton(View v){
        Flasher.flash(buttonLogin, "1x3");
      //  new Helper().executeRequest("", );
        Intent intent = new Intent(LoginActivity.this, StartActivity.class);
        startActivity(intent);
    }


}