package de.teambluebaer.patientix.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

import de.teambluebaer.patientix.R;
import de.teambluebaer.patientix.helper.Constants;
import de.teambluebaer.patientix.helper.Flasher;
import de.teambluebaer.patientix.helper.LayoutCreater;

/**
 * This Activity displays the overview of all questions.
 * The Layout of this Activity partly created by the LayoutCreater
 */

public class OverviewActivity extends Activity {

    private Button buttonReady;
    private LinearLayout list;
    private LayoutCreater layoutCreater;
    private Button buttonZoom;

    /**
     * In this method is defined what happens on create of the Activity:
     * Set Layout, remove titlebar
     *
     * @param savedInstanceState Standard parameter
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);

        //Reference Variables
        list = (LinearLayout) findViewById(R.id.list);
        buttonZoom = (Button) findViewById(R.id.buttonZoom);
        buttonReady = (Button) findViewById(R.id.buttonContinue);
        layoutCreater = new LayoutCreater();
        layoutCreater.CreatListLayout(this, list);


        //Check if the page is zoomed or not
        if (!Constants.zoomed) {
            buttonZoom.setText("+");
        } else {
            buttonZoom.setText("-");
        }
    }

    /**
     * Method to get the next Activity in this case the SignatureActivity
     *
     * @param v Parameter to change something in view
     */
    public void onClickContinueButton(View v) {
        Flasher.flash(buttonReady, "1x3");
        Intent intent = new Intent(OverviewActivity.this, SignatureActivity.class);
        startActivity(intent);
    }

    /**
     * This method zooms the Content of the pages and sets the
     * ZoomButton Text matching to the zoomlevel
     *
     * @param v Parameter to change something in view
     */
    public void onClickZoomButton(View v) {
        if (!Constants.zoomed) {
            Flasher.flash(buttonZoom, "1x1");
            Constants.zoomed = true;
            layoutCreater.CreatListLayout(this, list);
            buttonZoom.setText("-");
        } else {
            Constants.zoomed = false;
            layoutCreater.CreatListLayout(this, list);
            buttonZoom.setText("+");

        }
    }

}