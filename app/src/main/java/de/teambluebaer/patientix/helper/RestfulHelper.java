package de.teambluebaer.patientix.helper;

import android.app.Activity;
import android.util.Log;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import static java.lang.Thread.sleep;

/**
 * Created by chris on 15.05.2015.
 */
public class RestfulHelper extends Activity {


    private volatile String SERVER_URL = "https://" + Constants.SERVER_URL + "/";
    private final String POST_LOGIN = "MTRAWebApp/server/index.php/login";
    private final String POST_FORMULA = "MTRAWebApp/server/index.php/formula";
    private final String POST_GET_TABLET_ID = "MTRAWebApp/server/index.php/getTabletID";
    private final String POST_FILLED_FORMULA = "MTRAWebApp/server/index.php/filledformula";
    private final String POST_RESIGNED_FORMULA = "MTRAWebApp/server/index.php/resignFormula";
    private String POST_URL;

    //Respones Output
    public volatile HttpResponse response;
    public static volatile String responseString;
    private static volatile int responseCode;
    public volatile byte[] responseArray;
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
            networkThread.join(Constants.PING - 1);
            try {
                sleep(Constants.PING);
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
        client = createHttpClient();

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
            Log.d("Fehler", e.toString());

        } catch (ClientProtocolException e) {
            e.printStackTrace();
            Log.d("Fehler: ", String.valueOf(true));
            Log.d("Fehler", e.toString());

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            Log.d("Fehler: ", String.valueOf(true));
            Log.d("Fehler", e.toString());

        } catch (IOException e) {
            e.printStackTrace();
            responseCode = 503;
            Log.d("Fehler: ", String.valueOf(true));
            Log.d("Fehler", e.toString());

        }
    }


    /**
     * Method which set the URL for the RESTful-Request
     *
     * @param restMethod Method which to be set
     */
    private synchronized void setURLForRequest(String restMethod) {
        try {
            Log.d("RESTMETHOD", restMethod);
        } catch (Exception e) {
            System.err.print(e.toString());
        }
        if (restMethod.equals("login")) {
            POST_URL = SERVER_URL + POST_LOGIN;
        } else if (restMethod.equals("formula")) {
            POST_URL = SERVER_URL + POST_FORMULA;
        } else if (restMethod.equals("filledformula")) {
            POST_URL = SERVER_URL + POST_FILLED_FORMULA;
        } else if (restMethod.equals("getTabletID")) {
            POST_URL = SERVER_URL + POST_GET_TABLET_ID;
        } else if (restMethod.equals("resignFormula")) {
            POST_URL = SERVER_URL + POST_RESIGNED_FORMULA;
        }
    }

    /**
     * This method builds the response String out of the byte Array
     *
     * @param array Response Array
     * @return Response String
     */

    private synchronized String getStringFromInputStream(byte[] array) {
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

    public HttpClient createHttpClient() {
        try {
            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            trustStore.load(null, null);

            MySSLSocketFactory sf = new MySSLSocketFactory(trustStore);
            sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

            HttpParams params = new BasicHttpParams();
            HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
            HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);

            SchemeRegistry registry = new SchemeRegistry();
            registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
            registry.register(new Scheme("https", sf, 443));

            ClientConnectionManager ccm = new ThreadSafeClientConnManager(params, registry);

            return new DefaultHttpClient(ccm, params);
        } catch (Exception e) {
            return new DefaultHttpClient();
        }
    }



    public class MySSLSocketFactory extends SSLSocketFactory {
        SSLContext sslContext = SSLContext.getInstance("TLS");

        public MySSLSocketFactory(KeyStore truststore) throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException, UnrecoverableKeyException {
            super(truststore);

            TrustManager tm = new X509TrustManager() {
                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            };

            sslContext.init(null, new TrustManager[] { tm }, null);
        }

        @Override
        public Socket createSocket(Socket socket, String host, int port, boolean autoClose) throws IOException, UnknownHostException {
            return sslContext.getSocketFactory().createSocket(socket, host, port, autoClose);
        }

        @Override
        public Socket createSocket() throws IOException {
            return sslContext.getSocketFactory().createSocket();
        }
    }
}