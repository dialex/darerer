package com.diogonunes.darerer.settings;

import android.view.MenuItem;

public class Setting {
    private MenuItem _menuItem;
    private Object _value;
    private String _sharedPreferenceKey;

    public Setting() {
    }

    public Setting(String sharedPreferenceKey) {
        this();
        setSharedPrefKey(sharedPreferenceKey);
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