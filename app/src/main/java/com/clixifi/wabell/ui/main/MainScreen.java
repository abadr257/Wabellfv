package com.clixifi.wabell.ui.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.clixifi.wabell.R;
import com.clixifi.wabell.databinding.ActivityHomeScreenBinding;
import com.clixifi.wabell.helpers.prefs.PrefUtils;
import com.clixifi.wabell.ui.aboutUs.AboutUs;
import com.clixifi.wabell.ui.favMasters.FavMastersScreen;
import com.clixifi.wabell.ui.filterScreen.FilterScreen;
import com.clixifi.wabell.ui.homeStudent.StudentHome;
import com.clixifi.wabell.ui.homeTutor.HomeScreen;
import com.clixifi.wabell.ui.login.LoginScreen;
import com.clixifi.wabell.ui.messages.MessageAndCallScreen;
import com.clixifi.wabell.ui.more.MoreScreen;
import com.clixifi.wabell.ui.profile.ProfileScreen;
import com.clixifi.wabell.ui.searchByWord.SearchByWord;
import com.clixifi.wabell.ui.splash.SplashScreen;
import com.clixifi.wabell.ui.subscription.SubscriptionScreen;
import com.clixifi.wabell.ui.tutorSteps.TutorSteps;
import com.clixifi.wabell.ui.welcome.WelcomeScreen;
import com.clixifi.wabell.utils.CustomDialog;
import com.clixifi.wabell.utils.IntentUtilies;
import com.clixifi.wabell.utils.LocaleManager;
import com.clixifi.wabell.utils.StaticMethods;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainScreen extends AppCompatActivity {
    ActivityHomeScreenBinding binding;
    MyHandler handler;
    private FirebaseAuth mAuth;
    private DatabaseReference mUserRef;
    CustomDialog dialog;
    public int selectedPosition = 0;
    private static final int STORAGE_PERMISSION_CODE = 123;
    FragmentManager fragmentManager;
    Bundle extras = null;
    boolean profile = false ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseMessaging.getInstance().setAutoInitEnabled(true);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home_screen);
        handler = new MyHandler(this);
        dialog = new CustomDialog(this);
        requestStoragePermission();
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        binding.setHandler(handler);
        extras = getIntent().getExtras();
        if(extras != null){
            profile = extras.getBoolean("EditProfile");
            if(profile){
                displayView(2);
                extras = null;
                profile = false ;
            }
        }else {
            if (StaticMethods.userData != null) {
                if (StaticMethods.userData.getUserType().equals("tutor")) {
                    displayView(1);
                } else {
                    displayView(3);
                }
            } else /*if (StaticMethods.userRegisterResponse.Data.getType().equals("student"))*/ {
                displayView(3);

            }
        }

        getFirebaseToken();
    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (mAuth.getCurrentUser() != null) {
            mUserRef = FirebaseDatabase.getInstance().getReference().child("Users").child(mAuth.getCurrentUser().getUid());
        }
        if(currentUser == null){
            //sendToStart();
        } else {
            mUserRef.child("online").setValue("true");
        }

    }
    @Override
    protected void onStop() {
        super.onStop();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (mAuth.getCurrentUser() != null) {
            mUserRef = FirebaseDatabase.getInstance().getReference().child("Users").child(mAuth.getCurrentUser().getUid());
        }
        if(currentUser != null) {
            mUserRef.child("online").setValue(ServerValue.TIMESTAMP);
        }

    }

    private void getFirebaseToken() {
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w("TAG", "Fetching FCM registration token failed", task.getException());
                            return;
                        }
                        // Get new FCM registration token
                        String token = task.getResult();

                        // Log and toast
                        String msg = token;
                        Log.d("TAG", msg);
                        // Toast.makeText(MainScreen.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void onEditTopics(Bundle bundle) {
        IntentUtilies.openActivityWithBundle(MainScreen.this, TutorSteps.class, bundle);
    }

    public void onEditMedia(Bundle bundle) {
        IntentUtilies.openActivityWithBundle(MainScreen.this, TutorSteps.class, bundle);
    }

    public void onEditWorkDetails(Bundle bundle) {
        IntentUtilies.openActivityWithBundle(MainScreen.this, TutorSteps.class, bundle);
    }

    public class MyHandler {
        Context context;

        public MyHandler(Context context) {
            this.context = context;
        }

        public void more(View view) {
            if(binding.txtMore.getCurrentTextColor() == getResources().getColor(R.color.notActive) ){
                displayView(0);
            }

        }

        public void profile(View view) {
            if(binding.txtProfile.getCurrentTextColor() == getResources().getColor(R.color.notActive) ){
                displayView(2);
            }
        }

        public void home(View v) {
            if(binding.txtHome.getCurrentTextColor() == getResources().getColor(R.color.notActive)){
                if (StaticMethods.userData != null) {
                    if (StaticMethods.userData.getUserType().equals("tutor")) {
                        displayView(1);
                    } else {
                        displayView(3);
                    }
                } else {
                    displayView(3);
                }
            }

        }

        public void request(View v) {
            if(binding.txtChat.getCurrentTextColor() == getResources().getColor(R.color.notActive)){
                displayView(4);
            }

        }
    }

    @Override
    public void onBackPressed() {
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

                selectedPosition = 0;
                fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                fragmentTransaction.replace(R.id.fragments_parent, new MoreScreen());
                bottomBarView(viewPosition);
                break;
            case 1:
                selectedPosition = 1;
                fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                fragmentTransaction.replace(R.id.fragments_parent, new HomeScreen());
                bottomBarView(viewPosition);
                break;
            case 2:
                selectedPosition = 2;
                fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                fragmentTransaction.replace(R.id.fragments_parent, new ProfileScreen());
                bottomBarView(viewPosition);
                break;
            case 3:
                selectedPosition = 3;
                fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                fragmentTransaction.replace(R.id.fragments_parent, new StudentHome());
                bottomBarView(1);
                break;
            case 4:
                selectedPosition = 4;
                fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                fragmentTransaction.replace(R.id.fragments_parent, new MessageAndCallScreen());
                bottomBarView(viewPosition);
                break;
        }
        fragmentTransaction.commit();
    }

    private void bottomBarView(int viewPosition) {
        switch (viewPosition) {
            case 0:
                //onClick More Icon
                binding.iconMore.setImageResource(R.drawable.more_horizontal);
                binding.txtMore.setTextColor(getResources().getColor(R.color.active));
                binding.iconHome.setImageResource(R.drawable.unhome);
                binding.txtHome.setTextColor(getResources().getColor(R.color.notActive));
                binding.iconProfile.setImageResource(R.drawable.unuser);
                binding.txtProfile.setTextColor(getResources().getColor(R.color.notActive));
                binding.iconChat.setImageResource(R.drawable.unchatbubbles_outline);
                binding.txtChat.setTextColor(getResources().getColor(R.color.notActive));
                break;
            case 1:
                //onClick Home Icon
                binding.iconMore.setImageResource(R.drawable.unmore_horizontal);
                binding.txtMore.setTextColor(getResources().getColor(R.color.notActive));
                binding.iconHome.setImageResource(R.drawable.home);
                binding.txtHome.setTextColor(getResources().getColor(R.color.active));
                binding.iconProfile.setImageResource(R.drawable.unuser);
                binding.txtProfile.setTextColor(getResources().getColor(R.color.notActive));
                binding.iconChat.setImageResource(R.drawable.unchatbubbles_outline);
                binding.txtChat.setTextColor(getResources().getColor(R.color.notActive));
                break;
            case 2:
                //onClick Profile Icon
                binding.iconMore.setImageResource(R.drawable.unmore_horizontal);
                binding.txtMore.setTextColor(getResources().getColor(R.color.notActive));
                binding.iconHome.setImageResource(R.drawable.unhome);
                binding.txtHome.setTextColor(getResources().getColor(R.color.notActive));
                binding.iconProfile.setImageResource(R.drawable.user);
                binding.txtProfile.setTextColor(getResources().getColor(R.color.active));
                binding.iconChat.setImageResource(R.drawable.unchatbubbles_outline);
                binding.txtChat.setTextColor(getResources().getColor(R.color.notActive));
                break;
            case 4:
                //onClick Requests Icon
                binding.iconMore.setImageResource(R.drawable.unmore_horizontal);
                binding.txtMore.setTextColor(getResources().getColor(R.color.notActive));
                binding.iconHome.setImageResource(R.drawable.unhome);
                binding.txtHome.setTextColor(getResources().getColor(R.color.notActive));
                binding.iconProfile.setImageResource(R.drawable.unuser);
                binding.txtProfile.setTextColor(getResources().getColor(R.color.notActive));
                binding.iconChat.setImageResource(R.drawable.chatbubbles_outline);
                binding.txtChat.setTextColor(getResources().getColor(R.color.active));
                break;
        }
    }

    public void logout() {
        StaticMethods.ClearChash();
        PrefUtils.SignOut_User(MainScreen.this);
        mUserRef.child("online").setValue("false");
        FirebaseAuth.getInstance().signOut();
        IntentUtilies.openActivityInNewStack(MainScreen.this, SplashScreen.class);

    }
    public void onUpdate(){
        IntentUtilies.openActivityInNewStack(MainScreen.this , LoginScreen.class);
        finish();
    }
    public void onSub(){
        IntentUtilies.openActivity(MainScreen.this , SubscriptionScreen.class);
    }
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleManager.onAttach(base));
    }

    public void updateViews(String languageCode) {
        LocaleManager.setLocale(MainScreen.this, languageCode);
        recreate();
    }

    private void requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {

        }

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }

    public void ShareApp() {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Hello , Download Wabell app from google play");
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Google Play Store link will be here ");
        startActivity(Intent.createChooser(sharingIntent, "Share using"));
    }

    public void gotoAbout() {
        IntentUtilies.openActivity(MainScreen.this, AboutUs.class);
    }
    public void goToWhatsApp(String whats) {
        String url = "https://api.whatsapp.com/send?phone="+whats;
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    public void goToFilter() {
        IntentUtilies.openActivity(MainScreen.this, FilterScreen.class);
    }

    public void goToSearch() {
        IntentUtilies.openActivity(MainScreen.this, SearchByWord.class);
    }

    public void favMasters() {
        IntentUtilies.openActivity(MainScreen.this, FavMastersScreen.class);
    }
}