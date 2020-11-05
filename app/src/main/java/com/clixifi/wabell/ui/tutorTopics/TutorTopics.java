package com.clixifi.wabell.ui.tutorTopics;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.clixifi.wabell.R;
import com.clixifi.wabell.data.Response.requestTopic.RequestTopic;
import com.clixifi.wabell.data.Response.topic.Topics;
import com.clixifi.wabell.databinding.FragmentTutorTopicsBinding;
import com.clixifi.wabell.ui.Adapters.CategoryAdapter;
import com.clixifi.wabell.ui.main.MainScreen;
import com.clixifi.wabell.ui.tutorSteps.TutorSteps;
import com.clixifi.wabell.utils.CustomDialog;
import com.clixifi.wabell.utils.IntentUtilies;
import com.clixifi.wabell.utils.StaticMethods;
import com.clixifi.wabell.utils.ToastUtil;


public class TutorTopics extends Fragment implements TopicsCategoryInterface {
    FragmentTutorTopicsBinding binding ;
    View v ;
    MyHandler handler ;
    TopicsPresenter presenter ;
    CategoryAdapter adapter ;
    CustomDialog dialog ;
    String type;
    boolean edit = false ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_tutor_topics, container, false);
        v = binding.getRoot();
        handler = new MyHandler(getActivity());
        binding.setHandler(handler);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        initialViews();
        binding.setOnStudent(true);
        try{
            type = getArguments().getString("type");
            edit = getArguments().getBoolean("edit");
        }catch (Exception e){

        }


        return v;
    }

    private void initialViews() {
        dialog = new CustomDialog(getActivity());
        presenter = new TopicsPresenter(this);
        presenter.getCategory(getActivity());
        dialog.ShowDialog();
    }

    @Override
    public void onSuccess(Topics topics) {
        dialog.DismissDialog();
        adapter = new CategoryAdapter(getActivity() , topics , edit ,type);
        binding.recCategory.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.recCategory.setAdapter(adapter);
    }

    @Override
    public void onFail(boolean fail) {
        dialog.DismissDialog();
        ToastUtil.showErrorToast(getActivity() , R.string.error);
    }

    @Override
    public void onNoConnection(boolean IsConnected) {
        dialog.DismissDialog();
        ToastUtil.showErrorToast(getActivity() , R.string.noInternet);
    }

    @Override
    public void onReq(RequestTopic requestTopic) {
        dialog.DismissDialog();
        ToastUtil.showSuccessToast(getActivity() , R.string.reqSuccess);
    }

    public class MyHandler{
        Context con ;

        public MyHandler(Context con) {
            this.con = con;
        }

        public void onNext(View v ){
            if(edit){
                if(type.equals("student")){
                    ((TutorSteps)getActivity()).openHomeStudent();
                }else {
                    ((TutorSteps)getActivity()).openHomeStudent();
                }
            }else {
                if(type.equals("tutor")){
                    ((TutorSteps)getActivity()).displayView(1 , null);
                }else {
                    ((TutorSteps)getActivity()).openHomeStudent();
                }
            }

        }

        public void onRequest(View v ){
            openAlertDialog();
        }
    }

    private void openAlertDialog() {
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.send_sp_dialog, null);
        dialogBuilder.setView(dialogView);
        final AlertDialog alertDialog = dialogBuilder.create();
        Button send = dialogView.findViewById(R.id.btn_send);
        ImageView close = dialogView.findViewById(R.id.close);
        final EditText message = dialogView.findViewById(R.id.ed_request);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String messages = message.getText().toString();
                if(messages.isEmpty()){
                    ToastUtil.showErrorToast(getActivity() , R.string.empty);
                }else {
                    callRequestApi(messages);
                    alertDialog.dismiss();
                }

            }
        });
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        alertDialog.show();
    }

    private void callRequestApi(String message) {
        dialog.ShowDialog();
        if(StaticMethods.userRegisterResponse != null){
            presenter.req(getActivity() , message , StaticMethods.userRegisterResponse.Data.getEmail());
        }else{
            presenter.req(getActivity() , message , StaticMethods.userData.getEmail());
        }

    }
}