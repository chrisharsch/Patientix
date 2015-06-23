package de.teambluebaer.patientix.kioskMode;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import de.teambluebaer.patientix.activities.LoginActivity;

/**
 * Copyright 2015 By Authors
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * Author:
 * Andreas Schrade
 */
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