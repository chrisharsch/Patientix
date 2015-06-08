package de.teambluebaer.patientix.helper;

import de.teambluebaer.patientix.xmlParser.Element;
import de.teambluebaer.patientix.xmlParser.Page;
import de.teambluebaer.patientix.xmlParser.Radio;
import de.teambluebaer.patientix.xmlParser.Row;

/**
 * Created by Simon on 01.06.2015.
 *
 * this Class holds a Method to check if every radiogroup jn the currnd Form has a answer choosen
 */
public class AnswerChecker {


    /**
     * Checks the in Constants.GLOBALMETAANDFORM saved Form for answered Radiobuttons
     * @return boolean is true if every Radiogroup in the currend form is answered
     */
    public static boolean isEverythingAnswert() {

        boolean everythingIsAnswert = true;
        for (Page page : Constants.GLOBALMETAANDFORM.getForm().getPageList()) {
            if (!everythingIsAnswert) {
                break;
            }
            for (Row row : page.getRows()) {
                if (!everythingIsAnswert) {
                    break;
                }
                for (Element element : row.getElements()) {
                    if (element instanceof Radio) {
                        Radio radio = (Radio) element;
                        if (radio.isChecked()) {
                            everythingIsAnswert = true;
                            break;
                        } else {
                            everythingIsAnswert = false;
                        }
                    }
                    if (everythingIsAnswert && element instanceof Radio) {
                        break;
                    }
                }

            }
        }
        return everythingIsAnswert;
    }
}
