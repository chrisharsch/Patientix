package de.teambluebaer.patientix.helper;

import android.app.Activity;
import android.util.Log;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * Created by m.jando on 25.11.2014.
 */
public class RestfulHelper extends Activity {


    //URL's
    public final String SERVER_URL = "http://141.19.145.237/";
    private final String POST_LOGIN = "index.php/login";
    private final String POST_LOGOUT = "api/person/logout";
    private final String POST_FORMULA = "index.php/formula";
    private final String POST_FILLED_FORMULA = "index.php/filledformula";

    public String POST_URL;

    //Respsone Output
    public volatile HttpResponse response;
    public static volatile String responseString;
    private static volatile int responseCode;
    public byte[] responseArray;
    private static volatile HttpClient client;
    private static volatile HttpPost post;

    private final boolean DEBUG = false;


    /**
     * Method which executes a request to the server.
     * It must be a new Thread to send the data, otherwise u'll get an NetworkAuthentication-Error
     * The join operation simulates a wait for main-thread, otherwise, the main will run up
     *
     * @param method
     * @param parameterMap
     * @return
     */
    public int executeRequest(final String method, final ArrayList<NameValuePair> parameterMap) {
        Thread networkThread = new Thread() {
            public void run() {
                setURLForRequest(method);
                postDataToServer(method, parameterMap);
            }
        };
        try {
            networkThread.setDaemon(false);
            networkThread.start();
            //Join main-thread with Network-Thread to share Domain-Objects
            // The network-thread get an Timeout of 1 Second, otherwise, the application will hang
            networkThread.join(999);
          /*  try {
                sleep(1000);
            } catch (Exception e) {

            }*/
        } catch (InterruptedException e) {
            return 503;
        }

        return responseCode;
    }

    /**
     * Method which post data to the server
     *
     * @param restMethod Method which to be set
     * @param pM         Parameter-Map for the post request
     */
    public synchronized void postDataToServer(final String restMethod, final ArrayList<NameValuePair> pM) {
        if (DEBUG) {
            Log.d("pm", pM.toArray().toString());
            Log.d("restMethod", restMethod);
        }
        response = null;
        client = new DefaultHttpClient();
        try {
            if (DEBUG) {
                Log.d("POST_URL: ", POST_URL);
            }
            post = new HttpPost(POST_URL);
            post.setEntity(new UrlEncodedFormEntity(pM));
            Log.d("Http", new UrlEncodedFormEntity(pM).toString());
            response = client.execute(post);
            responseArray = IOUtils.toByteArray(response.getEntity().getContent());
            responseString = getStringFromInputStream(responseArray);
            responseCode = response.getStatusLine().getStatusCode();
        } catch (NullPointerException e) {
            e.printStackTrace();
            Log.d("Fehler: ", "1");
            Log.d("Fehler: ", String.valueOf(true));
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            Log.d("Fehler: ", "2");
            Log.d("Fehler: ", String.valueOf(true));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            Log.d("Fehler: ", "3");
            Log.d("Fehler: ", String.valueOf(true));
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("Fehler: ", "4");
            responseCode = 503;
            Log.d("Fehler: ", String.valueOf(true));
        }
    }


    /**
     * Method which set the URL for the RESTful-Request
     *
     * @param restMethod Method which to be set
     */
    private void setURLForRequest(String restMethod) {
        Log.d("RESTMETHOD", restMethod);
        if (restMethod.equals("login")) {
            POST_URL = SERVER_URL + POST_LOGIN;
        } else if (restMethod.equals("formula")) {
            POST_URL = SERVER_URL + POST_FORMULA;
        } else if (restMethod.equals("logout")) {
            POST_URL = SERVER_URL + POST_LOGOUT;
        } else if (restMethod.equals("filledformula")) {
            POST_URL = SERVER_URL + POST_FILLED_FORMULA;
        }
    }


    public String getStringFromInputStream(byte[] array) {
        BufferedReader br = null;
        InputStream is = null;
        StringBuilder sb = new StringBuilder();
        is = new ByteArrayInputStream(array);
        String line;
        try {
            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }

}