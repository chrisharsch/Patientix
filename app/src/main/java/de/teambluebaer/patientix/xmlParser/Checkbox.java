package de.teambluebaer.patientix.xmlParser;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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
    private String patientCommentar;
    private String mtraCommentar;
    private String doctorCommentar;


    /**
     * Constructor
     * @param checkboxText represents the showen Answer to this Checkbox
     */
    public Checkbox(String checkboxText){
        this.checkboxText = checkboxText;
        this.checked = false;
    }

    @Override
    public void addToView(Context context, final LinearLayout layout) {
        CheckBox checkBox = new CheckBox(context);
        checkBox.setText(checkboxText);
        checkBox.setChecked(checked);
        layout.addView(checkBox);

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                checked = isChecked;
            }
        });

    }
    /**
     * Insert a patient comment field to this Checkbox
     */
    public void addPatCommant(){

    }
    /**
     * Insert a MTRA comment field to this Checkbox
     */
    public void addMTRACommant(){

    }
    /**
     * Insert a Doctor comment field to this Checkbox
     */
    public void addDocCommant(){

    }
}
