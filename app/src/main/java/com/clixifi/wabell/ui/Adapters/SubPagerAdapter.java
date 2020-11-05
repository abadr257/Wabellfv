package com.clixifi.wabell.ui.Adapters;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.clixifi.wabell.R;
import com.clixifi.wabell.ui.historyFragment.HistoryScreen;
import com.clixifi.wabell.ui.packagesFragment.PackagesScreen;


public class SubPagerAdapter extends FragmentPagerAdapter {
    Context context;

    public SubPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }


    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if (position == 0) {
            fragment = new PackagesScreen();
        } else if (position == 1) {
            fragment = new HistoryScreen();
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        if (position == 0) {
            title = context.getResources().getString(R.string.packages);
        } else if (position == 1) {
            title = context.getResources().getString(R.string.history);
        }

        return title;
    }
}