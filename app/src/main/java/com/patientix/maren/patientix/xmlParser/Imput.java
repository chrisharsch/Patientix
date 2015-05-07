package com.patientix.maren.patientix.xmlParser;

/**
 * Created by Simon on 06.05.2015.
 */
public class Imput implements Element {
    private String showenText;
    private String imputText;

     void getImput(String text){
         imputText = text;
     }

    @Override
    public void addToView() {

    }
}
