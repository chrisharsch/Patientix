package de.teambluebaer.patientix.helper;


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
public class XMLCleaner {

    public static String cleanStringForXML(String toClean){
        String cleandString = "";

        for (char stringChar : toClean.toCharArray()){
            for(char testChar : Constants.ALLOWEDCHARS ){
                if(stringChar == testChar){
                    cleandString = cleandString + stringChar;
                }
            }
        }

        return cleandString;
    }
}
