package de.teambluebaer.patientix.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import java.util.Date;

import de.teambluebaer.patientix.R;
import de.teambluebaer.patientix.helper.Constants;
import de.teambluebaer.patientix.helper.Flasher;
import de.teambluebaer.patientix.helper.RestfulHelper;
import de.teambluebaer.patientix.xmlParser.JavaStrucBuilder;

import static de.teambluebaer.patientix.helper.Constants.TABLET_ID;

/**
 * This Activity is needed to download the formula data from the server.
 * After the download the data will be checked an you will get feedback
 * what is happened.
 */

public class StartActivity extends Activity {

    private TextView patientName;
    private TextView patientID;
    private TextView exameName;
    private Button buttonStart;
    private Button buttonUpdate;
    private ArrayList<NameValuePair> parameterMap = new ArrayList();
    private int responseCode;

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

        setContentView(R.layout.activity_start);

        buttonStart = (Button) findViewById(R.id.startbtn);
        buttonUpdate = (Button) findViewById(R.id.updatebutton);
        patientName = (TextView) findViewById(R.id.patientname);
        patientID = (TextView) findViewById(R.id.patientid);
        exameName = (TextView) findViewById(R.id.textExamination);

    }

    /**
     * If you click on the startbutton the Activity FormActivity will be opened
     * and this one close.
     *
     * @param v default parameter to change something of the view
     */
    public void onClickStartButton(View v) {
        Flasher.flash(buttonStart, "1x3");
        Intent intent = new Intent(StartActivity.this, FormActivity.class);
        startActivity(intent);
        finish();
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
        Flasher.flash(buttonUpdate, "1x5");
        parameterMap.add(new BasicNameValuePair("tabletID", TABLET_ID));

        //send the request to server
        RestfulHelper restfulHelper = new RestfulHelper();
        responseCode = restfulHelper.executeRequest("formula", parameterMap);
        if (responseCode == 200) {
            try {
                String xmlString = restfulHelper.responseString;
                JavaStrucBuilder strucBuilder = new JavaStrucBuilder();
                Constants.globalMetaandForm = strucBuilder.buildStruc(xmlString);
                Toast.makeText(getBaseContext(), "Fragebogen wurde gespeichert!", Toast.LENGTH_SHORT).show();

                patientID.setText(Constants.globalMetaandForm.getMeta().getPatientID());
                String nameSegment = Constants.globalMetaandForm.getMeta().getPatientLastName() + ", "
                        + Constants.globalMetaandForm.getMeta().getPatientFirstName();

                if (!Constants.globalMetaandForm.getMeta().getPatientBithDate().equals("Unbekannt")) {
                    nameSegment = nameSegment + " " + getbirthDate();
                }

                patientName.setText(nameSegment);
                exameName.setText(Constants.globalMetaandForm.getMeta().getExameName());
                buttonStart.setVisibility(View.VISIBLE);
                buttonStart.setClickable(true);
            } catch (Exception e) {
                Log.d("FileSaveExeption", e.toString());
                Toast.makeText(getBaseContext(), "Fehler beim Speichern des Fragebogens", Toast.LENGTH_LONG).show();
            }
        } else if (404 == responseCode) {
            Toast.makeText(StartActivity.this, "Keine Daten für dieses Tablet vorhanden", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(StartActivity.this, "Kein Verbindung zum Server! Fehlercode: " + responseCode, Toast.LENGTH_LONG).show();
        }
    }

    /**
     * This Method refactor the date spelling.
     *
     * @return String of german formated date.
     */

    private String getbirthDate() {
        String birthDate = "";
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
            Date date = dateFormat.parse(Constants.globalMetaandForm.getMeta().getPatientBithDate());
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

    }

}
