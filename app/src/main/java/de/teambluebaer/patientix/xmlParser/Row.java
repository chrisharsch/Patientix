package de.teambluebaer.patientix.xmlParser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Simon on 06.05.2015.
 */
public class Row {
    private List<Element> elementList;

    /**
     * Constructor
     */
    public Row() {
        elementList = new ArrayList<Element>();
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
        xmlString = xmlString + "<row>";
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