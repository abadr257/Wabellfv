package com.clixifi.wabell.ui.secSplash;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.clixifi.wabell.R;
import com.clixifi.wabell.databinding.ActivitySecSplashBinding;
import com.clixifi.wabell.ui.login.LoginScreen;
import com.clixifi.wabell.utils.IntentUtilies;
import com.clixifi.wabell.utils.StaticMethods;

public class SecSplash extends AppCompatActivity {
    ActivitySecSplashBinding binding;
    MyHandler handler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sec_splash);
        handler = new MyHandler(this);
        binding.setHandler(handler);
    }

    public class MyHandler {
        Context context;

        public MyHandler(Context context) {
            this.context = context;
        }

        public void onNext(View view) {
            if (StaticMethods.userRegisterResponse != null) {
                if (StaticMethods.userRegisterResponse.Data.getType().equals("tutor")) {
                    IntentUtilies.openActivity(SecSplash.this , LoginScreen.class);
                    finish();
                }
            }
        }
    }
}