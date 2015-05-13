package de.teambluebaer.patientix.helper;

import android.content.Context;
import android.widget.LinearLayout;

import java.util.List;

import de.teambluebaer.patientix.xmlParser.Element;
import de.teambluebaer.patientix.xmlParser.Page;
import de.teambluebaer.patientix.xmlParser.Row;

/**
 * Created by Simon on 08.05.2015.
 */
public class LayoutCreater {

    public LayoutCreater(){

    }


    public LinearLayout CreateRowLayout(Context context, Row row){
        LinearLayout rowLayout = new LinearLayout(context);
        List<Element> elements = row.getElements();
        for(Element element: elements){
            element.addToView(context, rowLayout);
        }
        return rowLayout;
    }

    public LinearLayout CreatPageLayout(Context context, Page page){
        LinearLayout pageLayout = new LinearLayout(context);
        List<Row> rows = page.getRows();
        for(Row row : rows){
            pageLayout.addView(CreateRowLayout(context, row));
        }
        return pageLayout;
    }
}
