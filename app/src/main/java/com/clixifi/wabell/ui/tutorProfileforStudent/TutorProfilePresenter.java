package com.clixifi.wabell.ui.tutorProfileforStudent;

import android.content.Context;
import android.util.Log;

import com.clixifi.wabell.data.Response.AddFav.AddFavorite;
import com.clixifi.wabell.data.Response.ResultBoolean;
import com.clixifi.wabell.data.Response.TutorProfileData.TutorProfileForStudent;
import com.clixifi.wabell.utils.StaticMethods;
import com.clixifi.wabell.utils.network.ConnectionListener;
import com.clixifi.wabell.utils.network.ConnectionResponse;
import com.clixifi.wabell.utils.network.MainApi;
import com.clixifi.wabell.utils.network.MainApiBody;

import okhttp3.RequestBody;

import static android.content.ContentValues.TAG;

public class TutorProfilePresenter {
    TutorProfileInterface tutor ;
    String  token , id ;

    public TutorProfilePresenter(TutorProfileInterface tutor) {
        this.tutor = tutor;
    }

    public void getTutorData(Context context , String id , boolean isViewed ){
        boolean net = StaticMethods.isConnectingToInternet(context);
        if(!net){
            tutor.onConnection(true);
        }else {
            RequestBody body = null;
            try {
                body = MainApiBody.getTutorProfile(id,isViewed);
            }catch (Exception e){

            }
            if(StaticMethods.userRegisterResponse != null){
                token ="Bearer "+ StaticMethods.userRegisterResponse.Data.getToken();
            }else {
                token = "Bearer " +StaticMethods.userData.getToken();
            }
            MainApi.getTutorProfile(token, body, new ConnectionListener<TutorProfileForStudent>() {
                @Override
                public void onSuccess(ConnectionResponse<TutorProfileForStudent> connectionResponse) {
                    if(connectionResponse.data != null){
                        tutor.onSuccess(connectionResponse.data);
                    }else {
                        tutor.onFail(true);
                    }
                }

                @Override
                public void onFail(Throwable throwable) {
                    tutor.onFail(true);
                    Log.e(TAG, "onFail: "+throwable.toString() );
                }
            });
        }
    }

    public void favorite(Context context , String tutorId){
        boolean net = StaticMethods.isConnectingToInternet(context);
        if(!net){
            tutor.onConnection(true);
        }else {

            if(StaticMethods.userRegisterResponse != null){
                token ="Bearer "+ StaticMethods.userRegisterResponse.Data.getToken();
                id = StaticMethods.userRegisterResponse.Data.getUserId();
            }else {
                token = "Bearer " +StaticMethods.userData.getToken();
                id = StaticMethods.userData.getUserId();
            }
            MainApi.addToFav(token, tutorId, new ConnectionListener<AddFavorite>() {
                @Override
                public void onSuccess(ConnectionResponse<AddFavorite> connectionResponse) {
                    if(connectionResponse.data != null){
                        tutor.OnAddedToFavorite(connectionResponse.data);
                    }else {
                        tutor.onFail(true);
                    }
                }

                @Override
                public void onFail(Throwable throwable) {
                    tutor.onFail(true);
                    Log.e(TAG, "onFail: "+throwable.toString() );
                }
            });
        }
    }

    public void unFavorite(Context context , String tutorId ){
        boolean net = StaticMethods.isConnectingToInternet(context);
        if(!net){
            tutor.onConnection(true);
        }else {

            if(StaticMethods.userRegisterResponse != null){
                token ="Bearer "+ StaticMethods.userRegisterResponse.Data.getToken();
                id = StaticMethods.userRegisterResponse.Data.getUserId();
            }else {
                token = "Bearer " +StaticMethods.userData.getToken();
                id = StaticMethods.userData.getUserId();
            }
            RequestBody body = null ;
            try{
                body = MainApiBody.unFavBody(tutorId , id);
            }catch (Exception e){

            }
            MainApi.deleteFav(token, body, new ConnectionListener<ResultBoolean>() {
                @Override
                public void onSuccess(ConnectionResponse<ResultBoolean> connectionResponse) {
                    if(connectionResponse.data != null){
                        tutor.onCall(connectionResponse.data);
                    }else {
                        tutor.onFail(true);
                    }
                }

                @Override
                public void onFail(Throwable throwable) {
                    tutor.onFail(true);
                    Log.e(TAG, "onFail: "+throwable.toString() );
                }
            });
        }
    }
    public void addRequestLogMessage(Context context , String tutorId , String message ){
        boolean net = StaticMethods.isConnectingToInternet(context);
        if(!net){
            tutor.onConnection(true);
        }else {

            if(StaticMethods.userRegisterResponse != null){
                token ="Bearer "+ StaticMethods.userRegisterResponse.Data.getToken();
                id = StaticMethods.userRegisterResponse.Data.getUserId();
            }else {
                token = "Bearer " +StaticMethods.userData.getToken();
                id = StaticMethods.userData.getUserId();
            }
            RequestBody body = null ;
            try{
                body = MainApiBody.addRequestLog("Message" ,tutorId ,   id ,0 ,message);
            }catch (Exception e){

            }
            MainApi.addRequestLog(token, body, new ConnectionListener<ResultBoolean>() {
                @Override
                public void onSuccess(ConnectionResponse<ResultBoolean> connectionResponse) {
                    if(connectionResponse.data != null){
                        tutor.onSendMessage(connectionResponse.data);
                    }else {
                        tutor.onFail(true);
                    }
                }

                @Override
                public void onFail(Throwable throwable) {
                    tutor.onFail(true);
                    Log.e(TAG, "onFail: "+throwable.toString() );
                }
            });
        }
    }
    public void addRequestLogCall(Context context , String tutorId , String message ){
        boolean net = StaticMethods.isConnectingToInternet(context);
        if(!net){
            tutor.onConnection(true);
        }else {

            if(StaticMethods.userRegisterResponse != null){
                token ="Bearer "+ StaticMethods.userRegisterResponse.Data.getToken();
                id = StaticMethods.userRegisterResponse.Data.getUserId();
            }else {
                token = "Bearer " +StaticMethods.userData.getToken();
                id = StaticMethods.userData.getUserId();
            }
            RequestBody body = null ;
            try{
                body = MainApiBody.addRequestLog("Call" ,tutorId ,   id ,0 ,"");
            }catch (Exception e){

            }
            MainApi.addRequestLog(token, body, new ConnectionListener<ResultBoolean>() {
                @Override
                public void onSuccess(ConnectionResponse<ResultBoolean> connectionResponse) {
                    if(connectionResponse.data != null){
                        tutor.onCall(connectionResponse.data);
                    }else {
                        tutor.onFail(true);
                    }
                }

                @Override
                public void onFail(Throwable throwable) {
                    tutor.onFail(true);
                    Log.e(TAG, "onFail: "+throwable.toString() );
                }
            });
        }
    }
}
