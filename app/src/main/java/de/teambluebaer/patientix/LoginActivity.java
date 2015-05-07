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

import com.patientix.maren.patientix.R;


public class LoginActivity extends Activity {

    DatabaseHelper myDb;
    private Button buttonLogin;
    private EditText editName;
    private EditText editPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Titlebar removed
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_login);

        myDb = new DatabaseHelper(this);

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
                finish();
            }
        });
    }

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
