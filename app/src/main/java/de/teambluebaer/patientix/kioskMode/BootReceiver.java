package de.teambluebaer.patientix.kioskMode;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import de.teambluebaer.patientix.activities.LoginActivity;

public class BootReceiver extends BroadcastReceiver {

    /**
     *
     * @param context
     * @param intent
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent myIntent = new Intent(context, LoginActivity.class);
        myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(myIntent);
    }
}