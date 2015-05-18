package de.teambluebaer.patientix.helper;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import java.util.List;

import de.teambluebaer.patientix.xmlParser.Element;
import de.teambluebaer.patientix.xmlParser.Page;
import de.teambluebaer.patientix.xmlParser.Radio;
import de.teambluebaer.patientix.xmlParser.Row;

/**
 * Created by Simon on 08.05.2015.
 */
public class LayoutCreater {

    //private MetaandForm metaandForm;

    public LayoutCreater(/*MetaandForm metaandForm*/){
        //this.metaandForm = metaandForm;
    }


    public LinearLayout CreateRowLayout(Context context, Row row){
        LinearLayout rowLayout = new LinearLayout(context);
        List<Element> elements = row.getElements();
        RadioGroup radioGroup = new RadioGroup(context);
        for(Element element: elements){
            if(element.getClass().equals("Radio")){
                Radio radio = (Radio)element;
                radio.addToView(context, rowLayout, radioGroup);
            }else {
                element.addToView(context, rowLayout);
            }

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
