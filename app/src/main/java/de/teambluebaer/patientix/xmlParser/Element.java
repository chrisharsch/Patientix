package de.teambluebaer.patientix.xmlParser;

import android.content.Context;
import android.widget.LinearLayout;

/**
 * Created by Simon on 06.05.2015.
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
     * @param context
     * @param layout
     */
    public void addToView(Context context, LinearLayout layout);
}
