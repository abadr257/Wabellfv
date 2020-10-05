package com.clixifi.wabell.ui.registerTutor;

import android.content.Context;
import android.util.Log;

import com.clixifi.wabell.data.Response.User.RegisterData;
import com.clixifi.wabell.data.Response.User.UserResponse;
import com.clixifi.wabell.data.Response.areas.Areas;
import com.clixifi.wabell.data.Response.areas.AreasItem;
import com.clixifi.wabell.data.Response.cities.Cities;
import com.clixifi.wabell.data.Response.cities.CityItem;
import com.clixifi.wabell.utils.StaticMethods;
import com.clixifi.wabell.utils.network.ConnectionListener;
import com.clixifi.wabell.utils.network.ConnectionResponse;
import com.clixifi.wabell.utils.network.MainApi;
import com.clixifi.wabell.utils.network.MainApiBody;

import java.util.ArrayList;

import okhttp3.RequestBody;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;

public class TutorPresenter {
    TutorInterface tutorInterface;

    public TutorPresenter(TutorInterface tutorInterface) {
        this.tutorInterface = tutorInterface;
    }

    public void tutorRegister(Context context, String email,
                              String pass, String phone, String Name, int locationId, String UserType) {
        boolean network = StaticMethods.isConnectingToInternet(context);
        if (!network) {
            tutorInterface.onNoConnection(true);
        } else {
            RequestBody body = null;
            try {
                body = MainApiBody.registerBody(email, "+966"+phone, pass, Name, locationId, UserType);
            } catch (Exception e) {

            }
            MainApi.RegisterApi(body, new ConnectionListener<UserResponse<RegisterData>>() {
                @Override
                public void onSuccess(ConnectionResponse<UserResponse<RegisterData>> connectionResponse) {
                    if (connectionResponse.data.Data != null) {
                        if (!connectionResponse.data.Status.equals("Failed")) {
                            tutorInterface.onSuccess(connectionResponse.data);
                        } else {
                            tutorInterface.onFail(true);
                            Log.e(TAG, "onSuccess: Error"+"Here1" );
                        }

                    }
                }

                @Override
                public void onFail(Throwable throwable) {
                    tutorInterface.onFail(true);
                    Log.e(TAG, "onSuccess: Error"+"Here2" );
                }
            });
        }

    }

    public void getCities(Context context){
        boolean network = StaticMethods.isConnectingToInternet(context);
        if (!network) {
            tutorInterface.onNoConnection(true);
        }else{
            MainApi.CitiesApi(1,new ConnectionListener<Cities>() {
                @Override
                public void onSuccess(ConnectionResponse<Cities> connectionResponse) {
                    if(connectionResponse.data != null){
                        tutorInterface.onCity(connectionResponse.data.getLocation());
                    }
                }

                @Override
                public void onFail(Throwable throwable) {
                    tutorInterface.onFail(true);
                }
            });
        }
    }
    public void getAres(Context context , int cityId){
        boolean network = StaticMethods.isConnectingToInternet(context);
        if (!network) {
            tutorInterface.onNoConnection(true);
        }else{
            MainApi.AreasApi(cityId, new ConnectionListener<Areas>() {
                @Override
                public void onSuccess(ConnectionResponse<Areas> connectionResponse) {
                    if(connectionResponse.data != null){
                        tutorInterface.onArea(connectionResponse.data.getAreas());
                    }

                }

                @Override
                public void onFail(Throwable throwable) {
                    tutorInterface.onFail(true);
                }
            });
        }
    }
}
