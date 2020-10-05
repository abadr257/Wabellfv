package com.clixifi.wabell.ui.favMasters;

import android.content.Context;
import android.util.Log;

import com.clixifi.wabell.data.Response.favMasters.FavMastersStudent;
import com.clixifi.wabell.ui.Adapters.PagerAdapter;
import com.clixifi.wabell.utils.StaticMethods;
import com.clixifi.wabell.utils.network.ConnectionListener;
import com.clixifi.wabell.utils.network.ConnectionResponse;
import com.clixifi.wabell.utils.network.MainApi;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;

public class FavMastersPresenter {
    FavMastersInterface fav ;
    String token ;
    public FavMastersPresenter(FavMastersInterface fav) {
        this.fav = fav;
    }

    public void getFav(Context context){
        boolean net = StaticMethods.isConnectingToInternet(context);
        if(!net){
            fav.onConnection(true);
        }else {
            if(StaticMethods.userRegisterResponse != null){
                token = "Bearer "+StaticMethods.userRegisterResponse.Data.getToken();
            }else {
                token = "Bearer "+StaticMethods.userData.getToken();
            }


            MainApi.getFavMasters(token, new ConnectionListener<FavMastersStudent>() {
                @Override
                public void onSuccess(ConnectionResponse<FavMastersStudent> connectionResponse) {
                    if(connectionResponse.data != null) {
                        fav.onSuccess(connectionResponse.data);
                    }else {
                        fav.onFail(true);
                    }
                }

                @Override
                public void onFail(Throwable throwable) {
                    fav.onFail(true);
                    Log.e(TAG, "onFail: "+throwable.toString());
                }
            });
        }
    }
}
