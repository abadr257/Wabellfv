package com.clixifi.wabell.ui.login;

import com.clixifi.wabell.data.Response.User.LoginData;
import com.clixifi.wabell.data.Response.User.UserResponse;

public interface LoginListener {
    void onSuccess(UserResponse<LoginData> userData);
    void onNoConnection(boolean isConnected);
    void onFail(boolean fail);
    void onMessage(String  message);
}
