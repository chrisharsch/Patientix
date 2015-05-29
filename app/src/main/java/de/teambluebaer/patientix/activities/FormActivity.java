package de.teambluebaer.patientix.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.teambluebaer.patientix.R;
import de.teambluebaer.patientix.helper.Constants;
import de.teambluebaer.patientix.helper.Flasher;
import de.teambluebaer.patientix.helper.LayoutCreater;
import de.teambluebaer.patientix.kioskMode.PrefUtils;
import de.teambluebaer.patientix.xmlParser.MetaandForm;

/**
 * This Activity displays the formula to fill for the patient
 * in page elements
 */

public class FormActivity extends Activity {

    private Button buttonContinue;
    private Button buttonBack;
    private Button buttonZoom;
    private Button buttonOk;
    private LinearLayout content;
    private TextView numberOfPages;
    private LayoutCreater layoutCreater;
    private MetaandForm metaandForm;
    private ScrollView scrollView;
    private final List blockedKeys = new ArrayList(Arrays.asList(KeyEvent.KEYCODE_VOLUME_DOWN, KeyEvent.KEYCODE_VOLUME_UP));


    /**
     * This method creates the layout of the Activity, sets the fullscreenmode and
     * removes the titlebar. In here also the Views are referenced.
     *
     * @param savedInstanceState default parameter
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        setContentView(R.layout.activity_form);
        Constants.CURRENTACTIVITY = this;
        PrefUtils.setKioskModeActive(true, getApplicationContext());

        buttonContinue = (Button) findViewById(R.id.buttonContinue);
        buttonBack = (Button) findViewById(R.id.buttonBack);
        buttonZoom = (Button) findViewById(R.id.buttonZoom);
        scrollView = (ScrollView) findViewById(R.id.ScrollViewOverview);
        buttonOk = (Button) findViewById(R.id.buttonOk);

        //Disable back button at first page
        buttonBack.setVisibility(View.INVISIBLE);
        buttonBack.setClickable(false);
        buttonOk.setClickable(false);
        buttonOk.setVisibility(View.INVISIBLE);

        //set the pageLayout for the content
        content = (LinearLayout) findViewById(R.id.content);
        metaandForm = Constants.globalMetaandForm;
        layoutCreater = new LayoutCreater();
        try {
            layoutCreater.CreatPageLayout(this, metaandForm.getForm().getFirstPage(), content);
            numberOfPages = (TextView) findViewById(R.id.pageOfNumbers);
            numberOfPages.setText(metaandForm.getForm().getCurrentPageText());
        } catch (NullPointerException e) {
            Intent intent = new Intent(FormActivity.this, StartActivity.class);
            startActivity(intent);
            finish();
        }
    }

    /**
     * This method shows the next Activity and closes this if you press
     * the "OK" button at the end of the formula
     *
     * @param v default parameter to change something of the view
     */
    public void onClickButtonOk(View v) {
        Flasher.flash(buttonOk, "1x3");
        Intent intent = new Intent(FormActivity.this, OverviewActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * This method swaps to the next page if you press the
     * "Weiter" button.
     *
     * @param v default parameter to change something of the view
     */
    public void onClickNextButton(View v) {
        Flasher.flash(buttonContinue, "1x3");
        scrollView.scrollTo(0, 0);
        if (!lastPageCheck()) {
            layoutCreater.CreatPageLayout(this, metaandForm.getForm().getNextPage(), content);
            numberOfPages = (TextView) findViewById(R.id.pageOfNumbers);
            numberOfPages.setText(metaandForm.getForm().getCurrentPageText());
        }
        lastPageCheck();
    }

    /**
     * This method swaps to the last page if you press the
     * "Zurück" button.
     *
     * @param v default parameter to change something of the view
     */
    public void onClickBackButton(View v) {
        Flasher.flash(buttonBack, "1x3");
        scrollView.scrollTo(0, 0);
        if (!firstPageCheck()) {
            layoutCreater.CreatPageLayout(this, metaandForm.getForm().getPreviousPage(), content);
            numberOfPages = (TextView) findViewById(R.id.pageOfNumbers);
            numberOfPages.setText(metaandForm.getForm().getCurrentPageText());
        }
        lastPageCheck();
        firstPageCheck();

    }

    /**
     * This method sets the text of the buttonZoom and changes the
     * text size to a bigger or a smaller one. There are just two
     * sizes of zoom between them you can switch.
     *
     * @param v default parameter to change something of the view
     */
    public void onClickZoomButton(View v) {
        if (!Constants.zoomed) {
            Flasher.flash(buttonZoom, "1x1");
            Constants.zoomed = true;
            layoutCreater.CreatPageLayout(this, metaandForm.getForm().getcurrenPage(), content);
            buttonZoom.setText("-");
        } else {
            Constants.zoomed = false;
            layoutCreater.CreatPageLayout(this, metaandForm.getForm().getcurrenPage(), content);
            buttonZoom.setText("+");
        }
    }

    /**
     * This method checks if there is the first page shown so the
     * button "Zurück" is disabled.
     *
     * @return true if the first page is shown
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

    /**
     * This method checks if there is the last page shown so the
     * button "Weiter" is disabled and the "OK" button is shown
     *
     * @return true if the last page is shown
     */
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

    /**
     * This method defines what happens when you press on the hardkey back on the Tablet.
     * In this case the functionality of the button is disabled.
     */
    @Override
    public void onBackPressed() {

    }


    /**
     * This method kills all system dialogs if they are shown
     *
     * @param hasFocus
     */
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (!hasFocus) {
            // Close every kind of system dialog
            Intent closeDialog = new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
            sendBroadcast(closeDialog);
        }
    }

    /**
     * This method disables the volumes keys
     *
     * @param event Listens on Keyinput event
     * @return Calls super class if key is allowed
     */
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (blockedKeys.contains(event.getKeyCode())) {
            return true;
        } else {
            return super.dispatchKeyEvent(event);
        }
    }
}
