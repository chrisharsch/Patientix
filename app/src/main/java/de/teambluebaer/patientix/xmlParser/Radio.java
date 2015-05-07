package de.teambluebaer.patientix.xmlParser;

/**
 * Created by Simon on 06.05.2015.
 */
public class Radio implements Element {
    private String radioText;
    private boolean checked;



    void check(){
        if(checked){
            checked = false;
        }else{
            checked = true;
        }
    }

    @Override
    public void addToView() {

    }
}
