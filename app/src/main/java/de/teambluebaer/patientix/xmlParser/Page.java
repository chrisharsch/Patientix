package de.teambluebaer.patientix.xmlParser;

import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Simon on 06.05.2015.
 *
 * represents a Page, that is Part of a <code>Form</code> and holds <code>Rows</code>
 * @see Form
 * @see Row
 */
public class Page {
    private List<Row> rowList;
    private String patientCommentar;
    private String mtraCommentar;
    private String doctorCommentar;

    /**
     * constructor
     */
    public Page(){
        rowList = new ArrayList<Row>();

    }

    /**
     * creat the View for the next shown Page
     * @param pageView represents the View the new content get add in
     */
     public void createNextPageView(View pageView){

     }
    /**
     * creat the View for the previous shown Page
     * @param pageView represents the View the new content get add in
     */
     public void createPriviosPageView(View pageView){

     }

    /**
     * adds an additional <code>Row</code> to this <code>Page</code>
     * @see Row
     * @param newRow the <code>Row</code> you want to add
     */
     public void addNewRow(Row newRow) {
           rowList.add(newRow);
     }

    /**
     * Insert a patient comment field to this Page
     */
     public void addPatCommant(){

     }
     /**
     * Insert a MTRA comment field to this Page
     */
     public void addMTRACommant(){

     }
     /**
     * Insert a Doctor comment field to this Page
     */
     public void addDocCommant(){

     }



}
