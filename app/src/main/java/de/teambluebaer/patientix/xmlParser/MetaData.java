package de.teambluebaer.patientix.xmlParser;

/**
 * Created by Simon on 06.05.2015.
 *
 * Singleton, represents patient and exam information and is patr of <code>MetaAndForm</code>
 * @see MetaAndForm
 */
public class MetaData {

    private String patientID;
    private String patientFirstName;
    private String patientLastName;
    private String patientBithDate;
    private String exameName;
    private String pExamID;


    /**
     * remove reference to old MeatData Objects
     */

    public MetaData() {
        setexameName("");
        setPatientBithDate("");
        setPatientFirstName("");
        setPatientID("");
        setPatientLastName("");
        setpExamID("");
    }


    public void setPatientID(String patientID){
        this.patientID = patientID;
    }

    public void setPatientFirstName(String patientFirstName){
        if(patientFirstName != null && !patientFirstName.isEmpty()){
            this.patientFirstName = patientFirstName;
        } else {
            this.patientFirstName = "";
        }
    }

    public void setPatientLastName(String patientLastName){
        if(patientLastName != null && !patientLastName.isEmpty()){
            this.patientLastName = patientLastName;
        } else {
            this.patientLastName = "";
        }
    }

    public void setPatientBithDate(String patientBithDate){
        if(patientBithDate != null && !patientBithDate.isEmpty()){
            this.patientBithDate = patientBithDate;
        } else {
            this.patientBithDate = "Unbekannt";
        }
    }

    public void setpExamID(String pExamID) {

        if(pExamID != null && !pExamID.isEmpty()){
            this.pExamID = pExamID;
        }else{
            this.pExamID = "";
        }
    }

    public void setexameName(String exameName){
        if(exameName != null && !exameName.isEmpty()){
            this.exameName = exameName;
        }else{
            this.exameName = "";
        }
    }

    public String toXMLString(){
        String xmlString = new String();
        xmlString = xmlString + "<meta>";
        xmlString = xmlString + "<pID>" + patientID + "</pID>";
        xmlString = xmlString + "<pExamID>" + pExamID + "</pExamID>";
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

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MetaData metaData = (MetaData) o;

        if (patientID != null ? !patientID.equals(metaData.patientID) : metaData.patientID != null)
            return false;
        if (patientFirstName != null ? !patientFirstName.equals(metaData.patientFirstName) : metaData.patientFirstName != null)
            return false;
        if (patientLastName != null ? !patientLastName.equals(metaData.patientLastName) : metaData.patientLastName != null)
            return false;
        if (patientBithDate != null ? !patientBithDate.equals(metaData.patientBithDate) : metaData.patientBithDate != null)
            return false;
        return !(exameName != null ? !exameName.equals(metaData.exameName) : metaData.exameName != null);

    }

    @Override
    public int hashCode() {
        int result = patientID != null ? patientID.hashCode() : 0;
        result = 31 * result + (patientFirstName != null ? patientFirstName.hashCode() : 0);
        result = 31 * result + (patientLastName != null ? patientLastName.hashCode() : 0);
        result = 31 * result + (patientBithDate != null ? patientBithDate.hashCode() : 0);
        result = 31 * result + (exameName != null ? exameName.hashCode() : 0);
        return result;
    }
}