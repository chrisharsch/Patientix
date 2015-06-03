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
public class Input extends Commentar implements Element {
    private String hint;
    private String inputText;
    private String patientInput;
    private boolean highlight;

    /**
     * Constructor
     */
    public Input(String patientImput, String inputText, String patientCommentar, String mtraCommentar, String doctorCommentar, String highlight) {

        this.inputText = inputText;

        this.patientInput = patientImput;

        this.hint = "bitte Hier eingeben";

        if(patientCommentar != null && !patientCommentar.isEmpty()){
            this.patientCommentar = patientCommentar;
        } else {
            this.patientCommentar = "";
        }

        if(mtraCommentar != null && !mtraCommentar.isEmpty()){
            this.mtraCommentar = mtraCommentar;
        } else {
            this.mtraCommentar = "";
        }
        if(doctorCommentar != null && !doctorCommentar.isEmpty()){
            this.doctorCommentar = doctorCommentar;
        } else {
            this.doctorCommentar = "";
        }
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

        if(Constants.resign){
            input.setClickable(false);
            input.setCursorVisible(false);
            input.setFocusable(false);
        }


        if (Constants.zoomed) {
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
        xmlString = xmlString + "comment=\"" + this.patientCommentar +"\" ";
        xmlString = xmlString + "mtraComment=\"" + this.mtraCommentar + "\" ";
        xmlString = xmlString + "docComment=\"" + this.doctorCommentar + "\" ";
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

        if (hint != null ? !hint.equals(input.hint) : input.hint != null) return false;
        if (inputText != null ? !inputText.equals(input.inputText) : input.inputText != null)
            return false;
        if (patientInput != null ? !patientInput.equals(input.patientInput) : input.patientInput != null)
            return false;
        if (patientCommentar != null ? !patientCommentar.equals(input.patientCommentar) : input.patientCommentar != null)
            return false;
        if (mtraCommentar != null ? !mtraCommentar.equals(input.mtraCommentar) : input.mtraCommentar != null)
            return false;
        return !(doctorCommentar != null ? !doctorCommentar.equals(input.doctorCommentar) : input.doctorCommentar != null);

    }

    @Override
    public int hashCode() {
        int result = hint != null ? hint.hashCode() : 0;
        result = 31 * result + (inputText != null ? inputText.hashCode() : 0);
        result = 31 * result + (patientInput != null ? patientInput.hashCode() : 0);
        result = 31 * result + (patientCommentar != null ? patientCommentar.hashCode() : 0);
        result = 31 * result + (mtraCommentar != null ? mtraCommentar.hashCode() : 0);
        result = 31 * result + (doctorCommentar != null ? doctorCommentar.hashCode() : 0);
        return result;
    }

    public String getHint() {
        return hint;
    }

    public String getPatientInput() {
        return patientInput;
    }

    public String getPatientCommentar() {
        return patientCommentar;
    }

    public String getMtraCommentar() {
        return mtraCommentar;
    }

    public String getDoctorCommentar() {
        return doctorCommentar;
    }
}