package de.teambluebaer.patientix.xmlParser;

import java.util.ArrayList;
import java.util.List;

import de.teambluebaer.patientix.helper.Constants;

/**
 * Created by Simon on 06.05.2015.
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
        if(o != null && o instanceof Page){
            Page page = (Page)o;
            return this.rowList.equals(page.getRows());
        }else{
            return false;
        }
    }

    @Override
    public int hashCode() {

        return rowList != null ? rowList.hashCode() : 0;
    }

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


