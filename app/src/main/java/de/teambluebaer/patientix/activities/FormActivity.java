package de.teambluebaer.patientix.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.LinkedList;

import de.teambluebaer.patientix.R;
import de.teambluebaer.patientix.helper.Flasher;
import de.teambluebaer.patientix.helper.LayoutCreater;
import de.teambluebaer.patientix.xmlParser.JavaStrucBuilder;
import de.teambluebaer.patientix.xmlParser.MetaandForm;

/**
 *
 */

public class FormActivity extends Activity {

    private static LinkedList<String> fragebogen = new LinkedList<String>();

    private Button buttonContinue;
    private Button buttonBack;
    private Button buttonZoomIn;
    private Button buttonZoomOut;
    // !!!!!!!!!!!!!!!!!!!!!!!!!!!
    private Button buttonOk;
    // !!!!!!!!!!!!!!!!!!!!!!!!!!!
    private LinearLayout content;
    private TextView numberOfPages;

    private LayoutCreater layoutCreater;
    private MetaandForm metaandForm;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Titlebar removed
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_form);

        //View add_phone = getLayoutInflater().inflate(R.layout.phone_info, null);
        content = (LinearLayout) getLayoutInflater().inflate(R.layout.activity_form, null).findViewById(R.id.content);
        //content = layoutCreater.CreatPageLayout(this, metaandForm.getForm().getFirstPage());

        buttonContinue = (Button) findViewById(R.id.buttonContinue);
        buttonBack = (Button) findViewById(R.id.buttonBack);
        buttonZoomIn = (Button) findViewById(R.id.buttonZoomIn);
        buttonZoomOut = (Button) findViewById(R.id.buttonZoomOut);

        // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        buttonOk = (Button) findViewById(R.id.buttonOk);
        // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

        //Disable back button at first page
        buttonBack.setVisibility(View.INVISIBLE);
        buttonBack.setClickable(false);

        //Disable zoom out button and set text size to button size
        // TODO now a Layout (content) questionText.setTextSize(40);
        buttonZoomOut.setClickable(false);
        buttonZoomOut.setVisibility(View.INVISIBLE);

        content = (LinearLayout) findViewById(R.id.content);

        JavaStrucBuilder strucBuilder = new JavaStrucBuilder();
        metaandForm = strucBuilder.buildStruc();
        layoutCreater = new LayoutCreater();
        layoutCreater.CreatPageLayout(this,metaandForm.getForm().getFirstPage(),content);




        numberOfPages = (TextView) findViewById(R.id.pageOfNumbers);
        numberOfPages.setText(Integer.toString(metaandForm.getForm().getcurrendPageNumber()));

    }

    // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    // Button um von FormActivity zu Overview und dann zu SignatureActivity zu kommen!
    // Braucht man oder nicht???
    // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    public void onClickButton(View v){
        Flasher.flash(buttonOk, "1x3");

        //  new Helper().executeRequest("", );
        Intent intent = new Intent(FormActivity.this, OverviewActivity.class);
        startActivity(intent);
    }

    /**
     * Show next page when click next (Weiter)
     *
     * @param v
     */
    public void onClickNextButton(View v) {
        // hier fehlt der flasher?

        layoutCreater.CreatPageLayout(this, metaandForm.getForm().getNextPage(), content);
        numberOfPages.setText(metaandForm.getForm().getcurrendPageNumber());

    }

    /**
     * Go back to the last page when click back (Zurück)
     *
     * @param v
     */
    public void onClickBackButton(View v) {
        // fehlt da nicht auch der flasher??

        layoutCreater.CreatPageLayout(this, metaandForm.getForm().getPreviousPage(), content);
        numberOfPages.setText(metaandForm.getForm().getcurrendPageNumber());

    }

    /**
     * Set the text size higher and schmaller
     * @param v
     */
    public void onClickZoomButton(View v) {
        if(buttonZoomIn.isClickable()){
            Flasher.flash(buttonZoomIn, "1x1");
            // TODO now a Layout (content) questionText.setTextSize(75);
            buttonZoomIn.setClickable(false);
            buttonZoomOut.setClickable(true);
            buttonZoomOut.setVisibility(View.VISIBLE);
            buttonZoomIn.setVisibility(View.INVISIBLE);
        }else {
            Flasher.flash(buttonZoomOut,"1x1");
            // TODO now a Layout (content) questionText.setTextSize(40);
            buttonZoomIn.setClickable(true);
            buttonZoomOut.setClickable(false);
            buttonZoomIn.setVisibility(View.VISIBLE);
            buttonZoomOut.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * This method defines what happens when you press on the hardkey back on the Tablet.
     * In this case the funktionality of the button is disabled.
     */
    @Override
    public void onBackPressed() {

    }

    /**
     * checks if the counter is 0 to diasable the back button on the first screen
     */
    public void firstPageCheck(){
        if(metaandForm.getForm().getcurrendPageNumber() == 0) {
            buttonBack.setClickable(false);
            buttonBack.setVisibility(View.INVISIBLE);
        }else{
            buttonBack.setClickable(true);
            buttonBack.setVisibility(View.VISIBLE);
        }
    }

}
