package de.teambluebaer.patientix.helper;

import android.app.Activity;

import java.util.LinkedList;

import de.teambluebaer.patientix.xmlParser.MetaAndForm;

/**
 * This class for the Global variables or the Constant ones.
 */
public class Constants {
    public static volatile String SERVER_URL = "141.19.145.237";
    public static boolean ISSEND = false;
    public static volatile String TABLET_ID = "-1";
    public static String PIN = "c6001d5b2ac3df314204a8f9d7a00e1503c9aba0fd4538645de4bf4cc7e2555cfe9ff9d0236bf327ed3e907849a98df4d330c4bea551017d465b4c1d9b80bcb0";
    public static MetaAndForm GLOBALMETAANDFORM;
    public static boolean ZOOMED = false;
    public static boolean CURRENTPAGEISANSWERED;
    public static Activity CURRENTACTIVITY;
    public static LinkedList<Activity> LISTOFACTIVITIES = new LinkedList<>();
    public static boolean RESIGN = false;
    public static boolean TORESTART = false;
    public static String EMPTYSIGNATURE = "";
    public static int PING = 2000;
    public static final String CONFIGURATION = "serverip = \"141.19.145.237\"" +
            "\nping= \"1\" -> in Sekunden(Standardwert 1 Sekunde)" +
            "\nPIN= \"0000\" -> Standardwert 0000";
    private static final String ALLOWDSTRING = "abcdefghijklmnopqrstuvwxyzäöüßABCDEFGHIJKLMNOPQRSTUVWXYZÄÖÜ1234567890.,? ";
    public static final char[] ALLOWEDCHARS = ALLOWDSTRING.toCharArray();


}


