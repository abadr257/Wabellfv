package com.clixifi.wabell.ui.register;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.clixifi.wabell.R;
import com.clixifi.wabell.databinding.ActivityRegisterScreenBinding;
import com.clixifi.wabell.ui.Adapters.PagerAdapter;
import com.clixifi.wabell.ui.aboutUs.AboutUs;
import com.clixifi.wabell.ui.code.VerificationCodeScreen;
import com.clixifi.wabell.ui.login.LoginScreen;
import com.clixifi.wabell.ui.privecy.PrivecyScreen;
import com.clixifi.wabell.ui.terms.TermsScreen;
import com.clixifi.wabell.utils.IntentUtilies;
import com.clixifi.wabell.utils.LocaleManager;
import com.google.android.material.tabs.TabLayout;

public class RegisterScreen extends AppCompatActivity {
    ActivityRegisterScreenBinding binding;
    MyHandlers handlers;
    PagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register_screen);
        handlers = new MyHandlers(this);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        binding.setHandlers(handlers);
        configTabs();
    }

    private void configTabs() {
        adapter = new PagerAdapter(getSupportFragmentManager(), RegisterScreen.this);
        binding.tabLayout.setupWithViewPager(binding.pager);
        binding.pager.setAdapter(adapter);
    }

    public void goToLogin() {
        IntentUtilies.openActivity(RegisterScreen.this, LoginScreen.class);
        finish();
    }

    public void terms() {
        IntentUtilies.openActivity(RegisterScreen.this, TermsScreen.class);
    }

    public void privacy() {
        IntentUtilies.openActivity(RegisterScreen.this, PrivecyScreen.class);
    }

    public void openVerificationScreen(Bundle bundle) {
        IntentUtilies.openActivityWithBundle(RegisterScreen.this, VerificationCodeScreen.class, bundle);
        finish();
    }

    public class MyHandlers {
        Context context;

        public MyHandlers(Context context) {
            this.context = context;
        }
        public void back(View view){
            onBackPressed();
        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleManager.onAttach(base));
    }
}