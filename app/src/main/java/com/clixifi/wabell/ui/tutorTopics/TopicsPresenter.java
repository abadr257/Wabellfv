package com.clixifi.wabell.ui.tutorTopics;

import android.content.Context;
import android.util.Log;

import com.clixifi.wabell.data.Response.requestTopic.RequestTopic;
import com.clixifi.wabell.data.Response.topic.Topics;
import com.clixifi.wabell.utils.StaticMethods;
import com.clixifi.wabell.utils.network.ConnectionListener;
import com.clixifi.wabell.utils.network.ConnectionResponse;
import com.clixifi.wabell.utils.network.MainApi;
import com.clixifi.wabell.utils.network.MainApiBody;

import okhttp3.RequestBody;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;

public class TopicsPresenter {
    TopicsCategoryInterface topics;
    String token ;

    public TopicsPresenter(TopicsCategoryInterface topics) {
        this.topics = topics;
    }

    public void getCategory(Context context) {
        boolean network = StaticMethods.isConnectingToInternet(context);
        if (!network) {
            topics.onNoConnection(true);
        } else {
            if(StaticMethods.userRegisterResponse != null){
                 token = "Bearer "+StaticMethods.userRegisterResponse.Data.getToken();
            }else {
                 token = "Bearer "+StaticMethods.userData.getToken();
            }
            MainApi.getTopics(token,new ConnectionListener<Topics>() {
                @Override
                public void onSuccess(ConnectionResponse<Topics> connectionResponse) {
                    if (connectionResponse.data != null) {
                        topics.onSuccess(connectionResponse.data);
                    } else {
                        topics.onFail(true);
                    }

                }
                @Override
                public void onFail(Throwable throwable) {
                    topics.onFail(true);
                    Log.e(TAG, "onFail: "+throwable.toString() );
                }
            });
        }
    }

    public void req(Context co , String message , String email ){
        boolean network = StaticMethods.isConnectingToInternet(co);
        if (!network) {
            topics.onNoConnection(true);
        } else {

            RequestBody body = null ;
            try{
                body = MainApiBody.requestTopic(message , email);
            }catch (Exception e){

            }

            String token = "Bearer "+ StaticMethods.userRegisterResponse.Data.getToken();
            MainApi.requestTopic(token , body ,new ConnectionListener<RequestTopic>() {
                @Override
                public void onSuccess(ConnectionResponse<RequestTopic> connectionResponse) {
                    if (connectionResponse.data != null) {
                        topics.onReq(connectionResponse.data);
                    } else {
                        topics.onFail(true);
                    }

                }

                @Override
                public void onFail(Throwable throwable) {
                    topics.onFail(true);
                }
            });
        }
    }
}
