package de.teambluebaer.patientix.helper;

import android.os.Handler;
import android.widget.Button;

import de.teambluebaer.patientix.R;

/**
 * Created by chris on 13.05.15.
 * This class supports the feedback of the buttons. The flash
 * means the background of the buttons is changes when you press
 * the buttons.
 */
public class Flasher {

    /**
     * This method does the feedback of the buttons.The feedback
     * of the buttons is described as flash.There are 3 kinds
     * of buttons: 1x1, 1x3 and 1x5. All buttons got to images
     * as background. The background is changed in a Handler() to
     * display that the button is pressed.
     *
     * @param myBtnToFlash Button which should be flashed
     * @param size         The size of the button which should be flashed
     */

    public static void flash(final Button myBtnToFlash, String size) {

        if (size.equals("1x1")) {
            myBtnToFlash.setBackgroundResource(R.drawable.button1x1aktiv);
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    myBtnToFlash.setBackgroundResource(R.drawable.button1x1normal);
                }
            }, 25);
            System.gc();
        } else if (size.equals("1x3")) {
            myBtnToFlash.setBackgroundResource(R.drawable.button1x3aktiv);
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    myBtnToFlash.setBackgroundResource(R.drawable.button1x3normal);
                }
            }, 25);
            System.gc();
        } else if (size.equals("1x5")) {
            myBtnToFlash.setBackgroundResource(R.drawable.button1x5aktiv);
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    myBtnToFlash.setBackgroundResource(R.drawable.button1x5normal);
                }
            }, 25);
            System.gc();
        }
    }
}
