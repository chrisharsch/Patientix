package de.teambluebaer.patientix.xmlParser;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
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
    private String patientCommentar;
    private String mtraCommentar;
    private String doctorCommentar;

    /**
     * Constructor
     */
    public Input(){
        inputText = "";
    }

    @Override
    public void addToView(Context context, LinearLayout layout) {
        EditText input = new EditText(context);
        input.setText(inputText);
        layout.addView(input);
        input.addTextChangedListener(new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                inputText = charSequence.toString();
            }
        });

    }

    /**
     * Insert a patient comment field to this InputFiled
     */
    public void addPatCommant(){

    }
    /**
     * Insert a MTRA comment field to this InputFiled
     */
    public void addMTRACommant(){

    }
    /**
     * Insert a Doctor comment field to this InputFiled
     */
    public void addDocCommant(){

    }
}
