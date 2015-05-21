package de.teambluebaer.patientix.helper;

import android.content.Context;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import java.util.List;

import de.teambluebaer.patientix.xmlParser.Commentar;
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

    private boolean hasComBut = false;

    public LayoutCreater(){

    }


    public LinearLayout CreateRowLayout(Context context, Row row){
        LinearLayout rowLayout = new LinearLayout(context);
        List<Element> elements = row.getElements();
        RadioGroup radioGroup = new RadioGroup(context);
        radioGroup.setOrientation(LinearLayout.HORIZONTAL);
        for(Element element: elements){
            if(element instanceof Radio){
                Radio radio = (Radio)element;
                radio.addToView(context, rowLayout, radioGroup);

            }else {
                element.addToView(context, rowLayout);
            }
            if(element instanceof Commentar && !hasComBut){
                ((Commentar) element).addCommentarField(context,rowLayout);
                hasComBut = true;
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
            hasComBut = false;
        }

    }

    public void CreatListLayout(Context context ,LinearLayout layout){


        List<Page> pageList = Constants.globalMetaandForm.getForm().getPageList();

        for(Page page: pageList){
            List<Row> rows = page.getRows();
            for(Row row : rows){
                layout.addView(CreateRowLayout(context, row));
            }
        }

    }

}
