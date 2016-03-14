package com.diogonunes.darerer.helpers;

import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.ContentViewEvent;

public class Analytics {
    public static void logEvent(String eventName, String eventType, String eventId) {
        Answers.getInstance().logContentView(new ContentViewEvent()
                .putContentName(eventName)
                .putContentType(eventType)
                .putContentId(eventId));
    }
}
