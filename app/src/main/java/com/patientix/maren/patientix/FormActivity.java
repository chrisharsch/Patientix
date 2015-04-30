package com.patientix.maren.patientix;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;


public class FormActivity extends Activity {

    private String[] fragebogen = {
            "Hier könnte die erste Seite stehen",
            "Hier steht die zweite Seite",
            "Hier steht noch eine Seite"
    };

    private int counter = 1;

    private Button buttonContinue;
    private Button buttonBack;
    private TextView questionText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Titlebar removed
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_form);

        buttonContinue = (Button) findViewById(R.id.buttonContinue);
        buttonBack = (Button) findViewById(R.id.buttonBack);
        questionText = (TextView) findViewById(R.id.questionText);

        questionText.setText(fragebogen[0]);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_form, menu);
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


    public void onClickNextButton(View v) {
        buttonContinue.setBackgroundResource(0);
        try {
            wait(10);
        } catch (Exception e) {

        }
        if (counter < fragebogen.length) {
            questionText.setText(fragebogen[counter]);
            counter++;

        } else {
            questionText.setText("Ende anzeigen: Bitte Tablet am Empfang....");
        }
    }

    public void onClickBackButton(View v) {
        buttonBack.setBackgroundResource(0);
        try {
            wait(10);
        } catch (Exception e) {

        }
        if (counter > 0) {
            counter--;
            questionText.setText(fragebogen[counter]);
        } else {

        }
    }

    public void onClickZoomButtonMore(View v) {
       /* float x = questionText.getScaleX();
        float y = questionText.getScaleY();
        questionText.setScaleX(x + 1);
        questionText.setScaleY(y + 1);*/
        float size = questionText.getTextSize();
        questionText.setTextSize(size+(float)0.0001);

    }
    public void onClickZoomButtonLow(View v) {
       /* float x = questionText.getScaleX();
        float y = questionText.getScaleY();
        questionText.setScaleX(x + 1);
        questionText.setScaleY(y + 1);*/
        float size = questionText.getTextSize();
        questionText.setTextSize(size-(float)0.0001);

    }

    // remove options for back button
    @Override
    public void onBackPressed() {

    }


}
