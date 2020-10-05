package com.clixifi.wabell.ui.more;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.clixifi.wabell.R;
import com.clixifi.wabell.databinding.FragmentMoreScreenBinding;
import com.clixifi.wabell.ui.main.MainScreen;
import com.clixifi.wabell.utils.IntentUtilies;
import com.clixifi.wabell.utils.LocaleManager;
import com.clixifi.wabell.utils.StaticMethods;
import com.clixifi.wabell.utils.dialogs.DialogUtil;
import com.clixifi.wabell.utils.dialogs.DialogUtilResponse;

import java.util.ArrayList;
import java.util.List;


public class MoreScreen extends Fragment {

    FragmentMoreScreenBinding binding;
    View v;
    MyHandler handler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_more_screen, container, false);
        v = binding.getRoot();
        handler = new MyHandler(getActivity());
        binding.setHandler(handler);
        checkType();
        return v;
    }

    private void checkType() {
        if (LocaleManager.getLanguage(getActivity()).equals("ar")) {
            binding.langTxt.setText(getActivity().getResources().getString(R.string.splash_text_ar));
        } else {
            binding.langTxt.setText(getActivity().getResources().getString(R.string.splash_text_en));

        }
        if (StaticMethods.userRegisterResponse != null) {
            if (StaticMethods.userRegisterResponse.Data.getType().equals("tutor")) {
                binding.setOnStudent(true);
            } else {
                binding.setOnTutor(true);
            }
        } else if (StaticMethods.userData != null) {
            if (StaticMethods.userData.getUserType().equals("tutor")) {
                binding.setOnStudent(true);
            } else {
                binding.setOnTutor(true);
            }
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(LocaleManager.onAttach(context));
    }

    public class MyHandler {
        Context c;

        public MyHandler(Context c) {
            this.c = c;
        }

        public void goToFav(View v) {
            ((MainScreen) getActivity()).favMasters();
        }

        public void logout(View v) {
            ((MainScreen) getActivity()).logout();
        }

        public void lang(View v) {
            if (LocaleManager.getLanguage(getActivity()).equals("ar")) {
                ((MainScreen) getActivity()).updateViews("en");
                binding.langTxt.setText(getActivity().getResources().getString(R.string.splash_text_ar));
            } else {
                ((MainScreen) getActivity()).updateViews("ar");
                binding.langTxt.setText(getActivity().getResources().getString(R.string.splash_text_en));
            }
        }

        public void share(View v) {
            ((MainScreen) getActivity()).ShareApp();
        }

        public void about(View v) {
            ((MainScreen) getActivity()).gotoAbout();
        }
    }
}