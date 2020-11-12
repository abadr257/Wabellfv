package com.clixifi.wabell.ui.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;

public class LoginScreen extends AppCompatActivity implements LoginListener {
    ActivityLoginScreenBinding binding;
    MyHandler handler;
    LoginPresenter loginPresenter;
    CustomDialog dialog;
    boolean inputTypeChanged = false ;
    String FirebaseToken ="" ;
    private FirebaseAuth mAuth;
    private DatabaseReference mUserDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //StaticMethods.statusBar(this);
        getFirebaseToken();
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login_screen);
        handler = new MyHandler(this);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        binding.setHandlers(handler);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && isRTL(this)) {

            // Force a right-aligned text entry, otherwise latin character input,
            // like "abc123", will jump to the left and may even disappear!
            binding.edPassword.setTextDirection(View.TEXT_DIRECTION_RTL);

            // Make the "Enter password" hint display on the right hand side
            binding.edPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        }
        binding.edPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && isRTL(LoginScreen.this)) {
                    if (s.length() > 0) {
                        if (!inputTypeChanged) {

                            // When a character is typed, dynamically change the EditText's
                            // InputType to PASSWORD, to show the dots and conceal the typed characters.
                            binding.edPassword.setInputType(InputType.TYPE_CLASS_TEXT |
                                    InputType.TYPE_TEXT_VARIATION_PASSWORD |
                                    InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);

                            // Move the cursor to the correct place (after the typed character)
                            binding.edPassword.setSelection(s.length());

                            inputTypeChanged = true;
                        }
                    } else {
                        // Reset EditText: Make the "Enter password" hint display on the right
                        binding.edPassword.setInputType(InputType.TYPE_CLASS_TEXT |
                                InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);

                        inputTypeChanged = false;
                    }
                }
            }

        });
        initialPresenter();
    }
    private void getFirebaseToken() {
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w("TAG", "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();

                        // Log and toast
                        String msg =  token;
                        FirebaseToken = msg ;
                        Log.d("TAG", msg);
                        // Toast.makeText(MainScreen.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });
    }
    public static boolean isRTL(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return context.getResources().getConfiguration().getLayoutDirection()
                    == View.LAYOUT_DIRECTION_RTL;

        } else {
            return false;
        }
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

            loginPresenter.loginWithEmail(LoginScreen.this, binding.edEmail.getText().toString(), binding.edPassword.getText().toString() , FirebaseToken);
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
        loginInFirebase(binding.edEmail.getText().toString(),binding.edPassword.getText().toString());
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
    public void loginInFirebase(String email , String pass){
        mAuth = FirebaseAuth.getInstance();

        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
        mAuth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){



                            String current_user_id = mAuth.getCurrentUser().getUid();
                            String deviceToken = FirebaseInstanceId.getInstance().getToken();

                            mUserDatabase.child(current_user_id).child("device_token").setValue(deviceToken).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.e(TAG, "onSuccess: "+"Done" );
                                }
                            });
                            mUserDatabase.child(current_user_id).child("image").setValue(StaticMethods.userData.getProfilePicture()).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.e(TAG, "onSuccess: "+"Done" );
                                }
                            });




                        }


                    }
                });
    }
}