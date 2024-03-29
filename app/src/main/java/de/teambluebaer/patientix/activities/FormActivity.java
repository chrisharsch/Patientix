package de.teambluebaer.patientix.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import de.teambluebaer.patientix.R;
import de.teambluebaer.patientix.helper.Constants;
import de.teambluebaer.patientix.helper.LayoutCreater;
import de.teambluebaer.patientix.kioskMode.PrefUtils;
import de.teambluebaer.patientix.xmlParser.MetaAndForm;

/**
 * Copyright 2015 By Authors
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * Authors:
 * Simon Sauerzapf
 * Maren Dietrich
 * Chris Harsch
 */

/**
 * This Activity displays the formula to fill for the patient
 * in page elements
 */

public class FormActivity extends Activity {

    private ImageView imageViewHelpScreen;
    private Button buttonContinue;
    private Button buttonBack;
    private Button buttonZoom;
    private Button buttonOk;
    private LinearLayout content;
    private TextView textViewNumberOfPages;
    private LayoutCreater layoutCreater;
    private MetaAndForm metaAndForm;
    private ScrollView scrollViewForm;

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
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        setContentView(R.layout.activity_form);
        Constants.TORESTART = true;

        if (!isFormula()) {
            Intent intent = new Intent(this, StartActivity.class);
            startActivity(intent);
            PrefUtils.setKioskModeActive(false, this);
            finish();
        }

        imageViewHelpScreen = (ImageView) findViewById(R.id.imageViewHelpScreen);
        imageViewHelpScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageViewHelpScreen.setVisibility(View.GONE);
                imageViewHelpScreen.setClickable(false);

            }
        });
        Log.d("ResponseTabletID", Constants.TABLET_ID);
        Constants.LISTOFACTIVITIES.add(this);
        Constants.CURRENTACTIVITY = this;
        PrefUtils.setKioskModeActive(true, this);

        buttonContinue = (Button) findViewById(R.id.buttonContinue);
        buttonBack = (Button) findViewById(R.id.buttonBack);
        buttonZoom = (Button) findViewById(R.id.buttonZoom);
        scrollViewForm = (ScrollView) findViewById(R.id.ScrollViewForm);
        buttonOk = (Button) findViewById(R.id.buttonOk);

        //Disable back button at first page
        buttonBack.setVisibility(View.INVISIBLE);
        buttonBack.setClickable(false);
        buttonOk.setClickable(false);
        buttonOk.setVisibility(View.INVISIBLE);

        //set the pageLayout for the content
        content = (LinearLayout) findViewById(R.id.content);
        metaAndForm = Constants.GLOBALMETAANDFORM;
        layoutCreater = new LayoutCreater();
        try {
            layoutCreater.CreatPageLayout(this, metaAndForm.getForm().getFirstPage(), content);
            textViewNumberOfPages = (TextView) findViewById(R.id.pageOfNumbers);
            textViewNumberOfPages.setText(metaAndForm.getForm().getCurrentPageText());
            lastPageCheck();
            firstPageCheck();
        } catch (NullPointerException e) {
            Intent intent = new Intent(FormActivity.this, StartActivity.class);
            startActivity(intent);
            PrefUtils.setKioskModeActive(false, this);
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

        Intent intent = new Intent(FormActivity.this, OverviewActivity.class);
        startActivity(intent);
        PrefUtils.setKioskModeActive(false, this);
        finish();
    }

    /**
     * Sets the herlperview when button clicked
     *
     * @param v
     */
    public void onClickHelpButton(View v) {
        try {
            InputMethodManager inputManager = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);

            inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (NullPointerException e) {
            Log.d("KeyboardError", e.toString());
        }
        imageViewHelpScreen.setVisibility(View.VISIBLE);
        imageViewHelpScreen.setClickable(true);
    }

    /**
     * This method swaps to the next page if you press the
     * "Weiter" button.
     *
     * @param v default parameter to change something of the view
     */
    public void onClickNextButton(View v) {

        scrollViewForm.scrollTo(0, 0);
        if (!lastPageCheck()) {
            layoutCreater.CreatPageLayout(this, metaAndForm.getForm().getNextPage(), content);
            textViewNumberOfPages = (TextView) findViewById(R.id.pageOfNumbers);
            textViewNumberOfPages.setText(metaAndForm.getForm().getCurrentPageText());
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

        scrollViewForm.scrollTo(0, 0);
        if (!firstPageCheck()) {
            layoutCreater.CreatPageLayout(this, metaAndForm.getForm().getPreviousPage(), content);
            textViewNumberOfPages = (TextView) findViewById(R.id.pageOfNumbers);
            textViewNumberOfPages.setText(metaAndForm.getForm().getCurrentPageText());
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
        if (!Constants.ZOOMED) {

            Constants.ZOOMED = true;
            layoutCreater.CreatPageLayout(this, metaAndForm.getForm().getcurrenPage(), content);
            buttonZoom.setText("-");
        } else {
            Constants.ZOOMED = false;
            layoutCreater.CreatPageLayout(this, metaAndForm.getForm().getcurrenPage(), content);
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
        if (metaAndForm.getForm().getCurrentPageNumber() == 1) {
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
        if (metaAndForm.getForm().getCurrentPageNumber() == metaAndForm.getForm().getLastPage()) {
            buttonContinue.setClickable(false);
            buttonContinue.setVisibility(View.INVISIBLE);
            buttonOk.setClickable(true);
            buttonOk.setVisibility(View.VISIBLE);
            return true;
        }
        if (metaAndForm.getForm().getLastPage() != 1) {
            buttonBack.setVisibility(View.VISIBLE);
            buttonBack.setClickable(true);
            buttonOk.setVisibility(View.INVISIBLE);
            buttonOk.setClickable(false);
        }
        return false;

    }

    /**
     * This method checks if there is a filled formula and
     * return true if there is one else it return false
     *
     * @return Boolean true or false
     */
    private boolean isFormula() {
        try {
            if (!Constants.GLOBALMETAANDFORM.toXMLString().isEmpty()) {
                return true;
            } else {
                return false;
            }
        } catch (NullPointerException e) {
            return false;
        }
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
}
