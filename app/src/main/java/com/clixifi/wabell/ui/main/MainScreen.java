package com.clixifi.wabell.ui.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.clixifi.wabell.R;
import com.clixifi.wabell.databinding.ActivityHomeScreenBinding;
import com.clixifi.wabell.ui.aboutUs.AboutUs;
import com.clixifi.wabell.ui.favMasters.FavMastersScreen;
import com.clixifi.wabell.ui.homeStudent.StudentHome;
import com.clixifi.wabell.ui.homeTutor.HomeScreen;
import com.clixifi.wabell.ui.login.LoginScreen;
import com.clixifi.wabell.ui.more.MoreScreen;
import com.clixifi.wabell.ui.profile.ProfileScreen;
import com.clixifi.wabell.ui.splash.SplashScreen;
import com.clixifi.wabell.ui.tutorSteps.TutorSteps;
import com.clixifi.wabell.ui.welcome.WelcomeScreen;
import com.clixifi.wabell.utils.CustomDialog;
import com.clixifi.wabell.utils.IntentUtilies;
import com.clixifi.wabell.utils.LocaleManager;
import com.clixifi.wabell.utils.StaticMethods;

public class MainScreen extends AppCompatActivity {
    ActivityHomeScreenBinding binding;
    MyHandler handler ;
    CustomDialog dialog ;
    public int selectedPosition = 0;
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this , R.layout.activity_home_screen);
        handler = new MyHandler(this);
        dialog = new CustomDialog(this);
        binding.setHandler(handler);
        if(StaticMethods.userData != null){
            if(StaticMethods.userData.getUserType().equals("tutor")){
                displayView(1);
            }else {
                displayView(3);
            }
        }else {
            if(StaticMethods.userRegisterResponse.Data.getType().equals("student")){
                displayView(3);
            }
        }

    }
    public void onEditTopics(Bundle bundle ){
        IntentUtilies.openActivityWithBundle(MainScreen.this , TutorSteps.class , bundle);
    }
    public void onEditMedia(Bundle bundle ){
        IntentUtilies.openActivityWithBundle(MainScreen.this , TutorSteps.class , bundle);
    }
    public void onEditWorkDetails(Bundle bundle ){
        IntentUtilies.openActivityWithBundle(MainScreen.this , TutorSteps.class , bundle);
    }
    public class MyHandler{
        Context context ;

        public MyHandler(Context context) {
            this.context = context;
        }

        public void more(View view){
            displayView(0);
        }
        public void profile(View view){
            displayView(2);
        }

        public void home(View v){
            if(StaticMethods.userData != null){
                if(StaticMethods.userData.getUserType().equals("tutor")){
                    displayView(1);
                }else {
                    displayView(3);
                }
            }
        }
    }
    @Override
    public void onBackPressed(){
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }
    public void displayView(int viewPosition) {
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        switch (viewPosition) {
            case 0:
                bottomBarView(viewPosition);
                selectedPosition = 0;
                fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                fragmentTransaction.replace(R.id.fragments_parent, new MoreScreen());
                break;
            case 1:
                bottomBarView(viewPosition);
                selectedPosition = 1;
                fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                fragmentTransaction.replace(R.id.fragments_parent, new HomeScreen());
                break;
            case 2:
                bottomBarView(viewPosition);
                selectedPosition = 2;
                fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                fragmentTransaction.replace(R.id.fragments_parent, new ProfileScreen());
                break;
            case 3:
                bottomBarView(1);
                selectedPosition = 3;
                fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                fragmentTransaction.replace(R.id.fragments_parent, new StudentHome());
                break;
        }
        fragmentTransaction.commit();
    }
    private void bottomBarView(int viewPosition) {
        switch (viewPosition) {
            case 0:
                //onClick More Icon
                binding.iconMore.setImageResource(R.drawable.more_active);
                binding.txtMore.setTextColor(getResources().getColor(R.color.active));
                binding.iconHome.setImageResource(R.drawable.home_inactive);
                binding.txtHome.setTextColor(getResources().getColor(R.color.notActive));
                binding.iconProfile.setImageResource(R.drawable.profile_inactive);
                binding.txtProfile.setTextColor(getResources().getColor(R.color.notActive));
                break;
            case 1:
                //onClick Home Icon
                binding.iconMore.setImageResource(R.drawable.more_inactive);
                binding.txtMore.setTextColor(getResources().getColor(R.color.notActive));
                binding.iconHome.setImageResource(R.drawable.home_active);
                binding.txtHome.setTextColor(getResources().getColor(R.color.active));
                binding.iconProfile.setImageResource(R.drawable.profile_inactive);
                binding.txtProfile.setTextColor(getResources().getColor(R.color.notActive));
                break;
            case 2:
                //onClick Profile Icon
                binding.iconMore.setImageResource(R.drawable.more_inactive);
                binding.txtMore.setTextColor(getResources().getColor(R.color.notActive));
                binding.iconHome.setImageResource(R.drawable.home_inactive);
                binding.txtHome.setTextColor(getResources().getColor(R.color.notActive));
                binding.iconProfile.setImageResource(R.drawable.profile_active);
                binding.txtProfile.setTextColor(getResources().getColor(R.color.active));
                break;
        }
    }

    public void logout(){
        StaticMethods.ClearChash();

        IntentUtilies.openActivity(MainScreen.this , LoginScreen.class);
        finish();

    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleManager.onAttach(base));
    }

    public void updateViews(String languageCode) {
        LocaleManager.setLocale(MainScreen.this, languageCode);
        recreate();
    }
    public void ShareApp(){
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Hello , Download Wabell app from google play");
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Google Play Store link will be here ");
        startActivity(Intent.createChooser(sharingIntent, "Share using"));
    }
    public void gotoAbout() {
        IntentUtilies.openActivity(MainScreen.this, AboutUs.class);
    }
    public void favMasters() {
        IntentUtilies.openActivity(MainScreen.this, FavMastersScreen.class);
    }
}