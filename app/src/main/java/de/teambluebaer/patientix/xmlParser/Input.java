package de.teambluebaer.patientix.xmlParser;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import de.teambluebaer.patientix.helper.Constants;
import de.teambluebaer.patientix.helper.TextSize;

/**
 * Created by Simon on 06.05.2015.
 * <p/>
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

    /**
     * Constructor
     */
    public Input(String patientImput, String inputText, String highlight) {

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
        TextView label = new TextView(context);
        EditText input = new EditText(context);
        label.setText(inputText);
        input.setHint(hint);

        label.setText(inputText);

        if(patientInput != null && !patientInput.isEmpty()){
            input.setText(patientInput);
        }


        layout.addView(label);
        layout.addView(input);
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
        xmlString = xmlString + "patientInput=\"" + this.patientInput + "\" ";
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
}