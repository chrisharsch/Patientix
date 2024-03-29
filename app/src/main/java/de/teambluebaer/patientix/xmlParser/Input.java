package de.teambluebaer.patientix.xmlParser;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import de.teambluebaer.patientix.helper.Constants;
import de.teambluebaer.patientix.helper.TextSize;
import de.teambluebaer.patientix.helper.XMLCleaner;



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
 * Represents an Input Field that could insert into a <code>Row</code>
 *
 * @see Row
 * @see Element
 */
public class Input  implements Element, de.teambluebaer.patientix.xmlParser.Editable {
    private String hint;
    private String inputText;
    private String patientInput;
    private boolean highlight;
    private int counter;

    /**
     * Constructor
     */
    public Input(String patientImput, String inputText, String highlight) {
        counter = 10;
        if(inputText != null && !inputText.isEmpty()){
            this.inputText = inputText;
        }else{
            this.inputText = "";
        }

        if(patientImput == null){
            this.patientInput="";
        }else{
            this.patientInput = patientImput;
        }

        this.hint = "bitte hier eingeben";
        if(highlight != null && !highlight.isEmpty()){
            this.highlight = (highlight.equals("1")|highlight.equals("true"));
        } else {
            this.highlight = false;
        }

    }

    @Override
    public void addToView(Context context, LinearLayout layout) {

        LinearLayout textAndInput = new LinearLayout(context);
        textAndInput.setOrientation(LinearLayout.VERTICAL);
        textAndInput.setGravity(Gravity.CENTER);
        TextView label = new TextView(context);
        EditText input = new EditText(context);

        label.setText(inputText);
        input.setHint(hint);

        label.setText(inputText);

        if(patientInput != null && !patientInput.isEmpty()){
            input.setText(patientInput);
        }


        textAndInput.addView(label);
        textAndInput.addView(input);
        layout.addView(textAndInput);
        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                patientInput = charSequence.toString();
            }
        });

        if(Constants.RESIGN){
            input.setClickable(false);
            input.setCursorVisible(false);
            input.setFocusable(false);
        }


        if (Constants.ZOOMED) {
            label.setTextSize(TypedValue.COMPLEX_UNIT_PX, TextSize.SUBTITEL.zoomedSize);
            input.setTextSize(TypedValue.COMPLEX_UNIT_PX, TextSize.SUBTITEL.zoomedSize);
        } else {
            label.setTextSize(TypedValue.COMPLEX_UNIT_PX, TextSize.SUBTITEL.normalSize);
            input.setTextSize(TypedValue.COMPLEX_UNIT_PX, TextSize.SUBTITEL.normalSize);
        }

    }


    public String toXMLString(){
        String xmlString = new String();
        xmlString = xmlString + "<input ";
        xmlString = xmlString + "text=\"" + this.inputText + "\" ";
        xmlString = xmlString + "patientInput=\"" + XMLCleaner.cleanStringForXML(this.patientInput) + "\" ";
        xmlString = xmlString + "highlight=\"" + this.highlight + "\" ";
        xmlString = xmlString + "/>";

        return xmlString;
    }

    public String getInputText() {
        return inputText;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Input input = (Input) o;

        if (highlight != input.highlight) return false;
        if (hint != null ? !hint.equals(input.hint) : input.hint != null) return false;
        if (inputText != null ? !inputText.equals(input.inputText) : input.inputText != null)
            return false;
        return !(patientInput != null ? !patientInput.equals(input.patientInput) : input.patientInput != null);

    }

    @Override
    public int hashCode() {
        int result = hint != null ? hint.hashCode() : 0;
        result = 31 * result + (inputText != null ? inputText.hashCode() : 0);
        result = 31 * result + (patientInput != null ? patientInput.hashCode() : 0);
        result = 31 * result + (highlight ? 1 : 0);
        return result;
    }

    public String getHint() {
        return hint;
    }

    public String getPatientInput() {
        return patientInput;
    }

    public boolean isHighlight() {
        return highlight;
    }

    @Override
    public int getCounter() {
        return counter;
    }
}