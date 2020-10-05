package com.clixifi.wabell.ui.newPassword;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.clixifi.wabell.R;
import com.clixifi.wabell.data.Response.User.RegisterData;
import com.clixifi.wabell.data.Response.User.UserId;
import com.clixifi.wabell.data.Response.User.UserResponse;
import com.clixifi.wabell.databinding.ActivityCreateNewPassBinding;
import com.clixifi.wabell.ui.login.LoginScreen;
import com.clixifi.wabell.utils.CustomDialog;
import com.clixifi.wabell.utils.IntentUtilies;
import com.clixifi.wabell.utils.ToastUtil;

public class CreateNewPass extends AppCompatActivity implements CreatePassLisetner {

    ActivityCreateNewPassBinding binding;
    MyHandler handler ;
    CustomDialog dialog ;
    String id = "";
    CreatePassPresenter presenter ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_create_new_pass);
        handler = new MyHandler(this);
        binding.setHandler(handler);
        dialog = new CustomDialog(this);
        presenter = new CreatePassPresenter(this);
        getId();

    }

    private void getId() {
        Bundle bundle = getIntent().getExtras();
        try {
            id = bundle.getString("Id");
        }catch (Exception e){

        }
    }

    @Override
    public void onSuccess(UserResponse<RegisterData> dataUserResponse) {
        dialog.DismissDialog();
        ToastUtil.showSuccessToast(this , R.string.newPassSuccess);
        IntentUtilies.openActivity(CreateNewPass.this, LoginScreen.class);
        finish();
    }

    @Override
    public void onFails(boolean fail) {
        dialog.DismissDialog();
        ToastUtil.showErrorToast(this , R.string.error);
    }

    @Override
    public void onNoConnection(boolean noInternet) {
        dialog.DismissDialog();
        ToastUtil.showErrorToast(this , R.string.noInternet);
    }

    @Override
    public void onEmptyFields(boolean empty) {
        dialog.DismissDialog();
        ToastUtil.showErrorToast(this , R.string.empty);
    }

    public class MyHandler {
        Context context;

        public MyHandler(Context context) {
            this.context = context;
        }

        public void back(View v){
            onBackPressed();
        }

        public void onReset(View v){
            dialog.ShowDialog();
            String pass = binding.edPass.getText().toString();
            String conPass = binding.edConPass.getText().toString();
            if(pass.equals(conPass)){
                presenter.setNewPass(CreateNewPass.this , id,pass);
            }else {
                ToastUtil.showErrorToast(CreateNewPass.this  , R.string.match);
            }
        }
    }
}