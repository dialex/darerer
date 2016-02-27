package com.diogonunes.darerer;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    private Toolbar _toolbar;
    private ViewPager _viewPager;
    private TabLayout _tabLayout;
    private Map<Integer, Fragment> _tabFragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initActivity();

        setSupportActionBar(_toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) supportActionBar.setDisplayHomeAsUpEnabled(true);

        initViewPager(_viewPager);
        _tabLayout.setupWithViewPager(_viewPager);
        initTabCaption();

        if (_tabLayout != null) _tabLayout.getTabAt(1).select(); //default tab
    }

    // Event Handling

    public void onClickFab(View view) {
        Fragment currentTabFragment = _tabFragments.get(_viewPager.getCurrentItem());

        if (currentTabFragment == null) {
            return;
        } else if (currentTabFragment instanceof FragmentKind) {
            ((FragmentKind) currentTabFragment).fabOnClick();
        } else if (currentTabFragment instanceof FragmentNice) {
            ((FragmentNice) currentTabFragment).fabOnClick();
        } else if (currentTabFragment instanceof FragmentNaughty) {
            ((FragmentNaughty) currentTabFragment).fabOnClick();
        }
    }

    // Auxiliary

    private void initActivity() {
        _toolbar = (Toolbar) findViewById(R.id.toolbar);
        _viewPager = (ViewPager) findViewById(R.id.viewpager);
        _tabLayout = (TabLayout) findViewById(R.id.tabs);
        _tabFragments = new HashMap<Integer, Fragment>();
    }

    private void initTabCaption() {
        TabLayout.Tab tab;

        tab = _tabLayout.getTabAt(0);
        if (tab != null) {
            //tab.setIcon(R.drawable.ic_star_white);
            tab.setText(R.string.tab_title_kind);
        }
        tab = _tabLayout.getTabAt(1);
        if (tab != null) {
            //tab.setIcon(R.drawable.ic_face_white);
            tab.setText(R.string.tab_title_Nice);
        }
        tab = _tabLayout.getTabAt(2);
        if (tab != null) {
            //tab.setIcon(R.drawable.ic_heart_white);
            tab.setText(R.string.tab_title_Naughty);
        }
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
    }
}
