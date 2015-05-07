package de.teambluebaer.patientix.xmlParser;

/**
 * Created by Simon on 06.05.2015.
 */
public class Radio implements Element {
    private String radioText;
    private boolean checked;



    void check(){
        checked = !checked;
    }

    public Radio(String radiText){
        this.radioText = radiText;
        this.checked = false;
    }

    @Override
    public void addToView() {

    }
}
