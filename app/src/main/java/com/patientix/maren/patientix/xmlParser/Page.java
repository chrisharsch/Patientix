package com.patientix.maren.patientix.xmlParser;

import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Simon on 06.05.2015.
 */
public class Page {
    private List<Row> rowList;
    private String patientCommentar;
    private String mtraCommentar;
    private String doctorCommentar;

    public Page(){
        rowList = new ArrayList<Row>();

    }

   public void createNextPageView(View pageView){


   }

   public void createPriviosPageView(View pageView){


   }

   public void addNewRow(Row newRow) {
       rowList.add(newRow);
   }

   public void addPatCommant(){

   }

   public void addMTRACommant(){

   }

   public void addDocCommant(){

   }



}
