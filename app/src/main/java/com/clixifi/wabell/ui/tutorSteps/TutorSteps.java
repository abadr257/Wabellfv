package com.clixifi.wabell.ui.tutorSteps;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.clixifi.wabell.R;
import com.clixifi.wabell.databinding.ActivityTutorStepsBinding;
import com.clixifi.wabell.ui.firstSplash.FirstSplashTutor;
import com.clixifi.wabell.ui.main.MainScreen;
import com.clixifi.wabell.ui.tutorBiography.TutorBio;
import com.clixifi.wabell.ui.tutorExpEdu.TutorExpEdu;
import com.clixifi.wabell.ui.tutorMedia.TutorMedia;
import com.clixifi.wabell.ui.tutorTopics.TutorTopics;
import com.clixifi.wabell.ui.tutorwork.TutorWorkScreen;
import com.clixifi.wabell.utils.IntentUtilies;
import com.clixifi.wabell.utils.StaticMethods;

public class TutorSteps extends AppCompatActivity {
    ActivityTutorStepsBinding binding;
    MyHandlers handlers;
    int position = 0;
    FragmentManager fragmentManager;
    public int selectedPosition = 0;
    int ProfileEdit = 0;
    TutorTopics tutorTopics;
    TutorWorkScreen tutorWorkScreen;
    String type = "";
    boolean edit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_tutor_steps);
        handlers = new MyHandlers(this);
        binding.setHandler(handlers);
        binding.setOnNext(false);
        binding.setOnStart(false);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        Bundle bundle = getIntent().getExtras();
        try {
            type = bundle.getString("type");
            ProfileEdit = bundle.getInt("position");
            edit = bundle.getBoolean("isEdit");
            StaticMethods.typeS = type;
        } catch (Exception e) {

        }
        if (ProfileEdit == 4 && edit) {
            Bundle b = new Bundle();
            b.putBoolean("edit", edit);

            if (StaticMethods.userRegisterResponse != null){
                b.putString("type", StaticMethods.userRegisterResponse.Data.getType());
            }else {
                b.putString("type", StaticMethods.userData.getUserType());
            }
            displayView(ProfileEdit, b);
        } else if (ProfileEdit == 0 && edit) {
            Bundle b = new Bundle();
            b.putBoolean("edit", edit);
            if (StaticMethods.userRegisterResponse != null){
                b.putString("type", StaticMethods.userRegisterResponse.Data.getType());
            }else {
                b.putString("type", StaticMethods.userData.getUserType());
            }
            displayView(ProfileEdit, b);
        }else if (ProfileEdit == 2 && edit) {
            Bundle b = new Bundle();
            b.putBoolean("edit", edit);
            if (StaticMethods.userRegisterResponse != null){
                b.putString("type", StaticMethods.userRegisterResponse.Data.getType());
            }else {
                b.putString("type", StaticMethods.userData.getUserType());
            }
            displayView(ProfileEdit, b);
        } else if (type.equals("student")) {
            Bundle bundles = new Bundle();
            bundles.putString("type", "student");
            displayView(0, bundle);
        } else {
            Bundle bundled = new Bundle();
            bundled.putString("type", "tutor");
            displayView(0, bundle);
        }

    }

    public void goToMain() {
        IntentUtilies.openActivity(TutorSteps.this , MainScreen.class);
        finish();
    }

    public void step1() {
        displayView(1, null);
        position = 1;
    }

    public void step2() {
        displayView(2, null);
        position = 2;
    }

    public void step3() {
        displayView(3, null);
        position = 3;
    }

    public void step4() {
        displayView(4, null);
        position = 4;
    }

    public void openSplash() {
        IntentUtilies.openActivity(TutorSteps.this, FirstSplashTutor.class);
        finish();
    }

    public void openHomeStudent() {
        IntentUtilies.openActivity(TutorSteps.this, MainScreen.class);
        finish();
    }

    public void displayView(int viewPosition, Bundle bundle) {
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        switch (viewPosition) {
            case 0:
                selectedPosition = 0;
                binding.setOnNext(false);
                binding.title.setText(R.string.sel_ur_special);
                if (edit) {
                    binding.backIcon.setVisibility(View.GONE);
                }
                fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                tutorTopics = new TutorTopics();
                tutorTopics.setArguments(bundle);
                fragmentTransaction.replace(R.id.fragment_parent, tutorTopics);
                break;
            case 1:
                binding.setOnNext(true);
                binding.pageCount.setText("" + 1);
                binding.prograssBar.setProgress(25);
                binding.title.setText(R.string.exp);
                selectedPosition = 1;
                fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                fragmentTransaction.replace(R.id.fragment_parent, new TutorExpEdu());
                break;
            case 2:
                if (edit) {
                    binding.backIcon.setVisibility(View.GONE);
                }
                binding.pageCount.setText("" + 2);
                binding.prograssBar.setProgress(50);
                binding.title.setText(R.string.media);
                selectedPosition = 2;
                fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                fragmentTransaction.replace(R.id.fragment_parent, new TutorMedia());
                break;
            case 3:
                binding.pageCount.setText("" + 3);
                binding.prograssBar.setProgress(75);
                binding.title.setText(R.string.bio);
                selectedPosition = 3;
                fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                fragmentTransaction.replace(R.id.fragment_parent, new TutorBio());
                break;
            case 4:
                binding.pageCount.setText("" + 4);
                binding.prograssBar.setProgress(100);
                binding.title.setText(R.string.workDetails);
                if (edit) {
                    binding.backIcon.setVisibility(View.GONE);
                }
                selectedPosition = 4;
                fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                tutorWorkScreen = new TutorWorkScreen();
                tutorWorkScreen.setArguments(bundle);
                fragmentTransaction.replace(R.id.fragment_parent, tutorWorkScreen);
                break;
        }
        fragmentTransaction.commit();
    }

    public class MyHandlers {
        Context context;

        public MyHandlers(Context context) {
            this.context = context;
        }

        public void next(View v) {
            binding.setOnNext(true);
            binding.setOnStart(false);
            position = 1;
            binding.pageCount.setText("" + 1);
            binding.prograssBar.setProgress(25);
            binding.title.setText(R.string.exp);

        }

        public void back(View v) {
            switch (selectedPosition) {
                case 4:
                    displayView(3, null);
                    break;
                case 3:
                    displayView(2, null);
                    break;
                case 2:
                    displayView(1, null);
                    break;
                case 1:
                    Bundle bundle = new Bundle();
                    if (StaticMethods.typeS.equals("student")) {
                        bundle.putString("type", "student");
                    } else {
                        bundle.putString("type", "tutor");
                    }
                    displayView(0, bundle);
                    break;
                case 0:
                    onBackPressed();
                    break;
            }
        }
    }


    @Override
    public void onBackPressed() {
        switch (selectedPosition) {
            case 4:
                if(edit){
                    super.onBackPressed();
                }else {
                    displayView(3, null);
                }
                break;
            case 3:
                displayView(2, null);
                break;
            case 2:
                if(edit){
                    super.onBackPressed();
                }else {
                    displayView(1, null);
                }
                break;
            case 1:
                Bundle bundle = new Bundle();
                bundle.putString("type", "student");
                displayView(0, bundle);
                break;
            case 0:
                super.onBackPressed();
                break;
        }

    }
}