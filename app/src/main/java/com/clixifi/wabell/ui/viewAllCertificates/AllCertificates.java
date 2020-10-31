package com.clixifi.wabell.ui.viewAllCertificates;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.clixifi.wabell.R;
import com.clixifi.wabell.databinding.ActivityAllCertificatesBinding;
import com.clixifi.wabell.ui.Adapters.ImageAdapter;
import com.clixifi.wabell.utils.StaticMethods;

public class AllCertificates extends AppCompatActivity {

    ActivityAllCertificatesBinding binding ;
    MyHandler han ;
    ImageAdapter adapter ;
    private TextView[] dots;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this ,R.layout.activity_all_certificates);
        han = new MyHandler( this);
        binding.setHan(han);
        if (StaticMethods.images != null){
            binding.txtNumOfFiles.setText(""+StaticMethods.images.size());
            adapter = new ImageAdapter(getSupportFragmentManager() ,this , StaticMethods.images );
            binding.viewPager.addOnPageChangeListener(viewPagerPageChangeListener);
            binding.viewPager.setAdapter(adapter);
        }

    }
    private void addBottomDots(int i) {
        dots = new TextView[StaticMethods.images.size()];

        int[] colorsActive = getResources().getIntArray(R.array.array_dot_active);
        int[] colorsInactive = getResources().getIntArray(R.array.array_dot_inactive);

        binding.layoutDots.removeAllViews();
        for (int j = 0; j < dots.length; j++) {
            dots[j] = new TextView(this);
            dots[j].setText(Html.fromHtml("&#8226;"));
            dots[j].setTextSize(30);
            dots[j].setTextColor(colorsInactive[j]);
            binding.layoutDots.addView(dots[j]);
        }

        if (dots.length > 0)
            dots[i].setTextColor(colorsActive[i]);
    }
    androidx.viewpager.widget.ViewPager.OnPageChangeListener viewPagerPageChangeListener = new androidx.viewpager.widget.ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            binding.txtTheCurrent.setText(""+position++);
            addBottomDots(position);

        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };

    public class MyHandler{
        Context context ;

        public MyHandler(Context context) {
            this.context = context;
        }
        public void onClose(View v){
            StaticMethods.images = null;
            onBackPressed();

        }
    }
}