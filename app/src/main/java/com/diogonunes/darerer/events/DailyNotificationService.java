package com.diogonunes.darerer.events;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.IBinder;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.diogonunes.darerer.R;
import com.diogonunes.darerer.StringRoulette;

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
        Log.d(LOG_TAG, "Create notification and display it to the user.");
        Notification dailyNotif = createNotification(getApplicationContext());
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, dailyNotif);
    }

    private Notification createNotification(Context context) {
        Resources rootResources = context.getResources();

        // Get text
        StringRoulette niceActions = new StringRoulette(rootResources.getStringArray(R.array.nice_challenges_actions));
        StringRoulette niceModifiers = new StringRoulette(rootResources.getStringArray(R.array.nice_challenges_modifiers));
        String challengeText = niceActions.roll() + " » " + niceModifiers.roll();

        // Get notification
        NotificationCompat.Builder notifBuilder = new NotificationCompat.Builder(context);
        notifBuilder.setColor(ContextCompat.getColor(context, R.color.colorNicePrimary));
        notifBuilder.setSmallIcon(R.drawable.ic_face_white);
        notifBuilder.setContentTitle(rootResources.getString(R.string.dialog_notification_title));
        notifBuilder.setContentText(challengeText);

        return notifBuilder.build();
    }
}
