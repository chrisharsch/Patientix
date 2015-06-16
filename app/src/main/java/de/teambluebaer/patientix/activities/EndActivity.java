package de.teambluebaer.patientix.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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

import java.io.File;
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
 * Created by Maren on 18.05.2015.
 */
public class EndActivity extends Activity {
    TextView textViewEndtext;
    private ArrayList<NameValuePair> parameterMap = new ArrayList();
    private int responseCode;
    RestfulHelper restfulHelper = new RestfulHelper();
    private final List blockedKeys = new ArrayList(Arrays.asList(KeyEvent.KEYCODE_VOLUME_DOWN, KeyEvent.KEYCODE_VOLUME_UP));

    /**
     * This method creates the layout of the Activity, sets the fullscreenmode and
     * removes the titlebar. In here also the Views are referenced.
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

        textViewEndtext = (TextView) findViewById(R.id.textViewEnd);
        Constants.TORESTART = false;
        Constants.LISTOFACTIVITIES.add(this);
        Constants.CURRENTACTIVITY = this;
        PrefUtils.setKioskModeActive(true, this);

        if (isFormula()) {
            String xml = Constants.GLOBALMETAANDFORM.toXMLString();
            //set um parameterMap for RestPost to send formula data
            parameterMap.add(new BasicNameValuePair("formula", xml));
            parameterMap.add(new BasicNameValuePair("patientID", Constants.GLOBALMETAANDFORM.getMeta().getPatientID()));
            Log.d("Response", Constants.GLOBALMETAANDFORM.getMeta().getPatientID());
            Log.d("Response", xml);
            new SendFormula().execute();
        } else {
            Toast.makeText(EndActivity.this, "Kein Formular vorhanden!", Toast.LENGTH_LONG).show();
            textViewEndtext.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Intent intent = new Intent(EndActivity.this, LoginActivity.class);
                    startActivity(intent);
                    PrefUtils.setKioskModeActive(false, EndActivity.this);
                    finish();
                    return true;
                }
            });
        }


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
            String requestPath = "filledformula";
            if (!Constants.ISSEND) {
                if (Constants.RESIGN) {
                    requestPath = "resignFormula";
                }
                    while (responseCode != 200) {
                        responseCode = restfulHelper.executeRequest(requestPath, parameterMap);
                        Log.d("ResponseString", restfulHelper.responseString);
                        Log.d("ResponseCode", responseCode + "");
                        if (responseCode == 404) {
                            Log.d("ResponseCode", responseCode + "");
                            Log.d("ResponseString", restfulHelper.responseString);
                            runOnUiThread(new Runnable() {
                                public void run() {
                                    Toast.makeText(EndActivity.this, restfulHelper.responseString, Toast.LENGTH_LONG).show();
                                    textViewEndtext.setOnLongClickListener(new View.OnLongClickListener() {
                                        @Override
                                        public boolean onLongClick(View v) {
                                            Intent intent = new Intent(EndActivity.this, LoginActivity.class);
                                            startActivity(intent);
                                            //PrefUtils.setKioskModeActive(false, EndActivity.this);
                                            // finish();
                                            System.exit(0);
                                            return true;
                                        }
                                    });
                                }
                            });
                            break;
                        }
                    }
                    if (responseCode == 200) {
                        runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(EndActivity.this, "Formular wurde erfolgreich übertragen.", Toast.LENGTH_LONG).show();
                                Log.d("Response String", restfulHelper.responseString);
                                textViewEndtext.setOnLongClickListener(new View.OnLongClickListener() {
                                    @Override
                                    public boolean onLongClick(View v) {
                                        Intent intent = new Intent(EndActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                        //PrefUtils.setKioskModeActive(false, EndActivity.this);
                                       // finish();
                                        System.exit(0);
                                        return true;
                                    }
                                });
                            }
                        });
                    }
                    Log.d("Response String", restfulHelper.responseString);
                    Log.d("ResponseCode", responseCode + "");
                    Constants.ISSEND = true;

            } else {
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(EndActivity.this, "Formular wurde bereits gesendet.", Toast.LENGTH_LONG).show();
                        textViewEndtext.setOnLongClickListener(new View.OnLongClickListener() {
                            @Override
                            public boolean onLongClick(View v) {
                                Intent intent = new Intent(EndActivity.this, LoginActivity.class);
                                startActivity(intent);
                                PrefUtils.setKioskModeActive(false, EndActivity.this);
                                finish();
                                return true;
                            }
                        });
                    }
                });
            }

            return null;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("Stop", "Stop motherfucker");
        System.gc();
        deleteCache(getApplicationContext());
    }
    public static void deleteCache(Context context) {
        try {
            File dir = context.getCacheDir();
            if (dir != null && dir.isDirectory()) {
                deleteDir(dir);
            }
        } catch (Exception e) {}
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
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
