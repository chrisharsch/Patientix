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
 * <p>
 * Represents a WhiteSpace that could insert into a <code>Row</code>
 *
 * @see Row
 * @see Element
 */
public class WhiteSpace implements Element {

    private String param;
    private int counter;

    /**
     * Constructor
     */
    public WhiteSpace() {
        param = "";
        counter = 0;
    }

    @Override
    public int getCounter() {
        return counter;
    }

    @Override
    public void addToView(Context context, LinearLayout layout) {

    }

    public String toXMLString() {
        return "";
    }

    public String getParam() {
        return param;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WhiteSpace that = (WhiteSpace) o;

        return !(param != null ? !param.equals(that.param) : that.param != null);

    }

    @Override
    public int hashCode() {
        return param != null ? param.hashCode() : 0;
    }
}
