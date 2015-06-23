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
 * @see Row
 */
public abstract class Commentar {

    protected String patientCommentar;
    protected String mtraCommentar;
    protected String doctorCommentar;


    /**
     * Add a Comentarbutton thet extends on Click to an Comentarfield
     * @param context Context of the Activite the Comment should be added
     * @param layout Layout the Comment should be added
     */
    public void addCommentarField(Context context, LinearLayout layout) {
        final LinearLayout linearLayout = layout;
        final Context comContext = context;
        if (patientCommentar.isEmpty()) {
            final Button comButton = new Button(context);
            comButton.setText("Kommentar hinzufügen");
            comButton.setBackgroundResource(R.drawable.button1x5);
            comButton.setLayoutParams(new LinearLayout.LayoutParams( 760, 152));
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
                    if (Constants.ZOOMED) {
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
            if (Constants.ZOOMED) {
                comment.setTextSize(TypedValue.COMPLEX_UNIT_PX, TextSize.SUBTITEL.zoomedSize);
            } else {
                comment.setTextSize(TypedValue.COMPLEX_UNIT_PX, TextSize.SUBTITEL.normalSize);
            }
        }

    }


    /**
     * Show all Commentartypes in different Colors
     * @param context Context of the Activite the Comment should be added
     * @param layout Layout the Comment should be added
     */
    public void showAllComments(Context context, LinearLayout layout) {
        if (patientCommentar != null && !patientCommentar.isEmpty()) {
            TextView patCom = new TextView(context);
            patCom.setText("Patient: " + this.patientCommentar);
            layout.addView(patCom);
            if (Constants.ZOOMED) {
                patCom.setTextSize(TypedValue.COMPLEX_UNIT_PX, TextSize.TEXT.zoomedSize);
            } else {
                patCom.setTextSize(TypedValue.COMPLEX_UNIT_PX, TextSize.TEXT.normalSize);
            }
        }
        if (mtraCommentar != null && !mtraCommentar.isEmpty()) {
            TextView mtraCom = new TextView(context);
            mtraCom.setText("MTRA: " + this.mtraCommentar);
            layout.addView(mtraCom);
            mtraCom.setTextColor(Color.GREEN);
            if (Constants.ZOOMED) {
                mtraCom.setTextSize(TypedValue.COMPLEX_UNIT_PX, TextSize.TEXT.zoomedSize);
            } else {
                mtraCom.setTextSize(TypedValue.COMPLEX_UNIT_PX, TextSize.TEXT.normalSize);
            }
        }
        if (doctorCommentar != null && !doctorCommentar.isEmpty()) {
            TextView docCom = new TextView(context);
            docCom.setText("Arzt: " + this.doctorCommentar);
            layout.addView(docCom);
            docCom.setTextColor(Color.RED);
            if (Constants.ZOOMED) {
                docCom.setTextSize(TypedValue.COMPLEX_UNIT_PX, TextSize.TEXT.zoomedSize);
            } else {
                docCom.setTextSize(TypedValue.COMPLEX_UNIT_PX, TextSize.TEXT.normalSize);
            }
        }
    }
}
