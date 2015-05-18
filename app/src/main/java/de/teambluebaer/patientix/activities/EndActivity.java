package de.teambluebaer.patientix.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import de.teambluebaer.patientix.R;

/**
 * Endscreen with screen lock
 */

public class EndActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //removes the titlebar in fullscreenmode
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_end);
    }


    // screen look with logout from MTRA


    /**
     * This method defines what happens when you press on the hardkey back on the Tablet.
     * In this case the functionality of the button is disabled.
     */
    @Override
    public void onBackPressed() {

    }
}
