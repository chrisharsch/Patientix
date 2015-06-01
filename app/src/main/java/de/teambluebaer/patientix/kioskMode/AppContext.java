package de.teambluebaer.patientix.kioskMode;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.PowerManager;

/**
 * Created by chris on 29.05.2015.
 */
public class AppContext extends Application {

    private AppContext instance;
    private PowerManager.WakeLock wakeLock;
    private OnScreenOffReceiver onScreenOffReceiver;

    /**
     * This method says what should happen on creation of the new screen
     */
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        registerKioskModeScreenOffReceiver();
        startKioskService();
    }

    /**
     * Registers that the Screen is of and set the Intent to be
     * shown after wakelock
     */
    private void registerKioskModeScreenOffReceiver() {
        // register screen off receiver
        final IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_OFF);
        onScreenOffReceiver = new OnScreenOffReceiver();
        registerReceiver(onScreenOffReceiver, filter);
    }

    /**
     * Says the App to stay on if the screen is locked.
     *
     * @return Wakelock to show app
     */
    public PowerManager.WakeLock getWakeLock() {
        if (wakeLock == null) {
            // lazy loading: first call, create wakeLock via PowerManager.
            PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
            wakeLock = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, "wakeup");
        }
        return wakeLock;
    }

    /**
     * Will start Kiosk mode when the app is closed.
     */
    private void startKioskService() {
        startService(new Intent(this, KioskService.class));
    }
}