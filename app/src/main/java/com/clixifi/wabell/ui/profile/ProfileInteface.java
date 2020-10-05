package com.clixifi.wabell.ui.profile;

import com.clixifi.wabell.data.Response.User.UserProfile;
import com.clixifi.wabell.data.Response.User.UserResponse;

public interface ProfileInteface {
    void onSuccess(UserResponse<UserProfile> profile);
    void onFail(boolean fail);
    void onConnection(boolean isConnected);
    void onUpdate(boolean updated);
}
