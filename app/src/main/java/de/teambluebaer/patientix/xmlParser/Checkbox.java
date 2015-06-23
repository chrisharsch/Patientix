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
 * Copyright 2015 By Authors
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * Authors:
 * Simon Sauerzapf
 * Maren Dietrich
 * Chris Harsch
 *
 * @see Row
 * @see Element
 */
public class Checkbox implements Element, de.teambluebaer.patientix.xmlParser.Editable {
    private String checkboxText;
    private boolean checked;
    private boolean highlight;
    private int counter;


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

        if(checkboxText.length()<=6){
            counter = 3;
        }else if(6 < checkboxText.length() & checkboxText.length() <= 10){
            counter = 5;
        }else{
            counter = 10;
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

    @Override
    public int getCounter() {
        return counter;
    }
}