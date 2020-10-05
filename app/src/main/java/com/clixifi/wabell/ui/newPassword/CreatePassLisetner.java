package com.clixifi.wabell.ui.newPassword;

import com.clixifi.wabell.data.Response.OTP.OTPResponse;
import com.clixifi.wabell.data.Response.User.RegisterData;
import com.clixifi.wabell.data.Response.User.UserId;
import com.clixifi.wabell.data.Response.User.UserResponse;

public interface CreatePassLisetner {
    void onSuccess(UserResponse<RegisterData> dataUserResponse);
    void onFails(boolean fail);
    void onNoConnection(boolean noInternet);
    void onEmptyFields(boolean empty);
}
