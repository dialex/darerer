package com.diogonunes.darerer.settings;

import android.view.MenuItem;

public class Setting {
    private String _sharedPreferenceKey;
    private MenuItem _menuItem;
    private Object _value;
    private Object _defaultValue;

    public Setting() {
    }

    public Setting(String sharedPreferenceKey, Object defaultValue) {
        this();
        this._defaultValue = defaultValue;
        setSharedPrefKey(sharedPreferenceKey);
    }

    public Object getDefaultValue() {
        return _defaultValue;
    }

    public Object getValue() {
        return _value;
    }

    public void setValue(Object value) {
        this._value = value;

        if (getMenuItem().isCheckable() && (value instanceof Boolean))
            getMenuItem().setChecked((Boolean) value);
    }

    public MenuItem getMenuItem() {
        return _menuItem;
    }

    public void setMenuItem(MenuItem menuItem) {
        this._menuItem = menuItem;
    }

    public String getSharedPrefKey() {
        return _sharedPreferenceKey;
    }

    public void setSharedPrefKey(String value) {
        this._sharedPreferenceKey = value;
    }
}