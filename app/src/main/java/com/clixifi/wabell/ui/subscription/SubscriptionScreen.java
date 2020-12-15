package com.clixifi.wabell.ui.subscription;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.clixifi.wabell.R;
import com.clixifi.wabell.databinding.ActivitySubscriptionScreenBinding;
import com.clixifi.wabell.ui.Adapters.MessagePagerAdapter;
import com.clixifi.wabell.ui.Adapters.SubPagerAdapter;
import com.clixifi.wabell.utils.LocaleManager;

public class SubscriptionScreen extends AppCompatActivity {
    ActivitySubscriptionScreenBinding binding ;
    MySubHandler handler ;
    SubPagerAdapter adapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this ,R.layout.activity_subscription_screen);
        handler = new MySubHandler(this);
        binding.setHandler(handler);
        configTabs(this);
    }
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleManager.onAttach(base));
    }
    private void configTabs(Context context) {
        adapter = new SubPagerAdapter(getSupportFragmentManager(), context);
        binding.tabLayout.setupWithViewPager(binding.pager);
        binding.pager.setAdapter(adapter);
    }
    public class MySubHandler{
        Context context ;

        public MySubHandler(Context context) {
            this.context = context;
        }

        public void back(View v){
            onBackPressed();
        }
    }
}