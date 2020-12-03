package com.clixifi.wabell.ui.Adapters;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.clixifi.wabell.R;
import com.clixifi.wabell.ui.callsTap.CallsFragmentTap;
import com.clixifi.wabell.ui.messagesTap.MessagesFragmentTap;

public class MessagePagerAdapter extends FragmentPagerAdapter {
    Context context;

    public MessagePagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if (position == 0) {
            fragment = new MessagesFragmentTap();
        } else if (position == 1) {
            fragment = new CallsFragmentTap();
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
            title = context.getResources().getString(R.string.reMessages);
        } else if (position == 1) {
            title = context.getResources().getString(R.string.reCalls);

        }

        return title;
    }
}