package de.teambluebaer.patientix.xmlParser;

/**
 * Created by Simon on 06.05.2015.
 *
 * Represents an Input Field that could insert into a <code>Row</code>
 * @see Row
 * @see Element
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
