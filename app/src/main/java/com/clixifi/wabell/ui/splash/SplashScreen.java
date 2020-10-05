package com.clixifi.wabell.ui.splash;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.clixifi.wabell.R;
import com.clixifi.wabell.databinding.ActivityMainBinding;
import com.clixifi.wabell.ui.welcome.WelcomeScreen;
import com.clixifi.wabell.utils.IntentUtilies;
import com.clixifi.wabell.utils.LocaleManager;
import com.clixifi.wabell.utils.StaticMethods;

public class SplashScreen extends AppCompatActivity {

    ActivityMainBinding binding;
    MyHandler handler;
    Handler mHandler;
    String lang = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //StaticMethods.statusBar(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        handler = new MyHandler(this);
        binding.setHandlers(handler);
        binding.setOnSplash(false);
        mHandler = new Handler();
        lang = LocaleManager.getLanguage(this);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                binding.setOnSplash(true);
            }
        }, 3000);

    }

    public class MyHandler {
        Context context;

        public MyHandler(Context context) {
            this.context = context;
        }

        public void arabic(View view) {

            updateViews("ar");

        }

        public void english(View view) {

            updateViews("en");

        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleManager.onAttach(base));
    }

    private void updateViews(String languageCode) {
        LocaleManager.setLocale(SplashScreen.this, languageCode);
        IntentUtilies.openActivity(SplashScreen.this, WelcomeScreen.class);
    }
}