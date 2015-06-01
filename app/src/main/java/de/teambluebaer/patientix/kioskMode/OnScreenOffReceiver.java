package de.teambluebaer.patientix.kioskMode;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class OnScreenOffReceiver extends BroadcastReceiver {

    /**
     * This method registers that the screen is off.
     * @param context of the current activity
     * @param intent of the current activity
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        if(Intent.ACTION_SCREEN_OFF.equals(intent.getAction())){
            AppContext ctx = (AppContext) context.getApplicationContext();
            if(PrefUtils.isKioskModeActive(ctx)) {
            }
        }
    }
}