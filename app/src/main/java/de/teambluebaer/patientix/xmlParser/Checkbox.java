package de.teambluebaer.patientix.xmlParser;

/**
 * Created by Simon on 06.05.2015.
 */
public class Checkbox implements Element{
    private String checkboxText;
    private boolean checked;

    void check(){
        checked = !checked;
    }

    public Checkbox(String checkboxText){
        this.checkboxText = checkboxText;
        this.checked = false;
    }

    @Override
    public void addToView() {

    }
}
