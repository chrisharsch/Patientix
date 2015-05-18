package de.teambluebaer.patientix.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import de.teambluebaer.patientix.R;
import de.teambluebaer.patientix.helper.Flasher;
import de.teambluebaer.patientix.helper.RestfulHelper;
import de.teambluebaer.patientix.xmlParser.Form;
import de.teambluebaer.patientix.xmlParser.JavaStrucBuilder;
import de.teambluebaer.patientix.xmlParser.MetaData;

import static de.teambluebaer.patientix.helper.Constants.TABLET_ID;

/**
 * This class have the patient data...
 */

public class StartActivity extends Activity {

    private Button buttonStart;
    private Button buttonUpdate;
    private ArrayList<NameValuePair> parameterMap = new ArrayList<NameValuePair>();
    private int responseCode;

    /**
     * In this method is defined what happens on create of the Activity
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //removes the titlebar in fullscreenmode
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_start);

        buttonStart = (Button) findViewById(R.id.startbtn);
        buttonUpdate = (Button) findViewById(R.id.updatebutton);

    }

    /**
     * When the start button is clicked, then opens the FormActivity
     *
     * @param v
     */
    public void onClickStartButton(View v) {
        Flasher.flash(buttonStart, "1x3");
        Intent intent = new Intent(StartActivity.this, FormActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * @param v
     */
    public void onClickUpdateButton(View v) {
        Flasher.flash(buttonUpdate, "1x5");


        //TODO SEND "OLD" DATA TO SERVER

        Form.getInstance().refresh();
        MetaData.getInstance().refresh();

        SAXParserFactory spf = SAXParserFactory.newInstance();
        try{
            SAXParser saxParser = spf.newSAXParser();
            saxParser.parse(new File(Environment.getExternalStorageDirectory().toString()+"/patientix/form.xml"), JavaStrucBuilder);

        } catch (Exception e){

        }



        parameterMap.add(new BasicNameValuePair("tabletID", TABLET_ID));

        //send the request to server
        RestfulHelper restfulHelper = new RestfulHelper();
        responseCode = restfulHelper.executeRequest("formula", parameterMap);
        if (200 == responseCode) {
            Toast.makeText(StartActivity.this, "Connection established", Toast.LENGTH_LONG).show();
            try {
                File myFile = new File("/sdcard/test.xml");
                myFile.createNewFile();
                FileOutputStream fOut = new FileOutputStream(myFile);
                OutputStreamWriter myOutWriter =
                        new OutputStreamWriter(fOut);
                myOutWriter.append(restfulHelper.responseString);
                myOutWriter.close();
                fOut.close();
                Toast.makeText(getBaseContext(), "Fragebogen wurde gespeichert!", Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(StartActivity.this, "Connection faild" + responseCode, Toast.LENGTH_LONG).show();
        }


    }

    /**
     * This method defines what happens when you press on the hardkey back on the Tablet.
     * In this case the functionality of the button is disabled.
     */
    @Override
    public void onBackPressed() {

    }

}
