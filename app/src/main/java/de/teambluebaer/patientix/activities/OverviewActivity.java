package de.teambluebaer.patientix.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import de.teambluebaer.patientix.R;
import de.teambluebaer.patientix.helper.AnswerChecker;
import de.teambluebaer.patientix.helper.Constants;
import de.teambluebaer.patientix.helper.Flasher;
import de.teambluebaer.patientix.helper.LayoutCreater;
import de.teambluebaer.patientix.kioskMode.PrefUtils;

/**
 * This Activity displays the overview of all questions.
 * The Layout of this Activity partly created by the LayoutCreater
 */

public class OverviewActivity extends Activity {

    private Button buttonContinue;
    private LinearLayout listLinearLayout;
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
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setContentView(R.layout.activity_overview);
        Constants.CURRENTACTIVITY = this;
        PrefUtils.setKioskModeActive(true, this);
        Constants.TORESTART = true;
        if (!isFormula()) {
            Intent intent = new Intent(this, StartActivity.class);
            startActivity(intent);
            PrefUtils.setKioskModeActive(false, this);
            finish();
        }
        Constants.LISTOFACTIVITIES.add(this);
        //Reference Variables
        listLinearLayout = (LinearLayout) findViewById(R.id.list);
        buttonZoom = (Button) findViewById(R.id.buttonZoom);
        buttonContinue = (Button) findViewById(R.id.buttonContinue);
        try {
            layoutCreater = new LayoutCreater();
            layoutCreater.CreatListLayout(this, listLinearLayout);
        } catch (NullPointerException e) {
            Intent intent = new Intent(OverviewActivity.this, StartActivity.class);
            startActivity(intent);
            PrefUtils.setKioskModeActive(false, this);
            finish();
        }

        //Check if the page is ZOOMED or not
        if (!Constants.ZOOMED) {
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
        Flasher.flash(buttonContinue, "1x3");

        if(Constants.RESIGN | AnswerChecker.isEverythingAnswert()) {
            Intent intent = new Intent(OverviewActivity.this, SignatureActivity.class);
            startActivity(intent);
            PrefUtils.setKioskModeActive(false, this);
            finish();
        }else{
            Toast.makeText(this, "Bitte füllen sie alle Fragen aus", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * This method zooms the Content of the pages and sets the
     * ZoomButton Text matching to the zoomlevel
     *
     * @param v Parameter to change something in view
     */
    public void onClickZoomButton(View v) {
        if (!Constants.ZOOMED) {
            Flasher.flash(buttonZoom, "1x1");
            Constants.ZOOMED = true;
            layoutCreater.CreatListLayout(this, listLinearLayout);
            buttonZoom.setText("-");
        } else {
            Constants.ZOOMED = false;
            layoutCreater.CreatListLayout(this, listLinearLayout);
            buttonZoom.setText("+");

        }
    }

    /**
     * This method checks if there is a filled formula and
     * return true if there is one else it return false
     *
     * @return Boolean true or false
     */
    private boolean isFormula() {
        try {
            if (!Constants.GLOBALMETAANDFORM.toXMLString().isEmpty()) {
                return true;
            } else {
                return false;
            }
        } catch (NullPointerException e) {
            return false;
        }
    }

    /**
     * This method defines what happens when you press on the hardkey back on the Tablet.
     * In this case the functionality of the button is disabled.
     */
    @Override
    public void onBackPressed() {

    }


    /**
     * This method kills all system dialogs if they are shown
     *
     * @param hasFocus
     */
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (!hasFocus) {
            // Close every kind of system dialog
            Intent closeDialog = new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
            sendBroadcast(closeDialog);
        }
    }


}