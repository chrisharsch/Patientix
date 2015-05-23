package de.teambluebaer.patientix.xmlParser;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

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
public class Checkbox implements Element, Commentar {
    private String checkboxText;
    private boolean checked;
    private String patientCommentar;
    private String mtraCommentar;
    private String doctorCommentar;



    /**
     * Constructor
     *
     * @param checkboxText represents the showen Answer to this Checkbox
     */
    public Checkbox(String checkboxText, String patientCommentar, String mtraCommentar, String doctorCommentar) {
        this.checkboxText = checkboxText;
        this.checked = false;
        this.patientCommentar = new String() + patientCommentar;
        this.mtraCommentar =  new String() + mtraCommentar;
        this.doctorCommentar = new String() + doctorCommentar;
    }

    @Override
    public void addToView(Context context, final LinearLayout layout) {
        CheckBox checkBox = new CheckBox(context);
        checkBox.setText(checkboxText);
        checkBox.setChecked(checked);
        layout.addView(checkBox);

        TextView mtraCom = new TextView(context);
        mtraCom.setText(mtraCommentar);
        //mtraCom.setTextColor();
        TextView docCom = new TextView(context);
        docCom.setText(doctorCommentar);
        //docCom.setTextColor();

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                checked = isChecked;
            }
        });
        if(Constants.zoomed){
            checkBox.setTextSize(TypedValue.COMPLEX_UNIT_PX, TextSize.SUBTITEL.zoomedSize);
            mtraCom.setTextSize(TypedValue.COMPLEX_UNIT_PX, TextSize.SUBTITEL.zoomedSize);
            docCom.setTextSize(TypedValue.COMPLEX_UNIT_PX, TextSize.SUBTITEL.zoomedSize);
        }else{
            checkBox.setTextSize(TypedValue.COMPLEX_UNIT_PX, TextSize.SUBTITEL.normalSize);
            mtraCom.setTextSize(TypedValue.COMPLEX_UNIT_PX, TextSize.SUBTITEL.normalSize);
            docCom.setTextSize(TypedValue.COMPLEX_UNIT_PX, TextSize.SUBTITEL.normalSize);
        }
    }

    @Override
    public void addCommentarField(Context context, LinearLayout layout) {
        final LinearLayout linearLayout = layout;
        final Context comContext = context;
        if (patientCommentar.isEmpty()) {
            final Button comButton = new Button(context);
            comButton.setText("Kommentar hinzufügen");
           // comButton.setBackgroundResource(R.drawable.button1x5normal);
            comButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
              //      Flasher.flash(comButton, "1x5");
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

    public String toXMLString(){
        String xmlString = new String();
        xmlString = xmlString + "<checkbox/";
        xmlString = xmlString + "text=\"" + this.checkboxText + "\" ";
        xmlString = xmlString + "checked=\"" + this.checked + "\" ";
        xmlString = xmlString + "comment\"" + this.patientCommentar +"\" ";
        xmlString = xmlString + "mtraComment\"" + this.mtraCommentar + "\" ";
        xmlString = xmlString + "docComment\"" + this.doctorCommentar + "\" ";
        xmlString = xmlString + ">";

        return xmlString;
    }

    public String getCheckboxText() {
        return checkboxText;
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
