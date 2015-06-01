package de.teambluebaer.patientix.kioskMode;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class OnScreenOffReceiver extends BroadcastReceiver {

    /**
     *
     * @param context
     * @param intent
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        if(Intent.ACTION_SCREEN_OFF.equals(intent.getAction())){
            AppContext ctx = (AppContext) context.getApplicationContext();
            // is Kiosk Mode active?
            if(PrefUtils.isKioskModeActive(ctx)) {
               // wakeUpDevice(ctx);
            }
        }
    }
}