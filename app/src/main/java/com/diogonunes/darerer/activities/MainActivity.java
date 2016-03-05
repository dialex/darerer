package com.diogonunes.darerer.activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.diogonunes.darerer.R;
import com.diogonunes.darerer.ViewPagerAdapter;
import com.diogonunes.darerer.fragments.FragmentKind;
import com.diogonunes.darerer.fragments.FragmentNaughty;
import com.diogonunes.darerer.fragments.FragmentNice;
import com.diogonunes.darerer.settings.Setting;
import com.diogonunes.darerer.settings.SettingsManager;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private SettingsManager _settings = new SettingsManager();

    private PendingIntent _pendingIntent;   // Used to register alarm manager
    private AlarmManager _alarmManager;     // Running AlarmManager instance
    private BroadcastReceiver _receiver;    // Callback method for AlarmManager event

    private Toolbar _toolbar;
    private ViewPager _viewPager;
    private TabLayout _tabLayout;
    private SparseArray<Fragment> _tabFragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initActivity();

        setSupportActionBar(_toolbar);

        initViewPager(_viewPager);
        _tabLayout.setupWithViewPager(_viewPager);

        //displayTabIcons();
        _tabLayout.getTabAt(1).select(); //default tab
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        _settings.setSettingMenuItem(R.id.settings_notification_daily, menu.findItem(R.id.settings_notification_daily));

        restorePreferences();
        registerAlarmBroadcast();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.settings_notification_daily:
                onClickSettingsDailyNotifications(item);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        savePreferences();
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(_receiver);
        super.onDestroy();
    }

    // Event Handling

    public void onClickFab(View view) {
        Fragment currentTabFragment = _tabFragments.get(_viewPager.getCurrentItem());

        // dispatch event to respective fragment
        if (currentTabFragment == null) {
            // do nothing
        } else if (currentTabFragment instanceof FragmentKind) {
            ((FragmentKind) currentTabFragment).fabOnClick();
        } else if (currentTabFragment instanceof FragmentNice) {
            ((FragmentNice) currentTabFragment).fabOnClick();
        } else if (currentTabFragment instanceof FragmentNaughty) {
            ((FragmentNaughty) currentTabFragment).fabOnClick();
        }

        //debug //TODO: set alarm
        scheduleAlarm();
    }

    private void onClickSettingsDailyNotifications(MenuItem item) {
        item.setChecked(!item.isChecked());
        _settings.setSettingValue(R.id.settings_notification_daily, item.isChecked());
    }

    // Settings

    private void restorePreferences() {
        SharedPreferences sharedPrefs = _settings.getSharedPreferences(this);
        Setting currentSetting;
        Object lastSavedValue;

        currentSetting = _settings.getSetting(R.id.settings_notification_daily);
        lastSavedValue = sharedPrefs.getBoolean(currentSetting.getSharedPrefKey(), (Boolean) currentSetting.getDefaultValue());
        currentSetting.setValue(lastSavedValue);
    }

    private void savePreferences() {
        SharedPreferences.Editor editor = _settings.getSharedPreferences(this).edit();
        Setting currentSetting;

        currentSetting = _settings.getSetting(R.id.settings_notification_daily);
        if (currentSetting.getValue() != null)
            editor.putBoolean(currentSetting.getSharedPrefKey(), (Boolean) currentSetting.getValue());

        editor.commit();
    }

    // Notifications

    private void scheduleAlarm() {
        // Set the alarm
//        Calendar calendar = Calendar.getInstance();
//        calendar.set(Calendar.HOUR_OF_DAY, 7);
//        calendar.set(Calendar.MINUTE, 0);
//        calendar.set(Calendar.SECOND, 0);
//        calendar.add(Calendar.DAY_OF_YEAR, 1);  // tomorrow
        _alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 5000, _pendingIntent);

//        AlarmManager alarmMan = (AlarmManager) getSystemService(ALARM_SERVICE);
//        PendingIntent pendingIntent = PendingIntent.getService(this, 0, new Intent(this, NotifyService.class), 0);
//        alarmMan.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pi);
    }

    private void registerAlarmBroadcast() {
        if ((Boolean) _settings.getSettingValue(R.id.settings_notification_daily) == false) return;

        Log.i("registerAlarmBroadcast", "Registering Intent.registerAlarmBroadcast");

        // This is the callback which will be called when the alarm fires
        _receiver = new BroadcastReceiver() {
            private static final String TAG = "Alarm Example Receiver";

            @Override
            public void onReceive(Context context, Intent intent) {
                Log.i(TAG, "BroadcastReceiver::OnReceive() >>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
                Toast.makeText(context, "Congrats! Your Alarm time has been reached", Toast.LENGTH_LONG).show();
            }
        };

        // register the alarm broadcast here
        registerReceiver(_receiver, new IntentFilter("com.diogonunes.darerer"));
        _pendingIntent = PendingIntent.getBroadcast(this, 0, new Intent("com.diogonunes.darerer"), 0);
        _alarmManager = (AlarmManager) (this.getSystemService(Context.ALARM_SERVICE));
    }

    private void UnregisterAlarmBroadcast() {
        _alarmManager.cancel(_pendingIntent);
        getBaseContext().unregisterReceiver(_receiver);
    }

    // Auxiliary

    private void displayTabIcons() {
        TabLayout.Tab tab;

        tab = _tabLayout.getTabAt(0);
        if (tab != null) tab.setIcon(R.drawable.ic_star_white);

        tab = _tabLayout.getTabAt(1);
        if (tab != null) tab.setIcon(R.drawable.ic_face_white);

        tab = _tabLayout.getTabAt(2);
        if (tab != null) tab.setIcon(R.drawable.ic_heart_white);
    }

    private void initActivity() {
        _toolbar = (Toolbar) findViewById(R.id.toolbar);
        _viewPager = (ViewPager) findViewById(R.id.viewpager);
        _tabLayout = (TabLayout) findViewById(R.id.tabs);
        _tabFragments = new SparseArray<>();
    }

    private void initViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        Fragment tabFragment;

        tabFragment = new FragmentKind();
        adapter.addFragment(tabFragment, getResources().getString(R.string.tab_title_kind));
        _tabFragments.put(0, tabFragment);

        tabFragment = new FragmentNice();
        adapter.addFragment(tabFragment, getResources().getString(R.string.tab_title_Nice));
        _tabFragments.put(1, tabFragment);

        tabFragment = new FragmentNaughty();
        adapter.addFragment(tabFragment, getResources().getString(R.string.tab_title_Naughty));
        _tabFragments.put(2, tabFragment);

        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(2); //disables viewpager cache, see http://goo.gl/NuQK2M
    }
}
