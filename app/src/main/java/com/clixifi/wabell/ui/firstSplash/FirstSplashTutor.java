package com.clixifi.wabell.ui.firstSplash;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.clixifi.wabell.R;
import com.clixifi.wabell.databinding.ActivityFirstSplashTutorBinding;
import com.clixifi.wabell.ui.secSplash.SecSplash;
import com.clixifi.wabell.utils.IntentUtilies;

public class FirstSplashTutor extends AppCompatActivity {
    ActivityFirstSplashTutorBinding binding ;
    MyHandler handler ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this ,R.layout.activity_first_splash_tutor);
        handler = new MyHandler(this);
        binding.setHandler(handler);
    }

    public class MyHandler{
        Context context ;

        public MyHandler(Context context) {
            this.context = context;
        }

        public void onNext(View view){
            IntentUtilies.openActivity(FirstSplashTutor.this , SecSplash.class);
            finish();
        }
    }
}