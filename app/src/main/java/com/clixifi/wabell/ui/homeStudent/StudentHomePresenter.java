package com.clixifi.wabell.ui.homeStudent;

import android.content.Context;
import android.util.Log;

import com.clixifi.wabell.data.Response.TutorList.TutorListArray;
import com.clixifi.wabell.data.Response.featuredTutors.FeaturedArray;
import com.clixifi.wabell.utils.StaticMethods;
import com.clixifi.wabell.utils.network.ConnectionListener;
import com.clixifi.wabell.utils.network.ConnectionResponse;
import com.clixifi.wabell.utils.network.MainApi;
import com.clixifi.wabell.utils.network.MainApiBody;

import okhttp3.RequestBody;

import static android.content.ContentValues.TAG;

public class StudentHomePresenter {
    StudentHomeInterface studentHomeInterface ;
    String  token ;
    public StudentHomePresenter(StudentHomeInterface studentHomeInterface) {
        this.studentHomeInterface = studentHomeInterface;
    }


    public void getFeatured(Context context ){
        boolean net = StaticMethods.isConnectingToInternet(context);
        if (!net) {
            studentHomeInterface.onConnection(true);
        }else {
            if(StaticMethods.userRegisterResponse != null){
                token ="Bearer "+ StaticMethods.userRegisterResponse.Data.getToken();
            }else {
                token ="Bearer "+ StaticMethods.userData.getToken();
            }
            MainApi.getFeatured(token, new ConnectionListener<FeaturedArray>() {
                @Override
                public void onSuccess(ConnectionResponse<FeaturedArray> connectionResponse) {
                    if(connectionResponse.data != null){
                        studentHomeInterface.onFeaturedTutors(connectionResponse.data);
                    }else {
                        studentHomeInterface.onFailFeatured(true);
                    }
                }

                @Override
                public void onFail(Throwable throwable) {
                    studentHomeInterface.onFailFeatured(true);
                }
            });
        }
    }

    public void getTutorList(Context context){
        boolean net = StaticMethods.isConnectingToInternet(context);
        if (!net) {
            studentHomeInterface.onConnection(true);
        }else {
            if(StaticMethods.userRegisterResponse != null){
                token = "Bearer "+ StaticMethods.userRegisterResponse.Data.getToken();
            }else {
                token = "Bearer "+ StaticMethods.userData.getToken();
            }
            MainApi.getTutorList(token, new ConnectionListener<TutorListArray>() {
                @Override
                public void onSuccess(ConnectionResponse<TutorListArray> connectionResponse) {
                    if(connectionResponse.data != null){
                        studentHomeInterface.onLogs(connectionResponse.data);
                    }else {
                        studentHomeInterface.onFailFeatured(true);
                    }
                }

                @Override
                public void onFail(Throwable throwable) {
                    studentHomeInterface.onFailFeatured(true);
                    Log.e(TAG, "onFail: "+throwable.toString() );
                }
            });
        }
    }

    public void getTutorList(Context context , RequestBody body){
        boolean net = StaticMethods.isConnectingToInternet(context);
        if (!net) {
            studentHomeInterface.onConnection(true);
        }else {
            if(StaticMethods.userRegisterResponse != null){
                token = "Bearer "+ StaticMethods.userRegisterResponse.Data.getToken();
            }else {
                token = "Bearer "+ StaticMethods.userData.getToken();
            }

            MainApi.getTutorList(token , body, new ConnectionListener<TutorListArray>() {
                @Override
                public void onSuccess(ConnectionResponse<TutorListArray> connectionResponse) {
                    if(connectionResponse.data != null){
                        studentHomeInterface.onFilter(connectionResponse.data);
                    }else {
                        studentHomeInterface.onFailFeatured(true);
                    }
                }

                @Override
                public void onFail(Throwable throwable) {
                    studentHomeInterface.onFailFeatured(true);
                    Log.e(TAG, "onFail: "+throwable.toString() );
                }
            });
        }
    }
}
