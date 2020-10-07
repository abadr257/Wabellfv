package com.clixifi.wabell.ui.tutorwork;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.clixifi.wabell.R;
import com.clixifi.wabell.databinding.FragmentTutorWorkScreenBinding;
import com.clixifi.wabell.ui.firstSplash.FirstSplashTutor;
import com.clixifi.wabell.ui.tutorSteps.TutorSteps;
import com.clixifi.wabell.ui.tutorwork.TutorWorkScreen;
import com.clixifi.wabell.utils.CustomDialog;
import com.clixifi.wabell.utils.IntentUtilies;
import com.clixifi.wabell.utils.StaticMethods;
import com.clixifi.wabell.utils.ToastUtil;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;

public class TutorWorkScreen extends Fragment implements TutorWorkInterface {
    FragmentTutorWorkScreenBinding binding ;
    View v ;
    MyHandler handler ;
    TutorWorkPresenter presenter ;
    List<Integer> days , times ;
    CustomDialog dialog ;
    boolean edit = false ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_tutor_work_screen, container, false);
        v = binding.getRoot();
        handler = new MyHandler(getActivity());
        binding.setHandler(handler);
        binding.setOnDays(false);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        binding.setOnTimes(false);
        days = new ArrayList<>();
        times = new ArrayList<>();
        dialog = new CustomDialog(getActivity());
        presenter = new TutorWorkPresenter(this);
        try {
            edit = getArguments().getBoolean("isEdit");
        } catch (Exception e) {

        }
        checkData();
        return v;
    }

    private void checkData() {
        binding.checkBoxAllDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(binding.checkBoxAllDay.isChecked()){
                    binding.checkBoxafter.setChecked(true);
                    binding.checkBoxEve.setChecked(true);
                    binding.checkBoxMor.setChecked(true);
                }else {
                    binding.checkBoxafter.setChecked(false);
                    binding.checkBoxEve.setChecked(false);
                    binding.checkBoxMor.setChecked(false);
                }
            }
        });
        binding.checkBoxAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(binding.checkBoxAll.isChecked()){
                    binding.checkBoxFri.setChecked(true);
                    binding.checkBoxThu.setChecked(true);
                    binding.checkBoxWed.setChecked(true);
                    binding.checkBoxTus.setChecked(true);
                    binding.checkBoxMon.setChecked(true);
                    binding.checkBoxSun.setChecked(true);
                    binding.checkBoxSat.setChecked(true);
                    binding.checkBoxAll.setChecked(true);
                }else {
                    binding.checkBoxFri.setChecked(false);
                    binding.checkBoxThu.setChecked(false);
                    binding.checkBoxWed.setChecked(false);
                    binding.checkBoxTus.setChecked(false);
                    binding.checkBoxMon.setChecked(false);
                    binding.checkBoxSun.setChecked(false);
                    binding.checkBoxSat.setChecked(false);
                    binding.checkBoxAll.setChecked(false);
                }
            }
        });
    }

    @Override
    public void onAddedSuccess(boolean success) {
        dialog.DismissDialog();
        if(edit){
            ((TutorSteps)getActivity()).goToMain();
        }else {
            ((TutorSteps)getActivity()).openSplash();
        }

    }

    @Override
    public void onAddedFail(boolean fail) {
        dialog.DismissDialog();
        ToastUtil.showErrorToast(getActivity() , R.string.error);
    }

    @Override
    public void onNoConnection(boolean isConnected) {
        dialog.DismissDialog();
        ToastUtil.showErrorToast(getActivity() , R.string.noInternet);
    }

    @Override
    public void onEmptyFields(boolean empty) {
        dialog.DismissDialog();
        ToastUtil.showErrorToast(getActivity() , R.string.empty);
    }

    public class MyHandler{
        Context context ;

        public MyHandler(Context context) {
            this.context = context;
        }
        public void avaDays(View v){
            if(binding.getOnDays()){
                binding.setOnDays(false);
            }else {
                binding.setOnDays(true);
            }
        }
        public void avaTimes(View v){
            if(binding.getOnTimes()){
                binding.setOnTimes(false);
            }else {
                binding.setOnTimes(true);
            }
        }
        public void save(View v){
            dialog.ShowDialog();
            if(binding.checkBoxSat.isChecked()){
                days.add(5);
            }
            if(binding.checkBoxSun.isChecked()){
                days.add(6);
            }
            if(binding.checkBoxMon.isChecked()){
                days.add(13);
            }
            if(binding.checkBoxTus.isChecked()){
                days.add(14);
            }
            if(binding.checkBoxWed.isChecked()){
                days.add(15);
            }
            if(binding.checkBoxThu.isChecked()){
                days.add(16);
            }
            if(binding.checkBoxFri.isChecked()){
                days.add(17);
            }

            if(binding.checkBoxafter.isChecked()){
                times.add(20);
            }
            if(binding.checkBoxEve.isChecked()){
                times.add(10);
            }
            if(binding.checkBoxMor.isChecked()){
                times.add(9);
            }
            if(days.size() == 0 || times.size() == 0 || binding.edPrice.getText().toString().isEmpty()){
                dialog.DismissDialog();
                if (edit){
                    ((TutorSteps)getActivity()).goToMain();
                }else {
                    ((TutorSteps)getActivity()).openSplash();
                }

            }else {
                Log.e(TAG, "save: "+ days );
                Log.e(TAG, "save: "+times );
                double price = Double.valueOf(binding.edPrice.getText().toString());
                JSONArray day = new JSONArray(days);
                JSONArray time = new JSONArray(times);
                presenter.uploadData(getActivity() ,price ,day , time );
            }

        }
    }
}