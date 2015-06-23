package de.teambluebaer.patientix.xmlParser;

import android.content.Context;
import android.util.TypedValue;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;

import de.teambluebaer.patientix.helper.TextSize;




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
 * Represents a Image that could insert into a <code>Row</code>
 * @see
 * @see Element
 */
public class Image implements Element {
    private String imageSource;
    private int counter;

    /**
     * Constructor
     *
     * @param src URL-String represents Location of the Image you want to add
     */
    public Image(String src) {
        counter = 10;
        if(src != null && !src.isEmpty()){
            imageSource = src;
        }else{
            imageSource = "";
        }
    }

    /**
     * Add a image to a view
     * @param context
     * @param layout
     */
    @Override
    public void addToView(Context context, LinearLayout layout) {

        if (!imageSource.isEmpty()) {
            try {
                WebView image = new WebView(context);
                image.loadUrl(imageSource);
                layout.addView(image);
            }catch (Exception e){
                TextView textView = new TextView(context);
                textView.setText("Das Bild konnte nicht geladen werden, bitte drücken Sie einmal Zurück ond Weiter um diese Seite erneut zu laden");
                textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, TextSize.SUBTITEL.zoomedSize);

                layout.addView(textView);
            }
        }
    }

    /**
     * create a XML-String
     * @return xmlString
     */
    public String toXMLString() {
        String xmlString = new String();
        xmlString = xmlString + "<picture ";
        xmlString = xmlString + "src=\"" + this.imageSource + "\" ";
        xmlString = xmlString + "/>";

        return xmlString;
    }

    /**
     * Getter from IamgeSource
     * @return imageSource
     */
    public String getImageSource() {
        return imageSource;
    }

    /**
     * Compares objects
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Image image = (Image) o;

        return !(imageSource != null ? !imageSource.equals(image.imageSource) : image.imageSource != null);
    }

    @Override
    public int hashCode() {
        return imageSource != null ? imageSource.hashCode() : 0;
    }

    @Override
    public int getCounter() {
        return counter;
    }
}

