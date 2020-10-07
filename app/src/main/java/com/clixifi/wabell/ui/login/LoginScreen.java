package com.clixifi.wabell.ui.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.clixifi.wabell.R;
import com.clixifi.wabell.data.Response.User.LoginData;
import com.clixifi.wabell.data.Response.User.UserResponse;
import com.clixifi.wabell.databinding.ActivityLoginScreenBinding;
import com.clixifi.wabell.helpers.prefs.PrefUtils;
import com.clixifi.wabell.ui.forgetpass.ForgetPasswordScreen;
import com.clixifi.wabell.ui.main.MainScreen;
import com.clixifi.wabell.ui.register.RegisterScreen;
import com.clixifi.wabell.utils.CustomDialog;
import com.clixifi.wabell.utils.IntentUtilies;
import com.clixifi.wabell.utils.LocaleManager;
import com.clixifi.wabell.utils.StaticMethods;
import com.clixifi.wabell.utils.ToastUtil;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;

public class LoginScreen extends AppCompatActivity implements LoginListener {
    ActivityLoginScreenBinding binding;
    MyHandler handler;
    LoginPresenter loginPresenter;
    CustomDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //StaticMethods.statusBar(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login_screen);
        handler = new MyHandler(this);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        binding.setHandlers(handler);
        initialPresenter();
    }

    private void initialPresenter() {
        dialog = new CustomDialog(this);
        loginPresenter = new LoginPresenter(this);
    }

    public class MyHandler {
        Context context;

        public MyHandler(Context context) {
            this.context = context;
        }

        public void login(View view) {
            dialog.ShowDialog();
            loginPresenter.loginWithEmail(LoginScreen.this, binding.edEmail.getText().toString(), binding.edPassword.getText().toString());
        }

        public void goRegister(View view) {
            IntentUtilies.openActivity(LoginScreen.this, RegisterScreen.class);
        }

        public void back(View view) {
            onBackPressed();
        }

        public void forgetPassword(View v) {
            IntentUtilies.openActivity(LoginScreen.this, ForgetPasswordScreen.class);
        }
    }

    @Override
    public void onSuccess(UserResponse<LoginData> dataUserResponse) {
        dialog.DismissDialog();
        ToastUtil.showSuccessToast(this, R.string.login_success);

        if (dataUserResponse.Data.getUserType().equals("tutor")) {
            Log.e("TAG", "onSuccess: "+"Here Login 1" );
            if (dataUserResponse.Data.isBlocked()) {
                ToastUtil.showErrorToast(LoginScreen.this, R.string.block);
            } else if (dataUserResponse.Data.isPending()) {
                ToastUtil.showErrorToast(LoginScreen.this, R.string.await);
            } else {
                Log.e("TAG", "onSuccess: "+"Here Login 2" );
                StaticMethods.userData = dataUserResponse.Data;
                PrefUtils.saveUserinformation(LoginScreen.this, dataUserResponse.Data, PrefUtils.User_Singin);
                IntentUtilies.openActivity(LoginScreen.this, MainScreen.class);
                finish();
            }
        } else {
            StaticMethods.userData = dataUserResponse.Data;
            PrefUtils.saveUserinformation(LoginScreen.this, dataUserResponse.Data, PrefUtils.User_Singin);
            IntentUtilies.openActivity(LoginScreen.this, MainScreen.class);
            finish();
        }

    }


    @Override
    public void onNoConnection(boolean isConnected) {
        dialog.DismissDialog();
        ToastUtil.showErrorToast(this, R.string.noInternet);
    }

    @Override
    public void onFail(boolean fail) {
        dialog.DismissDialog();
        ToastUtil.showErrorToast(this, R.string.inValidData);
    }

    @Override
    public void onMessage(String message) {
        dialog.DismissDialog();
        ToastUtil.showErrorToast(this, message);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleManager.onAttach(base));
    }
}