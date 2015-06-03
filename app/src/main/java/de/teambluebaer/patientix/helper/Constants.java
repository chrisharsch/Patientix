package de.teambluebaer.patientix.helper;

import android.app.Activity;

import java.util.LinkedList;

import de.teambluebaer.patientix.xmlParser.MetaandForm;

/**
 * This class for the Global variables or the Constant ones.
 */
public class Constants {
    public static boolean ISSEND = false;
    public static final String FIRST_COLUMN = "First";
    public static final String SECOND_COLUMN = "Second";
    public static final String TABLET_ID = "5";
    public static final String PIN = "d404559f602eab6fd602ac7680dacbfaadd13630335e951f097af3900e9de176b6db28512f2e000b9d04fba5133e8b1c6e8df59db3a8ab9d60be4b97cc9e81db";
    public static String PATIENTID = "";
    public static MetaandForm globalMetaandForm;
    public static boolean zoomed = false;
    public static boolean currendPageIsanswert;
    public static Activity CURRENTACTIVITY;
    public static LinkedList<Activity> LISTOFACTIVITIES = new LinkedList<>();
    public static boolean resign = false;
    public static boolean TORESTART = false;
}
