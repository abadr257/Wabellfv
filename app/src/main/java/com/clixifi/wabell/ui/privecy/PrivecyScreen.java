package com.clixifi.wabell.ui.privecy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.clixifi.wabell.R;
import com.clixifi.wabell.databinding.ActivityPrivecyScreenBinding;

public class PrivecyScreen extends AppCompatActivity {
    ActivityPrivecyScreenBinding binding ;
    MyHandler handler ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this ,R.layout.activity_privecy_screen);
        handler = new MyHandler(this);
        binding.setHandlers(handler);
    }

    public class MyHandler{
        Context context ;

        public MyHandler(Context context) {
            this.context = context;
        }

        public void back(View view){
            onBackPressed();
        }
    }
}