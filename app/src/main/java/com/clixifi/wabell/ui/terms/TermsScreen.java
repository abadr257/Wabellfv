package com.clixifi.wabell.ui.terms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;

import com.clixifi.wabell.R;
import com.clixifi.wabell.databinding.ActivityTermsScreenBinding;
import com.clixifi.wabell.ui.privecy.PrivecyScreen;
import com.clixifi.wabell.utils.LocaleManager;

import java.io.InputStream;

public class TermsScreen extends AppCompatActivity {
    ActivityTermsScreenBinding binding ;
    MyHandler handler ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil. setContentView(this ,R.layout.activity_terms_screen);
        handler = new MyHandler(this);
        binding.setHandlers(handler);
        setPrivacy();
    }

    public class MyHandler {
        Context context ;

        public MyHandler(Context context) {
            this.context = context;
        }

        public void back(View view){
            onBackPressed();
        }

    }
    public void setPrivacy() {
        try {
            Resources res;
            InputStream in_s;
            res = getResources();
            if (LocaleManager.getLanguage(TermsScreen.this).equals("en")) {
                in_s = res.openRawResource(R.raw.en_terms);
            } else {
                in_s = res.openRawResource(R.raw.ar_terms);
            }
            byte[] b = new byte[in_s.available()];
            in_s.read(b);
            binding.termsTxt.setText(new String(b));
        } catch (Exception e) {
            binding.termsTxt.setText("Error: can't show terms.");
        }
    }
}