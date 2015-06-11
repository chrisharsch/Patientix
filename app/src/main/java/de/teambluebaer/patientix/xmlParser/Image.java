package de.teambluebaer.patientix.xmlParser;

import android.content.Context;
import android.util.TypedValue;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;

import de.teambluebaer.patientix.helper.TextSize;


/**
 * Created by Simon on 06.05.2015.
 *
 * Represents a Image that could insert into a <code>Row</code>
 * @see
 * @see Element
 */
public class Image implements Element {
    private String imageSource;

    /**
     * Constructor
     *
     * @param src URL-String represents Location of the Image you want to add
     */
    public Image(String src) {

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
}

