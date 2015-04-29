package com.patientix.maren.patientix;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends Activity {

    private Button startBtn;
    private Button buttonUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startBtn = (Button)findViewById(R.id.startbtn);
        buttonUpdate = (Button) findViewById(R.id.updatebtn);

        //Press buttons method
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentFormActivity = new Intent(MainActivity.this, FormActivity.class);
                startActivity(intentFormActivity);
            }
        });

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {


                Toast.makeText(MainActivity.this, "Das Formular ist bereit", Toast.LENGTH_SHORT).show();
            }
        });


    }

    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
    } */
}
