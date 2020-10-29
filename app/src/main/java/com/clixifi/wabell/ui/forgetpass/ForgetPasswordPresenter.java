package com.clixifi.wabell.ui.forgetpass;

import android.content.Context;

import com.clixifi.wabell.data.Response.OTP.OTPResponse;
import com.clixifi.wabell.data.Response.User.UserId;
import com.clixifi.wabell.utils.StaticMethods;
import com.clixifi.wabell.utils.network.ConnectionListener;
import com.clixifi.wabell.utils.network.ConnectionResponse;
import com.clixifi.wabell.utils.network.MainApi;

public class ForgetPasswordPresenter {
    ForgetPasswordLisetner forget;

    public ForgetPasswordPresenter(ForgetPasswordLisetner forget) {
        this.forget = forget;
    }

    public void getCode(Context context, String userName) {
        boolean network = StaticMethods.isConnectingToInternet(context);
        if (!network) {
            forget.onNoConnection(true);
        } else if (userName.isEmpty()) {
            forget.onEmptyFields(true);
        } else {
            MainApi.getOTPForegtPass(userName, new ConnectionListener<UserId>() {
                @Override
                public void onSuccess(ConnectionResponse<UserId> connectionResponse) {
                    if (connectionResponse.data != null) {
                        forget.onSuccess(connectionResponse.data);
                    } else {
                        forget.onFails(true);
                    }
                }

                @Override
                public void onFail(Throwable throwable) {
                    forget.onFails(true);
                }
            });
        }
    }
}
