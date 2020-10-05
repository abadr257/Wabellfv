package com.clixifi.wabell.ui.code;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;

import com.clixifi.wabell.R;
import com.clixifi.wabell.data.Response.User.UserId;
import com.clixifi.wabell.databinding.ActivityVerificationCodeScreenBinding;
import com.clixifi.wabell.ui.forgetpass.ForgetPasswordLisetner;
import com.clixifi.wabell.ui.forgetpass.ForgetPasswordPresenter;
import com.clixifi.wabell.ui.newPassword.CreateNewPass;
import com.clixifi.wabell.ui.tutorSteps.TutorSteps;
import com.clixifi.wabell.utils.CustomDialog;
import com.clixifi.wabell.utils.IntentUtilies;
import com.clixifi.wabell.utils.StaticMethods;
import com.clixifi.wabell.utils.ToastUtil;

import static android.content.ContentValues.TAG;

public class VerificationCodeScreen extends AppCompatActivity implements VerificationInterface, ForgetPasswordLisetner {
    ActivityVerificationCodeScreenBinding binding;
    MyHandler handler;
    VerificationPresenter presenter;
    CustomDialog dialog;
    String email = "", type = "", code = "";
    boolean forget = false;
    ForgetPasswordPresenter forgetPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_verification_code_screen);
        handler = new MyHandler(this);
        binding.setHandler(handler);
        initialViews();
        if (!forget) {
            presenter.getVerify(VerificationCodeScreen.this);
        } else {
            ToastUtil.showSuccessToast(this, code);
        }
        getCode();
    }

    private void getCode() {
        binding.ed1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (i2 < i1) {
                    Log.e(TAG, "beforeTextChanged Character deleted" + "/ i=" + i + "/ i1" + i1 + "/ i2" + i2);
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (binding.ed1.getText().toString().length() == 1)     //size as per your requirement
                {
                    binding.ed2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        binding.ed2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (i2 < i1) {
                    Log.e(TAG, "beforeTextChanged Character deleted" + "/ i=" + i + "/ i1" + i1 + "/ i2" + i2);
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (binding.ed2.getText().toString().length() == 1)     //size as per your requirement
                {
                    binding.ed3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        binding.ed3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (i2 < i1) {
                    Log.e(TAG, "beforeTextChanged Character deleted" + "/ i=" + i + "/ i1" + i1 + "/ i2" + i2);
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (binding.ed3.getText().toString().length() == 1)     //size as per your requirement
                {
                    binding.ed4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        binding.ed4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (i2 < i1) {
                    Log.e(TAG, "beforeTextChanged Character deleted" + "/ i=" + i + "/ i1" + i1 + "/ i2" + i2);
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (binding.ed4.getText().toString().length() == 1)     //size as per your requirement
                {
                    StaticMethods.hideKeyboard(VerificationCodeScreen.this);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void initialViews() {
        forgetPresenter = new ForgetPasswordPresenter(this);
        Bundle bundle = getIntent().getExtras();
        try {
            email = bundle.getString("email");
            forget = bundle.getBoolean("forget");
            type = bundle.getString("type");
            code = bundle.getString("code");
        } catch (Exception e) {

        }
        presenter = new VerificationPresenter(this);
        dialog = new CustomDialog(this);
    }

    @Override
    public void onVerifySuccess(boolean success, String id) {
        dialog.DismissDialog();
        if (forget) {
            Bundle bundle = new Bundle();
            bundle.putString("Id", id);
            IntentUtilies.openActivityWithBundle(VerificationCodeScreen.this, CreateNewPass.class, bundle);
        } else if (type.equals("tutor")) {
            Bundle bundle = new Bundle();
            bundle.putString("type", "tutor");
            Log.e(TAG, "onVerifySuccess: " + "here Tutor");
            IntentUtilies.openActivityWithBundle(VerificationCodeScreen.this, TutorSteps.class, bundle);
            finish();
        } else if (type.equals("student")) {
            Bundle bundle = new Bundle();
            bundle.putString("type", "student");
            IntentUtilies.openActivityWithBundle(VerificationCodeScreen.this, TutorSteps.class, bundle);
            finish();
        }

    }

    @Override
    public void onSuccess(UserId otp) {
        dialog.DismissDialog();
        ToastUtil.showSuccessToast(VerificationCodeScreen.this , otp.getUserId());
    }

    @Override
    public void onFails(boolean fail) {

    }

    @Override
    public void onNoConnection(boolean isConnected) {
        dialog.DismissDialog();
        ToastUtil.showSuccessToast(VerificationCodeScreen.this, R.string.noInternet);
    }

    @Override
    public void onFail(boolean fail) {
        dialog.DismissDialog();
        ToastUtil.showErrorToast(VerificationCodeScreen.this, R.string.error);

    }

    @Override
    public void onResendSuccess(boolean isSuccess) {

    }

    @Override
    public void onEmptyFields(boolean isEmpty) {
        dialog.DismissDialog();
        ToastUtil.showErrorToast(VerificationCodeScreen.this, R.string.empty);
    }

    public class MyHandler {
        Context context;

        public MyHandler(Context context) {
            this.context = context;
        }

        public void resendCode(View v) {
            dialog.ShowDialog();
            forgetPresenter.getCode(VerificationCodeScreen.this, email);
        }

        public void verify(View v) {
            dialog.ShowDialog();
            String num1 = binding.ed1.getText().toString();
            String num2 = binding.ed2.getText().toString();
            String num3 = binding.ed3.getText().toString();
            String num4 = binding.ed4.getText().toString();
            presenter.sendVerify(VerificationCodeScreen.this, email, num1 + num2 + num3 + num4);
        }

        public void changePhone(View v) {

        }

        public void back(View v) {
            onBackPressed();
        }
    }
}