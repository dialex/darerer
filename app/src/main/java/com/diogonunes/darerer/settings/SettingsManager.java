package com.diogonunes.darerer.settings;

import android.app.Activity;
import android.content.SharedPreferences;
import android.view.MenuItem;

import java.util.HashMap;

public class SettingsManager {
    private static final String SETTINGS_NAME = "PreferredSettings";
    private HashMap<Id, Setting> _managedSettings;

    public SettingsManager() {
        _managedSettings = new HashMap<>();
        setSetting(Id.DAILY_NOTIFICATIONS, new Setting("dailyNotifications"));
    }

    public SharedPreferences getSharedPreferences(Activity activity) {
        return activity.getSharedPreferences(SETTINGS_NAME, 0);
    }

    public Setting getSetting(Id settingId) {
        return _managedSettings.get(settingId);
    }

    private Setting setSetting(Id settingId, Setting setting) {
        return _managedSettings.put(settingId, setting);
    }

    public MenuItem getSettingMenuItem(Id settingId) {
        return getSetting(settingId).getMenuItem();
    }

    public void setSettingMenuItem(Id settingId, MenuItem menuItem) {
        getSetting(settingId).setMenuItem(menuItem);
    }

    public Object getSettingValue(Id settingId) {
        return getSetting(settingId).getValue();
    }

    public void setSettingValue(Id settingId, Object value) {
        getSetting(settingId).setValue(value);
    }

    public enum Id {
        DAILY_NOTIFICATIONS
    }
}
