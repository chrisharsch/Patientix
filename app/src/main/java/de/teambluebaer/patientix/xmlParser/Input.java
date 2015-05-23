package de.teambluebaer.patientix.xmlParser;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

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
    private String inputText;
    private String patientCommentar;
    private String mtraCommentar;
    private String doctorCommentar;

    /**
     * Constructor
     */
    public Input(String patientCommentar, String mtraCommentar, String doctorCommentar) {
        inputText = "";
        this.patientCommentar = new String() + patientCommentar;
        this.mtraCommentar = new String() + mtraCommentar;
        this.doctorCommentar = new String() + doctorCommentar;

    }

    @Override
    public void addToView(Context context, LinearLayout layout) {
        EditText input = new EditText(context);
        input.setText(inputText);
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
                inputText = charSequence.toString();
            }
        });
        if (Constants.zoomed) {
            input.setTextSize(TypedValue.COMPLEX_UNIT_PX, TextSize.SUBTITEL.zoomedSize);
        } else {
            input.setTextSize(TypedValue.COMPLEX_UNIT_PX, TextSize.SUBTITEL.normalSize);
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
                  //  Flasher.flash(comButton,"1x5");
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
