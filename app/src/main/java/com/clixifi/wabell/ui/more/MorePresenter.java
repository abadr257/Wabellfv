package com.clixifi.wabell.ui.more;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import com.clixifi.wabell.data.CallsArray;
import com.clixifi.wabell.data.WhatsAppResponse;
import com.clixifi.wabell.utils.StaticMethods;
import com.clixifi.wabell.utils.network.ConnectionListener;
import com.clixifi.wabell.utils.network.ConnectionResponse;
import com.clixifi.wabell.utils.network.MainApi;

public class MorePresenter {
    String token ;
    MoreInterface more ;

    public MorePresenter(MoreInterface more) {
        this.more = more;
    }

    public void getNum(Context context){
        boolean net = StaticMethods.isConnectingToInternet(context);
        if(!net){
            more.onConnection(true);
        }else {

            if(StaticMethods.userRegisterResponse != null){
                token ="Bearer "+ StaticMethods.userRegisterResponse.Data.getToken();
            }else {
                token = "Bearer " +StaticMethods.userData.getToken();
            }
            MainApi.getWhatsAppNum(token,  new ConnectionListener<WhatsAppResponse>() {
                @Override
                public void onSuccess(ConnectionResponse<WhatsAppResponse> connectionResponse) {
                    if(connectionResponse.data != null){
                        more.onSuccess(connectionResponse.data);
                    }else {
                        more.onFail(true);
                    }
                }

                @Override
                public void onFail(Throwable throwable) {
                    more.onFail(true);
                    Log.e(ContentValues.TAG, "onFail: "+throwable.toString() );
                }
            });
        }
    }
}
