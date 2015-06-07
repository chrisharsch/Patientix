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
import java.util.Arrays;

import static java.lang.Thread.sleep;

/**
 * Created by chris on 15.05.2015.
 */
public class RestfulHelper extends Activity {

    //URL's
    //private  final String SERVER_URL = "http://192.168.1.7/";
    //private final String SERVER_URL ="http://192.168.43.168/"
    //private final String SERVER_URL = "http://192.168.178.40/";
    //private final String SERVER_URL = "http://141.19.145.237/";
    private final String SERVER_URL = "http://192.168.1.13/";
    //private final String SERVER_URL = "http://192.168.2.1/";
    //private final String SERVER_URL = "http://141.19.145.225/";
    private final String POST_LOGIN = "index.php/login";
    private final String POST_FORMULA = "index.php/formula";
    private final String POST_GET_TABLET_ID = "index.php/getTabletID";
    private final String POST_FILLED_FORMULA = "index.php/filledformula";
    private String POST_URL;

    //Respones Output
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
     * @param method       Is the End of the URL at which the request will be send
     * @param parameterMap ArrayList with parameters for the request
     * @return int of the responseCode
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
            try {
                sleep(1000);
            } catch (Exception e) {
                Log.d("SleepExeption", e.toString());
            }
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
            Log.d("Fehler: ", String.valueOf(true));
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            Log.d("Fehler: ", String.valueOf(true));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            Log.d("Fehler: ", String.valueOf(true));
        } catch (IOException e) {
            e.printStackTrace();
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
        try {
            Log.d("RESTMETHOD", restMethod);
        }catch(Exception e){
            System.err.print(e.toString());
        }
        if (restMethod.equals("login")) {
            POST_URL = SERVER_URL + POST_LOGIN;
        } else if (restMethod.equals("formula")) {
            POST_URL = SERVER_URL + POST_FORMULA;
        } else if (restMethod.equals("filledformula")) {
            POST_URL = SERVER_URL + POST_FILLED_FORMULA;
        }else if (restMethod.equals("getTabletID")) {
            POST_URL = SERVER_URL + POST_GET_TABLET_ID;
        }
    }

    /**
     * This method builds the response String out of the byte Array
     *
     * @param array Response Array
     * @return Response String
     */

    private String getStringFromInputStream(byte[] array) {
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

    /**
     * Equals method to check if this class equals another
     *
     * @param o Object to check sameness
     * @return true for the same Object false for not the same
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RestfulHelper that = (RestfulHelper) o;

        if (DEBUG != that.DEBUG) return false;
        if (SERVER_URL != null ? !SERVER_URL.equals(that.SERVER_URL) : that.SERVER_URL != null)
            return false;
        if (POST_LOGIN != null ? !POST_LOGIN.equals(that.POST_LOGIN) : that.POST_LOGIN != null)
            return false;
        if (POST_FORMULA != null ? !POST_FORMULA.equals(that.POST_FORMULA) : that.POST_FORMULA != null)
            return false;
        if (POST_FILLED_FORMULA != null ? !POST_FILLED_FORMULA.equals(that.POST_FILLED_FORMULA) : that.POST_FILLED_FORMULA != null)
            return false;
        if (POST_URL != null ? !POST_URL.equals(that.POST_URL) : that.POST_URL != null)
            return false;
        if (response != null ? !response.equals(that.response) : that.response != null)
            return false;
        return Arrays.equals(responseArray, that.responseArray);

    }

    /**
     * Hashcode generator of this class
     *
     * @return int Hashcode
     */
    @Override
    public int hashCode() {
        int result = SERVER_URL != null ? SERVER_URL.hashCode() : 0;
        result = 31 * result + (POST_LOGIN != null ? POST_LOGIN.hashCode() : 0);
        result = 31 * result + (POST_FORMULA != null ? POST_FORMULA.hashCode() : 0);
        result = 31 * result + (POST_FILLED_FORMULA != null ? POST_FILLED_FORMULA.hashCode() : 0);
        result = 31 * result + (POST_URL != null ? POST_URL.hashCode() : 0);
        result = 31 * result + (response != null ? response.hashCode() : 0);
        result = 31 * result + (responseArray != null ? Arrays.hashCode(responseArray) : 0);
        result = 31 * result + (DEBUG ? 1 : 0);
        return result;
    }
}