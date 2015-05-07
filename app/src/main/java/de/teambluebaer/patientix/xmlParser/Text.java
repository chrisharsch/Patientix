package de.teambluebaer.patientix.xmlParser;

/**
 * Created by Simon on 06.05.2015.
 */
public class Text implements Element {
    private String text;

    public Text(String text){
        this.text = text;
    }

    @Override
    public void addToView() {

    }
}
