package com.clixifi.wabell.ui.code;

import android.content.Context;
import android.util.Log;

import com.clixifi.wabell.data.Response.OTP.OTPResponse;
import com.clixifi.wabell.data.Response.ResultBoolean;
import com.clixifi.wabell.data.Response.User.UserId;
import com.clixifi.wabell.utils.StaticMethods;
import com.clixifi.wabell.utils.ToastUtil;
import com.clixifi.wabell.utils.network.ConnectionListener;
import com.clixifi.wabell.utils.network.ConnectionResponse;
import com.clixifi.wabell.utils.network.MainApi;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;

public class VerificationPresenter {
    VerificationInterface code ;

    public VerificationPresenter(VerificationInterface code) {
        this.code = code;
    }
    public void resendCode(Context context , String email){

    }

    public void getVerify(final Context context ){
        boolean network = StaticMethods.isConnectingToInternet(context);
        if(!network){
            code.onNoConnection(true);
        }else {
            String token = "Bearer "+StaticMethods.userRegisterResponse.Data.getToken();
            Log.e(TAG, "verify: "+token );
            MainApi.verificationCode(token, new ConnectionListener<OTPResponse>() {
                @Override
                public void onSuccess(ConnectionResponse<OTPResponse> connectionResponse) {
                    if(connectionResponse.data.getCode() != null){
                        Log.e(TAG, "onSuccess: "+connectionResponse.data.getCode() );
                        ToastUtil.showSuccessToast(context , connectionResponse.data.getCode());
                    }else {
                        Log.e(TAG, "onSuccess: "+"Error OnSuccess" );
                        code.onFail(true);
                    }
                }

                @Override
                public void onFail(Throwable throwable) {
                    Log.e(TAG, "onSuccess: "+"Error OnFail" );
                    code.onFail(true);
                }
            });
        }
    }
    public void sendVerify(Context context , String email , String codevERIFY){
        boolean network = StaticMethods.isConnectingToInternet(context);
        if(!network){
            code.onNoConnection(true);
        }else if(codevERIFY.length() < 4) {
            code.onEmptyFields(true);
        }else {
            /*//"Bearer "+
            String token = StaticMethods.userRegisterResponse.Data.getToken();
            Log.e(TAG, "verify: "+token );*/
            MainApi.sendVerify(email , codevERIFY, new ConnectionListener<UserId>() {
                @Override
                public void onSuccess(ConnectionResponse<UserId> connectionResponse) {
                    if(connectionResponse.data != null){
                        Log.e(TAG, "onSuccess: "+connectionResponse.data.getUserId());
                        code.onVerifySuccess(true , connectionResponse.data.getUserId() );
                    }else {
                        Log.e(TAG, "onSuccess: "+"Error OnSuccess" );
                        code.onFail(true);
                    }
                }

                @Override
                public void onFail(Throwable throwable) {
                    Log.e(TAG, "onSuccess: "+"Error OnFail" );
                    code.onFail(true);
                }
            });
        }
    }
}
