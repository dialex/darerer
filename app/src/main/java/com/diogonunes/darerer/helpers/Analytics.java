package com.diogonunes.darerer.helpers;

import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.ContentViewEvent;

public class Analytics {
    /**
     * Log event using Fabric.io for Answers analytics.
     *
     * @param eventName
     * @param eventType
     * @param eventId
     */
    public static void logEvent(String eventName, String eventType, int eventId) {
        Answers.getInstance().logContentView(new ContentViewEvent()
                .putContentName(eventName)
                .putContentType(eventType)
                .putContentId(Integer.toString(eventId)));
    }
}
