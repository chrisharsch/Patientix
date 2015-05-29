package de.teambluebaer.patientix.xmlParser;

import android.content.Context;
import android.widget.LinearLayout;

/**
 * Created by Simon on 06.05.2015.
 *
 * Represents a Sound File that could insert into a <code>Row</code>
 * @see Row
 * @see Element
 */
public class Sound implements Element {
    private String soundSource;

    /**
     * Constructor
     * @param src URL-String represents Location of the Sound you want to add
     */
    public Sound(String src){
        soundSource = src;
    }

    @Override
    public void addToView(Context context, LinearLayout layout) {
        //TODO How to add sound by URL
    }

    public String getSoundSource() {
        return soundSource;
    }

    public String toXMLString(){
        String xmlString = new String();
        xmlString = xmlString + "<audio";
        xmlString = xmlString + "src=\"" + this.soundSource + "\" ";
        xmlString = xmlString + "/>";

        return xmlString;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Sound sound = (Sound) o;

        return !(soundSource != null ? !soundSource.equals(sound.soundSource) : sound.soundSource != null);

    }

    @Override
    public int hashCode() {
        return soundSource != null ? soundSource.hashCode() : 0;
    }
}
