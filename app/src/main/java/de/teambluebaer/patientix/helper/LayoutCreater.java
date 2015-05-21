package de.teambluebaer.patientix.helper;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import java.util.List;

import de.teambluebaer.patientix.xmlParser.Element;
import de.teambluebaer.patientix.xmlParser.Form;
import de.teambluebaer.patientix.xmlParser.MetaData;
import de.teambluebaer.patientix.xmlParser.Page;
import de.teambluebaer.patientix.xmlParser.Radio;
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
        RadioGroup radioGroup = new RadioGroup(context);
        radioGroup.setOrientation(LinearLayout.HORIZONTAL);
        for(Element element: elements){
            if(element.getClass().equals(new Radio("").getClass())){
                Radio radio = (Radio)element;
                radio.addToView(context, rowLayout, radioGroup);

            }else {
                element.addToView(context, rowLayout);
            }

        }
        if(radioGroup.getChildCount()!=0){
            rowLayout.addView(radioGroup);
        }
        return rowLayout;
    }

    public void CreatPageLayout(Context context, Page page, LinearLayout pageLayout){
        pageLayout.removeAllViews();
        List<Row> rows = page.getRows();
        for(Row row : rows){
            pageLayout.addView(CreateRowLayout(context, row));
        }
    }

    public void CreatListLayout(Context context ,LinearLayout layout){
        MetaData meta = Constants.globalMetaandForm.getMeta();
        Form form = Constants.globalMetaandForm.getForm();

        LinearLayout linear = new LinearLayout(context);

        CreatPageLayout(context, form.getFirstPage(), linear);
        layout.addView(linear);
        for(int pageiterator = 1; pageiterator < form.getLastPage();pageiterator++){
            CreatPageLayout(context,form.getNextPage(),linear);
            layout.addView(linear);

        }
    }

}
