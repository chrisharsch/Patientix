package de.teambluebaer.patientix.xmlParser;

import java.util.ArrayList;
import java.util.List;

import de.teambluebaer.patientix.helper.Constants;



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
 * represents a Page, that is Part of a <code>Form</code> and holds <code>Rows</code>
 * @see Form
 * @see Row
 */
public class Page {
    private List<Row> rowList;


    /**
     * constructor
     */
    public Page(){

        rowList = new ArrayList<Row>();

    }

    /**
     * give a List of all conteining Row back
     */
     public List<Row> getRows(){
         return this.rowList;
     }

    /**
     * adds an additional <code>Row</code> to this <code>Page</code>
     * @see Row
     * @param newRow the <code>Row</code> you want to add
     */
     public void addNewRow(Row newRow) {
           rowList.add(newRow);
     }

    public String toXMLString(){
        String xmlString = new String();
        xmlString = xmlString + "<page>";
        for(Row row : rowList){
            xmlString = xmlString + row.toXMLString();
        }
        xmlString = xmlString + "</page>";

        return xmlString;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Page page = (Page) o;

        return rowList.equals(page.rowList);

    }

    @Override
    public int hashCode() {

        return rowList != null ? rowList.hashCode() : 0;
    }

    /**
     * is true for every Page that that contains a <code>Checkbox</code>, <code>Radio</code> und <code>Input</code>
     * @return is true for Pages that should be showen in the Overview Activite
     */
    public boolean isRelevant(){
        boolean relevant = false;
        for(Row row:rowList){
            for(Element element:row.getElements()){
                if(element instanceof Editable){
                    relevant = true;
                }else if(element instanceof Image && Constants.RESIGN){
                    relevant = true;
                }
                if(relevant){
                    break;
                }
            }
        if(relevant){
            break;
        }
    }
    return relevant;
    }
}


