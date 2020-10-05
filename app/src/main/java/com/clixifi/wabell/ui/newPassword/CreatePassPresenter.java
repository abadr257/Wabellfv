package com.clixifi.wabell.ui.newPassword;

import android.content.Context;
import android.util.Log;

import com.clixifi.wabell.data.Response.OTP.OTPResponse;
import com.clixifi.wabell.data.Response.User.RegisterData;
import com.clixifi.wabell.data.Response.User.UserId;
import com.clixifi.wabell.data.Response.User.UserResponse;
import com.clixifi.wabell.utils.StaticMethods;
import com.clixifi.wabell.utils.network.ConnectionListener;
import com.clixifi.wabell.utils.network.ConnectionResponse;
import com.clixifi.wabell.utils.network.MainApi;
import com.clixifi.wabell.utils.network.MainApiBody;

import okhttp3.RequestBody;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;

public class CreatePassPresenter {
    CreatePassLisetner newPass ;

    public CreatePassPresenter(CreatePassLisetner newPass) {
        this.newPass = newPass;
    }

    public void setNewPass(Context context , String id , String pass){
        boolean network = StaticMethods.isConnectingToInternet(context);
        if (!network) {
            newPass.onNoConnection(true);
        } else if (pass.isEmpty()) {
            newPass.onEmptyFields(true);
        } else {

            RequestBody body = null ;
            try {
                body = MainApiBody.setNewPassBody(id ,pass);
            }catch (Exception e){
                Log.e(TAG, "setNewPass: "+e.toString() );
            }
            MainApi.setNewPassword(body, new ConnectionListener<UserResponse<RegisterData>>() {
                @Override
                public void onSuccess(ConnectionResponse<UserResponse<RegisterData>> connectionResponse) {
                    if (connectionResponse.data != null) {
                        if(connectionResponse.data.Message.equals("Password updated.")){
                            newPass.onSuccess(connectionResponse.data);
                        }

                    } else {
                        newPass.onFails(true);
                    }
                }

                @Override
                public void onFail(Throwable throwable) {
                    newPass.onFails(true);
                }
            });
        }
    }
}
