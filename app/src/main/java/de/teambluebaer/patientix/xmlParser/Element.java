package de.teambluebaer.patientix.xmlParser;

import android.content.Context;
import android.widget.LinearLayout;



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
 * Represents any Object that can be add to a <code>Row</code>
 * @see Row
 * @see Checkbox
 * @see Image
 * @see Input
 * @see Radio
 * @see Sound
 * @see Video
 * @see WhiteSpace
 */
public interface Element {
    /**
     * put this Element into a View
     *
     * @param context
     * @param layout
     */
    public void addToView(Context context, LinearLayout layout);


    /**
     * convert an Element into an valid XML String
     *
     * @return an Element as a XML String
     */
    public String toXMLString();

    public int getCounter();


}