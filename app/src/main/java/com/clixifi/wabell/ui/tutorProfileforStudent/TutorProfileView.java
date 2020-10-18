package com.clixifi.wabell.ui.tutorProfileforStudent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.os.Bundle;

import com.clixifi.wabell.R;
import com.clixifi.wabell.databinding.ActivityTutorProfileViewBinding;
import com.clixifi.wabell.ui.Adapters.PagerAdapter;
import com.clixifi.wabell.ui.Adapters.ProfilePagerAdapter;
import com.clixifi.wabell.ui.register.RegisterScreen;
import com.clixifi.wabell.utils.LocaleManager;

public class TutorProfileView extends AppCompatActivity {
    ActivityTutorProfileViewBinding binding;
    TutorProfile handler;
    ProfilePagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_tutor_profile_view);
        handler = new TutorProfile(this);
        binding.setHandler(handler);
        configTabs();
    }

    private void configTabs() {
        adapter = new ProfilePagerAdapter(getSupportFragmentManager(), TutorProfileView.this);
        binding.tabLayout.setupWithViewPager(binding.pager);
        binding.pager.setAdapter(adapter);
    }

    public class TutorProfile {
        Context context;

        public TutorProfile(Context context) {
            this.context = context;
        }

    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleManager.onAttach(base));
    }
}