package de.teambluebaer.patientix.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import de.teambluebaer.patientix.R;
import de.teambluebaer.patientix.helper.Flasher;

/**
 * This class have the patient data...
 */

public class StartActivity extends Activity {

    private Button buttonStart;
    private Button buttonUpdate;

     /**
     * In this method is defined what happens on create of the Activity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //removes the titlebar in fullscreenmode
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_start);

        buttonStart = (Button) findViewById(R.id.startbtn);
        buttonUpdate = (Button) findViewById(R.id.updatebutton);

    }

    /**
     * When the start button is clicked, then opens the FormActivity
     * @param v
     */
    public void onClickStartButton(View v){
        Flasher.flash(buttonStart, "1x3");
        Intent intent = new Intent(StartActivity.this, FormActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     *
     * @param v
     */
    public void onClickUpdateButton(View v){
        Flasher.flash(buttonUpdate, "1x5");
        //TODO SEND "OLD" DATA TO SERVER


        Toast.makeText(StartActivity.this, "Das Formular ist bereit", Toast.LENGTH_SHORT).show();

    }

    /**
     * This method defines what happens when you press on the hardkey back on the Tablet.
     * In this case the functionality of the button is disabled.
     */
    @Override
    public void onBackPressed() {

    }

}
