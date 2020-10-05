package com.clixifi.wabell.ui.welcome;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.PagerAdapter;

import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.clixifi.wabell.R;
import com.clixifi.wabell.databinding.ActivityWelcomeScreenBinding;
import com.clixifi.wabell.ui.login.LoginScreen;
import com.clixifi.wabell.ui.register.RegisterScreen;
import com.clixifi.wabell.utils.IntentUtilies;
import com.clixifi.wabell.utils.LocaleManager;
import com.clixifi.wabell.utils.StaticMethods;

public class WelcomeScreen extends AppCompatActivity {
    ActivityWelcomeScreenBinding binding ;
    MyHandler handlers ;
    private int[] layouts;
    MyViewPagerAdapter adapter ;
    private TextView[] dots;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //StaticMethods.statusBar(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_welcome_screen);
        handlers = new MyHandler(this);
        binding.setHandlers(handlers);
        initialViews();
    }
    public class MyHandler{
        Context context ;

        public MyHandler(Context context) {
            this.context = context;
        }

        public void login(View view){
            IntentUtilies.openActivity(WelcomeScreen.this , LoginScreen.class);
        }
        public void register(View view){
            IntentUtilies.openActivity(WelcomeScreen.this , RegisterScreen.class);
        }
        public void back(View v){
            onBackPressed();
        }
    }
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleManager.onAttach(base));
    }

    private void initialViews() {

        layouts = new int[]{
                R.layout.welcome1,
                R.layout.welcome2,
                R.layout.welcome3
                };
        addBottomDots(0);
        adapter = new MyViewPagerAdapter();
        binding.viewPager.setAdapter(adapter);
        binding.viewPager.addOnPageChangeListener(viewPagerPageChangeListener);
        binding.viewPager.setRotationY(180);
        binding.view.setRotationY(180);
    }
    androidx.viewpager.widget.ViewPager.OnPageChangeListener viewPagerPageChangeListener = new androidx.viewpager.widget.ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            addBottomDots(position);

        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };
    private void addBottomDots(int i) {
        dots = new TextView[layouts.length];

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
    public class MyViewPagerAdapter extends PagerAdapter {
        private LayoutInflater layoutInflater;

        public MyViewPagerAdapter() {
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = layoutInflater.inflate(layouts[position], container, false);
            container.addView(view);

            return view;
        }

        @Override
        public int getCount() {
            return layouts.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }
}