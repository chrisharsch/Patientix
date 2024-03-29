package de.teambluebaer.patientix.helper;

import android.content.Context;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import java.util.List;

import de.teambluebaer.patientix.xmlParser.Commentar;
import de.teambluebaer.patientix.xmlParser.Editable;
import de.teambluebaer.patientix.xmlParser.Element;
import de.teambluebaer.patientix.xmlParser.Page;
import de.teambluebaer.patientix.xmlParser.Radio;
import de.teambluebaer.patientix.xmlParser.Row;


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
 */
public class LayoutCreater {


    private boolean hasComBut = false;

    /**
     * Constructor
     */
    public LayoutCreater() {
    }

    /**
     *
     * @param context Context of the Activite the Row should be added
     * @param row Row that should be added
     * @param pageLayout Pagelayout the row should be added to
     */
    public void CreateRowLayout(Context context, Row row, LinearLayout pageLayout) {
        int rowCounter = 0;
        int radioCounter = 0;
        LinearLayout rowLayout = new LinearLayout(context);
        rowLayout.setDrawingCacheEnabled(false);
        List<Element> elements = row.getElements();
        RadioGroup radioGroup = new RadioGroup(context);
        radioGroup.setDrawingCacheEnabled(false);
        radioGroup.setOrientation(LinearLayout.HORIZONTAL);
        Commentar commentar = null;
        for (Element element : elements) {

            if (element instanceof Radio) {
                Radio radio = (Radio) element;
                radio.addToView(context, radioGroup);
                radioCounter = radioCounter + radio.getCounter();

            } else {
                if(rowCounter + element.getCounter() >= 10){


                    rowLayout.setPadding(0, 30, 0, 30);
                    rowLayout.setGravity(Gravity.CENTER);
                    pageLayout.addView(rowLayout);
                    rowLayout = new LinearLayout(context);
                    rowCounter = element.getCounter();
                    rowLayout.setDrawingCacheEnabled(false);


                }
                element.addToView(context, rowLayout);
                rowCounter = rowCounter + element.getCounter();

            }
            if (element instanceof Editable && !hasComBut) {
                hasComBut = true;
                commentar = row;
            }
        }
        if (radioGroup.getChildCount() != 0) {

            pageLayout.addView(rowLayout);
            rowLayout = new LinearLayout(context);
            rowLayout.setDrawingCacheEnabled(false);
            rowLayout.addView(radioGroup);
            if(radioGroup.getChildCount()>=2 && radioCounter >= 10){
                radioGroup.setOrientation(LinearLayout.VERTICAL);
            }
        }
        rowLayout.setGravity(Gravity.CENTER);
        rowLayout.setPadding(0, 30, 0, 30);
        pageLayout.addView(rowLayout);
        if (hasComBut) {
            if (Constants.RESIGN) {
                commentar.showAllComments(context, pageLayout);
                hasComBut = false;
            } else {
                LinearLayout comRow = new LinearLayout(context);
                commentar.addCommentarField(context, comRow);
                comRow.setDrawingCacheEnabled(false);
                comRow.setPadding(0, 30, 0, 30);
                comRow.setGravity(Gravity.CENTER);
                pageLayout.addView(comRow);
                hasComBut = false;
            }
        }
    }

    /**
     * Create a given Page out of the Global metha and Form Pagelist
     * @param context Context of the Activite the Page should be added
     * @param page Page that shuld be convertet into a Layout
     * @param pageLayout Layout the Page should use
     */
    public void CreatPageLayout(Context context, Page page, LinearLayout pageLayout) {
        pageLayout.removeAllViews();
        List<Row> rows = page.getRows();
        for (Row row : rows) {
            CreateRowLayout(context, row, pageLayout);
        }
    }

    /**
     *
     * @param context Context of the Activite the List should be added
     * @param layout Layout the List is added to
     */
    public void CreatListLayout(Context context, LinearLayout layout) {
        layout.removeAllViews();
        List<Page> pageList = Constants.GLOBALMETAANDFORM.getForm().getPageList();
        for (Page page : pageList) {
            if (page.isRelevant()) {

                List<Row> rows = page.getRows();
                for (Row row : rows) {
                    CreateRowLayout(context, row, layout);
                }
            }
        }
    }
}
