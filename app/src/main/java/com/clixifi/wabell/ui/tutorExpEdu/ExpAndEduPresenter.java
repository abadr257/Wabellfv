package com.clixifi.wabell.ui.tutorExpEdu;

import android.content.Context;
import android.util.Log;

import com.clixifi.wabell.data.Response.ResultBoolean;
import com.clixifi.wabell.utils.StaticMethods;
import com.clixifi.wabell.utils.network.ConnectionListener;
import com.clixifi.wabell.utils.network.ConnectionResponse;
import com.clixifi.wabell.utils.network.MainApi;
import com.clixifi.wabell.utils.network.MainApiBody;

import okhttp3.RequestBody;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;

public class ExpAndEduPresenter {
    ExpAndEduInterface expAndEduInterface ;

    public ExpAndEduPresenter(ExpAndEduInterface expAndEduInterface) {
        this.expAndEduInterface = expAndEduInterface;
    }

    public void uploadData(Context context , String exp , String edu){
        boolean network = StaticMethods.isConnectingToInternet(context);
        if(!network){
            expAndEduInterface.onNoConnection(true);
        }else {
            RequestBody body = null ;
            try{
                body = MainApiBody.tutorExpandEduBody(exp ,edu);
            }catch (Exception e){

            }
            String token = "Bearer " +StaticMethods.userRegisterResponse.Data.getToken();
            Log.e(TAG, "uploadData: "+token );
            MainApi.expAndEduApi(token, body, new ConnectionListener<ResultBoolean>() {
                @Override
                public void onSuccess(ConnectionResponse<ResultBoolean> connectionResponse) {
                    if(connectionResponse.data != null){
                        expAndEduInterface.onAddedSuccess(true);
                    }else {
                        expAndEduInterface.onAddedFail(true);
                    }
                }

                @Override
                public void onFail(Throwable throwable) {
                    expAndEduInterface.onAddedFail(true);
                    Log.e(TAG, "onFail: "+throwable.toString() );
                }
            });
        }
    }
}
