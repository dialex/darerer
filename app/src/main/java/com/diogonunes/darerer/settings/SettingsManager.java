package com.diogonunes.darerer.settings;

import android.app.Activity;
import android.content.SharedPreferences;
import android.view.MenuItem;

import com.diogonunes.darerer.R;

import java.util.HashMap;

public class SettingsManager {
    private static final String SETTINGS_NAME = "PreferredSettings";
    private HashMap<Integer, Setting> _managedSettings;
    private HashMap<Integer, String> _mapIdToPreferenceName;

    public SettingsManager() {
        initMapIdToPreferenceName();
        initManagedSettings();
    }

    private void initManagedSettings() {
        _managedSettings = new HashMap<>();

        for (int settingId : _mapIdToPreferenceName.keySet()) {
            String sharedPrefName = _mapIdToPreferenceName.get(settingId);
            setSetting(settingId, new Setting(sharedPrefName));
        }
    }

    private void initMapIdToPreferenceName() {
        _mapIdToPreferenceName = new HashMap<>();
        _mapIdToPreferenceName.put(R.id.settings_notification_daily, "dailyNotifications");
    }

    public SharedPreferences getSharedPreferences(Activity activity) {
        return activity.getSharedPreferences(SETTINGS_NAME, 0);
    }

    public Setting getSetting(int settingId) {
        return _managedSettings.get(settingId);
    }

    private Setting setSetting(int settingId, Setting setting) {
        return _managedSettings.put(settingId, setting);
    }

    public MenuItem getSettingMenuItem(int settingId) {
        return getSetting(settingId).getMenuItem();
    }

    public void setSettingMenuItem(int settingId, MenuItem menuItem) {
        getSetting(settingId).setMenuItem(menuItem);
    }

//    public Object getDefaultValue(Setting setting) {
//        setting.
//        switch (settingId) {
//            case R.id.settings_notification_daily:
//                return true;
//            default:
//                return null;
//        }
//    }

    public Object getSettingValue(int settingId) {
        return getSetting(settingId).getValue();
    }

    public void setSettingValue(int settingId, Object value) {
        getSetting(settingId).setValue(value);
    }

}
