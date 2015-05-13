package de.teambluebaer.patientix.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import java.util.LinkedList;

import de.teambluebaer.patientix.R;
import de.teambluebaer.patientix.helper.Flasher;
import de.teambluebaer.patientix.helper.LayoutCreater;


public class FormActivity extends Activity {

    private static LinkedList<String> fragebogen = new LinkedList<String>();
    private int counter = -1;


    private Button buttonContinue;
    private Button buttonBack;
    private Button buttonZoomIn;
    private Button buttonZoomOut;
    private TextView questionText;
    private TextView numberOfPages;

    LayoutCreater formView;

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
        buttonBack = (Button) findViewById(R.id.buttonBack);
        buttonZoomIn = (Button) findViewById(R.id.buttonZoomIn);
        buttonZoomOut = (Button) findViewById(R.id.buttonZoomOut);
        questionText = (TextView) findViewById(R.id.questionText);
        numberOfPages = (TextView) findViewById(R.id.pageOfNumbers);

        onClickNextButton(buttonContinue);

        //Disable back button at first page
        buttonBack.setVisibility(View.INVISIBLE);
        buttonBack.setClickable(false);

        //Disable zoom out button and set text size to button size
        questionText.setTextSize(40);
        buttonZoomOut.setClickable(false);
        buttonZoomOut.setVisibility(View.INVISIBLE);

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
            Flasher.flash(buttonContinue, "1x3");

            counter++;
            questionText.setText(fragebogen.get(counter));
            numberOfPages.setText("Seite " + (fragebogen.indexOf(fragebogen.get(counter)) + 1));

        } else if (counter < fragebogen.size()) {
            counter++;


            numberOfPages.setText("Seite " + (fragebogen.size()+1));
            buttonContinue.setVisibility(View.INVISIBLE);
            buttonContinue.setClickable(false);
            questionText.setText("Ende anzeigen: Bitte Tablet am Empfang....");

        }
        firstPageCheck();
    }

    /**
     * Go back to the last page when click back (Zurück)
     *
     * @param v
     */
    public void onClickBackButton(View v) {


        if (counter > 0) {
            Flasher.flash(buttonBack, "1x3");
            counter--;
            questionText.setText(fragebogen.get(counter));

            numberOfPages.setText("Seite " + (fragebogen.indexOf(fragebogen.get(counter)) + 1));
            buttonContinue.setVisibility(View.VISIBLE);
            buttonContinue.setClickable(true);

        }
        firstPageCheck();
    }
// show temprarily the overview activity

    public void onClickZoomButtonIn(View v) {


      /*  Intent intentFormActivity = new Intent(FormActivity.this, OverviewActivity.class);
        startActivity(intentFormActivity);
        finish();*/
        /*
        float size = questionText.getTextSize();
        size = size + 1;
        questionText.setTextSize(size);
*/
    }

    public void onClickZoomButton(View v) {
        if(buttonZoomIn.isClickable()){
            Flasher.flash(buttonZoomIn, "1x1");
            questionText.setTextSize(75);
            buttonZoomIn.setClickable(false);
            buttonZoomOut.setClickable(true);
            buttonZoomOut.setVisibility(View.VISIBLE);
            buttonZoomIn.setVisibility(View.INVISIBLE);
        }else {
            Flasher.flash(buttonZoomOut,"1x1");
            questionText.setTextSize(40);
            buttonZoomIn.setClickable(true);
            buttonZoomOut.setClickable(false);
            buttonZoomIn.setVisibility(View.VISIBLE);
            buttonZoomOut.setVisibility(View.INVISIBLE);
        }

    }

    // remove options for back button
    @Override
    public void onBackPressed() {

    }

    /**
     * checks if the counter is 0 to diasable the back button on the first screen
     */
    public void firstPageCheck(){
        if(counter == 0) {
            buttonBack.setClickable(false);
            buttonBack.setVisibility(View.INVISIBLE);
        }else{
            buttonBack.setClickable(true);
            buttonBack.setVisibility(View.VISIBLE);
        }
    }

}
