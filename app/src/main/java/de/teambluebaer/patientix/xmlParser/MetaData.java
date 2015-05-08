package de.teambluebaer.patientix.xmlParser;

/**
 * Created by Simon on 06.05.2015.
 */
public class MetaData {
    private static MetaData ourInstance = new MetaData();

    public static MetaData getInstance() {
        return ourInstance;
    }

    private String patientID;
    private String patientFirstName;
    private String patientLastName;
    private String exameID;
    private String exameName;

    public void refresh(){
        ourInstance = new MetaData();
    }

    private MetaData() {
    }

    public void setMetaData(String patID, String patFN, String patLN, String exID, String exName){
        ourInstance.patientID = patID;
        ourInstance.patientFirstName = patFN;
        ourInstance.patientLastName = patLN;
        ourInstance.exameID = exID;
        ourInstance.exameName = exName;
    }

    }
