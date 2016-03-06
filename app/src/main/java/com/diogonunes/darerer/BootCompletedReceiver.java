package com.diogonunes.darerer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BootCompletedReceiver extends BroadcastReceiver {
    private static final String LOG_TAG = BootCompletedReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(LOG_TAG, "Broadcast received, now handling.");
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            AlarmReceiver.registerAlarmCallback(context);
        }
    }
}