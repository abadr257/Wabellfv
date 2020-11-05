package com.clixifi.wabell.ui.login;

import android.content.Context;
import android.util.Log;

import com.clixifi.wabell.data.Response.User.LoginData;
import com.clixifi.wabell.data.Response.User.UserResponse;
import com.clixifi.wabell.utils.StaticMethods;
import com.clixifi.wabell.utils.network.ConnectionListener;
import com.clixifi.wabell.utils.network.ConnectionResponse;
import com.clixifi.wabell.utils.network.MainApi;
import com.clixifi.wabell.utils.network.MainApiBody;

import org.json.JSONException;

import okhttp3.RequestBody;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;

public class LoginPresenter {
    LoginListener login ;

    public LoginPresenter(LoginListener login) {
        this.login = login;
    }

    public void loginWithEmail(Context context , String userName , String password ,String fireToken){
        boolean network = StaticMethods.isConnectingToInternet(context);
        if(!network){
            login.onNoConnection(true);
        }else if(userName.isEmpty() || password.isEmpty()){
            login.onFail(true);
        }else {
            RequestBody requestBody = null;
            try {
                requestBody = MainApiBody.loginBody(userName ,  password , fireToken);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            MainApi.LoginApi(requestBody, new ConnectionListener<UserResponse<LoginData>>() {
                @Override
                public void onSuccess(ConnectionResponse<UserResponse<LoginData>> connectionResponse) {
                    if(connectionResponse.data.Data != null){
                        login.onSuccess(connectionResponse.data);
                    }else {
                        login.onMessage(connectionResponse.data.Message);
                    }
                }

                @Override
                public void onFail(Throwable throwable) {
                    login.onFail(true);
                }
            });
        }
    }
}
