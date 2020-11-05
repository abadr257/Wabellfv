package com.clixifi.wabell.ui.more;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

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

import static android.content.ContentValues.TAG;


public class MoreScreen extends Fragment {

    FragmentMoreScreenBinding binding;
    View v;
    LayoutInflater inflater;
    AlertDialog alertDialog;
    TextView txtUntil;
    MyHandler handler;
    boolean isOnline = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_more_screen, container, false);
        v = binding.getRoot();
        handler = new MyHandler(getActivity());
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
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
        public void onSub(View v) {
            ((MainScreen) getActivity()).onSub();
        }
        public void lang(View v) {
            openAlertDialog();

        }

        public void share(View v) {
            ((MainScreen) getActivity()).ShareApp();
        }

        public void about(View v) {
            ((MainScreen) getActivity()).gotoAbout();
        }
    }

    public void openAlertDialog() {
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        inflater = getActivity().getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.change_lang, null);
        dialogBuilder.setView(dialogView);
        alertDialog = dialogBuilder.create();
        Button update = dialogView.findViewById(R.id.btn_update);
        ImageView close = dialogView.findViewById(R.id.close);
        final RadioButton online = dialogView.findViewById(R.id.rad_online);
        final RadioButton offline = dialogView.findViewById(R.id.rad_offline);


        if (LocaleManager.getLanguage(getActivity()).equals("ar")) {
            online.setChecked(true);
        } else {
            offline.setChecked(true);
        }
        online.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (online.isChecked()) {
                    isOnline = true;
                    offline.setChecked(false);
                }
            }
        });
        offline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (offline.isChecked()) {
                    isOnline = false;
                    online.setChecked(false);

                }
            }
        });


        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (offline.isChecked()) {
                    ((MainScreen) getActivity()).updateViews("en");
                    binding.langTxt.setText(getActivity().getResources().getString(R.string.splash_text_ar));

                } else if (online.isChecked()) {
                    ((MainScreen) getActivity()).updateViews("ar");
                    binding.langTxt.setText(getActivity().getResources().getString(R.string.splash_text_ar));
                }
                alertDialog.dismiss();
            }
        });
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        alertDialog.show();
    }
}