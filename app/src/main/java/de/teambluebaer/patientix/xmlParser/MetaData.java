package de.teambluebaer.patientix.xmlParser;

/**
 * Created by Simon on 06.05.2015.
 *
 * Singleton, represents patient and exam information and is patr of <code>MetaandForm</code>
 * @see MetaandForm
 */
public class MetaData {

    private String patientID;
    private String patientFirstName;
    private String patientLastName;
    private String patientBithDate;
    private String exameName;


    /**
     * remove reference to old MeatData Objects
     */

    public MetaData() {
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

    public String toXMLString(){
        String xmlString = new String();
        xmlString = xmlString + "<meta>";
        xmlString = xmlString + "<pFirstName>" + patientFirstName + "</pFirstName>";
        xmlString = xmlString + "<pLastName>" + patientLastName + "</pLastName>";
        xmlString = xmlString + "<pDate>" + patientBithDate + "</pDate>";
        xmlString = xmlString + "<name>" + exameName + "</name>";
        xmlString = xmlString + "</meta>";

        return xmlString;
    }


    public String getPatientID() {
        return patientID;
    }

    public String getPatientFirstName() {
        return patientFirstName;
    }

    public String getPatientLastName() {
        return patientLastName;
    }

    public String getPatientBithDate() {
        return patientBithDate;
    }

    public String getExameName() {
        return exameName;
    }
}
