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

    /**
     * add a new <code>Element</code> to this <code>Row</code>
     * @param newElement Element you want to add
     */
    public void addElement(Element newElement){
        elementList.add(newElement);
    }
}
