package com.clixifi.wabell.ui.payment;

import android.content.Context;

import com.clixifi.wabell.data.PaymentResponse;
import com.clixifi.wabell.data.Response.ResultBoolean;
import com.clixifi.wabell.data.Response.User.ResultForProfile;
import com.clixifi.wabell.utils.StaticMethods;
import com.clixifi.wabell.utils.network.ConnectionListener;
import com.clixifi.wabell.utils.network.ConnectionResponse;
import com.clixifi.wabell.utils.network.MainApi;
import com.clixifi.wabell.utils.network.MainApiBody;

import okhttp3.RequestBody;

public class PaymentPresenter {
    PaymentInterface pay ;

    public PaymentPresenter(PaymentInterface pay) {
        this.pay = pay;
    }



    public void doPayment(Context context  , RequestBody body){
        boolean network = StaticMethods.isConnectingToInternet(context);
        if(!network){
            pay.onConnection(true);
        }else {

            String token = "Bearer "+StaticMethods.userData.getToken();
            MainApi.payPackage(token, body, new ConnectionListener<ResultForProfile<PaymentResponse>>() {
                @Override
                public void onSuccess(ConnectionResponse<ResultForProfile<PaymentResponse>> connectionResponse) {
                    if(connectionResponse.data != null){
                        pay.onPayment(connectionResponse.data.result.description);
                    }else {
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
}
