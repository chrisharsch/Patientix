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
 * Created by Simon on 08.05.2015.
 */
public class LayoutCreater {

    private int LineCounter;

    private boolean hasComBut = false;

    /**
     * Constructor
     */
    public LayoutCreater() {
        LineCounter = 0;
    }

    /**
     *
     * @param context Context of the Activite the Row should be added
     * @param row Row that should be added
     * @param pageLayout Pagelayout the row should be added to
     */
    public void CreateRowLayout(Context context, Row row, LinearLayout pageLayout) {
        int rowCounter = 0;
        LinearLayout rowLayout = new LinearLayout(context);
        List<Element> elements = row.getElements();
        RadioGroup radioGroup = new RadioGroup(context);
        radioGroup.setOrientation(LinearLayout.HORIZONTAL);
        Commentar commentar = null;
        for (Element element : elements) {

            rowCounter = rowCounter + element.getCounter();
            if (element instanceof Radio) {
                Radio radio = (Radio) element;
                radio.addToView(context, radioGroup);
                if(radioGroup.getChildCount()>2 && rowCounter > 10){
                    radioGroup.setOrientation(LinearLayout.VERTICAL);
                }

            } else {
                if(rowCounter + element.getCounter() > 10){
                    rowLayout.setPadding(0, 30, 0, 30);
                    rowLayout.setGravity(Gravity.CENTER);
                    pageLayout.addView(rowLayout);
                    rowLayout = new LinearLayout(context);
                    rowCounter = 0;
                    rowCounter = rowCounter + element.getCounter();
                }
                element.addToView(context, rowLayout);

            }
            if (element instanceof Editable && !hasComBut) {
                hasComBut = true;
                commentar = row;
            }
        }
        if (radioGroup.getChildCount() != 0) {
            rowLayout.addView(radioGroup);
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
