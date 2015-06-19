package de.teambluebaer.patientix.kioskMode;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import de.teambluebaer.patientix.activities.LoginActivity;

public class BootReceiver extends BroadcastReceiver {

    /**
     * This method checks if the app is displayed when the screen
     * wakes up
     *
     * @param context context to set new Activity
     * @param intent  to set as new task
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent myIntent = new Intent(context, LoginActivity.class);
        myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(myIntent);
    }
}