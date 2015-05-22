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
import de.teambluebaer.patientix.helper.Constants;
import de.teambluebaer.patientix.helper.Flasher;
import de.teambluebaer.patientix.helper.LayoutCreater;
import de.teambluebaer.patientix.xmlParser.MetaandForm;

/**
 *
 */

public class FormActivity extends Activity {

    private static LinkedList<String> fragebogen = new LinkedList<String>();

    private Button buttonContinue;
    private Button buttonBack;
    private Button buttonZoom;
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
        //content = (LinearLayout) getLayoutInflater().inflate(R.layout.activity_form, null).findViewById(R.id.content);
        //content = layoutCreater.CreatPageLayout(this, metaandForm.getForm().getFirstPage());

        buttonContinue = (Button) findViewById(R.id.buttonContinue);
        buttonBack = (Button) findViewById(R.id.buttonBack);
        buttonZoom = (Button) findViewById(R.id.buttonZoom);

        // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        buttonOk = (Button) findViewById(R.id.buttonOk);
        // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

        //Disable back button at first page
        buttonBack.setVisibility(View.INVISIBLE);
        buttonBack.setClickable(false);
        buttonOk.setClickable(false);
        buttonOk.setVisibility(View.INVISIBLE);

        //Disable zoom out button and set text size to button size
        // TODO now a Layout (content) questionText.setTextSize(40);

        content = (LinearLayout) findViewById(R.id.content);

        metaandForm = Constants.globalMetaandForm;

        layoutCreater = new LayoutCreater();
        layoutCreater.CreatPageLayout(this, metaandForm.getForm().getFirstPage(), content);


        numberOfPages = (TextView) findViewById(R.id.pageOfNumbers);
        numberOfPages.setText(metaandForm.getForm().getCurrentPageText());

    }

    // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    // Button um von FormActivity zu Overview und dann zu SignatureActivity zu kommen!
    // Braucht man oder nicht???
    // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    public void onClickButton(View v) {
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
        Flasher.flash(buttonContinue, "1x3");
        if (!lastPageCheck()) {
            layoutCreater.CreatPageLayout(this, metaandForm.getForm().getNextPage(), content);
            numberOfPages = (TextView) findViewById(R.id.pageOfNumbers);
            numberOfPages.setText(metaandForm.getForm().getCurrentPageText());
        }
        lastPageCheck();

    }

    /**
     * Go back to the last page when click back (Zurück)
     *
     * @param v
     */
    public void onClickBackButton(View v) {
        Flasher.flash(buttonBack, "1x3");
        if (!firstPageCheck()) {
            layoutCreater.CreatPageLayout(this, metaandForm.getForm().getPreviousPage(), content);
            numberOfPages = (TextView) findViewById(R.id.pageOfNumbers);
            numberOfPages.setText(metaandForm.getForm().getCurrentPageText());

        }
        firstPageCheck();

    }

    /**
     * Set the text size higher and schmaller
     *
     * @param v
     */
    public void onClickZoomButton(View v) {




        if (!Constants.zoomed) {
            Flasher.flash(buttonZoom, "1x1");
            // TODO now a Layout (content) questionText.setTextSize(75);

            Constants.zoomed = true;

            layoutCreater.CreatPageLayout(this, metaandForm.getForm().getcurrenPage(),content);


            buttonZoom.setText("-");

        } else {
            Constants.zoomed = false;
            layoutCreater.CreatPageLayout(this, metaandForm.getForm().getcurrenPage(), content);

            buttonZoom.setText("+");

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
    private boolean firstPageCheck() {
        if (metaandForm.getForm().getCurrentPageNumber() == 1) {
            buttonBack.setClickable(false);
            buttonBack.setVisibility(View.INVISIBLE);
            return true;
        }
        buttonContinue.setClickable(true);
        buttonContinue.setVisibility(View.VISIBLE);
        return false;
    }

    private boolean lastPageCheck() {
        if (metaandForm.getForm().getCurrentPageNumber() == metaandForm.getForm().getLastPage()) {
            buttonContinue.setClickable(false);
            buttonContinue.setVisibility(View.INVISIBLE);
            buttonOk.setClickable(true);
            buttonOk.setVisibility(View.VISIBLE);
            return true;
        }
        buttonBack.setVisibility(View.VISIBLE);
        buttonBack.setClickable(true);
        buttonOk.setVisibility(View.INVISIBLE);
        buttonOk.setClickable(false);

        return false;
    }

}
