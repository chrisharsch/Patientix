package de.teambluebaer.patientix.xmlParser;

import android.content.Context;
import android.widget.LinearLayout;

/**
 * Created by Simon on 06.05.2015.
 *
 * Represents a Image that could insert into a <code>Row</code>
 * @see Row
 * @see Element
 */
public class Image implements Element {
    private String imageSource;

    /**
     * Constructor
     * @param src URL-String represents Location of the Image you want to add
     */
    public Image(String src){
        imageSource = src;
    }

    @Override
    public void addToView(Context context, LinearLayout layout) {
        //TODO How to get Image by URL

    }

    public String toXMLString() {
        String xmlString = new String();
        xmlString = xmlString + "<picture/ ";
        xmlString = xmlString + "src=\"" + this.imageSource + "\" ";
        xmlString = xmlString + ">";

        return xmlString;
    }

    public String getImageSource() {
        return imageSource;
    }
}
