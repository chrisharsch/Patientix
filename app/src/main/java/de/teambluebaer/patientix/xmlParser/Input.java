package de.teambluebaer.patientix.xmlParser;

import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import de.teambluebaer.patientix.R;
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
public class Input implements Element, Commentar {
    private String hint;
    private String inputText;
    private String patientInput;
    private String patientCommentar;
    private String mtraCommentar;
    private String doctorCommentar;

    /**
     * Constructor
     */
    public Input(String patientImput, String inputText, String patientCommentar, String mtraCommentar, String doctorCommentar) {
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

    }

    @Override
    public void addToView(Context context, LinearLayout layout) {
        TextView label = new TextView(context);
        EditText input = new EditText(context);
        label.setText(inputText);
        input.setHint(hint);



        TextView mtraCom = new TextView(context);
        mtraCom.setText(mtraCommentar);
        mtraCom.setTextColor(Color.GREEN);
        TextView docCom = new TextView(context);
        docCom.setText(doctorCommentar);
        docCom.setTextColor(Color.RED);

        if(patientInput != null && !patientInput.isEmpty()){
            input.setText(patientInput);
        }


        layout.addView(label);
        layout.addView(input);
        //layout.addView(mtraCom);
        //layout.addView(docCom);
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
        if (Constants.zoomed) {
            label.setTextSize(TypedValue.COMPLEX_UNIT_PX, TextSize.SUBTITEL.zoomedSize);
            input.setTextSize(TypedValue.COMPLEX_UNIT_PX, TextSize.SUBTITEL.zoomedSize);
            mtraCom.setTextSize(TypedValue.COMPLEX_UNIT_PX, TextSize.SUBTITEL.zoomedSize);
            docCom.setTextSize(TypedValue.COMPLEX_UNIT_PX, TextSize.SUBTITEL.zoomedSize);
        } else {
            label.setTextSize(TypedValue.COMPLEX_UNIT_PX, TextSize.SUBTITEL.normalSize);
            input.setTextSize(TypedValue.COMPLEX_UNIT_PX, TextSize.SUBTITEL.normalSize);
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
            comButton.setBackgroundResource(R.drawable.button1x5normal);
            comButton.setLayoutParams(new LinearLayout.LayoutParams(760, 152));
            comButton.setTextSize(TypedValue.COMPLEX_UNIT_PX,TextSize.TEXT.normalSize);
            comButton.setTextColor(Color.parseColor("#fffafa"));
            comButton.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
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
                    if (Constants.zoomed) {
                        comment.setTextSize(TypedValue.COMPLEX_UNIT_PX, TextSize.SUBTITEL.zoomedSize);
                    } else {
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
            if (Constants.zoomed) {
                comment.setTextSize(TypedValue.COMPLEX_UNIT_PX, TextSize.SUBTITEL.zoomedSize);
            } else {
                comment.setTextSize(TypedValue.COMPLEX_UNIT_PX, TextSize.SUBTITEL.normalSize);
            }
        }

    }

    public String toXMLString(){
        String xmlString = new String();
        xmlString = xmlString + "<checkbox/";
        xmlString = xmlString + "text=\"" + this.inputText + "\" ";
        xmlString = xmlString + "comment\"" + this.patientCommentar +"\" ";
        xmlString = xmlString + "mtraComment\"" + this.mtraCommentar + "\" ";
        xmlString = xmlString + "docComment\"" + this.doctorCommentar + "\" ";
        xmlString = xmlString + ">";

        return xmlString;
    }

    public String getInputText() {
        return inputText;
    }
}
