package com.clixifi.wabell.ui.callsTap;

import android.content.Context;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.clixifi.wabell.R;
import com.clixifi.wabell.data.CallsArray;
import com.clixifi.wabell.data.Response.RequestLogs.RequestLogsArray;
import com.clixifi.wabell.databinding.FragmentCallsTapBinding;
import com.clixifi.wabell.ui.Adapters.RequestLogsAdapter;
import com.clixifi.wabell.utils.CustomDialog;
import com.clixifi.wabell.utils.StaticMethods;
import com.clixifi.wabell.utils.ToastUtil;


public class CallsFragmentTap extends Fragment implements CallsInterface {


    FragmentCallsTapBinding binding;
    View v;
    CallsPresenter presenter;
    CustomDialog dialog;
    RequestLogsAdapter adapter;
    MyHandler handler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_calls_tap, container, false);
        v = binding.getRoot();
        handler = new MyHandler(getActivity());
        binding.setHandler(handler);
        dialog = new CustomDialog( getActivity());
        presenter = new CallsPresenter(this);
        dialog.ShowDialog();
        presenter.getCallsRequest(getActivity());
        binding.setOnNoData(false);
        if(StaticMethods.userRegisterResponse != null){
            if(StaticMethods.userRegisterResponse.DataProfile.getType().equals("student")){
                binding.setButton(true);
            }else {
                binding.setButton(false);
            }
        }else {
            if(StaticMethods.userData.getUserType().equals("student")){
                binding.setButton(true);
            }else {
                binding.setButton(false);
            }
        }
        return v;
    }

    @Override
    public void onSuccess(RequestLogsArray array) {
        if(array.getResult().size()>0){
            binding.setOnNoData(false);
            binding.setButton(false);
            adapter = new RequestLogsAdapter(array, getActivity());
            binding.recCalls.setLayoutManager(new LinearLayoutManager(getActivity()));
            binding.recCalls.setAdapter(adapter);
        }else {
            binding.setOnNoData(true);
        }
        dialog.DismissDialog();

    }

    @Override
    public void onFail(boolean fail) {
        dialog.DismissDialog();
        ToastUtil.showErrorToast(getActivity() , R.string.error);
    }

    @Override
    public void onConnection(boolean isConnected) {
        dialog.DismissDialog();
        ToastUtil.showErrorToast(getActivity() , R.string.noInternet);
    }

    public class MyHandler {
        Context context;

        public MyHandler(Context context) {
            this.context = context;
        }
    }
}