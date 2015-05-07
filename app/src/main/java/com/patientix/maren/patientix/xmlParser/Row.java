package com.patientix.maren.patientix.xmlParser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Simon on 06.05.2015.
 */
public class Row {
    private List<Element> elementList;

    public Row() {
        elementList = new ArrayList<Element>();
    }

    public void addElement(Element newElement){
        elementList.add(newElement);
    }
}
