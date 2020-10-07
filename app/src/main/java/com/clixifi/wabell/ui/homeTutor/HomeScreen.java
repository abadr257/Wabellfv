package com.clixifi.wabell.ui.homeTutor;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.clixifi.wabell.R;
import com.clixifi.wabell.data.Response.UserTutorCounters;
import com.clixifi.wabell.databinding.FragmentHomeScreenBinding;
import com.clixifi.wabell.utils.CustomDialog;
import com.clixifi.wabell.utils.LocaleManager;
import com.clixifi.wabell.utils.ToastUtil;


public class HomeScreen extends Fragment implements HomeTutorInterface {

    MyHandler handler;
    FragmentHomeScreenBinding binding;
    HomeTutorPresenter pre;
    View v;
    CustomDialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home_screen, container, false);
        v = binding.getRoot();
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        handler = new MyHandler(getActivity());
        pre = new HomeTutorPresenter(this);
        dialog = new CustomDialog(getActivity());
        binding.setOnNoData(true);
        getCounters();
        return v;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(LocaleManager.onAttach(context));
    }

    private void getCounters() {
        dialog.ShowDialog();
        pre.getCounters(getActivity());
    }

    @Override
    public void onSuccess(UserTutorCounters userTutorCounters) {
        dialog.DismissDialog();
        binding.simTxt.setText(userTutorCounters.getSimilarMastersCount());
        binding.favTxt.setText(userTutorCounters.getFavoritedCount());
        binding.requTxt.setText(userTutorCounters.getCallsCount());
        binding.viewsTxt.setText(userTutorCounters.getViewsCount());
    }

    @Override
    public void onFail(boolean fail) {
        dialog.DismissDialog();
        ToastUtil.showErrorToast(getActivity(), R.string.error);
    }

    @Override
    public void onNoConnection(boolean isConnected) {
        dialog.DismissDialog();
        ToastUtil.showErrorToast(getActivity(), R.string.noInternet);
    }

    public class MyHandler {
        Context context;

        public MyHandler(Context context) {
            this.context = context;
        }
    }
}