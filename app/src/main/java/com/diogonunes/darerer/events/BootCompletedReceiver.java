package com.diogonunes.darerer.events;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BootCompletedReceiver extends BroadcastReceiver {
    private static final String LOG_TAG = BootCompletedReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(LOG_TAG, "Broadcast received will be handled by AlarmReceiver.");
        //TODO: implement BootCompletedReceiver
//        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
//            AlarmReceiver.registerAlarmCallback(context);
//        }
    }
}