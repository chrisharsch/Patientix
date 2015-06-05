package de.teambluebaer.patientix.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
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

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.teambluebaer.patientix.R;
import de.teambluebaer.patientix.helper.Constants;
import de.teambluebaer.patientix.helper.Flasher;
import de.teambluebaer.patientix.kioskMode.PrefUtils;

/**
 * This Activity displays the Login for the Docs or the MTRA.
 * Here they can enter their pin to access to the APP.
 */

public class LoginActivity extends Activity {

    private Button buttonLogin;
    private EditText editPassword = null;
    Integer responseCode;
    private final List blockedKeys = new ArrayList(Arrays.asList(KeyEvent.KEYCODE_VOLUME_DOWN, KeyEvent.KEYCODE_VOLUME_UP));
    private ArrayList<NameValuePair> parameterMap = new ArrayList();


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

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        Constants.TORESTART = false;
        PrefUtils.setKioskModeActive(true, this);
        buttonLogin = (Button) findViewById(R.id.buttonLogin);
        editPassword = (EditText) findViewById(R.id.editPassword);
        editPassword.setOnEditorActionListener(
                new EditText.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        if (actionId == EditorInfo.IME_ACTION_DONE) {
                            onClickLoginButton(v);
                        }
                        return true;
                    }
                });

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
            if (passwordHash(editPassword.getText().toString()).equals(Constants.PIN)) {
              /* IP-Addresse Vergeben:

                parameterMap.add(new BasicNameValuePair("macAddress", getMacAddress()));


                //send the request to server
                RestfulHelper restfulHelper = new RestfulHelper();
                responseCode = restfulHelper.executeRequest("getIP", parameterMap);
                if(responseCode == 200) {
                    android.provider.Settings.System.putString(getContentResolver(), android.provider.Settings.System.WIFI_USE_STATIC_IP, "1");
                    android.provider.Settings.System.putString(getContentResolver(), android.provider.Settings.System.WIFI_STATIC_IP, restfulHelper.responseString);
                    android.provider.Settings.System.putString(getContentResolver(), android.provider.Settings.System.WIFI_STATIC_NETMASK, "255.255.255.0");
                    android.provider.Settings.System.putString(getContentResolver(), android.provider.Settings.System.WIFI_STATIC_DNS1, "192.168.0.254");
                    android.provider.Settings.System.putString(getContentResolver(), android.provider.Settings.System.WIFI_STATIC_GATEWAY, "192.168.0.254");
                }else{
                    Toast.makeText(this,"Fehler bei der Addressvergabe des Tablets Fehlercode: "+ responseCode,Toast.LENGTH_LONG).show();
                }*/
                Log.d("Login successful: ", responseCode + "");
                Intent intent = new Intent(LoginActivity.this, StartActivity.class);
                startActivity(intent);
                PrefUtils.setKioskModeActive(false, this);
                finish();
            } else {
                editPassword.setText("");
                Toast.makeText(this, "Falscher PIN!!!", Toast.LENGTH_LONG).show();
            }
        } else {
            editPassword.setText("");
            Toast.makeText(this, "WiFi ist abgeschaltet", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Deactive kioskmode with press on exit button if you
     * entered the PIN-Code
     *
     * @param v Parameter to change something in view
     */
    public void onClickButtonExit(View v) {
        if (passwordHash(editPassword.getText().toString()).equals(Constants.PIN)) {
            PrefUtils.setKioskModeActive(false, Constants.CURRENTACTIVITY);
            PrefUtils.setKioskModeActive(false, this);
            for (Activity activity : Constants.LISTOFACTIVITIES) {
                PrefUtils.setKioskModeActive(false, activity);
                activity.finish();
            }
            System.exit(0);
        } else {
            editPassword.setText("");
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
}