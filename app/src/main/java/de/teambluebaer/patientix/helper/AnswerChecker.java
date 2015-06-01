package de.teambluebaer.patientix.helper;

import de.teambluebaer.patientix.xmlParser.Checkbox;
import de.teambluebaer.patientix.xmlParser.Element;
import de.teambluebaer.patientix.xmlParser.Input;
import de.teambluebaer.patientix.xmlParser.Page;
import de.teambluebaer.patientix.xmlParser.Radio;
import de.teambluebaer.patientix.xmlParser.Row;

/**
 * Created by Simon on 01.06.2015.
 */
public class AnswerChecker {



    public static boolean isEverythingAnswert(){

        boolean everythingIsAnswert = true;
        for (Page page:Constants.globalMetaandForm.getForm().getPageList()){
            for(Row row: page.getRows()){
                if(!row.getElements().contains(Element.class)){
                    for(Element element:row.getElements()){
                        if(element instanceof Radio){
                            Radio radio =(Radio)element;
                            if(radio.isChecked()){
                                everythingIsAnswert = true;
                                break;
                            }else{
                                everythingIsAnswert = false;
                            }

                        }else if(element instanceof Checkbox){
                            //Checkbox dont have to be checked

                        }else if(element instanceof Input){
                            //optional Imput ??
                        }
                    }
                }
            }
        }
        return everythingIsAnswert;
    }


}
