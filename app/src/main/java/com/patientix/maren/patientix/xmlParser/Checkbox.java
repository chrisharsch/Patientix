package com.patientix.maren.patientix.xmlParser;

/**
 * Created by Simon on 06.05.2015.
 */
public class Checkbox implements Element{
    private String checkboxText;
    private boolean checked;

    void check(){
        if(checked){
            checked = false;
        }else{
            checked = true;
        }
    }

    public Checkbox(String checkboxText){
        this.checkboxText = checkboxText;
        this.checked = false;
    }

    @Override
    public void addToView() {

    }
}
