package com.diogonunes.darerer.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.diogonunes.darerer.R;
import com.diogonunes.darerer.Utils;
import com.diogonunes.darerer.ViewPagerAdapter;
import com.diogonunes.darerer.events.AlarmReceiver;
import com.diogonunes.darerer.events.DailyNotificationService;
import com.diogonunes.darerer.fragments.FragmentKind;
import com.diogonunes.darerer.fragments.FragmentNaughty;
import com.diogonunes.darerer.fragments.FragmentNice;
import com.diogonunes.darerer.settings.Setting;
import com.diogonunes.darerer.settings.SettingsManager;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private SettingsManager _settings = new SettingsManager();

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

        _tabLayout.getTabAt(1).select(); //default tab
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        _settings.setSettingMenuItem(R.id.settings_notification_daily, menu.findItem(R.id.settings_notification_daily));

        restorePreferences();
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
            case R.id.action_share:
                onClickActionShare();
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
        super.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        outState.putInt("currentTabIndex", _viewPager.getCurrentItem());
        switch (_viewPager.getCurrentItem()) {
            case 0:
                outState.putBoolean("isChallengeOn", findViewById(R.id.layout_kind_challenge_on).getVisibility() == View.VISIBLE);
                outState.putString("challengeAction", (String) ((TextView) findViewById(R.id.card_kind_challenge_title)).getText());
                outState.putString("challengeModifier", "");
                break;
            case 1:
                outState.putBoolean("isChallengeOn", findViewById(R.id.layout_nice_challenge_on).getVisibility() == View.VISIBLE);
                outState.putString("challengeAction", (String) ((TextView) findViewById(R.id.card_nice_challenge_action_title)).getText());
                outState.putString("challengeModifier", (String) ((TextView) findViewById(R.id.card_nice_challenge_modifier_title)).getText());
                break;
            case 2:
                outState.putBoolean("isChallengeOn", findViewById(R.id.layout_naughty_challenge_on).getVisibility() == View.VISIBLE);
                outState.putString("challengeAction", (String) ((TextView) findViewById(R.id.card_naughty_challenge_title)).getText());
                outState.putString("challengeModifier", "");
                break;
        }
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
    }

    private void onClickActionShare() {
        String shareBody = Utils.formatForSharing(getCurrentChallengeText());
        if (shareBody != "") {
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "");
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
            startActivity(Intent.createChooser(sharingIntent, getString(R.string.dialog_share_caption)));
        } else {
            Toast.makeText(MainActivity.this, R.string.dialog_share_unavailable, Toast.LENGTH_SHORT).show();
        }
    }

    private String getCurrentChallengeText() {
        String challengeText = "";
        int currentTabIndex = _viewPager.getCurrentItem();

        switch (currentTabIndex) {
            case 0:
                if (Utils.isLayoutVisible((LinearLayout) findViewById(R.id.layout_kind_challenge_on)))
                    challengeText = (String) ((TextView) findViewById(R.id.card_kind_challenge_title)).getText();
                break;
            case 1:
                if (Utils.isLayoutVisible((LinearLayout) findViewById(R.id.layout_nice_challenge_on)))
                    challengeText = ((TextView) findViewById(R.id.card_nice_challenge_action_title)).getText()
                            + " " + ((TextView) findViewById(R.id.card_nice_challenge_modifier_title)).getText();
                break;
            case 2:
                if (Utils.isLayoutVisible((LinearLayout) findViewById(R.id.layout_naughty_challenge_on)))
                    challengeText = (String) ((TextView) findViewById(R.id.card_naughty_challenge_title)).getText();
                break;
        }
        return challengeText;
    }

    private void onClickSettingsDailyNotifications(MenuItem item) {
        item.setChecked(!item.isChecked());
        _settings.setSettingValue(R.id.settings_notification_daily, item.isChecked());
    }

    // Settings

    private void initAccordingToPreferences() {
        Log.d(LOG_TAG, "Initializing app according to preferences.");

        Boolean areDailyNotificationsActive = (Boolean) _settings.getSettingValue(R.id.settings_notification_daily);
        if (areDailyNotificationsActive) enableDailyNotifications();
        else disableDailyNotifications();
    }

    private void restorePreferences() {
        SharedPreferences sharedPrefs = _settings.getSharedPreferences(this);
        Setting currentSetting;
        Object lastSavedValue;

        currentSetting = _settings.getSetting(R.id.settings_notification_daily);
        lastSavedValue = sharedPrefs.getBoolean(currentSetting.getSharedPrefKey(), (Boolean) currentSetting.getDefaultValue());
        currentSetting.setValue(lastSavedValue);

        Log.d(LOG_TAG, "Preferences restored.");
        //initAccordingToPreferences(); // currently, the preferences are only relevant for the next app run
    }

    private void savePreferences() {
        SharedPreferences.Editor editor = _settings.getSharedPreferences(this).edit();
        Setting currentSetting;

        currentSetting = _settings.getSetting(R.id.settings_notification_daily);
        if (currentSetting.getValue() != null)
            editor.putBoolean(currentSetting.getSharedPrefKey(), (Boolean) currentSetting.getValue());

        editor.apply();

        Log.d(LOG_TAG, "Preferences saved.");
        initAccordingToPreferences(); // app needs to work according to the latest settings
    }

    // Notifications

    private void enableDailyNotifications() {
        DailyNotificationService.start(this);
        AlarmReceiver.registerAlarmCallback(this);

        //TODO: implement BootCompletedReceiver
//        ComponentName receiver = new ComponentName(this, BootCompletedReceiver.class);
//        getPackageManager().setComponentEnabledSetting(receiver,
//                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
//                PackageManager.DONT_KILL_APP);

        Log.d(LOG_TAG, "Daily notifications enabled.");
    }

    private void disableDailyNotifications() {
        DailyNotificationService.stop(this);
        AlarmReceiver.unregisterAlarmCallback(this);

        //TODO: implement BootCompletedReceiver
//        ComponentName receiver = new ComponentName(this, BootCompletedReceiver.class);
//        getPackageManager().setComponentEnabledSetting(receiver,
//                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
//                PackageManager.DONT_KILL_APP);

        Log.d(LOG_TAG, "Daily notifications disabled.");
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

        boolean showIconsOnly = true;

        tabFragment = new FragmentKind();
        adapter.addFragment(tabFragment, (showIconsOnly ? "" : getResources().getString(R.string.tab_title_kind)));
        _tabFragments.put(0, tabFragment);

        tabFragment = new FragmentNice();
        adapter.addFragment(tabFragment, (showIconsOnly ? "" : getResources().getString(R.string.tab_title_nice)));
        _tabFragments.put(1, tabFragment);

        tabFragment = new FragmentNaughty();
        adapter.addFragment(tabFragment, (showIconsOnly ? "" : getResources().getString(R.string.tab_title_naughty)));
        _tabFragments.put(2, tabFragment);

        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(2); //disables viewpager cache, see http://goo.gl/NuQK2M
        _tabLayout.setupWithViewPager(_viewPager);

        if (showIconsOnly) displayTabIcons();
    }
}
