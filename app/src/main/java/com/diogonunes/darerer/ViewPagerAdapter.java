package com.diogonunes.darerer;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private final List<Fragment> _fragmentList;
    private final List<String> _fragmentTitleList;

    public ViewPagerAdapter(FragmentManager manager) {
        super(manager);

        _fragmentList = new ArrayList<>();
        _fragmentTitleList = new ArrayList<>();
    }

    @Override
    public Fragment getItem(int position) {
        return _fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return _fragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return _fragmentTitleList.get(position);  // display icon and text, or return null to display only icon
    }

    public void addFragment(Fragment fragment, String title) {
        _fragmentList.add(fragment);
        _fragmentTitleList.add(title);
    }
}