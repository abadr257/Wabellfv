package com.clixifi.wabell.ui.packagesFragment;

import android.content.Context;
import android.util.Log;

import com.clixifi.wabell.data.CurrentPackages;
import com.clixifi.wabell.data.PackagesArray;
import com.clixifi.wabell.data.Response.ResultBoolean;
import com.clixifi.wabell.data.Response.User.ResultForProfile;
import com.clixifi.wabell.utils.StaticMethods;
import com.clixifi.wabell.utils.network.ConnectionListener;
import com.clixifi.wabell.utils.network.ConnectionResponse;
import com.clixifi.wabell.utils.network.MainApi;
import com.clixifi.wabell.utils.network.MainApiBody;

import okhttp3.RequestBody;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;

public class PackagesPresenter {

    PackagesInterface packagesInterface;
    String token;

    public PackagesPresenter(PackagesInterface packagesInterface) {
        this.packagesInterface = packagesInterface;
    }

    public void getPackages(Context context) {
        if (StaticMethods.isConnectingToInternet(context)) {

            token = "Bearer " + StaticMethods.userData.getToken();


            MainApi.getPackagesApi(token, new ConnectionListener<PackagesArray>() {
                @Override
                public void onSuccess(ConnectionResponse<PackagesArray> connectionResponse) {
                    if (connectionResponse.data != null) {
                        packagesInterface.onPackages(connectionResponse.data);
                    } else {
                        packagesInterface.onFail(true);
                    }
                }

                @Override
                public void onFail(Throwable throwable) {
                    packagesInterface.onFail(true);
                    Log.e(TAG, "onFail: " + throwable.toString());
                }
            });
        } else {
            packagesInterface.onConnection(true);
        }
    }

    public void getCurrent(Context context) {
        if (StaticMethods.isConnectingToInternet(context)) {

            token = "Bearer " + StaticMethods.userData.getToken();


            MainApi.getCurrentPackagesApi(token, new ConnectionListener<ResultForProfile<CurrentPackages>>() {
                @Override
                public void onSuccess(ConnectionResponse<ResultForProfile<CurrentPackages>> connectionResponse) {
                    if (connectionResponse.data != null) {
                        packagesInterface.onCurrentPackage(connectionResponse.data);
                    } else {
                        packagesInterface.onFail(true);
                    }
                }

                @Override
                public void onFail(Throwable throwable) {
                    packagesInterface.onFail(true);
                    Log.e(TAG, "onFail: " + throwable.toString());
                }
            });
        } else {
            packagesInterface.onConnection(true);
        }
    }
}
