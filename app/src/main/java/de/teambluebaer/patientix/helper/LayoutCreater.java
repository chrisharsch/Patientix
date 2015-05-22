package de.teambluebaer.patientix.helper;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import java.util.List;

import de.teambluebaer.patientix.xmlParser.Commentar;
import de.teambluebaer.patientix.xmlParser.Element;
import de.teambluebaer.patientix.xmlParser.Page;
import de.teambluebaer.patientix.xmlParser.Radio;
import de.teambluebaer.patientix.xmlParser.Row;

/**
 * Created by Simon on 08.05.2015.
 */
public class LayoutCreater {

    private boolean hasComBut = false;

    public LayoutCreater() {

    }


    public void CreateRowLayout(Context context, Row row, LinearLayout pageLayout) {
        LinearLayout rowLayout = new LinearLayout(context);
        List<Element> elements = row.getElements();
        RadioGroup radioGroup = new RadioGroup(context);
        radioGroup.setOrientation(LinearLayout.HORIZONTAL);
        Commentar commentar = null;
        for (Element element : elements) {
            if (element instanceof Radio) {
                Radio radio = (Radio) element;
                radio.addToView(context, radioGroup);

            } else {
                element.addToView(context, rowLayout);
            }
            if (element instanceof Commentar && !hasComBut) {
                hasComBut = true;
                commentar = (Commentar) element;
            }


        }
        if (radioGroup.getChildCount() != 0) {
            rowLayout.addView(radioGroup);
        }
        rowLayout.setPadding(0,10,0,10);
        pageLayout.addView(rowLayout);
        if (hasComBut) {
            LinearLayout comRow = new LinearLayout(context);
            commentar.addCommentarField(context, comRow);
            comRow.setPadding(0,10,0,10);
            pageLayout.addView(comRow);
            hasComBut = false;
        }
    }

    public void CreatPageLayout(Context context, Page page, LinearLayout pageLayout) {
        pageLayout.removeAllViews();
        List<Row> rows = page.getRows();
        for (Row row : rows) {
            CreateRowLayout(context, row, pageLayout);

        }

    }

    public void CreatListLayout(Context context, LinearLayout layout) {
        layout.removeAllViews();

        List<Page> pageList = Constants.globalMetaandForm.getForm().getPageList();

        for (Page page : pageList) {

                List<Row> rows = page.getRows();
                for (Row row : rows) {


                    CreateRowLayout(context, row, layout);


            }
        }

    }

    public boolean isHasComBut() {
        return hasComBut;
    }
}
