package de.teambluebaer.patientix.xmlParser;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

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
public class Radio implements Element, Commentar {
    private String radioText;
    private boolean checked;
    private String patientCommentar;
    private String mtraCommentar;
    private String doctorCommentar;



    /**
     * constructor
     *
     * @param radioText represents the showen Answer to this Radio Button
     */
    public Radio(String radioText) {
        this.radioText = radioText;
        this.checked = false;
        patientCommentar = "";

    }

    @Override
    public void addToView(Context context, LinearLayout layout) {
        RadioButton radio = new RadioButton(context);
        radio.setText(radioText);
        radio.setChecked(checked);
        layout.addView(radio);
    }

    public void addToView(Context context, RadioGroup radiogroup) {
        RadioButton radio = new RadioButton(context);
        radio.setText(radioText);
        radiogroup.addView(radio);
        radio.setChecked(checked);
        radio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                checked = isChecked;
            }
        });
        if(Constants.zoomed){
            radio.setTextSize(TypedValue.COMPLEX_UNIT_PX, TextSize.SUBTITEL.zoomedSize);
        }else{
            radio.setTextSize(TypedValue.COMPLEX_UNIT_PX, TextSize.SUBTITEL.normalSize);
        }
    }


    @Override
    public void addCommentarField(Context context, LinearLayout layout) {
        final LinearLayout linearLayout = layout;
        final Context comContext = context;
        if (patientCommentar.isEmpty()) {
            final Button comButton = new Button(context);
            comButton.setText("Kommentar hinzufügen");
            comButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    comButton.setVisibility(View.GONE);
                    comButton.setClickable(false);
                    EditText comment = new EditText(comContext);
                    comment.setHint("Hier Kommentar eingeben");
                    linearLayout.addView(comment);
                    comment.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        }

                        @Override
                        public void afterTextChanged(Editable editable) {
                        }

                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                            patientCommentar = charSequence.toString();
                        }
                    });
                    if(Constants.zoomed){
                        comment.setTextSize(TypedValue.COMPLEX_UNIT_PX,TextSize.SUBTITEL.zoomedSize);
                    }else{
                        comment.setTextSize(TypedValue.COMPLEX_UNIT_PX, TextSize.SUBTITEL.normalSize);
                    }
                }
            });
            linearLayout.addView(comButton);

        } else {
            EditText comment = new EditText(comContext);
            comment.setText(patientCommentar);
            linearLayout.addView(comment);
            comment.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void afterTextChanged(Editable editable) {
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    patientCommentar = charSequence.toString();
                }
            });
            if(Constants.zoomed){
                comment.setTextSize(TypedValue.COMPLEX_UNIT_PX,TextSize.SUBTITEL.zoomedSize);
            }else{
                comment.setTextSize(TypedValue.COMPLEX_UNIT_PX, TextSize.SUBTITEL.normalSize);
            }
        }

    }

    public String getRadioText() {
        return radioText;
    }

    public boolean isChecked() {
        return checked;
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
