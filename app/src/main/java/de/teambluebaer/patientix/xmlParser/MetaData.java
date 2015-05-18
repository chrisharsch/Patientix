package de.teambluebaer.patientix.xmlParser;

/**
 * Created by Simon on 06.05.2015.
 *
 * Singleton, represents patient and exam information and is patr of <code>MetaandForm</code>
 * @see MetaandForm
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
    private String patientBithDate;
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
        this.setPatientBithDate("Unbekannt");
    }


    public void setPatientID(String patientID){
        this.patientID = patientID;
    }

    public void setPatientFirstName(String patientFirstName){
        this.patientFirstName = patientFirstName;
    }

    public void setPatientLastName(String patientLastName){
        this.patientLastName = patientLastName;
    }

    public void setPatientBithDate(String patientBithDate){
        this.patientBithDate = patientBithDate;
    }

    public void setexameName(String exameName){
        this.exameName = exameName;
    }


    }
