package com.clixifi.wabell.ui.homeTutor;

import android.content.Context;

import com.clixifi.wabell.data.Response.User.UserId;
import com.clixifi.wabell.data.Response.UserTutorCounters;
import com.clixifi.wabell.utils.StaticMethods;
import com.clixifi.wabell.utils.network.ConnectionListener;
import com.clixifi.wabell.utils.network.ConnectionResponse;
import com.clixifi.wabell.utils.network.MainApi;

public class HomeTutorPresenter {
    HomeTutorInterface counters ;

    public HomeTutorPresenter(HomeTutorInterface counters) {
        this.counters = counters;
    }

    public void getCounters(Context context ){
        boolean network = StaticMethods.isConnectingToInternet(context);
        if (!network) {
            counters.onNoConnection(true);
        }  else {
            String token = StaticMethods.userData.getToken();
            MainApi.getTutorCounters(token,30, new ConnectionListener<UserTutorCounters>() {
                @Override
                public void onSuccess(ConnectionResponse<UserTutorCounters> connectionResponse) {
                    if (connectionResponse.data != null) {
                        counters.onSuccess(connectionResponse.data);
                    } else {
                        counters.onFail(true);
                    }
                }

                @Override
                public void onFail(Throwable throwable) {
                    counters.onFail(true);
                }
            });
        }
    }
}
