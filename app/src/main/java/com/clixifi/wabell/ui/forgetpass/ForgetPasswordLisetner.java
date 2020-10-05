package com.clixifi.wabell.ui.forgetpass;

import com.clixifi.wabell.data.Response.OTP.OTPResponse;
import com.clixifi.wabell.data.Response.User.UserId;

public interface ForgetPasswordLisetner {
    void onSuccess(UserId otp);
    void onFails(boolean fail);
    void onNoConnection(boolean noInternet);
    void onEmptyFields(boolean empty);
}
