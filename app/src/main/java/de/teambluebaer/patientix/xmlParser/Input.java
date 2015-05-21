package de.teambluebaer.patientix.xmlParser;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
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
public class Input implements Element,Commentar {
    private String inputText;
    private String patientCommentar;
    private String mtraCommentar;
    private String doctorCommentar;

    /**
     * Constructor
     */
    public Input(){
        inputText = "";
        patientCommentar="";
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

    @Override
    public void addCommentarField(Context context, LinearLayout layout) {
        final LinearLayout linearLayout = layout;
        final Context comContext = context;
        if(patientCommentar.isEmpty()){
            Button comButton = new Button(context);
            comButton.setText("Kommentar hinzuf�gen");
            comButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    EditText comment = new EditText(comContext);
                    comment.setText("Hier Kommentar eingeben");
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
                }
            });
            linearLayout.addView(comButton);
        }else{
            EditText comment = new EditText(comContext);
            comment.setText(patientCommentar);
            linearLayout.addView(comment);
            comment.addTextChangedListener(new TextWatcher(){
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

                @Override
                public void afterTextChanged(Editable editable) {}

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    patientCommentar = charSequence.toString();
                }
            });
        }

    }
}
