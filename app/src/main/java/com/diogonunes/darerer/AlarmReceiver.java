package com.diogonunes.darerer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AlarmReceiver extends BroadcastReceiver {
    private static final String LOG_TAG = AlarmReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(LOG_TAG, "Broadcast received, now handling callback.");
        Intent dailyNotifService = new Intent(context, DailyNotificationService.class);
        context.startService(dailyNotifService);
        Log.d(LOG_TAG, "Daily notification service started.");
    }
}
