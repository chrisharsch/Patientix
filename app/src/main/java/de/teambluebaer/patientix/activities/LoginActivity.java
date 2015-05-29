package de.teambluebaer.patientix.activities;

import android.app.Activity;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.security.MessageDigest;

import de.teambluebaer.patientix.R;
import de.teambluebaer.patientix.helper.Constants;
import de.teambluebaer.patientix.helper.Flasher;

/**
 * This Activity displays the Login for the Docs or the MTRA.
 * Here they can enter their pin to access to the APP.
 */
public class LoginActivity extends Activity {

    private Button buttonLogin;
    private EditText editPassword = null;
    Integer responseCode;

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
        setContentView(R.layout.activity_login);
        buttonLogin = (Button) findViewById(R.id.buttonLogin);
        editPassword = (EditText) findViewById(R.id.editPassword);

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
                Log.d("Login successful: ", responseCode + "");
                Intent intent = new Intent(LoginActivity.this, StartActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "Falscher PIN!!!", Toast.LENGTH_LONG).show();
                editPassword.setText("");
            }
        } else {
            Toast.makeText(this, "WiFi ist abgeschaltet", Toast.LENGTH_LONG).show();
            editPassword.setText("");

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

}