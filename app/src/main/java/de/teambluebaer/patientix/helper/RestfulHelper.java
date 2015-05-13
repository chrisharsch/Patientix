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
import java.util.Date;

/**
 * Created by m.jando on 25.11.2014.
 */
public class RestfulHelper extends Activity {


    //URL's
    //public final String SERVER_URL = "http://192.168.2.108:8080/FocusedHealth/";
    //public final String SERVER_URL = "http://141.19.140.22/uip2/FocusedHealth/";
    //public final String SERVER_URL = "http://anakin.informatik.hs-mannheim.de/uip2/FocusedHealth/";
    // public final String SERVER_URL = "http://anakin.informatik.hs-mannheim.de/uip2/FocusedHealth/";
    //public final String SERVER_URL = "http://141.19.142.46:8080/FocusedHealth/";
    //public final String SERVER_URL = "http://141.19.144.212:8080/FocusedHealth/";
    public final String SERVER_URL = "http://141.19.145.237:8080/FocusedHealth/";
    private final String POST_LOGIN = "api/person/login";
  /*  private final String POST_LOGOUT = "api/person/logout";
    private final String POST_REGISTER = "api/person/register";
    private final String DELETE_PROVIDER = "api/person/deleteProvider";
    private final String POST_DELETEACCOUNT = "api/person/deleteAccount";
    private final String POST_MISSING_PASSWORD = "api/person/forgetPassword";
    private final String POST_OAUTH = "api/person/oAuthProcess";
    private final String POST_CHANGE_MAIL = "api/person/changeMail";
    private final String POST_CHANGE_PASSWORD = "api/person/changePassword";
    private final String POST_GETDATA = "api/fitbit/steps";
    private final String POST_CHECK_PROVIDER = "api/person/checkProviders";
    private final String POST_WATER = "api/fitbit/water";
    private final String POST_WATER_GOAL = "api/fitbit/waterGoal";
    private final String POST_FOOD = "api/fitbit/food";
    private final String POST_BODY_FAT_GOAL = "api/fitbit/fatGoal";
    private final String POST_DAILY_GOAL = "api/fitbit/dailyGoal";
    private final String POST_WEIGHT_GOAL = "api/fitbit/weightGoal";
    private final String POST_FOOD_GOAL = "api/fitbit/foodGoal";
    private final String POST_BODY = "api/fitbit/body";
    private final String POST_BODY_WEIGHT = "api/fitbit/weight";
    private final String POST_BODY_FAT = "api/fitbit/fat";
    private final String POST_WEEKLY_GOAL = "api/fitbit/weeklyGoal";
    private final String POST_FITBIT_ACTIVITIES = "api/fitbit/activities";
    private final String POST_WITHINGS_ACTIVITIES = "api/withings/activity";
    private final String POST_FITBIT_SLEEP = "api/fitbit/sleep";
    private final String POST_WITHINGS_SLEEP = "api/withings/sleep";
    private final String POST_MEDISANA_SLEEP = "api/medisana/sleep";
    private final String POST_ACTIVITIES = "api/fitbit/activities";
    private final String POST_SLEEP = "api/fitbit/sleep";
    private final String POST_CALORIES = "api/fitbit/calories";
    private final String POST_DISTANCE = "api/fitbit/distance";
    private final String POST_SLEEP_GOAL = "api/medisana/sleepGoal";
    private final String POST_WIHTHINGS_ACTIVITIES = "api/withings/activity";
    private final String POST_FITBIT_BODY = "api/fitbit/body";
    private final String POST_WITHINGS_BODY = "api/withings/body";*/

    public String POST_URL;


  //  SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    //Respsone Output
    public volatile HttpResponse response;
    public static volatile String responseString;
    private static volatile int responseCode;
    public byte[] responseArray;
    private static volatile HttpClient client;
    private static volatile HttpPost post;
    public String startDate;
    public String endDate;

    private final boolean DEBUG = false;
    Date calcDate = new Date();
    private ArrayList<NameValuePair> parameterMap = new ArrayList<NameValuePair>();


