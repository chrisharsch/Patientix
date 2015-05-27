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
/**
 * Created by Maren on 16.05.2015.
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

        if (Constants.globalMetaandForm.toXMLString().length() > 1) {
            isFormula = "true";
        } else {
            isFormula = "false";
        }
    //get macaddress to verify device
        WifiManager manager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = manager.getConnectionInfo();
        String address = info.getMacAddress();

        String hallo ="<page>\n" +
                "\t<row>\n" +
                "\t\t<text text=\"Informationen f�r unsere Patienten zur Computertomographie\" size=\"20\">\n" +
                "\t\t</text>        \t\t\t\n" +
                "\t</row>        \t\t\t\n" +
                "\t<row>        \t\t\t\t\n" +
                "\t\t<text text=\"Was ist Computertomographie?\" size=\"15\">\n" +
                "\t\t</text>        \t\t\t\n" +
                "\t</row>        \t\t\t\n" +
                "\t<row>        \t\t\t\t\n" +
                "\t\t<text text=\"Die Computertomographie, auch CT genannt, ist neben der Kernspintomographie eine weitere moderne Form der diagnostischen Abbildung des menschlichen K�rpers. In der Computertomographie werden R�ntgenstrahlen zur Bilderstellung verwendet.\" size=\"14\">\n" +
                "\t\t</text>        \t\t\t\n" +
                "\t</row>        \t\t\t\n" +
                "\t<row>        \t\t\t\t\n" +
                "\t\t<text text=\"Wie bereite ich mich auf die Untersuchung vor?\" size=\"15\">\n" +
                "\t\t</text>        \t\t\t\n" +
                "\t</row>        \t\t\t\n" +
                "\t<row>        \t\t\t\t\n" +
                "\t\t<text text=\"Wird bei Ihnen eine CT-Untersuchung ohne Kontrastmittel (nativ) durchgef�hrt, ist keine besondere Vorbereitung notwendig. Sie k�nnen wie �blich essen und trinken. Bei einigen Untersuchungen ist die Gabe von Kontrastmittel n�tig, um die diagnostische Information der CT-Untersuchung zu verbessern. Diese Kontrastmittel enthalten einen Jod - Komplex, sind in der Regel gut vertr�glich und werden �ber einen  ven�sen Zugang in der Ellenbeuge oder an der Hand eingespritzt. In vielen F�llen beobachten Patienten ein intensives W�rmegef�hl im gesamten K�rper oder einen ungewohnten Geschmack im Mund.        Dies ist eine normale Reaktion, keine Unvertr�glichkeit! In seltenen F�llen kann es zu einem Austritt des Kontrastmittels aus dem Gef�� kommen (so genanntes Paravasat), was zu einer Schwellung und Gewebsuntergang f�hren kann, in schweren F�llen kann es zu einem Kompartmentsyndrom f�hren.        In extrem seltenen F�llen wurden auch mittelgradige und schwere Unvertr�glichkeitsreaktionen auf die Kontrastmittelgabe mit Komplikationen beobachtet. Sollte solch eine Unvertr�glichkeit bereits bei einer vorangegangenen Untersuchung bei Ihnen aufgetreten sein, so bitten wir Sie, dieses unbedingt anzugeben. Sollte eine unvorhergesehene Komplikation auftreten, so stehen alle n�tigen M�glichkeiten zur sofortigen Behandlung zur Verf�gung. Da das Kontrastmittel wird �ber die Nieren ausgeschieden wird sollten sie nach der Untersuchung viel trinken.        Ist eine Kontrastmittelgabe f�r die Untersuchung notwendig, m�ssen eine Nierenfunktionsst�rung und/oder eine Schilddr�senfunktionsst�rung ausgeschlossen werden. Dazu werden zwei Blutwerte bestimmt, Kreatinin (Nierenwert) und TSH (Schilddr�senwert). Auch m�ssen metforminhaltige Antidiabetika nach der Untersuchung f�r 48 Stunden abgesetzt werden, da sonst Wechselwirkungen mit dem Kontrastmittel auftreten k�nnen!\" size=\"14\">\n" +
                "\t\t</text>        \t\t\t\n" +
                "\t</row>        \t\t\n" +
                "</page>\n" +
                "<page>        \t\t\t\n" +
                "\t<row>        \t\t\t\t\n" +
                "\t\t<text text=\"Ist die Untersuchung schmerzhaft oder gef�hrlich?\" size=\"15\">\n" +
                "\t\t</text>        \t\t\t\n" +
                "\t</row>        \t\t\t\n" +
                "\t<row>        \t\t\t\t\n" +
                "\t\t<text text=\"Die CT-Untersuchung ist ein schmerzloses Verfahren. W�hrend der Untersuchung versp�ren Sie nichts. Die Strahlenbelastung ist deutlich h�her als bei einer konventionellen R�ntgenuntersuchung. Deshalb muss sorgf�ltig gepr�ft werden, ob eine eventuelle Sch�digung durch die R�ntgenstrahlung den Informationsgewinn aufwiegt.  Da Kinder, Jugendliche und ungeborene Kinder besonders strahlenempfindlich sind, wird bei diesem Personenkreis die Indikation zu einer CT-Untersuchung besonders streng �berpr�ft! Teilen Sie dem Arzt deshalb unbedingt mit, ob eine Schwangerschaft besteht, auch wenn Sie nicht sicher sind!\" size=\"14\">\n" +
                "\t\t</text>        \t\t\t\n" +
                "\t</row>        \t\t\t\n" +
                "\t<row>        \t\t\t\t\n" +
                "\t\t<text text=\"Wie lange dauert die Untersuchung?\" size=\"15\">\n" +
                "\t\t</text>        \t\t\t\n" +
                "\t</row>        \t\t\t\n" +
                "\t<row>        \t\t\t\t\n" +
                "\t\t<text text=\"Standarduntersuchungen in der CT dauern meist 5-10 Minuten, wobei Vorbereitungszeiten wie Kontrastmittelzugang legen und Umkleidezeiten nicht mit eingerechnet sind.\" size=\"14\">\n" +
                "\t\t</text>        \t\t\t\n" +
                "\t</row>        \t\t\t\n" +
                "\t<row>        \t\t\t\t\n" +
                "\t\t<text text=\"Wie l�uft die Untersuchung ab?\" size=\"15\">\n" +
                "\t\t</text>        \t\t\t\n" +
                "\t</row>        \t\t\t\n" +
                "\t<row>        \t\t\t\t\n" +
                "\t\t<text text=\"Sie werden vom CT-Personal auf einem Untersuchungstisch gelagert und in den Untersuchungsring (Gantry) gefahren. Sie stehen st�ndig unter �rztlicher Beobachtung und sind in Sprechverbindung mit dem Untersuchungspersonal. W�hrend der Untersuchung sollten sie ganz ruhig und entspannt liegen. Sollte eine D�nndarmuntersuchung notwendig sein, erhalten Sie ca. 30-40 Minuten vor der CT eine Trinkl�sung, was die Abgrenzung des Darmes vom restlichen Gewebe vereinfacht. Bei einer Dickdarmuntersuchung ist es manchmal erforderlich, �ber ein Darmrohr Luft oder Wasser einzubringen. F�r Herzuntersuchungen ist es notwendig, Ihnen ein EKG anzulegen.\" size=\"14\">\n" +
                "\t\t</text>        \t\t\t\n" +
                "\t</row>        \t\t\n" +
                "</page>\n" +
                "<page>        \t\t\t\n" +
                "\t<row>        \t\t\t\t\n" +
                "\t\t<input text=\"Name, Vorname\">\n" +
                "\t\t</input>        \t\t\t\n" +
                "\t</row>        \t\t\t\n" +
                "\t<row>        \t\t\t\t\n" +
                "\t\t<text text=\"Wurde bei Ihnen bereits eine CT/MRT Untersuchung durchgef�hrt? Wenn ja, wo und welches Organ?\" size=\"14\">\n" +
                "\t\t</text>        \t\t\t\t\n" +
                "\t\t<radiobutton text=\"Ja\">\n" +
                "\t\t</radiobutton>        \t\t\t\t\n" +
                "\t\t<radiobutton text=\"Nein\">\n" +
                "\t\t</radiobutton>        \t\t\t\n" +
                "\t</row>\n" +
                "</page>    \n" +
                "<page>      \t\t\t\n" +
                "\t<row>        \t\t\t\t\n" +
                "\t\t<text text=\"Traten bei fr�heren Untersuchungen Kontrastmittel-Reaktionen auf z.B.: Hautausschlag, Erbrechen o.�.?\" size=\"14\">\n" +
                "\t\t</text>        \t\t\t\t\n" +
                "\t\t<radiobutton text=\"Ja\">\n" +
                "\t\t</radiobutton>        \t\t\t\t\n" +
                "\t\t<radiobutton text=\"Nein\">\n" +
                "\t\t</radiobutton>        \t\t\t\n" +
                "\t</row>\n" +
                "</page> \n" +
                "<page>         \t\t\t\n" +
                "\t<row>        \t\t\t\t\n" +
                "\t\t<text text=\"Ist eine Stoffwechsel- (Diabetes) oder Funktionsst�rung (Nieren) bekannt? Nehmen sie Metformin ein?\" size=\"14\">\n" +
                "\t\t</text>        \t\t\t\t\n" +
                "\t\t<radiobutton text=\"Ja\">\n" +
                "\t\t</radiobutton>        \t\t\t\t\n" +
                "\t\t<radiobutton text=\"Nein\">\n" +
                "\t\t</radiobutton>        \t\t\t\n" +
                "\t</row>   \n" +
                "</page> \n" +
                "<page>      \t\t\t\n" +
                "\t<row>        \t\t\t\t\n" +
                "\t\t<text text=\"Besteht eine Schilddr�sen�berfunktion?\" size=\"14\">\n" +
                "\t\t</text>        \t\t\t\t\n" +
                "\t\t<radiobutton text=\"Ja\">\n" +
                "\t\t</radiobutton>        \t\t\t\t\n" +
                "\t\t<radiobutton text=\"Nein\">\n" +
                "\t\t</radiobutton>        \t\t\t\n" +
                "\t</row>\n" +
                "</page>\n" +
                "<page>          \t\t\t\n" +
                "\t<row>        \t\t\t\t\n" +
                "\t\t<text text=\"Besteht bei Ihnen eine Allergie oder �berempfindlichkeit gegen Medikamente (Jod, Penicillin etc), Pflaster, Latex, �rtliche Betr�ubungsmittel etc?\" size=\"14\">\n" +
                "\t\t</text>        \t\t\t\t\n" +
                "\t\t<radiobutton text=\"Ja\">\n" +
                "\t\t</radiobutton>        \t\t\t\t\n" +
                "\t\t<radiobutton text=\"Nein\">\n" +
                "\t\t</radiobutton>        \t\t\t\n" +
                "\t</row> \n" +
                "</page>\n" +
                "<page>         \t\t\t\n" +
                "\t<row>        \t\t\t\t\n" +
                "\t\t<text text=\"Nehmen Sie regelm��ig Medikamente ein (Antidiabetika, Marcumar, Aspirin, Herzmedikamente etc.)?\" size=\"14\">\n" +
                "\t\t</text>        \t\t\t\t\n" +
                "\t\t<radiobutton text=\"Ja\">\n" +
                "\t\t</radiobutton>        \t\t\t\t\n" +
                "\t\t<radiobutton text=\"Nein\">\n" +
                "\t\t</radiobutton>        \t\t\t\n" +
                "\t</row> \n" +
                "</page>\n" +
                "<page>         \t\t\t\n" +
                "\t<row>        \t\t\t\t\n" +
                "\t\t<text text=\"K�nnten Sie schwanger sein oder stillen Sie?\" size=\"14\">\n" +
                "\t\t</text>        \t\t\t\t\n" +
                "\t\t<radiobutton text=\"Ja\">\n" +
                "\t\t</radiobutton>        \t\t\t\t\n" +
                "\t\t<radiobutton text=\"Nein\">\n" +
                "\t\t</radiobutton>        \t\t\t\n" +
                "\t</row>        \t\t\n" +
                "</page> \n" +
                "</page>\n" +
                "\n" +
                "<page>        \t\t\t\n" +
                "\t<row>        \t\t\t\t\n" +
                "\t\t<text text=\"Meine Fragen wurden vollst�ndig und verst�ndlich beantwortet, ich willige hiermit in die geplante Untersuchung ein.\" size=\"14\">\n" +
                "\t\t</text>        \t\t\t\n" +
                "\t</row>        \t\t\t\n" +
                "\t<row>        \t\t\t\t\n" +
                "\t\t<input text=\"Datum/Unterschrift Patient\">\n" +
                "\t\t</input>        \t\t\t\n" +
                "\t</row> \n" +
                "</page>\n" +
                "<page>        \t\t\t\n" +
                "\t<row>        \t\t\t\t\n" +
                "\t\t<text text=\"Ich habe eine Kopie erhalten\" size=\"14\">\n" +
                "\t\t</text>        \t\t\t\t\n" +
                "\t\t<radiobutton>\n" +
                "\t\t</radiobutton>  \n" +
                "</page>\n" +
                "<page>        \t\t\t\t\n" +
                "\t\t<text text=\"Ich w�nsche keine Kopie\" size=\"14\">\n" +
                "\t\t</text>        \t\t\t\t\n" +
                "\t\t<radiobutton>\n" +
                "\t\t</radiobutton>        \t\t\t\n" +
                "\t</row>        \t\t\t\n" +
                "\t<row>\n" +
                "\t\t<input text=\"Unterschrift Patient\">\n" +
                "\t\t</input>        \t\t\t\n" +
                "\t</row>        \t\t\n" +
                "</page>";


        String xml = Constants.globalMetaandForm.toXMLString();
        //set um parameterMap for RestPost to send formula data
        parameterMap.add(new BasicNameValuePair("isFormula", "true"));
        parameterMap.add(new BasicNameValuePair("formula", xml));
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
                Log.d("response", responseCode + "");
            }else if(responseCode == 405){
                parameterMap.clear();
                parameterMap.add(new BasicNameValuePair("isFormula", "true"));
                parameterMap.add(new BasicNameValuePair("formula", xml));
                parameterMap.add(new BasicNameValuePair("macaddress", "34567"));
                parameterMap.add(new BasicNameValuePair("patientID", "3"));
            }
            Toast.makeText(this, ""+ responseCode, Toast.LENGTH_LONG).show();
            Log.d("response", Constants.globalMetaandForm.toXMLString());
            Log.d("response", responseCode + "");
            Log.d("response", RestfulHelper.responseString);
        }


        Toast.makeText(this, "Formular wurde erfolgreich übertragen.", Toast.LENGTH_LONG).show();
        //Delete old Patientdata
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
