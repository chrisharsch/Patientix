package de.teambluebaer.patientix.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.security.MessageDigest;
import java.util.ArrayList;

import de.teambluebaer.patientix.R;
import de.teambluebaer.patientix.helper.Flasher;
import de.teambluebaer.patientix.helper.RestfulHelper;

public class LoginActivity extends Activity {

    private Button buttonLogin;
    private EditText editName = null;
    private EditText editPassword = null;
    private String url;
    private String thisExeption;
    private String httpData;
    Integer responseCode;

    private ArrayList<NameValuePair> parameterMap = new ArrayList<NameValuePair>();

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

        buttonLogin = (Button) findViewById(R.id.buttonBack);
        editName = (EditText) findViewById(R.id.editName);
        editPassword = (EditText) findViewById(R.id.editPassword);

    }

    public void onClickLoginButton(View v) {
        Flasher.flash(buttonLogin, "1x3");
        //create parameterMap to add parameters of the request

        parameterMap.add(new BasicNameValuePair("userName", editName.getText().toString()));
        parameterMap.add(new BasicNameValuePair("userPW", passwordHash(editPassword.getText().toString())));

        //send the request to server
        RestfulHelper restfulHelper = new RestfulHelper();
        responseCode = restfulHelper.executeRequest("login", parameterMap);
        if(responseCode==200){
            Toast.makeText(LoginActivity.this,"Login established",Toast.LENGTH_SHORT).show();
            Log.d("Login successful:", responseCode + "");
        Intent intentFormActivity = new Intent(LoginActivity.this, StartActivity.class);
        startActivity(intentFormActivity);
        }else {
            Log.d("ResponseCode", responseCode+"");
            Toast.makeText(LoginActivity.this, restfulHelper.responseString, Toast.LENGTH_LONG).show();
        }

    }

    private String passwordHash(String pw) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-512");
            byte[] hash = digest.digest(pw.getBytes("UTF-8"));

            StringBuilder hexString = new StringBuilder();
            for (int i : hash) {
                hexString.append(Integer.toHexString(0XFF & i));
            }
            return hexString.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void checkResponseCode() {
        switch (responseCode) {
            case 500:
                Toast.makeText(this, "Server temporary offline", Toast.LENGTH_LONG).show();
                break;
            case 401:
                Toast.makeText(this, "Login-Information incorrect", Toast.LENGTH_LONG).show();
                break;
            case 200:
               //TODO
                Toast.makeText(this, "Login successful", Toast.LENGTH_LONG).show();
                break;
        }
    }


}