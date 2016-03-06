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

    private static DailyNotificationService _instance = null;

    public static boolean isActive() {
        return (_instance != null);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(LOG_TAG, "OnStartCommand.");
        int i = super.onStartCommand(intent, flags, startId);

        Log.d(LOG_TAG, "Creating daily notification.");
        Notification dailyNotif = getDailyNotification(getApplicationContext());
        dailyNotif.flags |= Notification.FLAG_AUTO_CANCEL;

        Log.d(LOG_TAG, "Displaying notification to the user.");
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, dailyNotif);

        return i;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        _instance = this;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        _instance = null;
    }

    private Notification getDailyNotification(Context context) {
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
