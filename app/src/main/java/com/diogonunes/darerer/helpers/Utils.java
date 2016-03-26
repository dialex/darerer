package com.diogonunes.darerer.helpers;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.diogonunes.darerer.settings.Constants;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class Utils {
    private static final String LOG_TAG = Utils.class.getSimpleName();
    private static Random _randGenerator = new Random();

    /**
     * Returns a random number between two numbers.
     *
     * @param min Minimum expected number.
     * @param max Maximum expected number.
     * @return An integer between [min,max], inclusive.
     */
    public static int getRandomInteger(int min, int max) {
        int range = (max - min) + 1;
        return _randGenerator.nextInt(range) + min;
    }

    /**
     * Returns True or False, randomly.
     *
     * @return A boolean.
     */
    public static boolean getRandomBool() {
        int number = getRandomInteger(0, 1);
        return (number == 1);
    }

    /**
     * Returns True or False, randomly but slightly biased.
     *
     * @param truePercentage The probability of returning true (0-100).
     * @return A boolean.
     */
    public static boolean getRandomBool(int truePercentage) {
        if (truePercentage > 100) truePercentage = 100;
        if (truePercentage <= 0) return false;

        int number = getRandomInteger(1, 100);
        return (number <= truePercentage);
    }

    /**
     * Formats a text to be shared on social networks.
     *
     * @param text
     * @return
     */
    public static String formatForSharing(String text) {
        if ((text == null) || (text.isEmpty()))
            return text;
        else
            return String.format("\"%s\" #ChallengeAccepted #DARERER @ %s", text, "", Constants.APP_STORE_URL);
    }

    /**
     * @param layout
     * @return True if layout is not null and visible. False otherwise.
     */
    public static boolean isLayoutVisible(LinearLayout layout) {
        return (layout != null) && (layout.getVisibility() == View.VISIBLE);
    }

    /**
     * Checks if there's an internet connection. Requires permission.ACCESS_NETWORK_STATE.
     *
     * @param context
     * @return True if internet is ON. False otherwise.
     */
    public static boolean hasInternetConnection(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    /**
     * Encodes a string using MD5 algorithm.
     *
     * @param s
     * @return
     */
    public static final String md5(final String s) {
        try {
            // Create MD5 Hash
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < messageDigest.length; i++) {
                String h = Integer.toHexString(0xFF & messageDigest[i]);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            Log.e(LOG_TAG, e.getMessage());
        }
        return "";
    }

    /**
     * Returns the current device ID.
     *
     * @param activity
     * @return
     */
    public static String getDeviceId(Activity activity) {
        return Settings.Secure.getString(activity.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    /**
     * Returns the current device ID encoded in MD5 (uppercase).
     *
     * @param activity
     * @return
     */
    public static String getDeviceIdMD5(Activity activity) {
        return md5(getDeviceId(activity)).toUpperCase();
    }

    public static Notification createNotification(Context context, String title, String description, int iconId, int iconBkgColorId) {
        NotificationCompat.Builder notifBuilder = new NotificationCompat.Builder(context);

        notifBuilder.setColor(ContextCompat.getColor(context, iconBkgColorId));
        notifBuilder.setSmallIcon(iconId);
        notifBuilder.setContentTitle(title);
        notifBuilder.setContentText(description);

        return notifBuilder.build();
    }

    public static void showNotification(Context context, Notification notification) {
        showNotification(context, notification, 0);
    }

    public static void showNotification(Context context, Notification notification, int notificationId) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(notificationId, notification);
    }
}