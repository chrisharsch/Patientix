package de.teambluebaer.patientix.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.teambluebaer.patientix.R;
import de.teambluebaer.patientix.helper.Constants;
import de.teambluebaer.patientix.helper.Flasher;
import de.teambluebaer.patientix.helper.RestfulHelper;
import de.teambluebaer.patientix.kioskMode.PrefUtils;

import static de.teambluebaer.patientix.helper.Constants.CURRENTACTIVITY;

/**
 * This Activity displays the Login for the Docs or the MTRA.
 * Here they can enter their pin to access to the APP.
 */
/**
 * Created by Maren on 21.05.2015.
 */
public class LoginActivity extends Activity {


    private String pathOfConfig = Environment.getExternalStorageDirectory() + "/.patientix";
    private Button buttonLogin;
    private EditText editTextPassword = null;
    private final List blockedKeys = new ArrayList(Arrays.asList(KeyEvent.KEYCODE_VOLUME_DOWN, KeyEvent.KEYCODE_VOLUME_UP));
    private ArrayList<NameValuePair> parameterMap = new ArrayList();
    private int responseCode;
    RestfulHelper restfulHelper = new RestfulHelper();

    /**
     * In this method is defined what happens on create of the Activity:
     * Set Layout and remove titlebar
     *
     * @param savedInstanceState Standard parameter
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);

        setContentView(R.layout.activity_login);
        Constants.CURRENTACTIVITY = this;
        Constants.LISTOFACTIVITIES.add(this);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        Constants.TORESTART = false;
        PrefUtils.setKioskModeActive(true, this);
        buttonLogin = (Button) findViewById(R.id.buttonLogin);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        editTextPassword.setOnEditorActionListener(
                new EditText.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        if (actionId == EditorInfo.IME_ACTION_DONE) {
                            onClickLoginButton(v);
                        }
                        return true;
                    }
                });

        createDirIfNotExists(pathOfConfig);
        createConfigIfNotExists(pathOfConfig);
    }

    /**
     *
     * @param path
     * @return
     */
    public static boolean createDirIfNotExists(String path) {
        boolean ret = true;

        File file = new File(path);
        if (!file.exists()) {
            if (!file.mkdirs()) {
                Log.e("TravellerLog :: ", "Problem creating folder");
                ret = false;
            }
        }
        return ret;
    }

    public static boolean createConfigIfNotExists(String path) {
        File file = new File(path + "/config.txt");

        if (!file.exists()) {
            try {
                OutputStream fo = new FileOutputStream(file);
                fo.write(Constants.CONFIGURATION.getBytes());
                fo.close();
                file.createNewFile();
                Log.d("File", "Config.txt created");
            } catch (IOException e) {
                Log.d("FileCreationExeption", e.toString());
                return false;
            }
            return true;
        }else{
            BufferedReader br = null;
            try {
                String sCurrentLine = "";
                br = new BufferedReader(new FileReader(path+"/config.txt"));
                sCurrentLine =sCurrentLine + br.readLine();
                sCurrentLine =sCurrentLine + br.readLine();
                    Log.d("currentLine",sCurrentLine);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (br != null)br.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }

            }
        return false;
    }

    /**
     * On press of the "Login" button, the users can access to the APP
     * with their PIN and after the StartActivity will be loaded.
     *
     * @param v default parameter to change something of the view
     */
    public void onClickLoginButton(View v) {
        Flasher.flash(buttonLogin, "1x3");
        ConnectivityManager connManager = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (mWifi.isConnected()) {
            if (passwordHash(editTextPassword.getText().toString()).equals(Constants.PIN)) {
                new GetTabletID().execute();
            } else {
                editTextPassword.setText("");
                Toast.makeText(this, "Falscher PIN!!!", Toast.LENGTH_LONG).show();
            }
        } else {
            editTextPassword.setText("");
            Toast.makeText(this, "WiFi ist abgeschaltet, bitte schalten sie das WiFi wieder ein.", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Deactive kioskmode with press on exit button if you
     * entered the PIN-Code
     *
     * @param v Parameter to change something in view
     */
    public void onClickButtonExit(View v) {
        if (passwordHash(editTextPassword.getText().toString()).equals(Constants.PIN)) {
            PrefUtils.setKioskModeActive(false, Constants.CURRENTACTIVITY);
            Constants.CURRENTACTIVITY.finish();
            PrefUtils.setKioskModeActive(false, this);
            for (Activity activity : Constants.LISTOFACTIVITIES) {
                PrefUtils.setKioskModeActive(false, activity);
                activity.finish();
            }
            System.exit(0);
        } else {
            editTextPassword.setText("");
            Toast.makeText(this, "Falscher PIN!!!", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * This method converts the bytes of String to hex
     *
     * @param data byte Array to convert
     * @return String of hexcode
     */
    private String convertByteToHex(byte data[]) {
        StringBuffer hexData = new StringBuffer();
        for (int byteIndex = 0; byteIndex < data.length; byteIndex++)
            hexData.append(Integer.toString((data[byteIndex] & 0xff) + 0x100, 16).substring(1));
        return hexData.toString();
    }

    /**
     * This method is used to hash the password or pin
     *
     * @param textToHash String of text to hash
     * @return String which is hashed
     */
    private String passwordHash(String textToHash) {
        try {
            final MessageDigest sha512 = MessageDigest.getInstance("SHA-512");
            sha512.update(textToHash.getBytes());
            return convertByteToHex(sha512.digest());
        } catch (Exception e) {
            Log.d("HashFail", e.toString());
        }
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

    /**
     * This AsyncTask sets the TabletID in Constants that the Tablet can
     * work wirh the System.
     */
    private class GetTabletID extends AsyncTask<String, Void, String> {
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
            parameterMap.add(new BasicNameValuePair("macAddress", getMacAddress()));
            responseCode = restfulHelper.executeRequest("getTabletID", parameterMap);

            while (responseCode != 200) {
                responseCode = restfulHelper.executeRequest("getTabletID", parameterMap);
                Log.d("ResponseCode", responseCode + "");
                if (responseCode == 404) {
                    Log.d("ResponseCode", responseCode + "");
                    runOnUiThread(new Runnable() {
                        public void run() {
                            editTextPassword.setText("");
                            Toast.makeText(LoginActivity.this, "Login fehlgeschalgen dieses Tablet ist nicht im System.", Toast.LENGTH_LONG).show();
                        }
                    });
                    break;
                } else if (responseCode == 503) {
                    Log.d("ResponseCode", responseCode + "");
                    runOnUiThread(new Runnable() {
                        public void run() {
                            editTextPassword.setText("");
                            Toast.makeText(LoginActivity.this, "Login fehlgeschalgen Server nicht erreichbar", Toast.LENGTH_LONG).show();
                        }
                    });
                    break;

                }
            }
            if (responseCode == 200) {
                Constants.TABLET_ID = RestfulHelper.responseString;
                Intent intent = new Intent(LoginActivity.this, StartActivity.class);
                startActivity(intent);
                PrefUtils.setKioskModeActive(false, LoginActivity.this);
                PrefUtils.setKioskModeActive(false, CURRENTACTIVITY);
                CURRENTACTIVITY.finish();
                finish();
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(LoginActivity.this, "Login erfolgreich.", Toast.LENGTH_LONG).show();
                    }
                });
            }
            Log.d("ResponseCode", responseCode + "");
            return null;
        }
    }
}