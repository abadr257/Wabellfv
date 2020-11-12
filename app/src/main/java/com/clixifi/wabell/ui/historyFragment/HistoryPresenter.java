package com.clixifi.wabell.ui.historyFragment;

import android.content.Context;
import android.util.Log;

import com.clixifi.wabell.data.CurrentPackages;
import com.clixifi.wabell.data.HistoryArray;
import com.clixifi.wabell.utils.StaticMethods;
import com.clixifi.wabell.utils.network.ConnectionListener;
import com.clixifi.wabell.utils.network.ConnectionResponse;
import com.clixifi.wabell.utils.network.MainApi;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;

public class HistoryPresenter {
    HistoryInterface historyInterface ;
    String token ;
    public HistoryPresenter(HistoryInterface historyInterface) {
        this.historyInterface = historyInterface;
    }

    public void getHistory(Context context){
        if (StaticMethods.isConnectingToInternet(context)) {

            token = "Bearer " + StaticMethods.userData.getToken();


            MainApi.getHistoryPackages(token, new ConnectionListener<HistoryArray>() {
                @Override
                public void onSuccess(ConnectionResponse<HistoryArray> connectionResponse) {
                    if (connectionResponse.data != null) {
                        historyInterface.onHistory(connectionResponse.data);
                    } else {
                        historyInterface.onFail(true);
                    }
                }

                @Override
                public void onFail(Throwable throwable) {
                    historyInterface.onFail(true);
                    Log.e(TAG, "onFail: " + throwable.toString());
                }
            });
        } else {
            historyInterface.onConnection(true);
        }
    }
}
