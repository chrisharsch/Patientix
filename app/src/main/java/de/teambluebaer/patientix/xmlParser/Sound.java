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
}
