package de.teambluebaer.patientix.xmlParser;

/**
 * Created by Simon on 06.05.2015.
 *
 * Represents an Input that could insert into a Page
 *
 */
public class Input implements Element {
    private String inputText;

    /**
     * Constructor
     */
    public Input(){
        inputText = "";
    }

    @Override
    public void addToView() {

    }
}
