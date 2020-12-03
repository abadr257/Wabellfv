package com.clixifi.wabell.ui.payment;

import android.content.Context;
import android.util.JsonReader;
import android.util.Log;

import com.clixifi.wabell.data.GetResultString;
import com.clixifi.wabell.data.PaymentResponse;
import com.clixifi.wabell.data.Response.ResultBoolean;
import com.clixifi.wabell.data.Response.User.ResultForProfile;
import com.clixifi.wabell.data.Response.User.UserResponse;
import com.clixifi.wabell.utils.StaticMethods;
import com.clixifi.wabell.utils.ToastUtil;
import com.clixifi.wabell.utils.network.ConnectionListener;
import com.clixifi.wabell.utils.network.ConnectionResponse;
import com.clixifi.wabell.utils.network.MainApi;
import com.clixifi.wabell.utils.network.MainApiBody;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import okhttp3.RequestBody;

import static android.content.ContentValues.TAG;

public class PaymentPresenter {
    PaymentInterface pay;

    public PaymentPresenter(PaymentInterface pay) {
        this.pay = pay;
    }


    public void doPayment(Context context, int body) {
        boolean network = StaticMethods.isConnectingToInternet(context);
        if (!network) {
            pay.onConnection(true);
        } else {
            String token = "Bearer " + StaticMethods.userData.getToken();
            MainApi.payPackage(token,body, new ConnectionListener<PaymentResponse>() {
                @Override
                public void onSuccess(ConnectionResponse<PaymentResponse> connectionResponse) {
                    if (connectionResponse.data != null) {
                        pay.onPayment(connectionResponse.data.getCode());
                    } else {
                        pay.onFail(true);
                    }
                }

                @Override
                public void onFail(Throwable throwable) {
                    pay.onFail(true);
                }
            });
        }
    }
    public void requestStatus(final Context context, int body , String checkOut) {
        boolean network = StaticMethods.isConnectingToInternet(context);
        if (!network) {
            pay.onConnection(true);
        } else {
            String token = "Bearer " + StaticMethods.userData.getToken();
            MainApi.requestStatus(token,body,checkOut , new ConnectionListener<GetResultString>() {
                @Override
                public void onSuccess(ConnectionResponse<GetResultString> connectionResponse) {
                    if (connectionResponse.data != null) {
                        pay.onPaymentSuccess(true);
                        ToastUtil.showSuccessToast(context , connectionResponse.data.getResultString());
                    } else {
                        pay.onFail(true);
                    }
                }

                @Override
                public void onFail(Throwable throwable) {
                    pay.onFail(true);
                    Log.e(TAG, "onFail: "+"Here" );
                }
            });
        }
    }



}
