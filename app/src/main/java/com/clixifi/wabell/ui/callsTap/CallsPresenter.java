package com.clixifi.wabell.ui.callsTap;

import android.content.Context;
import android.util.Log;

import com.clixifi.wabell.data.Response.RequestLogs.RequestLogsArray;
import com.clixifi.wabell.data.Response.ResultBoolean;
import com.clixifi.wabell.utils.StaticMethods;
import com.clixifi.wabell.utils.network.ConnectionListener;
import com.clixifi.wabell.utils.network.ConnectionResponse;
import com.clixifi.wabell.utils.network.MainApi;
import com.clixifi.wabell.utils.network.MainApiBody;

import okhttp3.RequestBody;

import static android.content.ContentValues.TAG;

public class CallsPresenter {
    CallsInterface calls ;
    String token ;

    public CallsPresenter(CallsInterface calls) {
        this.calls = calls;
    }

    void getCallsRequest(Context context){
        boolean net = StaticMethods.isConnectingToInternet(context);
        if(!net){
            calls.onConnection(true);
        }else {

            if(StaticMethods.userRegisterResponse != null){
                token ="Bearer "+ StaticMethods.userRegisterResponse.Data.getToken();

            }else {
                token = "Bearer " +StaticMethods.userData.getToken();

            }

            MainApi.getRequestCalls(token,  new ConnectionListener<RequestLogsArray>() {
                @Override
                public void onSuccess(ConnectionResponse<RequestLogsArray> connectionResponse) {
                    if(connectionResponse.data != null){
                        calls.onSuccess(connectionResponse.data);
                    }else {
                        calls.onFail(true);
                    }
                }

                @Override
                public void onFail(Throwable throwable) {
                    calls.onFail(true);
                    Log.e(TAG, "onFail: "+throwable.toString() );
                }
            });
        }
    }
}
