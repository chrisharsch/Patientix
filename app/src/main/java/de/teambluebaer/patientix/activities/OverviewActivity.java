package de.teambluebaer.patientix.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
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
import de.teambluebaer.patientix.signatureHelper.SignatureActivity;

import static de.teambluebaer.patientix.helper.Constants.FIRST_COLUMN;
import static de.teambluebaer.patientix.helper.Constants.SECOND_COLUMN;

public class OverviewActivity extends Activity {

    private ArrayList<HashMap<String, String>> list;
    private Button buttonReady;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Titlebar removed
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_overview);

        ListView listView = (ListView)findViewById(R.id.listView1);
        buttonReady = (Button) findViewById(R.id.buttonReady);


        populateList();
        ListViewAdapter adapter=new ListViewAdapter(this, list);
        listView.setAdapter(adapter);
    }

    public void onClickReadyButton(View v){
        Flasher.flash(buttonReady, "1x3");
        Intent intentSignatureActivity = new Intent(OverviewActivity.this, SignatureActivity.class);
        startActivity(intentSignatureActivity);
    }

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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_overview, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
