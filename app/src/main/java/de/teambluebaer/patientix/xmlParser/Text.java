package de.teambluebaer.patientix.xmlParser;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Simon on 06.05.2015.
 *
 * Represents a Text Field that could insert into a <code>Row</code>
 * @see Row
 * @see Element
 */
public class Text implements Element {
    private String text;

    /**
     * Constructor
     * @param text represents the shoen Text
     */
    public Text(String text){
        this.text = text;
    }

    @Override
    public void addToView(Context context, LinearLayout layout) {
        TextView textView = new TextView(context);
        textView.setText(text);
        layout.addView(textView);
    }
}
