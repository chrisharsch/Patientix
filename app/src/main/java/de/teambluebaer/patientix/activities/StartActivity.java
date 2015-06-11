package de.teambluebaer.patientix.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import de.teambluebaer.patientix.R;
import de.teambluebaer.patientix.helper.Constants;
import de.teambluebaer.patientix.helper.Flasher;
import de.teambluebaer.patientix.helper.RestfulHelper;
import de.teambluebaer.patientix.kioskMode.PrefUtils;
import de.teambluebaer.patientix.xmlParser.JavaStrucBuilder;
import de.teambluebaer.patientix.xmlParser.MetaAndForm;

import static de.teambluebaer.patientix.helper.Constants.CURRENTACTIVITY;
import static de.teambluebaer.patientix.helper.Constants.TABLET_ID;

/**
 * This Activity is needed to download the formula data from the server.
 * After the download the data will be checked an you will get feedback
 * what is happened.
 */

/**
 * Created by Maren on 29.04.2015.
 */
public class StartActivity extends Activity {

    private TextView textViewPatientName;
    private TextView textViewPatientBirth;
    private TextView textViewExameName;
    private Button buttonStart;
    private Button buttonUpdate;
    private RestfulHelper restfulHelper =new RestfulHelper();
    private ArrayList<NameValuePair> parameterMap = new ArrayList();
    private int responseCode;
    private final List blockedKeys = new ArrayList(Arrays.asList(KeyEvent.KEYCODE_VOLUME_DOWN, KeyEvent.KEYCODE_VOLUME_UP));

    /**
     * OnCreation of the Activity this method runs and removes the titlebar and
     * set the application to fullscreen mode.
     * After the Layout is created all view who have to be changed are referenced.
     *
     * @param savedInstanceState default parameter
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);

        Constants.TORESTART = false;
        setContentView(R.layout.activity_start);
        Constants.LISTOFACTIVITIES.add(this);
        Constants.CURRENTACTIVITY = this;
        PrefUtils.setKioskModeActive(true, this);

        buttonStart = (Button) findViewById(R.id.buttonStart);
        buttonUpdate = (Button) findViewById(R.id.buttonUpdate);
        textViewPatientName = (TextView) findViewById(R.id.textViewPatientName);
        textViewPatientBirth = (TextView) findViewById(R.id.textViewPatientBirthDate);
        textViewExameName = (TextView) findViewById(R.id.textViewExamination);

        new GetTabletID().execute();
    }

    /**
     * If you click on the startbutton the Activity FormActivity will be opened
     * and this one close.
     *
     * @param v default parameter to change something of the view
     */
    public void onClickStartButton(View v) {
        Flasher.flash(buttonStart, "1x3");
        if(Constants.RESIGN){
            Intent intent = new Intent(StartActivity.this, OverviewActivity.class);
            startActivity(intent);
            PrefUtils.setKioskModeActive(false, this);
            finish();
        }else{
            Intent intent = new Intent(StartActivity.this, FormActivity.class);
            startActivity(intent);
            PrefUtils.setKioskModeActive(false, this);
            finish();
        }

    }

