package de.teambluebaer.patientix.kioskMode;

import android.app.ActivityManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.util.List;
import java.util.concurrent.TimeUnit;

import de.teambluebaer.patientix.helper.Constants;

public class KioskService extends Service {

    private static final long INTERVAL = TimeUnit.SECONDS.toMillis(2); // periodic interval to check in seconds -> 1 seconds
    private static final String TAG = KioskService.class.getSimpleName();

    private Thread t = null;
    private Context ctx = null;
    private boolean running = false;

    /**
     * This method makes a Log message that the Kiosk-Mode is disabled
     */
    @Override
    public void onDestroy() {
        Log.i(TAG, "Stopping service 'KioskService'");
        running = false;
        super.onDestroy();
    }

    /**
     * @param intent  intent to show
     * @param flags   flags to set
     * @param startId not used just because of override
     * @return
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "Starting service 'KioskService'");
        running = true;
        ctx = this;

        // start a thread that periodically checks if your app is in the foreground
        t = new Thread(new Runnable() {
            @Override
            public void run() {
                do {
                    handleKioskMode();
                    try {
                        Thread.sleep(INTERVAL);
                    } catch (InterruptedException e) {
                        Log.i(TAG, "Thread interrupted: 'KioskService'");
                    }
                } while (running);
                stopSelf();
            }
        });

        t.start();
        return Service.START_NOT_STICKY;
    }

    /**
     * This method handles the Kiosk-Mode and try to restore the app if
     * it is in the background and and activated
     */
    private void handleKioskMode() {
        if (PrefUtils.isKioskModeActive(ctx)) {
            if (isInBackground()) {
                restoreApp();
            }
        }
    }

    /**
     * This method checks if the app runs in the background
     *
     * @return true for app in background
     */
    private boolean isInBackground() {
        ActivityManager am = (ActivityManager) ctx.getSystemService(Context.ACTIVITY_SERVICE);

        List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
        ComponentName componentInfo = taskInfo.get(0).topActivity;
        return (!ctx.getApplicationContext().getPackageName().equals(componentInfo.getPackageName()));
    }

    /**
     * This method restores the App to the last Activity
     */
    private void restoreApp() {
        // Restart activity
        Log.d("App","Restarted");
        Intent i = new Intent(ctx, Constants.CURRENTACTIVITY.getClass());
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ctx.startActivity(i);
    }

    /**
     * This Method is just use to disable the super function
     *
     * @param intent nor used
     * @return everytime null
     */
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}