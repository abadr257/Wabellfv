package com.clixifi.wabell.ui.forgetpass;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.clixifi.wabell.R;
import com.clixifi.wabell.data.Response.OTP.OTPResponse;
import com.clixifi.wabell.data.Response.User.UserId;
import com.clixifi.wabell.databinding.ActivityForgetPasswordScreenBinding;
import com.clixifi.wabell.ui.code.VerificationCodeScreen;
import com.clixifi.wabell.utils.CustomDialog;
import com.clixifi.wabell.utils.IntentUtilies;
import com.clixifi.wabell.utils.LocaleManager;
import com.clixifi.wabell.utils.ToastUtil;

public class ForgetPasswordScreen extends AppCompatActivity implements ForgetPasswordLisetner {
    ActivityForgetPasswordScreenBinding binding ;
    CustomDialog dialog ;
    MyHandler handler ;
    ForgetPasswordPresenter presenter ;
    String email ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this , R.layout.activity_forget_password_screen );
        handler = new MyHandler(this);
        dialog = new CustomDialog(this);
        presenter = new ForgetPasswordPresenter(this);
        binding.setHandler(handler);
    }

    @Override
    public void onSuccess(UserId otp) {
        dialog.DismissDialog();
        ToastUtil.showSuccessToast(this , R.string.codeSuccess);
        Bundle bundle = new Bundle();
        bundle.putString("email" , email);
        bundle.putBoolean("forget" , true);
        bundle.putString("code" , otp.getUserId());
        IntentUtilies.openActivityWithBundle(this , VerificationCodeScreen.class , bundle);
    }
    @Override
    public void onFails(boolean fail) {
        dialog.DismissDialog();
        ToastUtil.showErrorToast(this , R.string.error);
    }
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleManager.onAttach(base));
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

    public class MyHandler{
        Context context ;

        public MyHandler(Context context) {
            this.context = context;
        }
        public void back(View v){
            onBackPressed();
        }
        public void onNext(View v){
            dialog.ShowDialog();
            email = binding.edEmail.getText().toString();
            presenter.getCode(ForgetPasswordScreen.this ,email);
        }
    }
}