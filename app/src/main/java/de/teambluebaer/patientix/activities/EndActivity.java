package de.teambluebaer.patientix.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.teambluebaer.patientix.R;
import de.teambluebaer.patientix.helper.Constants;
import de.teambluebaer.patientix.helper.RestfulHelper;
import de.teambluebaer.patientix.kioskMode.PrefUtils;

/**
 * This Activity shows the endscreen there the patient is afford
 * to bring the tablet back. In this Activity the filled formula
 * is send to the server.
 */

/**
 * Created by Chris on 16.05.2015.
 */
public class EndActivity extends Activity {

    TextView endtext;
    private ArrayList<NameValuePair> parameterMap = new ArrayList();
    private int responseCode;
    RestfulHelper restfulHelper = new RestfulHelper();
    private final List blockedKeys = new ArrayList(Arrays.asList(KeyEvent.KEYCODE_VOLUME_DOWN, KeyEvent.KEYCODE_VOLUME_UP));

    /**
     * @param savedInstanceState default parameter
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //removes the titlebar in fullscreenmode
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        setContentView(R.layout.activity_end);

        Constants.CURRENTACTIVITY = this;
        PrefUtils.setKioskModeActive(true, getApplicationContext());

        if (isFormula()) {
            String xml = Constants.globalMetaandForm.toXMLString();

            //set um parameterMap for RestPost to send formula data
            parameterMap.add(new BasicNameValuePair("isFormula", isFormula() + ""));
            parameterMap.add(new BasicNameValuePair("formula", xml));
            parameterMap.add(new BasicNameValuePair("macaddress", getMacAddress()));
            parameterMap.add(new BasicNameValuePair("patientID", Constants.TABLET_ID));
            new SendFormula().execute();
        } else {
            Toast.makeText(EndActivity.this, "Kein Formular vorhanden!", Toast.LENGTH_LONG).show();
        }

        endtext.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent intent = new Intent(EndActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                return true;
            }
        });
    }


    /**
     * Async Task to send the filledformula in the background
     * and 2 UI Threads to make a Toast for connection established
     * or worng formula data
     */
    private class SendFormula extends AsyncTask<String, Void, String> {
        /**
         * Everything in this method happens in the background and in here
         * the formula data is send to the server as long as they arrive
         * and the right response code is send back.
         *
         * @param params default parameters
         * @return null because not needed
         */
        @Override
        protected String doInBackground(String... params) {
            responseCode = restfulHelper.executeRequest("filledformula", parameterMap);
            if (!Constants.ISSEND) {
                while (responseCode != 200) {
                    responseCode = restfulHelper.executeRequest("filledformula", parameterMap);
                    if (responseCode == 404) {
                        runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(EndActivity.this, "Keine oder Fehlerhafte Formulardaten!", Toast.LENGTH_LONG).show();
                            }
                        });
                        Log.d("ResponseCode", responseCode + "");
                        break;
                    }
                }
                if (responseCode == 200) {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(EndActivity.this, "Formular wurde erfolgreich übertragen.", Toast.LENGTH_LONG).show();
                        }
                    });
                }
                Log.d("ResponseCode", responseCode + "");
                Constants.ISSEND = true;
            } else {
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(EndActivity.this, "Formular wurde bereits gesendet.", Toast.LENGTH_LONG).show();
                    }
                });
            }

            return null;
        }
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

    /**
     * This method checks if there is a filled formula and
     * return true if there is one else it return false
     *
     * @return Boolean true or false
     */
    private boolean isFormula() {
        try {
            if (!Constants.globalMetaandForm.toXMLString().isEmpty()) {
                return true;
            } else {
                return false;
            }
        } catch (NullPointerException e) {
            return false;
        }
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
