package com.diogonunes.darerer.events;

import android.app.Notification;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.IBinder;
import android.util.Log;

import com.diogonunes.darerer.R;
import com.diogonunes.darerer.StringRoulette;
import com.diogonunes.darerer.helpers.Utils;

public class DailyNotificationService extends Service {
    private static final String LOG_TAG = DailyNotificationService.class.getSimpleName();
    private static DailyNotificationService _instance;

    public static void start(Context context) {
        if (getInstance() == null) {
            Log.d(LOG_TAG, "Starting...");
            Intent intent = new Intent(context, DailyNotificationService.class);
            context.startService(intent);
        } else
            Log.d(LOG_TAG, "Was already started.");
    }

    public static void stop(Context context) {
        Log.d(LOG_TAG, "Stopping...");
        Intent intent = new Intent(context, DailyNotificationService.class);
        context.stopService(intent);
    }

    public static DailyNotificationService getInstance() {
        return _instance;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        _instance = this;
        Log.v(LOG_TAG, "Created.");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        _instance = null;
        Log.v(LOG_TAG, "Destroyed/Stopped.");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int i = super.onStartCommand(intent, flags, startId);
        Log.v(LOG_TAG, "Started.");

        return i;
    }

    public void showNotification() {
        Context rootContext = getApplicationContext();
        Resources rootResources = rootContext.getResources();

        Log.d(LOG_TAG, "Create and show daily notification to user.");
        StringRoulette niceActions = new StringRoulette(rootResources.getStringArray(R.array.nice_challenges_actions));
        StringRoulette niceModifiers = new StringRoulette(rootResources.getStringArray(R.array.nice_challenges_modifiers));
        String challengeText = niceActions.roll() + " » " + niceModifiers.roll();
        String title = rootResources.getString(R.string.dialog_notification_title);

        Notification dailyNotif = Utils.createNotification(rootContext, title, challengeText, R.drawable.ic_face_white, R.color.colorNicePrimary);
        Utils.showNotification(rootContext, dailyNotif);
    }
}
