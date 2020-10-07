package com.clixifi.wabell.ui.tutorExpEdu;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.clixifi.wabell.R;
import com.clixifi.wabell.databinding.FragmentTutorExpEduBinding;
import com.clixifi.wabell.ui.tutorSteps.TutorSteps;
import com.clixifi.wabell.utils.CustomDialog;
import com.clixifi.wabell.utils.ToastUtil;


public class TutorExpEdu extends Fragment implements ExpAndEduInterface {
    FragmentTutorExpEduBinding binding ;
    View v ;
    MyHandler handler ;
    ExpAndEduPresenter presenter ;
    CustomDialog dialog ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_tutor_exp_edu, container, false);
        v = binding.getRoot();
        handler = new MyHandler(getActivity());
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        binding.setHandler(handler);
        presenter = new ExpAndEduPresenter(this);
        dialog = new CustomDialog(getActivity());
        return v;
    }

    @Override
    public void onAddedSuccess(boolean success) {
        dialog.DismissDialog();
        ((TutorSteps)getActivity()).step2();
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
        Context context;

        public MyHandler(Context context) {
            this.context = context;
        }
        public void nextStep(View v){
            dialog.ShowDialog();
            String exp = binding.edExp.getText().toString();
            String edu = binding.edEdu.getText().toString();
            if (exp.isEmpty() && edu.isEmpty()){
                dialog.DismissDialog();
                ((TutorSteps)getActivity()).step2();
            }else {
                presenter.uploadData(getActivity() , exp , edu);
            }
        }
    }
}