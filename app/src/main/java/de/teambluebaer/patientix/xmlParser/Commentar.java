package de.teambluebaer.patientix.xmlParser;

import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.InputFilter;
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
 * Created by Simon on 21.05.2015.
 */
public abstract class Commentar {

    protected String patientCommentar;
    protected String mtraCommentar;
    protected String doctorCommentar;

    public void addCommentarField(Context context, LinearLayout layout) {
        final LinearLayout linearLayout = layout;
        final Context comContext = context;
        if (patientCommentar.isEmpty()) {
            final Button comButton = new Button(context);
            comButton.setText("Kommentar hinzufügen");
            comButton.setBackgroundResource(R.drawable.button1x5normal);
            comButton.setLayoutParams(new LinearLayout.LayoutParams(760, 152));
            comButton.setTextSize(TypedValue.COMPLEX_UNIT_PX, TextSize.TEXT.normalSize);
            comButton.setTextColor(Color.parseColor("#fffafa"));
            comButton.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            comButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    comButton.setVisibility(View.GONE);
                    comButton.setClickable(false);
                    EditText comment = new EditText(comContext);
                    comment.setFilters(new InputFilter[]{new InputFilter.LengthFilter(100)});
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


    public void showAllComments(Context context, LinearLayout layout) {
        if(patientCommentar != null && !patientCommentar.isEmpty()){
            TextView patCom = new TextView(context);
            patCom.setText(this.patientCommentar);
            layout.addView(patCom);
            if (Constants.zoomed) {
                patCom.setTextSize(TypedValue.COMPLEX_UNIT_PX, TextSize.SUBTITEL.zoomedSize);
            } else {
                patCom.setTextSize(TypedValue.COMPLEX_UNIT_PX, TextSize.SUBTITEL.normalSize);
            }
        }
        if(mtraCommentar != null && !mtraCommentar.isEmpty()){
            TextView mtraCom = new TextView(context);
            mtraCom.setText(this.mtraCommentar);
            layout.addView(mtraCom);
            mtraCom.setTextColor(Color.GREEN);
            if (Constants.zoomed) {
                mtraCom.setTextSize(TypedValue.COMPLEX_UNIT_PX, TextSize.SUBTITEL.zoomedSize);
            } else {
                mtraCom.setTextSize(TypedValue.COMPLEX_UNIT_PX, TextSize.SUBTITEL.normalSize);
            }
        }
        if(doctorCommentar != null && !doctorCommentar.isEmpty()){
            TextView docCom = new TextView(context);
            docCom.setText(this.doctorCommentar);
            layout.addView(docCom);
            docCom.setTextColor(Color.RED);
            if (Constants.zoomed) {
                docCom.setTextSize(TypedValue.COMPLEX_UNIT_PX, TextSize.SUBTITEL.zoomedSize);
            } else {
                docCom.setTextSize(TypedValue.COMPLEX_UNIT_PX, TextSize.SUBTITEL.normalSize);
            }
        }
    }
}
