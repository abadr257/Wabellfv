package com.clixifi.wabell.ui.Adapters;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.clixifi.wabell.R;
import com.clixifi.wabell.ui.registerStudent.RegisterStudent;
import com.clixifi.wabell.ui.registerTutor.RegisterTutor;
import com.clixifi.wabell.ui.tutorAbout.TutorAboutFragment;
import com.clixifi.wabell.ui.tutorReviews.TutorReviewsFragment;

public class ProfilePagerAdapter extends FragmentPagerAdapter {
    Context context;

    public ProfilePagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if (position == 0) {
            fragment = new TutorAboutFragment();
        } else if (position == 1) {
            fragment = new TutorReviewsFragment();
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
            title = context.getResources().getString(R.string.tuAbout);
        } else if (position == 1) {
            title = context.getResources().getString(R.string.tuReviews);
        }

        return title;
    }
}