    private boolean check;
    public String mobileLoginHash;
    public String oAuthUrl;

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
            // The network-thread get an Timeout of 2 Seconds, otherwise, the application will hang
            networkThread.join(5000);
        } catch (InterruptedException e) {
            // ignore
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
            checkAndSetSpecialReturns(restMethod);
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
            Log.d("Fehler: ", String.valueOf(true));
        }
    }

    /**
     * //TODO
     *
     * @param restMethod
     */
    private void checkAndSetSpecialReturns(String restMethod) {
        Log.d("OAUTH", restMethod);
        if (restMethod.equals("login")) {
            mobileLoginHash = response.getFirstHeader("LoginHash").getValue();
        } else if (restMethod.equals("oAuthProcess")) {
            oAuthUrl = response.getFirstHeader("url").getValue();
            Log.d("Test", oAuthUrl);
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
 /*       } else if (restMethod.equals("deleteAccount")) {
            POST_URL = SERVER_URL + POST_DELETEACCOUNT;
        } else if (restMethod.equals("forgetPassword")) {
            POST_URL = SERVER_URL + POST_MISSING_PASSWORD;
        } else if (restMethod.equals("register")) {
            POST_URL = SERVER_URL + POST_REGISTER;
        } else if (restMethod.equals("oAuthProcess")) {
            POST_URL = SERVER_URL + POST_OAUTH;
        } else if (restMethod.equals("changeMail")) {
            POST_URL = SERVER_URL + POST_CHANGE_MAIL;
        } else if (restMethod.equals("logout")) {
            POST_URL = SERVER_URL + POST_LOGOUT;
        } else if (restMethod.equals("steps")) {
            POST_URL = SERVER_URL + POST_GETDATA;
        } else if (restMethod.equals("changePassword")) {
            POST_URL = SERVER_URL + POST_CHANGE_PASSWORD;
        } else if (restMethod.equals("checkProviders")) {
            POST_URL = SERVER_URL + POST_CHECK_PROVIDER;
        } else if (restMethod.equals("waterGoal")) {
            POST_URL = SERVER_URL + POST_WATER_GOAL;
        } else if (restMethod.equals(("water"))) {
            POST_URL = SERVER_URL + POST_WATER;
        } else if (restMethod.equals(("food"))) {
            POST_URL = SERVER_URL + POST_FOOD;
        } else if (restMethod.equals("bodyFatGoal")) {
            POST_URL = SERVER_URL + POST_BODY_FAT_GOAL;
        } else if (restMethod.equals("dailyGoal")) {
            POST_URL = SERVER_URL + POST_DAILY_GOAL;
        } else if (restMethod.equals("weightGoal")) {
            POST_URL = SERVER_URL + POST_WEIGHT_GOAL;
        } else if (restMethod.equals("foodGoal")) {
            POST_URL = SERVER_URL + POST_FOOD_GOAL;
        } else if (restMethod.equals("fitbitBody")) {
            POST_URL = SERVER_URL + POST_FITBIT_BODY;
        } else if (restMethod.equals("withingsBody")) {
            POST_URL = SERVER_URL + POST_WITHINGS_BODY;
        } else if (restMethod.equals("body")) {
            POST_URL = SERVER_URL + POST_BODY;
        } else if (restMethod.equals("weeklyGoal")) {
            POST_URL = SERVER_URL + POST_WEEKLY_GOAL;
        } else if (restMethod.equals("weight")) {
            POST_URL = SERVER_URL + POST_BODY_WEIGHT;
        } else if (restMethod.equals("fat")) {
            POST_URL = SERVER_URL + POST_BODY_FAT;
        } else if (restMethod.equals("fitbitActivities")) {
            POST_URL = SERVER_URL + POST_FITBIT_ACTIVITIES;
        } else if (restMethod.equals("withingsActivities")) {
            POST_URL = SERVER_URL + POST_WITHINGS_ACTIVITIES;
        } else if (restMethod.equals("fitbitSleep")) {
            POST_URL = SERVER_URL + POST_FITBIT_SLEEP;
        } else if (restMethod.equals("withingsSleep")) {
            POST_URL = SERVER_URL + POST_WITHINGS_SLEEP;
        } else if (restMethod.equals("activities")) {
            POST_URL = SERVER_URL + POST_ACTIVITIES;
        } else if (restMethod.equals("sleep")) {
            POST_URL = SERVER_URL + POST_SLEEP;
        } else if (restMethod.equals("sleepGoal")) {
            POST_URL = SERVER_URL + POST_SLEEP_GOAL;
        } else if (restMethod.equals("activity")) {
            POST_URL = SERVER_URL + POST_WIHTHINGS_ACTIVITIES;
        } else if (restMethod.equals("calories")) {
            POST_URL = SERVER_URL + POST_CALORIES;
        } else if (restMethod.equals("distance")) {
            POST_URL = SERVER_URL + POST_DISTANCE;
        } else if (restMethod.equals("medisanaSleep")) {
            POST_URL = SERVER_URL + POST_MEDISANA_SLEEP;
        } else if (restMethod.equals("deleteProvider")) {
            POST_URL = SERVER_URL + DELETE_PROVIDER;
            */
        }
    }

    /**
     * Method, which checks if the input password has an special character
     *
     * @param s The String to check
     * @return True or false, whether the password has an special or not
     */
  /*  public boolean passwordHasSpecialCharacter(String s) {
        return !(s.matches("([A-Za-z]|[0-9])+"));
    }*/

    /**
     * @param parameterMap
     * @return
     */
 /*   public int getDataFromServer(final ArrayList<NameValuePair> parameterMap) {
        responseCode = executeRequest("steps", parameterMap);
        return responseCode;
    }
*/

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

    /**
     * @return

    public String getOneWeekFromNow() {
        calcDate.setTime(new Date().getTime() - 604800000);
        return formatter.format(calcDate);
    }
*/
    /**
     * @return

    public String getOneMonthFromNow() {
        calcDate.setTime(new Date().getTime() - 2419200000l);
        return formatter.format(calcDate);
    }
    */
    /**
     * @return

    public String getOneYearFromNow() {
        calcDate.setTime(new Date().getTime() - 31449600000l);
        return formatter.format(calcDate);
    }
    */
    /**
     * @return

    public String getToday() {
        return formatter.format(new Date());
    }
    */
    /*
    public void calculateDates(String buttonText) {
        endDate = getToday();
        Log.i("Test1", String.valueOf(startDate));
        if (buttonText.equals("Year")) {
            startDate = getOneYearFromNow();
            Log.i("Test2", String.valueOf(startDate));
        } else if (buttonText.equals("Month")) {
            startDate = getOneMonthFromNow();
            Log.i("Test3", String.valueOf(startDate));
        } else if (buttonText.equals("Week")) {
            startDate = getOneWeekFromNow();
            Log.i("Test4", String.valueOf(startDate));
        } else {
            startDate = getToday();
            Log.i("Test1", String.valueOf(startDate));
        }
    }*/
}