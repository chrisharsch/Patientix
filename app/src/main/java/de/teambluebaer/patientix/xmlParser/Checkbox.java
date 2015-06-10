package de.teambluebaer.patientix.xmlParser;

import android.content.Context;
import android.util.TypedValue;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import de.teambluebaer.patientix.R;
import de.teambluebaer.patientix.helper.Constants;
import de.teambluebaer.patientix.helper.TextSize;

/**
 * Created by Simon on 06.05.2015.
 * <p/>
 * Represents a Checkbox that could insert into a <code>Row</code>
 *
 * @see Row
 * @see Element
 */
public class Checkbox implements Element, de.teambluebaer.patientix.xmlParser.Editable {
    private String checkboxText;
    private boolean checked;
    private boolean highlight;


    /**
     * Constructor
     *
     * @param checkboxText represents the showen Answer to this Checkbox
     */
    public Checkbox(String checkboxText, String checked, String highlight) {
        if(checkboxText != null && !checkboxText.isEmpty()){
            this.checkboxText = checkboxText;
        } else {
            this.checkboxText = "";
        }
        if(checked != null && !checked.isEmpty()){
            this.checked = (checked.equals("1")|checked.equals("true"));
        } else {
            this.checked = false;
        }

        if(highlight != null && !highlight.isEmpty()){
            this.highlight = (highlight.equals("1")|highlight.equals("true"));
        } else {
            this.highlight = false;
        }

    }

    @Override
    public void addToView(Context context, final LinearLayout layout) {
        CheckBox checkBox = new CheckBox(context);
        checkBox.setText(checkboxText);
        checkBox.setButtonDrawable(R.drawable.radio);
        checkBox.setChecked(checked);
        layout.addView(checkBox);



        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                checked = isChecked;
            }
        });

        if(Constants.RESIGN){
            checkBox.setClickable(false);
        }

        if(Constants.ZOOMED){
            checkBox.setTextSize(TypedValue.COMPLEX_UNIT_PX, TextSize.SUBTITEL.zoomedSize);

        }else{
            checkBox.setTextSize(TypedValue.COMPLEX_UNIT_PX, TextSize.SUBTITEL.normalSize);

        }
    }




    public String toXMLString(){
        String xmlString = new String();
        xmlString = xmlString + "<checkbox ";
        xmlString = xmlString + "text=\"" + this.checkboxText + "\" ";
        if(checked){
            xmlString = xmlString + "checked=\"" + 1 + "\" ";
        }else{
            xmlString = xmlString + "checked=\"" + 0 + "\" ";
        }

        xmlString = xmlString + "highlight=\"" + this.highlight + "\" ";
        xmlString = xmlString + "/>";

        return xmlString;
    }

    public String getCheckboxText() {
        return checkboxText;
    }

    public boolean isChecked() {
        return checked;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Checkbox checkbox = (Checkbox) o;

        if (checked != checkbox.checked) return false;
        if (highlight != checkbox.highlight) return false;
        return !(checkboxText != null ? !checkboxText.equals(checkbox.checkboxText) : checkbox.checkboxText != null);

    }

    @Override
    public int hashCode() {
        int result = checkboxText != null ? checkboxText.hashCode() : 0;
        result = 31 * result + (checked ? 1 : 0);
        result = 31 * result + (highlight ? 1 : 0);
        return result;
    }
}