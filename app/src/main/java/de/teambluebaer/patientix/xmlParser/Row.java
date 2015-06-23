package de.teambluebaer.patientix.xmlParser;

import java.util.ArrayList;
import java.util.List;

import de.teambluebaer.patientix.helper.XMLCleaner;



/**
 * Copyright 2015 By Authors
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * Authors:
 * Simon Sauerzapf
 * Maren Dietrich
 * Chris Harsch
 *
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