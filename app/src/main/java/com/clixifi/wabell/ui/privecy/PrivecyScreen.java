package com.clixifi.wabell.ui.privecy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;

import com.clixifi.wabell.R;
import com.clixifi.wabell.databinding.ActivityPrivecyScreenBinding;
import com.clixifi.wabell.utils.LocaleManager;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class PrivecyScreen extends AppCompatActivity {
    ActivityPrivecyScreenBinding binding;
    MyHandler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_privecy_screen);
        handler = new MyHandler(this);
        binding.setHandlers(handler);
        setPrivacy() ;
    }

    public class MyHandler {
        Context context;

        public MyHandler(Context context) {
            this.context = context;
        }

        public void back(View view) {
            onBackPressed();
        }
    }

    public void setPrivacy() {
        try {
            Resources res;
            InputStream in_s;
            res = getResources();
            if (LocaleManager.getLanguage(PrivecyScreen.this).equals("en")) {
                in_s = res.openRawResource(R.raw.en_privacy);
            } else {
                in_s = res.openRawResource(R.raw.ar_privacy);
            }
            byte[] b = new byte[in_s.available()];
            in_s.read(b);
            binding.privacyTxt.setText(new String(b));
        } catch (Exception e) {
            binding.privacyTxt.setText("Error: can't show terms.");
        }
    }


}