package de.teambluebaer.patientix.helper;

import android.os.Handler;
import android.widget.Button;

import de.teambluebaer.patientix.R;

/**
 * Created by chris on 13.05.15.
 */
public class Flasher {

   /* public static void flash(final Button myBtnToFlash, String size){
        flash(myBtnToFlash, size,);
    }*/
    public static void flash(final Button myBtnToFlash, String size){

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
