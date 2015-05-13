package de.teambluebaer.patientix.xmlParser;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

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
    private RadioGroup radiogroup;

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
    public Radio(String radioText, RadioGroup radiogroup){
        this.radioText = radioText;
        this.checked = false;
        this.radiogroup = radiogroup;
    }

    @Override
    public void addToView(Context context, LinearLayout layout) {
        RadioButton radio = new RadioButton(context);
        radio.setChecked(checked);
        radiogroup.addView(radio);
        layout.addView(radio);
    }
}
