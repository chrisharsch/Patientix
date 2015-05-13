package de.teambluebaer.patientix.xmlParser;

import android.content.Context;
import android.widget.EditText;
import android.widget.LinearLayout;

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
    public void addToView(Context context, LinearLayout layout) {
        EditText imput = new EditText(context);
        layout.addView(imput);
    }
}
