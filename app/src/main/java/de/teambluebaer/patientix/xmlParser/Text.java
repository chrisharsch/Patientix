package de.teambluebaer.patientix.xmlParser;

import android.content.Context;
import android.util.TypedValue;
import android.widget.LinearLayout;
import android.widget.TextView;

import de.teambluebaer.patientix.helper.Constants;
import de.teambluebaer.patientix.helper.TextSize;

/**
 * Created by Simon on 06.05.2015.
 *
 * Represents a Text Field that could insert into a <code>Row</code>
 * @see Row
 * @see Element
 */
public class Text implements Element {
    private String text;
    private TextSize size;

    /**
     * Constructor
     * @param text represents the showen Text
     * @param size represents the Fontsize
     */
    public Text(String text, String size){
        int sizeint = Integer.parseInt(size);
        this.text = text;
        if(sizeint == 20){
            this.size = TextSize.TITEL;
        }else if(sizeint == 15){
            this.size = TextSize.SUBTITEL;
        }else if(sizeint == 14){
            this.size = TextSize.TEXT;
        }
    }

    @Override
    public void addToView(Context context, LinearLayout layout) {
        TextView textView = new TextView(context);
        textView.setText(text);
        if(Constants.zoomed){
            textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, size.zoomedSize);
        }else{
            textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, size.normalSize);
        }

        layout.addView(textView);
    }
}
