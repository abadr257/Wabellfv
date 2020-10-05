package com.clixifi.wabell.ui.topicsDetails;

import android.content.Context;
import android.util.Log;

import com.clixifi.wabell.data.Response.OTP.OTPResponse;
import com.clixifi.wabell.data.Response.topicChild.ChildResponse;
import com.clixifi.wabell.utils.StaticMethods;
import com.clixifi.wabell.utils.ToastUtil;
import com.clixifi.wabell.utils.network.ConnectionListener;
import com.clixifi.wabell.utils.network.ConnectionResponse;
import com.clixifi.wabell.utils.network.MainApi;
import com.clixifi.wabell.utils.network.MainApiBody;

import okhttp3.RequestBody;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;

public class TopicsDetailsPresenter {
    TopicsDetailsInterface details ;
    String token ;

    public TopicsDetailsPresenter(TopicsDetailsInterface details) {
        this.details = details;
    }

    public void getChilds(Context context , int id){
        boolean network = StaticMethods.isConnectingToInternet(context);
        if(!network){
            details.onConnection(true);
        }else {
            if(StaticMethods.userRegisterResponse != null){
                token ="Bearer "+ StaticMethods.userRegisterResponse.Data.getToken();
            }else {
                token ="Bearer "+ StaticMethods.userData.getToken();
            }

            RequestBody body = null ;
            try{
                body = MainApiBody.fourthLevelBody(id);
            }catch (Exception e){

            }
            MainApi.getTopicsChild(token, body , new ConnectionListener<ChildResponse>() {
                @Override
                public void onSuccess(ConnectionResponse<ChildResponse> connectionResponse) {
                    if(connectionResponse.data != null){
                        details.onSuccess(connectionResponse.data);
                    }else {
                        Log.e(TAG, "onSuccess: "+"Error OnSuccess" );
                        details.onFail(true);
                    }
                }

                @Override
                public void onFail(Throwable throwable) {
                    Log.e(TAG, "onSuccess: "+"Error OnFail" +throwable.toString() );
                    details.onFail(true);
                }
            });
        }
    }
}