    /**
     * This Method defines what happens if you press the 'Aktualisieren' button.
     * The tabletID will be set to the parametermap to have a parameter for the
     * http post of the Restfullhelper. In the next step  if we recieve the
     * responseCode 200 the JavaStructBuilder is called that the recived String
     * can be made ready for working with. After that the patientdata will be
     * shown on the screen.
     * In the case if there are no data to work with or the tablet has no
     * connection to the server there will be some toasts shown to display
     * the error.
     *
     * @param v default parameter to change something of the view
     */
    public void onClickUpdateButton(View v) {
        textViewPatientName.setText("");
        textViewPatientBirth.setText("");
        textViewExameName.setText("");
        Constants.GLOBALMETAANDFORM = new MetaAndForm();
        buttonStart.setVisibility(View.INVISIBLE);
        buttonStart.setClickable(false);


        Flasher.flash(buttonUpdate, "1x5");
        parameterMap.clear();
        parameterMap.add(new BasicNameValuePair("tabletID", TABLET_ID));


        //send the request to server
        responseCode = restfulHelper.executeRequest("formula", parameterMap);

        //changing some things on the layout
        Log.d("ResponseCode", responseCode + "");
        Log.d("ResponseString", restfulHelper.responseString);
        if (responseCode == 200) {
            if(!restfulHelper.responseString.isEmpty() || restfulHelper.responseString.length()<10) {
                try {
                    String xmlString = restfulHelper.responseString;
                    Log.d("ResponseString: ", restfulHelper.responseString);
                    JavaStrucBuilder strucBuilder = new JavaStrucBuilder();
                    Constants.GLOBALMETAANDFORM = strucBuilder.buildStruc(xmlString);

                    Toast.makeText(getBaseContext(), "Fragebogen wurde gespeichert!", Toast.LENGTH_SHORT).show();

                    textViewPatientName.setText(Constants.GLOBALMETAANDFORM.getMeta().getPatientLastName() + ", "
                            + Constants.GLOBALMETAANDFORM.getMeta().getPatientFirstName());
                    textViewPatientName.setBackgroundColor(Color.parseColor("#ffffff"));
                    textViewPatientBirth.setBackgroundColor(Color.parseColor("#ffffff"));
                    textViewExameName.setBackgroundColor(Color.parseColor("#ffffff"));
                    String birthDate = "";
                    if (!Constants.GLOBALMETAANDFORM.getMeta().getPatientBithDate().equals("Unbekannt")) {
                        birthDate = getbirthDate();
                    }
                    textViewPatientBirth.setText("Geb. " + birthDate);

                    textViewExameName.setText(Constants.GLOBALMETAANDFORM.getMeta().getExameName());
                    buttonStart.setVisibility(View.VISIBLE);
                    buttonStart.setClickable(true);

                } catch (Exception e) {
                    Log.d("FileSaveExeption", e.toString());
                    Toast.makeText(getBaseContext(), "Fehler beim Speichern des Fragebogens", Toast.LENGTH_LONG).show();
                }
                Constants.ISSEND = false;
            }else{
                Toast.makeText(getBaseContext(), "Keine Verbindung zum Server!", Toast.LENGTH_LONG).show();
            }

        } else if (404 == responseCode) {
            Toast.makeText(StartActivity.this, "Keine Daten für dieses Tablet vorhanden", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(StartActivity.this, "Kein Verbindung zum Server! Fehlercode: " + responseCode, Toast.LENGTH_LONG).show();
      }
    }

    /**
     * This Method refactors the spelling of the date.
     *
     * @return String of german formated date.
     */

    private String getbirthDate() {
        String birthDate = "";
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
            Date date = dateFormat.parse(Constants.GLOBALMETAANDFORM.getMeta().getPatientBithDate());
            SimpleDateFormat dt1 = new SimpleDateFormat("dd.mm.yyyy");
            birthDate = dt1.format(date);
        } catch (ParseException ex2) {
            Log.d("DateParseFail", ex2.toString());
        }
        return birthDate;
    }

    /**
     * This method defines what happens when you press on the hardkey back on the Tablet.
     * In this case the functionality of the button is disabled.
     */
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(StartActivity.this, LoginActivity.class);
        startActivity(intent);
        PrefUtils.setKioskModeActive(false, this);
        PrefUtils.setKioskModeActive(false, CURRENTACTIVITY);
        CURRENTACTIVITY.finish();
        finish();

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

    /**
     * This AsyncTask sets the TabletID in Constants that the Tablet can
     * work wirh the System.
     */
    private class GetTabletID extends AsyncTask<String, Void, String> {
        private ArrayList<NameValuePair> parameterMap = new ArrayList();

        /**
         * Everything in this method happens in the background and in here
         * the there will be send so many Requests until the Server connection
         * is availabe or the MacAddress isn't in the Sytem.
         *
         * @param params default parameters
         * @return null because not needed
         */
        @Override
        protected String doInBackground(String... params) {
            this.parameterMap.add(new BasicNameValuePair("macAddress", getMacAddress()));
            responseCode = restfulHelper.executeRequest("getTabletID", this.parameterMap);

            while (responseCode != 200) {
                responseCode = restfulHelper.executeRequest("getTabletID", this.parameterMap);
                Log.d("ResponseCode", responseCode + "");
                if (responseCode == 404) {
                    Log.d("ResponseCode", responseCode + "");
                    break;
                }
            }
            if (responseCode == 200) {
                Constants.TABLET_ID = RestfulHelper.responseString;
            }
            Log.d("ResponseCode", responseCode + "");

            return null;
        }

        /**
         * Method to get the MACAddress of the device
         *
         * @return String of the MACAddress of the device
         */
        private String getMacAddress() {
            WifiManager manager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
            WifiInfo info = manager.getConnectionInfo();
            return info.getMacAddress();
        }
    }
}
