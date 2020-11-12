package com.clixifi.wabell.ui.profile;

import com.clixifi.wabell.data.DeleteCertificates;
import com.clixifi.wabell.data.GetCertificates;
import com.clixifi.wabell.data.MediaResponse;
import com.clixifi.wabell.data.Response.User.UserProfile;
import com.clixifi.wabell.data.Response.User.UserResponse;

public interface ProfileInteface {
    void onSuccessProfile(UserResponse<UserProfile> profile);
    void onFail(boolean fail);
    void onConnection(boolean isConnected);
    void onUpdate(boolean updated);

    void onUpdateProfile(MediaResponse media);


    void onGetCer(GetCertificates cer);
    void onDelete(DeleteCertificates delete);
}
