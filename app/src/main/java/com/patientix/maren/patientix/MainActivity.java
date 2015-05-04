package com.patientix.maren.patientix;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends Activity {

    private Button buttonStart;
    private Button buttonUpdate;

    //Disable back button WARUM??
    @Override
    public void onBackPressed() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Titlebar removed
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Set the first View
        setContentView(R.layout.activity_main);

        buttonStart = (Button) findViewById(R.id.startbtn);
        buttonUpdate = (Button) findViewById(R.id.updatebtn);

        //Press buttons method -> Start-Button
        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flashButtonsmall(buttonStart);
                Intent intentFormActivity = new Intent(MainActivity.this, FormActivity.class);
                startActivity(intentFormActivity);
                finish();
            }
        });

        // Update-Button
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                flashButtonlarge(buttonUpdate);

                Toast.makeText(MainActivity.this, "Das Formular ist bereit", Toast.LENGTH_SHORT).show();

            }
        });
    }

    //
    public void flashButtonsmall(final Button myBtnToFlash) {
        myBtnToFlash.setBackgroundResource(R.drawable.button1x3aktiv);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                myBtnToFlash.setBackgroundResource(R.drawable.button1x3normal);
            }
        }, 10);

    }
    public void flashButtonlarge(final Button myBtnToFlash) {
        myBtnToFlash.setBackgroundResource(R.drawable.button1x5aktiv);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                myBtnToFlash.setBackgroundResource(R.drawable.button1x5normal);
            }
        }, 10);

    }
}
