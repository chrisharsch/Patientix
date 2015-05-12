package de.teambluebaer.patientix;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginActivity extends Activity {

    private Button buttonLogin;
    private EditText editName = null;
    private EditText editPassword = null;
    private static final int NUMBER_OF_ITERATIONS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Titlebar removed
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_login);

        // Keyboard open when touch editfield
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        buttonLogin = (Button) findViewById(R.id.buttonReady);
        editName = (EditText) findViewById(R.id.editName);
        editPassword = (EditText) findViewById(R.id.editPassword);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flashButtonsmall(buttonLogin);
                if (new DownloadHelper().doInBackground()) {
                    Intent intentFormActivity = new Intent(LoginActivity.this, StartActivity.class);
                    startActivity(intentFormActivity);
                }
            }
        });
    }

    private class DownloadHelper extends AsyncTask<String, Void, Boolean> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Boolean doInBackground(String... urls) {
            /**
             * Check the login data from the MTRA
             */

            try {
                String passwordHash = getHash(editPassword.getText().toString());
                String userName = editName.getText().toString();

                //if(getHTML("141.19.145.237/login.php?userName="+userName+"&userPW="+passwordHash).equals("200")) {
                if (getHTML("192.168.1.7/login.php?userName=" + userName + "&userPW=" + passwordHash).equals("200")) {
                    Toast.makeText(getApplicationContext(), "Login...", Toast.LENGTH_SHORT).show();
                    return true;
                } else {
                    Toast.makeText(getApplicationContext(), "Login faild", Toast.LENGTH_SHORT).show();
                }

            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Login fehlgeschlagen", Toast.LENGTH_SHORT).show();
            }
            return false;
        }

        public String getHTML(String urlToRead) {
            URL url;
            HttpURLConnection conn;
            BufferedReader rd;
            String line;
            String result = "";
            try {
                url = new URL(urlToRead);
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line = rd.readLine()) != null) {
                    result += line;
                }
                rd.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }
    }

    /**
     * Change the view from the button to press
     *
     * @param myBtnToFlash
     */
    public void flashButtonsmall(final Button myBtnToFlash) {
        myBtnToFlash.setBackgroundResource(R.drawable.button1x3aktiv);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                myBtnToFlash.setBackgroundResource(R.drawable.button1x3normal);
            }
        }, 10);
    }

    public static String getHash(String password) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest digest = MessageDigest.getInstance("SHA-512");
        digest.reset();
        String internal = "sec";
        String concat = password + internal;
        // digest.update(salt);
        byte[] input = digest.digest(concat.getBytes("UTF-8"));
        for (int i = 0; i < NUMBER_OF_ITERATIONS; i++) {
            digest.reset();
            input = digest.digest(input);
        }
        String s = input.toString();
        return s;
    }
}