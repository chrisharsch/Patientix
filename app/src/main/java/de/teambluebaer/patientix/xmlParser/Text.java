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
    private int counter;

    /**
     * Constructor
     * @param text represents the showen Text
     * @param size represents the Fontsize
     */
    public Text(String text, String size){
        counter = 10;
        int sizeint = Integer.parseInt(size);
        this.text = text;
        if(sizeint == 20){
            this.size = TextSize.TITEL;
        }else if(sizeint == 15){
            this.size = TextSize.SUBTITEL;
        }else if(sizeint == 14){
            this.size = TextSize.TEXT;
        }else{
            this.size = TextSize.TEXT;
        }
    }

    @Override
    public void addToView(Context context, LinearLayout layout) {
        TextView textView = new TextView(context);
        textView.setText(text);
        if(Constants.ZOOMED){
            textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, size.zoomedSize);
        }else{
            textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, size.normalSize);
        }

        layout.addView(textView);
    }

    public String toXMLString() {
        String xmlString = new String();
        xmlString = xmlString + "<text ";
        xmlString = xmlString + "text=\"" + this.text + "\" ";
        if(size.equals(TextSize.TITEL)){
            xmlString = xmlString + "size=\"20\" ";
        }else if(size.equals(TextSize.SUBTITEL)){
            xmlString = xmlString + "size=\"15\" ";
        }else {
            xmlString = xmlString + "size=\"14\" ";
        }
        xmlString = xmlString + "/>";

        return xmlString;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Text text1 = (Text) o;

        if (text != null ? !text.equals(text1.text) : text1.text != null) return false;
        return size == text1.size;

    }

    @Override
    public int hashCode() {
        int result = text != null ? text.hashCode() : 0;
        result = 31 * result + (size != null ? size.hashCode() : 0);
        return result;
    }

    public String getText() {
        return text;
    }

    public TextSize getSize() {
        return size;
    }

    @Override
    public int getCounter() {
        return counter;
    }
}