package de.teambluebaer.patientix.xmlParser;

import android.content.Context;
import android.util.TypedValue;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import de.teambluebaer.patientix.R;
import de.teambluebaer.patientix.helper.Constants;
import de.teambluebaer.patientix.helper.TextSize;

/**
 * Created by Simon on 06.05.2015.
 * <p/>
 * Represents a radiobutton that could insert into a <code>Row</code>
 *
 * @see Row
 * @see Element
 */
public class Radio implements Element,Editable {
    private String radioText;
    private boolean checked;
    private boolean highlight;


    /**
     * constructor
     *
     * @param radioText represents the showen Answer to this Radio Button
     */
    public Radio(String radioText, String checked, String highlight) {

        if(checked != null && !checked.isEmpty()){
            this.checked = (checked.equals("1")|checked.equals("true"));
        } else {
            this.checked = false;
        }
        if(radioText != null && !radioText.isEmpty()){
            this.radioText = radioText;
        } else {
            this.radioText = "";
        }
        if(highlight != null && !highlight.isEmpty()){
            this.highlight = (highlight.equals("1")|highlight.equals("true"));
        } else {
            this.highlight = false;
        }
    }

    @Override
    public void addToView(Context context, LinearLayout layout) {
        RadioButton radio = new RadioButton(context);
        radio.setText(radioText);
        radio.setChecked(checked);
        layout.addView(radio);
    }

    /**
     * Costem addToView Methode that should be Used to add Radiobutton to Views
     * @param context Context of the Activite the Radiobutton should be added
     * @param radiogroup could contains severel Radiobutton
     */
    public void addToView(Context context, RadioGroup radiogroup) {
        RadioButton radio = new RadioButton(context);
        radio.setText(radioText);
        radio.setButtonDrawable(R.drawable.radio);
        radiogroup.addView(radio);
        radio.setChecked(checked);


        radio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                checked = isChecked;
            }
        });

        if(Constants.RESIGN){
            radio.setClickable(false);
        }

        if(Constants.ZOOMED){
            radio.setTextSize(TypedValue.COMPLEX_UNIT_PX, TextSize.SUBTITEL.zoomedSize);

        }else{
            radio.setTextSize(TypedValue.COMPLEX_UNIT_PX, TextSize.SUBTITEL.normalSize);
        }
    }


    public String toXMLString() {
        String xmlString = new String();
        xmlString = xmlString + "<radiobutton ";
        xmlString = xmlString + "text=\"" + this.radioText + "\" ";
        if(checked){
            xmlString = xmlString + "checked=\"" + 1 + "\" ";
        }else {
            xmlString = xmlString + "checked=\"" + 0 + "\" ";
        }
        xmlString = xmlString + "highlight=\"" + this.highlight + "\" ";
        xmlString = xmlString + "/>";

        return xmlString;
    }

    public String getRadioText() {
        return radioText;
    }

    public boolean isChecked() {
        return checked;
    }

    public boolean isHighlight() {
        return highlight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Radio radio = (Radio) o;

        if (checked != radio.checked) return false;
        if (highlight != radio.highlight) return false;
        return !(radioText != null ? !radioText.equals(radio.radioText) : radio.radioText != null);

    }

    @Override
    public int hashCode() {
        int result = radioText != null ? radioText.hashCode() : 0;
        result = 31 * result + (checked ? 1 : 0);
        result = 31 * result + (highlight ? 1 : 0);
        return result;
    }
}