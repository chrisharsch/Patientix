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
import de.teambluebaer.patientix.helper.Flasher;
import de.teambluebaer.patientix.helper.LayoutCreater;

/**
 * This has have the overview about all questions.
 */

public class OverviewActivity extends Activity {

    //private ArrayList<HashMap<String, String>> list;
    private Button buttonReady;

    private LinearLayout list;
    private LayoutCreater layoutCreater;

    /**
     * In this method is defined what happens on create of the Activity:
     * Set Layout, remove titlebar
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Titlebar removed
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);

        list = (LinearLayout) getLayoutInflater().inflate(R.layout.activity_overview, null).findViewById(R.id.list);
        setContentView(R.layout.activity_overview);

        layoutCreater = new LayoutCreater();
        layoutCreater.CreatListLayout(this,list);



        buttonReady = (Button) findViewById(R.id.buttonReady);



    }

    /**
     * When the start button is clicked, then opens the SignatureActivity
     * @param v
     */
    public void onClickReadyButton(View v){
        Flasher.flash(buttonReady, "1x3");
        Intent intent = new Intent(OverviewActivity.this, SignatureActivity.class);
        startActivity(intent);
    }

    /*
    private void populateList() {

        list = new ArrayList<HashMap<String, String>>();


        HashMap<String, String> temp = new HashMap();
        temp.put(FIRST_COLUMN, "colored Notebook");
        temp.put(SECOND_COLUMN, "By Navneet");

        list.add(temp);
        list.add(temp);
        list.add(temp);
        list.add(temp);
        list.add(temp);
        list.add(temp);
        list.add(temp);
        list.add(temp);
        list.add(temp);
        list.add(temp);
        list.add(temp);
        list.add(temp);
        list.add(temp);
        list.add(temp);
        list.add(temp);
        list.add(temp);
        list.add(temp);
        list.add(temp);
        list.add(temp);
        list.add(temp);
        list.add(temp);
        list.add(temp);
        list.add(temp);
        list.add(temp);
        list.add(temp);
        list.add(temp);
        list.add(temp);
        list.add(temp);
        list.add(temp);
        list.add(temp);
        list.add(temp);
        list.add(temp);
        list.add(temp);
        list.add(temp);
        list.add(temp);
        list.add(temp);
        list.add(temp);
        list.add(temp);

    }*/
}
