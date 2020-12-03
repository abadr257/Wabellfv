package com.clixifi.wabell.ui.splash;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.clixifi.wabell.R;
import com.clixifi.wabell.databinding.ActivityMainBinding;
import com.clixifi.wabell.helpers.prefs.PrefUtils;
import com.clixifi.wabell.ui.homeTutor.HomeScreen;
import com.clixifi.wabell.ui.main.MainScreen;
import com.clixifi.wabell.ui.welcome.WelcomeScreen;
import com.clixifi.wabell.utils.IntentUtilies;
import com.clixifi.wabell.utils.LocaleManager;
import com.clixifi.wabell.utils.StaticMethods;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

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
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        binding.setHandlers(handler);

       /* SimpleDateFormat dateFormatGmt = new SimpleDateFormat("dd:MM:yyyy HH:mm:ss");
        dateFormatGmt.setTimeZone(TimeZone.g("GMT"));*/
        TimeZone tz = TimeZone.getDefault();
        String timeZone = tz.getDisplayName(false, TimeZone.SHORT).substring(4, 6);
        String equ = tz.getDisplayName(false, TimeZone.SHORT).substring(3, 4);
        int time = Integer.parseInt(timeZone) ;
        Log.e("Splash Screen", "TimeZone is :" + time +"equ :"+ equ);
        StaticMethods.timeZone = time;
        StaticMethods.timeEqu = equ ;
        binding.setOnSplash(false);
        mHandler = new Handler();
        lang = LocaleManager.getLanguage(this);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (PrefUtils.getUserformation(SplashScreen.this)) {
                    IntentUtilies.openActivity(SplashScreen.this, MainScreen.class);
                } else {
                    binding.setOnSplash(true);

                }

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