package com.clixifi.wabell.ui.homeTutor;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.clixifi.wabell.R;
import com.clixifi.wabell.data.Response.RequestLogs.RequestLogsArray;
import com.clixifi.wabell.data.Response.ResultBoolean;
import com.clixifi.wabell.data.Response.UserTutorCounters;
import com.clixifi.wabell.databinding.FragmentHomeScreenBinding;
import com.clixifi.wabell.ui.Adapters.RequestLogsAdapter;
import com.clixifi.wabell.utils.CustomDialog;
import com.clixifi.wabell.utils.LocaleManager;
import com.clixifi.wabell.utils.StaticMethods;
import com.clixifi.wabell.utils.ToastUtil;
import com.clixifi.wabell.utils.dialogs.DialogUtil;
import com.clixifi.wabell.utils.dialogs.DialougUtilsDate;

import java.util.Calendar;

import static android.content.ContentValues.TAG;


public class HomeScreen extends Fragment implements HomeTutorInterface, DialougUtilsDate {

    MyHandler handler;
    FragmentHomeScreenBinding binding;
    HomeTutorPresenter pre;
    View v;
    CustomDialog dialog;
    RequestLogsAdapter adapter;
    boolean isOnline = true;
    DialogUtil dailogDate;
    String date ,dateToView = "";
    LayoutInflater inflater;
    AlertDialog alertDialog;
    TextView txtUntil;
    View dialogView ;
    RadioButton online  , offline ;
    AlertDialog.Builder dialogBuilder;
    UserTutorCounters userTutorCounters;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home_screen, container, false);
        v = binding.getRoot();
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        handler = new MyHandler(getActivity());
        binding.setHandler(handler);
        dailogDate = new DialogUtil(this);
        pre = new HomeTutorPresenter(this);
        dialog = new CustomDialog(getActivity());

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
        pre.getRequestLogs(getActivity());
    }

    @Override
    public void onSuccess(UserTutorCounters userTutorCounters) {
        dialog.DismissDialog();
        this.userTutorCounters = userTutorCounters;
        if (userTutorCounters.IsOnline) {
            binding.avilabilty.setText(getResources().getString(R.string.online));
            binding.avilabilty.setTextColor(getResources().getColor(R.color.online));
        } else {
            binding.avilabilty.setText(getResources().getString(R.string.offlines));
            binding.avilabilty.setTextColor(getResources().getColor(R.color.colorRed));
        }
        binding.simTxt.setText(userTutorCounters.getSimilarMastersCount() + "");
        binding.favTxt.setText(userTutorCounters.getFavoritedCount() + "");
        binding.requTxt.setText(userTutorCounters.getCallsCount() + "");
        binding.viewsTxt.setText(userTutorCounters.getViewsCount() + "");
        if (LocaleManager.getLanguage(getActivity()).equals("ar")){
            if(userTutorCounters.getSimilarMastersCount() > 2){
                binding.txtSim.setText(getActivity().getResources().getString(R.string.similar));
            }else if(userTutorCounters.getSimilarMastersCount() == 2 || userTutorCounters.getSimilarMastersCount() == 1){
                binding.txtSim.setText("مدرس مماثل");
            }
            if(userTutorCounters.getViewsCount() > 2){
                binding.txtViews.setText("مشاهدات");
            }else if(userTutorCounters.getViewsCount() == 2 || userTutorCounters.getViewsCount() == 1){
                binding.txtViews.setText("مشاهدة");
            }
            if(userTutorCounters.getCallsCount() > 2){
                binding.txtReq.setText("طلبات");
            }else if(userTutorCounters.getCallsCount() == 2 || userTutorCounters.getCallsCount() == 1){
                binding.txtReq.setText("طلب");
            }
            if(userTutorCounters.getFavoritedCount() > 2){
                binding.txtFav.setText("مفضلون");
            }else if(userTutorCounters.getFavoritedCount() == 2 || userTutorCounters.getFavoritedCount() == 1){
                binding.txtFav.setText("مفضل");
            }
        }else {
            if(userTutorCounters.getSimilarMastersCount() > 1){
                binding.txtSim.setText(getActivity().getResources().getString(R.string.similar));
            }else if(userTutorCounters.getSimilarMastersCount() == 1){
                binding.txtSim.setText("Similar Master");
            }
            if(userTutorCounters.getViewsCount() > 1){
                binding.txtViews.setText("Views");
            }else if(userTutorCounters.getViewsCount() == 1){
                binding.txtViews.setText("View");
            }
            if(userTutorCounters.getCallsCount() > 1){
                binding.txtReq.setText("Requests");
            }else if(userTutorCounters.getCallsCount() == 1){
                binding.txtReq.setText("Request");
            }
            if(userTutorCounters.getFavoritedCount() > 1){
                binding.txtFav.setText("Favorites");
            }else if(userTutorCounters.getFavoritedCount() == 1){
                binding.txtFav.setText("Favorite");
            }
        }

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

    @Override
    public void onUpdate(ResultBoolean resultBoolean) {
        dialog.DismissDialog();
        ToastUtil.showSuccessToast(getActivity(), R.string.saved);
        if (offline.isChecked()) {
            if (LocaleManager.getLanguage(getActivity()).equals("en")) {
                binding.avilabilty.setText("Offline");
                binding.avilabilty.setTextColor(getResources().getColor(R.color.colorRed));
            } else {
                binding.avilabilty.setText("غير متاح");
                binding.avilabilty.setTextColor(getResources().getColor(R.color.colorRed));
            }

        } else if (online.isChecked()) {
            if (LocaleManager.getLanguage(getActivity()).equals("en")) {
                binding.avilabilty.setText("Online");
                binding.avilabilty.setTextColor(getResources().getColor(R.color.online));
            } else {
                binding.avilabilty.setText("نشط");
                binding.avilabilty.setTextColor(getResources().getColor(R.color.online));
            }
        }
    }

    @Override
    public void onRequestLogs(RequestLogsArray array) {
        if (array.getResult().size() == 0) {
            binding.setOnNoData(true);
        } else {
            binding.setOnNoData(false);
            adapter = new RequestLogsAdapter(array, getActivity());
            binding.recRequests.setLayoutManager(new LinearLayoutManager(getActivity()));
            binding.recRequests.setAdapter(adapter);
        }

    }

    @Override
    public void onRequestFail(boolean fail) {
        binding.setOnNoData(true);
    }

    @Override
    public void DateFormatResponse(Calendar myCalendar) {
        int day = myCalendar.get(Calendar.DAY_OF_MONTH);
        int month = myCalendar.get(Calendar.MONTH);
        int year = myCalendar.get(Calendar.YEAR);
        date = (month+1) + "/" + day + "/" + year;
        dateToView = day + "/" + (month+1) + "/" + year ;
        txtUntil.setText(dateToView);
        //StaticMethods.date = date ;
    }

    public class MyHandler {
        Context context;

        public MyHandler(Context context) {
            this.context = context;
        }

        public void isOnline(View v) {
            openAlertDialog();
        }
    }

    public void openAlertDialog() {
        dialogBuilder = new AlertDialog.Builder(getActivity());
        inflater = getActivity().getLayoutInflater();
        dialogView = inflater.inflate(R.layout.isonline_change, null);
        online = dialogView.findViewById(R.id.rad_online);
        offline = dialogView.findViewById(R.id.rad_offline);
        dialogBuilder.setView(dialogView);
        alertDialog = dialogBuilder.create();
        Button update = dialogView.findViewById(R.id.btn_update);
        ImageView close = dialogView.findViewById(R.id.close);
        txtUntil = dialogView.findViewById(R.id.txt_untill);
        txtUntil.setText(userTutorCounters.getOfflineUntil() + "");
        if (dateToView.length() > 0){
            txtUntil.setText(dateToView);
        }




        if(binding.avilabilty.getText().equals(getResources().getString(R.string.online))){
            online.setChecked(true);
        }else {
            offline.setChecked(true);
        }
        online.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (online.isChecked()) {
                    isOnline = true;
                    offline.setChecked(false);
                    Log.e(TAG, "openAlertDialog: " + isOnline);
                }
            }
        });
        offline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (offline.isChecked()) {
                    isOnline = false;
                    online.setChecked(false);
                    dailogDate.showDialogDatePicker(getActivity());
                    Log.e(TAG, "openAlertDialog: " + isOnline);
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
                Log.e(TAG, "onClick: " + date);

                pre.updateAva(getActivity(), isOnline, date);

                alertDialog.dismiss();
            }
        });
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        alertDialog.show();
    }
}