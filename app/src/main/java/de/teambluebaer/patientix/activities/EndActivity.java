package de.teambluebaer.patientix.activities;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;

import de.teambluebaer.patientix.R;
import de.teambluebaer.patientix.helper.Constants;
import de.teambluebaer.patientix.helper.RestfulHelper;

/**
 * Endscreen with screen lock
 */

public class EndActivity extends Activity {

    private ArrayList<NameValuePair> parameterMap = new ArrayList();
    private int responseCode;
    private String isFormula;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //removes the titlebar in fullscreenmode
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_end);

        ConnectivityManager connManager = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        Constants.FILLEDFORMULA="<ahsdfadsfaewfawef>asdasdoansdobnawodfboi>aoisdaipsdjpoaj><asiodnpijn>";
        if (Constants.FILLEDFORMULA.length() > 1) {
            isFormula = "true";
        } else {
            isFormula = "false";
        }
    //get macaddress to verify device
        WifiManager manager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = manager.getConnectionInfo();
        String address = info.getMacAddress();
        //set um parameterMap for RestPost to send formula data

        Log.d("response", address);
        parameterMap.add(new BasicNameValuePair("isFormula", "true"));
        parameterMap.add(new BasicNameValuePair("formula", "<xml>hallo</xml>"));
        parameterMap.add(new BasicNameValuePair("macaddress", "DE:AD:BE:EF:10:26"));
        parameterMap.add(new BasicNameValuePair("patientID", "3"));


        //send the request to server
        RestfulHelper restfulHelper = new RestfulHelper();
        responseCode = restfulHelper.executeRequest("filledformula", parameterMap);
        Log.d("response", responseCode+"");
        Log.d("response", RestfulHelper.responseString);
        while (responseCode != 200) {
            responseCode = restfulHelper.executeRequest("filledformula", parameterMap);
            if (responseCode == 404) {
                Toast.makeText(this, "Keine oder Fehlerhafte Formulardaten!", Toast.LENGTH_LONG).show();
            }
            Log.d("response", responseCode+"");
            Log.d("response", RestfulHelper.responseString);
            Toast.makeText(this, ""+ responseCode, Toast.LENGTH_LONG).show();
        }

        Toast.makeText(this, "Formular wurde erfolgreich übertragen.", Toast.LENGTH_LONG).show();
        //Delete old Patientdata
        Constants.FILLEDFORMULA="";
        Constants.PATIENTID="";
        parameterMap.clear();

    }


    // screen look with logout from MTRA


    /**
     * This method defines what happens when you press on the hardkey back on the Tablet.
     * In this case the functionality of the button is disabled.
     */
    @Override
    public void onBackPressed() {

    }
}
