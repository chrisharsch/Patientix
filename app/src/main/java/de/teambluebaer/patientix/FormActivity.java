package de.teambluebaer.patientix;

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
import android.widget.TextView;


import java.util.LinkedList;

import de.teambluebaer.patientix.ViewCreator.FormView;


public class FormActivity extends Activity {

    private static LinkedList<String> fragebogen = new LinkedList<String>();
    private int counter = -1;


    private Button buttonContinue;
    private Button buttonBack;
    private Button buttonZoomIn;
    private Button buttonZoomOut;
    private TextView questionText;
    private TextView numberOfPages;

    FormView formView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        formView = new FormView(this.questionText.getContext());
      //  setContentView(formView);
        //Titlebar removed
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_form);

        fragebogen.add("Hier könnte die erste Seite stehen");
        fragebogen.add("Hier steht die zweite Seite");
        fragebogen.add("Hier steht noch eine Seite");

        buttonContinue = (Button) findViewById(R.id.buttonContinue);
        buttonBack = (Button) findViewById(R.id.buttonReady);
        buttonZoomIn = (Button) findViewById(R.id.buttonZoomIn);
        buttonZoomOut = (Button) findViewById(R.id.buttonZoomOut);
        questionText = (TextView) findViewById(R.id.questionText);
        numberOfPages = (TextView) findViewById(R.id.pageOfNumbers);

        onClickNextButton(buttonContinue);

    }

    public static LinkedList<String> getListe(){
        return fragebogen;
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

    /**
     * Show next page when click next (Weiter)
     *
     * @param v
     */
    public void onClickNextButton(View v) {

        if (counter < fragebogen.size() - 1) {
            flashButton1x3(buttonContinue);
            counter++;
            questionText.setText(fragebogen.get(counter));
            numberOfPages.setText("Seite " + (fragebogen.indexOf(fragebogen.get(counter))+1));

        } else if (counter < fragebogen.size()) {
            counter++;


            numberOfPages.setText("Seite " + (fragebogen.size()+1));
            buttonContinue.setVisibility(View.INVISIBLE);
            buttonContinue.setClickable(false);
            questionText.setText("Ende anzeigen: Bitte Tablet am Empfang....");

        }

    }

    /**
     * Go back to the last page when click back (Zurück)
     *
     * @param v
     */
    public void onClickBackButton(View v) {

        if (counter > 0) {
            flashButton1x3(buttonBack);
            counter--;
            questionText.setText(fragebogen.get(counter));

            numberOfPages.setText("Seite " + (fragebogen.indexOf(fragebogen.get(counter))+1));
            buttonContinue.setVisibility(View.VISIBLE);
            buttonContinue.setClickable(true);
        }
    }
// show temprarily the overview activity

    public void onClickZoomButtonIn(View v) {
        flashButton1x1(buttonZoomIn);

        Intent intentFormActivity = new Intent(FormActivity.this, OverviewActivity.class);
        startActivity(intentFormActivity);
        finish();
        /*
        float size = questionText.getTextSize();
        size = size + 1;
        questionText.setTextSize(size);
*/
    }

    public void onClickZoomButtonOut(View v) {
        flashButton1x1(buttonZoomOut);
        float size = questionText.getTextSize();
        size = size - 1;
        questionText.setTextSize(size);

    }

    public void flashButton1x3(final Button myBtnToFlash) {
        myBtnToFlash.setBackgroundResource(R.drawable.button1x3aktiv);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                myBtnToFlash.setBackgroundResource(R.drawable.button1x3normal);
            }
        }, 25);

    }
    public void flashButton1x1(final Button myBtnToFlash) {
        myBtnToFlash.setBackgroundResource(R.drawable.button1x1aktiv);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                myBtnToFlash.setBackgroundResource(R.drawable.button1x1normal);
            }
        }, 25);

    }

    // remove options for back button
    @Override
    public void onBackPressed() {

    }


}
