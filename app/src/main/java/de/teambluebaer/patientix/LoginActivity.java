package de.teambluebaer.patientix;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends Activity {

    private Button buttonLogin;
    private EditText editName = null;
    private EditText editPassword = null;
    private String url;
    private String thisExeption;
    private String httpData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Titlebar removed
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_login);

        // Keyboard open when touch editfield
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        buttonLogin = (Button) findViewById(R.id.buttonReady);
        editName = (EditText) findViewById(R.id.editName);
        editPassword = (EditText) findViewById(R.id.editPassword);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flashButtonsmall(buttonLogin);

                Intent intentFormActivity = new Intent(LoginActivity.this, StartActivity.class);
                startActivity(intentFormActivity);

            }
        });
    }


    /**
     * Change the view from the button to press
     *
     * @param myBtnToFlash
     */
    public void flashButtonsmall(final Button myBtnToFlash) {
        myBtnToFlash.setBackgroundResource(R.drawable.button1x3aktiv);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                myBtnToFlash.setBackgroundResource(R.drawable.button1x3normal);
            }
        }, 10);
    }
}