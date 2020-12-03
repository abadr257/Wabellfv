package com.clixifi.wabell.ui.aboutUs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.clixifi.wabell.R;
import com.clixifi.wabell.databinding.ActivityAboutUsBinding;
import com.clixifi.wabell.ui.privecy.PrivecyScreen;
import com.clixifi.wabell.ui.register.RegisterScreen;
import com.clixifi.wabell.ui.terms.TermsScreen;
import com.clixifi.wabell.utils.IntentUtilies;
import com.clixifi.wabell.utils.LocaleManager;

public class AboutUs extends AppCompatActivity {
    ActivityAboutUsBinding binding ;
    MyHandlers handlers ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_about_us);
        handlers = new MyHandlers(this);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        binding.setHandlers(handlers);
        if(LocaleManager.getLanguage(AboutUs.this).equals("ar")){
            binding.atlantisLogo.setImageResource(R.drawable.atcode_ar2);
        }else {
            binding.atlantisLogo.setImageResource(R.drawable.at_code_en);
        }
    }

    public class MyHandlers{
        Context context ;

        public MyHandlers(Context context) {
            this.context = context;
        }
        public void back(View view){
            onBackPressed();
        }
        public void pri(View view){
            IntentUtilies.openActivity(AboutUs.this , PrivecyScreen.class);
        }
        public void terms(View view){
            IntentUtilies.openActivity(AboutUs.this , TermsScreen.class);
        }
        public void atCode(View view){
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.atcode.net"));
            startActivity(browserIntent);
        }
        public void addicta(View view){
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.addictaco.com"));
            startActivity(browserIntent);
        }
        public void openMail(View v ){
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("message/rfc822");
            i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"ask@wabell.com"});
            i.putExtra(Intent.EXTRA_SUBJECT, "");
            i.putExtra(Intent.EXTRA_TEXT   , "");
            try {
                startActivity(Intent.createChooser(i, "Send mail..."));
            } catch (android.content.ActivityNotFoundException ex) {
            }
        }
    }
}