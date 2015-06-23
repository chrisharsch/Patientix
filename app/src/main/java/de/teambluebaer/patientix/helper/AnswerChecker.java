package de.teambluebaer.patientix.helper;

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
