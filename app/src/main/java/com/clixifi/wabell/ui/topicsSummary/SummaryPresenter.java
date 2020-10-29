package com.clixifi.wabell.ui.topicsSummary;

import android.content.Context;

import com.clixifi.wabell.data.Response.ResultBoolean;
import com.clixifi.wabell.data.Response.topicChild.ChildResponse;
import com.clixifi.wabell.utils.StaticMethods;
import com.clixifi.wabell.utils.network.ConnectionListener;
import com.clixifi.wabell.utils.network.ConnectionResponse;
import com.clixifi.wabell.utils.network.MainApi;
import com.clixifi.wabell.utils.network.MainApiBody;

import okhttp3.RequestBody;

public class SummaryPresenter {
    SummaryInterface summary;
    String token,userid;

    public SummaryPresenter(SummaryInterface summary) {
        this.summary = summary;
    }

    public void getSummary(Context context, int id) {
        boolean net = StaticMethods.isConnectingToInternet(context);
        if (!net) {
            summary.onConnection(true);
        } else {
            RequestBody body = null;
            try {
                body = MainApiBody.summaryBody(id);
            } catch (Exception e) {

            }
            if (StaticMethods.userRegisterResponse != null) {
                token = "Bearer " + StaticMethods.userRegisterResponse.Data.getToken();
            } else {
                token = "Bearer " + StaticMethods.userData.getToken();
            }
            MainApi.summaryApi(token, body, new ConnectionListener<ChildResponse>() {
                @Override
                public void onSuccess(ConnectionResponse<ChildResponse> connectionResponse) {
                    if (connectionResponse.data != null) {
                        if (connectionResponse.data.getSubcategory().size() != 0) {
                         summary.onSummarySuccess(connectionResponse.data);
                        } else {
                            summary.onNothing(true);
                        }
                    }
                }

                @Override
                public void onFail(Throwable throwable) {
                    summary.onFail(true);
                }
            });
        }
    }
    public void cancelAll(Context context ,int id ){
        boolean net = StaticMethods.isConnectingToInternet(context);
        if (!net) {
            summary.onConnection(true);
        } else {
            if (StaticMethods.userRegisterResponse != null) {
                token = "Bearer " + StaticMethods.userRegisterResponse.Data.getToken();
                userid = StaticMethods.userRegisterResponse.Data.getUserId();
            } else {
                token = "Bearer " + StaticMethods.userData.getToken();
                userid = StaticMethods.userData.getUserId();
            }
            RequestBody body = null;
            try {
                body = MainApiBody.cancelAllTopic(id,userid);
            } catch (Exception e) {

            }

            MainApi.cancelAll(token, body, new ConnectionListener<ResultBoolean>() {
                @Override
                public void onSuccess(ConnectionResponse<ResultBoolean> connectionResponse) {
                    if (connectionResponse.data != null) {
                        if (connectionResponse.data.isResult()) {
                            summary.onCancel(true);
                        } else {
                            summary.onCancel(false);
                        }
                    }
                }

                @Override
                public void onFail(Throwable throwable) {
                    summary.onFail(true);
                }
            });
        }
    }
}
