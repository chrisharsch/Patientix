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

import org.apache.http.NameValuePair;

import java.security.MessageDigest;
import java.util.ArrayList;

import de.teambluebaer.patientix.R;
import de.teambluebaer.patientix.helper.Constants;
import de.teambluebaer.patientix.helper.Flasher;

/**
 * This class is for the MTRA Login
 */
public class LoginActivity extends Activity {

    private Button buttonLogin;
    private EditText editName = null;
    private EditText editPassword = null;
    Integer responseCode;
    private ArrayList<NameValuePair> parameterMap = new ArrayList();

    /**
     * In this method is defined what happens on create of the Activity:
     * Set Layout, remove titlebar, keyboard open
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Titlebar removed
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_login);

        // Keyboard open when touch editfield
        //getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        buttonLogin = (Button) findViewById(R.id.buttonLogin);
        editPassword = (EditText) findViewById(R.id.editPassword);

    }

    /**
     * Check the login credentials from MTRA
     *
     * @param v
     */
    public void onClickLoginButton(View v) {
        Flasher.flash(buttonLogin, "1x3");

        //create parameterMap to add parameters of the request

        ConnectivityManager connManager = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        if (mWifi.isConnected()) {
            if(passwordHash(editPassword.getText().toString()).equals(Constants.PIN)) {
                Log.d("Login successful: ", responseCode + "");
                Intent intent = new Intent(LoginActivity.this, StartActivity.class);
                startActivity(intent);
                finish();
            }else{
                Toast.makeText(this,"Falscher PIN!!!",Toast.LENGTH_LONG).show();
            }


        } else {
            Toast.makeText(this, "WiFi ist abgeschaltet", Toast.LENGTH_LONG).show();
        }

    }


    private String convertByteToHex(byte data[]) {
        StringBuffer hexData = new StringBuffer();
        for (int byteIndex = 0; byteIndex < data.length; byteIndex++)
            hexData.append(Integer.toString((data[byteIndex] & 0xff) + 0x100, 16).substring(1));

        return hexData.toString();
    }

    private String passwordHash(String textToHash){
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
     *
     */
    private void checkResponseCode() {
        switch (responseCode) {
            default:
                Toast.makeText(this, "Fehlercode: " + responseCode + " melden sie sich beim Systemadministrator", Toast.LENGTH_LONG).show();
                break;
            case 503:
                Toast.makeText(this, "Server nicht erreichbar", Toast.LENGTH_LONG).show();
                break;
            case 401:
                Toast.makeText(this, "Login-Daten falsch", Toast.LENGTH_LONG).show();
                break;
            case 200:
                Toast.makeText(this, "Login erfolgreich", Toast.LENGTH_LONG).show();
                break;
        }
    }


}