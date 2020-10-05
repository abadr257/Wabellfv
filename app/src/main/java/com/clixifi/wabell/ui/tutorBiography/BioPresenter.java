package com.clixifi.wabell.ui.tutorBiography;

import android.content.Context;

import com.clixifi.wabell.data.Response.ResultBoolean;
import com.clixifi.wabell.data.Response.User.RegisterData;
import com.clixifi.wabell.data.Response.User.UserResponse;
import com.clixifi.wabell.utils.StaticMethods;
import com.clixifi.wabell.utils.network.ConnectionListener;
import com.clixifi.wabell.utils.network.ConnectionResponse;
import com.clixifi.wabell.utils.network.MainApi;
import com.clixifi.wabell.utils.network.MainApiBody;

import okhttp3.RequestBody;

public class BioPresenter {
    BioInterface bioInterface ;

    public BioPresenter(BioInterface bioInterface) {
        this.bioInterface = bioInterface;
    }
    public void uploadData(Context context , String tagLine , String biography){
        boolean network = StaticMethods.isConnectingToInternet(context);
        if(!network){
            bioInterface.onNoConnection(true);
        }else if(tagLine.isEmpty() || biography.isEmpty()){
            bioInterface.onEmptyFields(true);
        }else {
            RequestBody body = null ;
            try{
                body = MainApiBody.tutorBioBody(tagLine ,biography);
            }catch (Exception e){

            }
            String token = "Bearer "+StaticMethods.userRegisterResponse.Data.getToken();
            MainApi.BioApi(token, body, new ConnectionListener<ResultBoolean>() {
                @Override
                public void onSuccess(ConnectionResponse<ResultBoolean> connectionResponse) {
                    if(connectionResponse.data != null){
                        bioInterface.onAddedSuccess(true);
                    }else {
                        bioInterface.onAddedFail(true);
                    }
                }

                @Override
                public void onFail(Throwable throwable) {
                    bioInterface.onAddedFail(true);
                }
            });
        }
    }
}
