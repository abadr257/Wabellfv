package com.clixifi.wabell.ui.messagesTap;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import com.clixifi.wabell.R;
import com.clixifi.wabell.data.CallsArray;
import com.clixifi.wabell.data.Response.TutorProfileData.TutorProfileForStudent;
import com.clixifi.wabell.data.Response.User.RegisterData;
import com.clixifi.wabell.data.Response.User.UserResponse;
import com.clixifi.wabell.utils.StaticMethods;
import com.clixifi.wabell.utils.network.ConnectionListener;
import com.clixifi.wabell.utils.network.ConnectionResponse;
import com.clixifi.wabell.utils.network.MainApi;
import com.clixifi.wabell.utils.network.MainApiBody;

import okhttp3.RequestBody;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;

public class MessagesPresenter {
    MessagesInterFace messages ;
    String  token ;

    public MessagesPresenter(MessagesInterFace messages) {
        this.messages = messages;
    }

    public void getMessagesLog(Context context ){
        boolean net = StaticMethods.isConnectingToInternet(context);
        if(!net){
            messages.onConnection(true);
        }else {

            if(StaticMethods.userRegisterResponse != null){
                token ="Bearer "+ StaticMethods.userRegisterResponse.Data.getToken();
            }else {
                token = "Bearer " +StaticMethods.userData.getToken();
            }
            MainApi.getRequestMessages(token,  new ConnectionListener<CallsArray>() {
                @Override
                public void onSuccess(ConnectionResponse<CallsArray> connectionResponse) {
                    if(connectionResponse.data != null){
                        messages.onMessages(connectionResponse.data);
                    }else {
                        messages.onFail(true);
                    }
                }

                @Override
                public void onFail(Throwable throwable) {
                    messages.onFail(true);
                    Log.e(ContentValues.TAG, "onFail: "+throwable.toString() );
                }
            });
        }
    }
}
