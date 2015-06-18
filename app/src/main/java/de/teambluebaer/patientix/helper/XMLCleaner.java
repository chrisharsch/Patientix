package de.teambluebaer.patientix.helper;

/**
 * Created by Simon on 18.06.2015.
 */
public class XMLCleaner {

    public static String cleanStringForXML(String toClean){
        String cleandString = "";

        for (char stringChar : toClean.toCharArray()){
            for(char testChar : Constants.ALLOWEDCHARS ){
                if(stringChar == testChar){
                    cleandString = cleandString + stringChar;
                }
            }
        }

        return cleandString;
    }
}
