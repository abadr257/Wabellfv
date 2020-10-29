package com.clixifi.wabell.ui.profile;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.clixifi.wabell.data.MediaResponse;
import com.clixifi.wabell.data.Response.ResultBoolean;
import com.clixifi.wabell.data.Response.User.ResultForProfile;
import com.clixifi.wabell.data.Response.User.UserProfile;
import com.clixifi.wabell.data.Response.User.UserResponse;
import com.clixifi.wabell.data.Response.files.Files;
import com.clixifi.wabell.utils.StaticMethods;
import com.clixifi.wabell.utils.network.ConnectionListener;
import com.clixifi.wabell.utils.network.ConnectionResponse;
import com.clixifi.wabell.utils.network.MainApi;
import com.clixifi.wabell.utils.network.MainApiBody;

import org.json.JSONArray;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;

public class ProfilePresenter {
    ProfileInteface profileInteface;
    String token, id;


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
                id = StaticMethods.userRegisterResponse.Data.getUserId();
            } else if (StaticMethods.userData != null) {
                token = "Bearer " + StaticMethods.userData.getToken();
                id = StaticMethods.userData.getUserId();
            }
            Log.e(TAG, "getProfileData: " + token);
            RequestBody body = null;
            try {
                body = MainApiBody.tutorProfileUpdateBody(Name, Email, Phone, LocationId, Experience, Education,
                        Tagline, Biography, id);
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

    public void uploadImageProfileTutor(Context context, ArrayList<File> profile) {
        if (StaticMethods.isConnectingToInternet(context)) {

            if (StaticMethods.userRegisterResponse != null) {
                id = StaticMethods.userRegisterResponse.Data.getUserId();
            } else if (StaticMethods.userData != null) {
                id = StaticMethods.userData.getUserId();
            }
            /*RequestBody requestFile =
                    RequestBody.create(MediaType.parse("multipart/form-data"), profile);
            MultipartBody.Part body =
                    MultipartBody.Part.createFormData("profilepic", profile.getName(), requestFile);*/

           List<MultipartBody.Part> parts = new ArrayList<>();
            for (int i = 0 ; i < profile.size() ; i++){
                File f = profile.get(i);
                RequestBody requestImageFile = RequestBody.create(MediaType.parse("multipart/form-data"),f);
                parts.add(MultipartBody.Part.createFormData("profilepic",f.getName(),requestImageFile));
            }


            MainApi.uploadProfileTutor(id, "tutor",false, parts, new ConnectionListener<MediaResponse>() {
                @Override
                public void onSuccess(ConnectionResponse<MediaResponse> connectionResponse) {
                    if (connectionResponse.data != null){
                        profileInteface.onUpdateProfile(connectionResponse.data);
                    }else {
                        profileInteface.onFail(true);
                    }
                }

                @Override
                public void onFail(Throwable throwable) {
                    profileInteface.onFail(true);
                    Log.e(TAG, "onFail: Media"+throwable.toString() );
                }
            });
        } else {
            profileInteface.onConnection(true);
        }
    }
    public void uploadImageProfileStudent(Context context, ArrayList<File> profile) {
        if (StaticMethods.isConnectingToInternet(context)) {

            if (StaticMethods.userRegisterResponse != null) {
                id = StaticMethods.userRegisterResponse.Data.getUserId();
            } else if (StaticMethods.userData != null) {
                id = StaticMethods.userData.getUserId();
            }
            /*RequestBody requestFile =
                    RequestBody.create(MediaType.parse("multipart/form-data"), profile);
            MultipartBody.Part body =
                    MultipartBody.Part.createFormData("profilepic", profile.getName(), requestFile);*/

            List<MultipartBody.Part> parts = new ArrayList<>();
            for (int i = 0 ; i < profile.size() ; i++){
                File f = profile.get(i);
                RequestBody requestImageFile = RequestBody.create(MediaType.parse("multipart/form-data"),f);
                parts.add(MultipartBody.Part.createFormData("profilepic",f.getName(),requestImageFile));
            }


            MainApi.uploadProfileTutor(id, "student",false, parts, new ConnectionListener<MediaResponse>() {
                @Override
                public void onSuccess(ConnectionResponse<MediaResponse> connectionResponse) {
                    if (connectionResponse.data != null){
                        profileInteface.onUpdateProfile(connectionResponse.data);
                    }else {
                        profileInteface.onFail(true);
                    }
                }

                @Override
                public void onFail(Throwable throwable) {
                    profileInteface.onFail(true);
                    Log.e(TAG, "onFail: Media"+throwable.toString() );
                }
            });
        } else {
            profileInteface.onConnection(true);
        }
    }
    public void uploadImageProfileCertificates(Context context, ArrayList<File> profile) {
        if (StaticMethods.isConnectingToInternet(context)) {

            if (StaticMethods.userRegisterResponse != null) {
                id = StaticMethods.userRegisterResponse.Data.getUserId();
            } else if (StaticMethods.userData != null) {
                id = StaticMethods.userData.getUserId();
            }


            List<MultipartBody.Part> parts = new ArrayList<>();
            for (int i = 0 ; i < profile.size() ; i++){
                File f = profile.get(i);
                RequestBody requestImageFile = RequestBody.create(MediaType.parse("multipart/form-data"),f);
                parts.add(MultipartBody.Part.createFormData("profilepic",f.getName(),requestImageFile));
            }


            MainApi.uploadProfileTutor(id, "certificates",false, parts, new ConnectionListener<MediaResponse>() {
                @Override
                public void onSuccess(ConnectionResponse<MediaResponse> connectionResponse) {
                    if (connectionResponse.data != null){
                        profileInteface.onUpdateProfile(connectionResponse.data);
                    }else {
                        profileInteface.onFail(true);
                    }
                }

                @Override
                public void onFail(Throwable throwable) {
                    profileInteface.onFail(true);
                    Log.e(TAG, "onFail: Media"+throwable.toString() );
                }
            });
        } else {
            profileInteface.onConnection(true);
        }
    }

}
