package de.teambluebaer.patientix.xmlParser;

import android.content.Context;
import android.widget.CheckBox;
import android.widget.LinearLayout;

/**
 * Created by Simon on 06.05.2015.
 *
 * Represents a Checkbox that could insert into a <code>Row</code>
 * @see Row
 * @see Element
 *
 */
public class Checkbox implements Element{
    private String checkboxText;
    private boolean checked;

    /**
     * changes the state of the boolean checked
     */
    void check(){
        checked = !checked;
    }

    /**
     * Constructor
     * @param checkboxText represents the showen Answer to this Checkbox
     */
    public Checkbox(String checkboxText){
        this.checkboxText = checkboxText;
        this.checked = false;
    }

    @Override
    public void addToView(Context context, LinearLayout layout) {
        CheckBox checkBox = new CheckBox(context);
        checkBox.setText(checkboxText);
        checkBox.setChecked(checked);
        layout.addView(checkBox);
    }
}
