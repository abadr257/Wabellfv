package com.clixifi.wabell.ui.historyFragment;

import android.content.Context;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.clixifi.wabell.R;
import com.clixifi.wabell.data.HistoryArray;
import com.clixifi.wabell.databinding.FragmentHistoryScreenBinding;
import com.clixifi.wabell.ui.Adapters.HistoryPackagesAdapter;
import com.clixifi.wabell.utils.CustomDialog;
import com.clixifi.wabell.utils.ToastUtil;

public class HistoryScreen extends Fragment implements HistoryInterface {


    public HistoryScreen() {

    }

    View v ;
    FragmentHistoryScreenBinding binding ;
    CustomDialog dialog ;
    HistoryHandler handler ;
    HistoryPackagesAdapter adapter ;
    HistoryPresenter presenter ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_history_screen, container, false);
        v = binding.getRoot();
        handler = new HistoryHandler(getActivity());
        binding.setHandler(handler);
        dialog = new CustomDialog(getActivity());
        presenter = new HistoryPresenter(this);
        dialog.ShowDialog();
        presenter.getHistory(getActivity());
        return v;
    }

    @Override
    public void onHistory(HistoryArray array) {
        if(array.getResult().size() >0){
            adapter = new HistoryPackagesAdapter(getActivity() , array);
            binding.recHistory.setLayoutManager(new LinearLayoutManager(getActivity()));
            binding.recHistory.setAdapter(adapter);
        }else {
            binding.noHistory.setVisibility(View.VISIBLE);
        }

        dialog.DismissDialog();
    }

    @Override
    public void onFail(boolean fail) {
        dialog.DismissDialog();
        ToastUtil.showErrorToast(getActivity() ,R.string.error);
    }

    @Override
    public void onConnection(boolean isConnected) {
        dialog.DismissDialog();
        ToastUtil.showErrorToast(getActivity() ,R.string.noInternet);
    }

    public class HistoryHandler{
        Context context ;

        public HistoryHandler(Context context) {
            this.context = context;
        }
    }
}