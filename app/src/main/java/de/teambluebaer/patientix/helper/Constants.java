package de.teambluebaer.patientix.helper;

import android.app.Activity;

import java.util.LinkedList;

import de.teambluebaer.patientix.xmlParser.MetaAndForm;

/**
 * This class for the Global variables or the Constant ones.
 */
public class Constants {
    public static boolean ISSEND = false;
    public static String TABLET_ID = "3";
    public static final String PIN = "d404559f602eab6fd602ac7680dacbfaadd13630335e951f097af3900e9de176b6db28512f2e000b9d04fba5133e8b1c6e8df59db3a8ab9d60be4b97cc9e81db";
    public static MetaAndForm GLOBALMETAANDFORM;
    public static boolean ZOOMED = false;
    public static boolean CURRENTPAGEISANSWERED;
    public static Activity CURRENTACTIVITY;
    public static LinkedList<Activity> LISTOFACTIVITIES = new LinkedList<>();
    public static boolean RESIGN = false;
    public static boolean TORESTART = false;
    public static String EMPTYSIGNATURE = "";
    public static int PING = 2000;
    public static final String CONFIGURATION = "serverip = \"141.19.145.237\"\nping= \"1\" -> in Sekunden(Standardwert 1 Sekunde)";
}
