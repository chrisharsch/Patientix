package de.teambluebaer.patientix.xmlParser;

import java.util.ArrayList;
import java.util.List;

import de.teambluebaer.patientix.helper.XMLCleaner;

/**
 * Created by Simon on 06.05.2015.
 */
public class Row extends Commentar{
    private List<Element> elementList;

    /**
     * Constructor
     */
    public Row(String patientCommentar, String mtraCommentar,
               String doctorCommentar) {
        elementList = new ArrayList<Element>();
        if(patientCommentar != null && !patientCommentar.isEmpty()){
            this.patientCommentar = patientCommentar;
        } else {
            this.patientCommentar = "";
        }
        if(mtraCommentar != null && !mtraCommentar.isEmpty()){
            this.mtraCommentar = mtraCommentar;
        } else {
            this.mtraCommentar = "";
        }
        if(doctorCommentar != null && !doctorCommentar.isEmpty()){
            this.doctorCommentar = doctorCommentar;
        } else {
            this.doctorCommentar = "";
        }
    }

    public List<Element> getElements() {
        return this.elementList;
    }

    /**
     * add a new <code>Element</code> to this <code>Row</code>
     *
     * @param newElement Element you want to add
     */
    public void addElement(Element newElement) {
        elementList.add(newElement);
    }

    public String toXMLString(){
        String xmlString = new String();
        xmlString = xmlString + "<row";
        if(!patientCommentar.isEmpty()){
            xmlString = xmlString + " comment=\"" + XMLCleaner.cleanStringForXML(this.patientCommentar) +"\"";
        }
        if(!mtraCommentar.isEmpty()){
            xmlString = xmlString + " mtraComment=\"" + this.mtraCommentar + "\"";
        }
        if(!doctorCommentar.isEmpty()){
            xmlString = xmlString + " docComment=\"" + this.doctorCommentar + "\"";
        }
        xmlString = xmlString + ">";
        for(Element element : elementList){
            xmlString = xmlString + element.toXMLString();
        }
        xmlString = xmlString + "</row>";

        return xmlString;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Row row = (Row) o;

        return !(elementList != null ? !elementList.equals(row.elementList) : row.elementList != null);

    }

    @Override
    public int hashCode() {
        return elementList != null ? elementList.hashCode() : 0;
    }
}