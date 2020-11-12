package com.clixifi.wabell.ui.tutorwork;

import android.content.Context;
import android.util.Log;

import com.clixifi.wabell.data.Response.ResultBoolean;
import com.clixifi.wabell.data.Response.User.RegisterData;
import com.clixifi.wabell.data.Response.User.UserResponse;
import com.clixifi.wabell.utils.StaticMethods;
import com.clixifi.wabell.utils.network.ConnectionListener;
import com.clixifi.wabell.utils.network.ConnectionResponse;
import com.clixifi.wabell.utils.network.MainApi;
import com.clixifi.wabell.utils.network.MainApiBody;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import okhttp3.RequestBody;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;

public class TutorWorkPresenter {
    TutorWorkInterface tutorWorkInterface ;
    String token ;
    public TutorWorkPresenter(TutorWorkInterface tutorWorkInterface) {
        this.tutorWorkInterface = tutorWorkInterface;
    }

    public void uploadData(Context context , double price , JSONArray DayIds , JSONArray TimeIds ){
        boolean network = StaticMethods.isConnectingToInternet(context);
        String id ;
        if(!network){
            tutorWorkInterface.onNoConnection(true);
            Log.e(TAG, "save: " + DayIds);
            Log.e(TAG, "save: " + TimeIds);
        }else {
            RequestBody body = null ;
            try{
                if(StaticMethods.userRegisterResponse != null){
                    id = StaticMethods.userRegisterResponse.Data.getUserId();
                }else {
                    id = StaticMethods.userData.getUserId();
                }
                body = MainApiBody.tutorWorkDetailsBody(price ,DayIds,TimeIds,id);
            }catch (Exception e){

            }
            if(StaticMethods.userRegisterResponse != null){
                token = "Bearer "+StaticMethods.userRegisterResponse.Data.getToken();
            }else {
                token = "Bearer "+StaticMethods.userData.getToken();
            }
            MainApi.workApi(token, body, new ConnectionListener<ResultBoolean>() {
                @Override
                public void onSuccess(ConnectionResponse<ResultBoolean> connectionResponse) {
                    if(connectionResponse.data != null){
                        tutorWorkInterface.onAddedSuccess(true);
                    }else {
                        tutorWorkInterface.onAddedFail(true);
                    }
                }

                @Override
                public void onFail(Throwable throwable) {
                    tutorWorkInterface.onAddedFail(true);
                    Log.e(TAG, "onFail: Work Details "+throwable.toString() );
                }
            });
        }
    }
}
