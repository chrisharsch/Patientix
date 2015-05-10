package de.teambluebaer.patientix;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.patientix.maren.patientix.R;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class LoginActivity extends Activity {

    private DatabaseActivity databaseClass;
    private Button buttonLogin;
    private EditText editName = null;
    private EditText editPassword = null;

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
                checkCredentials();
            }
        });
    }

    /**
     * Check the login data from the MTRA
     */
    private void checkCredentials() {

        try {
            Connection conn = databaseClass.getCon();
            Statement stmt = conn.createStatement();
            ResultSet result = stmt.executeQuery("SELECT userID FROM Users WHERE userName = "
                    +editName.getText().toString()+" AND userPW = "+editPassword.getText().toString());

            while(result.next()) {
                // Warum sollte er die ausgeben?
            }

            // Aus RowObjekt die Werte auslesen??? Waaaas??

            Toast.makeText(getApplicationContext(), "Login...", Toast.LENGTH_SHORT).show();

            /*      TEST
            if (editName.getText().toString().equals("MTRA") &&
                    editPassword.getText().toString().equals("MTRA")) {
                Toast.makeText(getApplicationContext(), "Login...", Toast.LENGTH_SHORT).show();
                Intent intentFormActivity = new Intent(LoginActivity.this, StartActivity.class);
                startActivity(intentFormActivity);
            } else {
                Toast.makeText(getApplicationContext(), "Login fehlgeschlagen", Toast.LENGTH_SHORT).show();
            } */

        } catch (SQLException se) {
            Toast.makeText(getApplicationContext(), "Login fehlgeschlagen", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Login fehlgeschlagen", Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * Change the view from the button to press
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
}
