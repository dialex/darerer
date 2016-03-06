package com.diogonunes.darerer.events;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Calendar;

public class AlarmReceiver extends BroadcastReceiver {
    private static final String LOG_TAG = AlarmReceiver.class.getSimpleName();
    private static PendingIntent _pendingIntent;

    public static void registerAlarmCallback(Context context) {
        Log.d(LOG_TAG, "Picking a time for the alarm.");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 9);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.add(Calendar.DATE, 1);

        Log.d(LOG_TAG, "Registering alarm for every day.");
        _pendingIntent = PendingIntent.getBroadcast(context, 0, new Intent(context, AlarmReceiver.class), PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, _pendingIntent);
    }

    public static void unregisterAlarmReceiver(Context context) {
        Log.d(LOG_TAG, "Unregistering alarm receiver.");
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(_pendingIntent);

        //TODO: disable service
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(LOG_TAG, "Broadcast received, now handling callback.");
        Intent dailyNotifService = new Intent(context, DailyNotificationService.class);
        context.startService(dailyNotifService);
        Log.d(LOG_TAG, "Daily notification service started.");
    }
}
