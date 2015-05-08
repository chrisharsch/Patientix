package de.teambluebaer.patientix.xmlParser;

/**
 * Created by Simon on 06.05.2015.
 *
 * Singleton, represents patient and exam information and is patr of <code>MetaAndForm</code>
 * @see MetaAndForm
 */
public class MetaData {
    private static MetaData ourInstance = new MetaData();

    /**
     * give access to the Singleton Instance
     * @return a Instance of MetaData
     */
    public static MetaData getInstance() {
        return ourInstance;
    }

    private String patientID;
    private String patientFirstName;
    private String patientLastName;
    private String exameID;
    private String exameName;


    /**
     * remove reference to old MeatData Objects
     */
    public void refresh(){
        ourInstance = new MetaData();
    }

    /**
     * private Constructor
     */
    private MetaData() {
    }

    /**
     * set the variables of the MetaData Singleton
     * @param patID represents the unique patient identification number
     * @param patFN represents the firstname of a patient
     * @param patLN represents the lastname of a patient
     * @param exID represents the unique Exam identification number
     * @param exName represents the name of the Exam
     */
    public void setMetaData(String patID, String patFN, String patLN, String exID, String exName){
        ourInstance.patientID = patID;
        ourInstance.patientFirstName = patFN;
        ourInstance.patientLastName = patLN;
        ourInstance.exameID = exID;
        ourInstance.exameName = exName;
    }

    }
