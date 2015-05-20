package de.teambluebaer.patientix.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;

import de.teambluebaer.patientix.R;
import de.teambluebaer.patientix.helper.Flasher;
import de.teambluebaer.patientix.helper.ListViewAdapter;


import static de.teambluebaer.patientix.helper.Constants.FIRST_COLUMN;
import static de.teambluebaer.patientix.helper.Constants.SECOND_COLUMN;

/**
 * This has have the overview about all questions.
 */

public class OverviewActivity extends Activity {

    private ArrayList<HashMap<String, String>> list;
    private Button buttonReady;

    /**
     * In this method is defined what happens on create of the Activity:
     * Set Layout, remove titlebar
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Titlebar removed
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_overview);

        buttonReady = (Button) findViewById(R.id.buttonReady);

        //
        ListView listView = (ListView)findViewById(R.id.listView1);
        populateList();
        ListViewAdapter adapter = new ListViewAdapter(this, list);
        listView.setAdapter(adapter);
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

    /**
     *
     */
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

    }
}
