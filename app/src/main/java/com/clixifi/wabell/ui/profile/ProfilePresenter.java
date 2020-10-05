package com.clixifi.wabell.ui.profile;

import android.content.Context;
import android.util.Log;

import com.clixifi.wabell.data.Response.ResultBoolean;
import com.clixifi.wabell.data.Response.User.ResultForProfile;
import com.clixifi.wabell.data.Response.User.UserProfile;
import com.clixifi.wabell.data.Response.User.UserResponse;
import com.clixifi.wabell.utils.StaticMethods;
import com.clixifi.wabell.utils.network.ConnectionListener;
import com.clixifi.wabell.utils.network.ConnectionResponse;
import com.clixifi.wabell.utils.network.MainApi;
import com.clixifi.wabell.utils.network.MainApiBody;

import okhttp3.RequestBody;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;

public class ProfilePresenter {
    ProfileInteface profileInteface;
    String token;


    public ProfilePresenter(ProfileInteface profileInteface) {
        this.profileInteface = profileInteface;
    }

    public void updateTutorProfile(Context context, String Name, String Email,
                                   String Phone, int LocationId,
                                   String Experience, String Education,
                                   String Tagline, String Biography) {
        if (StaticMethods.isConnectingToInternet(context)) {
            if (StaticMethods.userRegisterResponse != null) {
                token = "Bearer " + StaticMethods.userRegisterResponse.Data.getToken();
            } else if (StaticMethods.userData != null) {
                token = "Bearer " + StaticMethods.userData.getToken();
            }
            Log.e(TAG, "getProfileData: " + token);
            RequestBody body = null;
            try {
                body = MainApiBody.tutorProfileUpdateBody(Name, Email, Phone, LocationId, Experience, Education,
                        Tagline, Biography);
            } catch (Exception e) {

            }
            MainApi.updateTutorProfile(token, body, new ConnectionListener<ResultBoolean>() {
                @Override
                public void onSuccess(ConnectionResponse<ResultBoolean> connectionResponse) {
                    if (connectionResponse.data != null) {
                        if (connectionResponse.data.isResult()) {
                            profileInteface.onUpdate(true);
                        }
                    } else {
                        profileInteface.onFail(true);
                    }
                }

                @Override
                public void onFail(Throwable throwable) {
                    profileInteface.onFail(true);
                    Log.e(TAG, "onFail: " + throwable.toString());
                }
            });
        } else {
            profileInteface.onConnection(true);
        }
    }

    public void updateStudentProfile(Context context, String Name, String Email,
                                     String Phone, int LocationId) {
        if (StaticMethods.isConnectingToInternet(context)) {
            if (StaticMethods.userRegisterResponse != null) {
                token = "Bearer " + StaticMethods.userRegisterResponse.Data.getToken();
            } else if (StaticMethods.userData != null) {
                token = "Bearer " + StaticMethods.userData.getToken();
            }
            Log.e(TAG, "getProfileData: " + token);
            RequestBody body = null;
            try {
                body = MainApiBody.studentUpdateProfileBody(Name, Email, Phone, LocationId);
            } catch (Exception e) {

            }
            MainApi.updateStudentProfile(token, body, new ConnectionListener<ResultBoolean>() {
                @Override
                public void onSuccess(ConnectionResponse<ResultBoolean> connectionResponse) {
                    if (connectionResponse.data != null) {
                        if (connectionResponse.data.isResult()) {
                            profileInteface.onUpdate(true);
                        }
                    } else {
                        profileInteface.onFail(true);
                    }
                }

                @Override
                public void onFail(Throwable throwable) {
                    profileInteface.onFail(true);
                    Log.e(TAG, "onFail: " + throwable.toString());
                }
            });
        } else {
            profileInteface.onConnection(true);
        }
    }

    public void getProfileData(Context context) {
        if (StaticMethods.isConnectingToInternet(context)) {
            if (StaticMethods.userRegisterResponse != null) {
                token = "Bearer " + StaticMethods.userRegisterResponse.Data.getToken();
            } else if (StaticMethods.userData != null) {
                token = "Bearer " + StaticMethods.userData.getToken();
            }
            Log.e(TAG, "getProfileData: " + token);
            MainApi.getProfileData(token, new ConnectionListener<ResultForProfile<UserResponse<UserProfile>>>() {
                @Override
                public void onSuccess(ConnectionResponse<ResultForProfile<UserResponse<UserProfile>>> connectionResponse) {
                    if (connectionResponse.data != null) {
                        Log.e(TAG, "onSuccess: " + connectionResponse.data.result.DataProfile.getEmail());
                        profileInteface.onSuccess(connectionResponse.data.result);
                    } else {
                        profileInteface.onFail(true);
                    }
                }

                @Override
                public void onFail(Throwable throwable) {
                    profileInteface.onFail(true);
                    Log.e(TAG, "onFail: " + throwable.toString());
                }
            });
        } else {
            profileInteface.onConnection(true);
        }
    }
}
