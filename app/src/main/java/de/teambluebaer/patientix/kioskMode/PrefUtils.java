package de.teambluebaer.patientix.kioskMode;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by chris on 30.05.2015.
 */
public class PrefUtils {
    private static final String PREF_KIOSK_MODE = "pref_kiosk_mode";

    /**
     * In this method save the state of the Kiosk-Mode and return false if its disabled
     * or true if the Kiosk-Mode is enabled
     * @param context of the current Activity
     * @return true or false for the state of Kiosk-Mode
     */

    public static boolean isKioskModeActive(final Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getBoolean(PREF_KIOSK_MODE, false);
    }

    /**
     * With this method the Kiosk-Mode can be enabled or disabled
     * @param active parameter to set true for enable Kiosk-Mode
     * @param context of the current Activity
     */
    public static void setKioskModeActive(final boolean active, final Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putBoolean(PREF_KIOSK_MODE, active).commit();
    }
}
