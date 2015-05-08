package de.teambluebaer.patientix.xmlParser;

/**
 * Created by Simon on 06.05.2015.
 *
 * Represents a radiobutton that could insert into a <code>Row</code>
 * @see Row
 * @see Element
 */
public class Radio implements Element {
    private String radioText;
    private boolean checked;

    /**
     * changes the state of the boolean checked
     */
    void check(){
        checked = !checked;
    }

    /**
     * constructor
     * @param radioText represents the showen Answer to this Radio Button
     */
    public Radio(String radioText){
        this.radioText = radioText;
        this.checked = false;
    }

    @Override
    public void addToView() {

    }
}
