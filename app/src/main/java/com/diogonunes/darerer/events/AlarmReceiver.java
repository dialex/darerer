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
        if (_pendingIntent == null) {
            Log.d(LOG_TAG, "Start receiving alarms, every day.");
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, 9);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.add(Calendar.DATE, 1);

            _pendingIntent = PendingIntent.getBroadcast(context, 0, new Intent(context, AlarmReceiver.class), PendingIntent.FLAG_UPDATE_CURRENT);
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, _pendingIntent);
        } else
            Log.d(LOG_TAG, "Was already receiving alarms.");
    }

    public static void unregisterAlarmCallback(Context context) {
        Log.d(LOG_TAG, "Stop receiving alarms.");
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(_pendingIntent);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(LOG_TAG, "Broadcast received, time to trigger the service.");
        DailyNotificationService.getInstance().showNotification();
    }
}
