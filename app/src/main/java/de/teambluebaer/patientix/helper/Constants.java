package de.teambluebaer.patientix.helper;

import de.teambluebaer.patientix.xmlParser.MetaandForm;

/**
 *This class is used to represent the columns for the overview
 * activity. There are just 2 columns, but you can add more columns
 * if you want. If you want to add more columns you also have to
 * prepare the code of OverviewActivity.java and activity_overview.xml
 */
public class Constants {

    public static final String FIRST_COLUMN = "First";
    public static final String SECOND_COLUMN = "Second";
    public static final String TABLET_ID= "3";
    public static final String PIN="d404559f602eab6fd602ac7680dacbfaadd13630335e951f097af3900e9de176b6db28512f2e000b9d04fba5133e8b1c6e8df59db3a8ab9d60be4b97cc9e81db";
    public static String PATIENTID = "";
    public static MetaandForm globalMetaandForm;
    public static boolean zoomed = false;
    public static boolean currendPageIsanswert;

}
