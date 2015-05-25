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
import de.teambluebaer.patientix.xmlParser.MetaandForm;

/**
 * This has have the overview about all questions.
 */

public class OverviewActivity extends Activity {

    //private ArrayList<HashMap<String, String>> list;
    private Button buttonReady;
    private LinearLayout list;
    private LayoutCreater layoutCreater;
    private MetaandForm metaandForm;
    private Button buttonZoom;


    /**
     * In this method is defined what happens on create of the Activity:
     * Set Layout, remove titlebar
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Titlebar removed
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);
        list = (LinearLayout) findViewById(R.id.list);
        buttonZoom = (Button) findViewById(R.id.buttonZoom);

        metaandForm = Constants.globalMetaandForm;

        layoutCreater = new LayoutCreater();
        layoutCreater.CreatListLayout(this, list);

        buttonReady = (Button) findViewById(R.id.buttonReady);
        if(!Constants.zoomed){
            buttonZoom.setText("+");

        }else{
            buttonZoom.setText("-");
        }
    }

    /**
     * When the start button is clicked, then opens the SignatureActivity
     *
     * @param v
     */
    public void onClickReadyButton(View v) {
        Flasher.flash(buttonReady, "1x3");
        Intent intent = new Intent(OverviewActivity.this, SignatureActivity.class);
        startActivity(intent);
    }

    public void onClickZoomButton(View v) {

        if (!Constants.zoomed) {
            Flasher.flash(buttonZoom, "1x1");
            // TODO now a Layout (content) questionText.setTextSize(75);

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