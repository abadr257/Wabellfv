package com.clixifi.wabell.utils.network;



import com.clixifi.wabell.data.Response.OTP.OTPResponse;
import com.clixifi.wabell.data.Response.RequestLogs.RequestLogsArray;
import com.clixifi.wabell.data.Response.ResultBoolean;
import com.clixifi.wabell.data.Response.TutorList.TutorListArray;
import com.clixifi.wabell.data.Response.User.LoginData;
import com.clixifi.wabell.data.Response.User.RegisterData;
import com.clixifi.wabell.data.Response.User.ResultForProfile;
import com.clixifi.wabell.data.Response.User.UserId;
import com.clixifi.wabell.data.Response.User.UserProfile;
import com.clixifi.wabell.data.Response.User.UserResponse;
import com.clixifi.wabell.data.Response.UserTutorCounters;
import com.clixifi.wabell.data.Response.areas.Areas;
import com.clixifi.wabell.data.Response.cities.Cities;
import com.clixifi.wabell.data.Response.favMasters.FavMastersStudent;
import com.clixifi.wabell.data.Response.featuredTutors.FeaturedArray;
import com.clixifi.wabell.data.Response.requestTopic.RequestTopic;
import com.clixifi.wabell.data.Response.topic.Topics;
import com.clixifi.wabell.data.Response.topicChild.ChildResponse;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface MainApiInterface {

    //Auth
    @POST("/api/account/Login")
    Observable<UserResponse<LoginData>> login(@Body RequestBody requestBody);
    @POST("/api/account/Register")
    Observable<UserResponse<RegisterData>> register(@Body RequestBody requestBody);


    @GET("/api/Lookup/GetLocationByParentId")
    Observable<Cities> cities(@Query("ParentId") int ParentId);
    @GET("/api/Lookup/GetLocationByParentId")
    Observable<Areas> areas(@Query("ParentId") int ParentId);
    @GET("/api/account/GetOTPVerification")
    Observable<OTPResponse> getverificationCode(@Header("Authorization") String auth /*, @Query("userName") String userName , @Query("OTP") String OTP*/);
    @GET("/api/account/VerifyOTPVerification")
    Observable<UserId> sendVerificationCode( @Query("userName") String userName , @Query("OTP") String OTP);
    @POST("/api/account/AddTutorEducationAndExperience")
    Observable<ResultBoolean> tutorExp(@Header("Authorization") String auth , @Body RequestBody requestBody);
    @POST("/api/account/AddTutorBiography")
    Observable<ResultBoolean> tutorBio(@Header("Authorization") String auth , @Body RequestBody requestBody);


    @POST("/api/account/AddAndEditTutorWorkDetails")
    Observable<ResultBoolean> tutorWorkDetails(@Header("Authorization") String auth , @Body RequestBody requestBody);


    //Topics
    @GET("/api/Topic/GetSelectedParent")
    Observable<Topics> getTopicsCategory(@Header("Authorization") String auth);

    //GetChild
    @POST("/api/Topic/GetSelectedSecondLevel")
    Observable<ChildResponse> getChildOfTopics(@Header("Authorization") String auth ,@Body RequestBody body);



    //add the fourth topic
    @POST("/api/account/AddUserTopic")
    Observable<ResultBoolean> addTopics(@Header("Authorization") String auth ,@Body RequestBody body);


    //forgetPassword
    @GET("/api/account/ForgetPassword")
    Observable<UserId> getCodeForgetPass(@Query("userName") String userName);

    //setNewPassword
    @POST("/api/account/SetPassword")
    Observable<UserResponse<RegisterData>> newPassword(@Body RequestBody requestBody);


    //getTutorCounters
    @GET("/api/Tutor/TutorCounters")
    Observable<UserTutorCounters> getTutorCounters(@Header("Authorization") String auth ,@Query("days") int days);



    //requestTopics
    @POST("/api/Tutor/TutorCounters")
    Observable<RequestTopic> requestTopic(@Header("Authorization") String auth , @Body RequestBody body);


    //favMastersFor Student
    @GET("/api/Student/GetFavoriteTutors")
    Observable<FavMastersStudent> getFavMasters(@Header("Authorization") String auth );


    //Featured Masters for Student
    @GET("/api/Student/FeaturedTutors")
    Observable<FeaturedArray> getFeaturedMasters(@Header("Authorization") String auth );

    //get User Profile
    @GET("/api/account/GetProfile")
    Observable<ResultForProfile<UserResponse<UserProfile>>> getUserProfile(@Header("Authorization") String auth );


    // on Cancel a topic
    @POST("/api/account/CancelUserTopic")
    Observable<ResultBoolean> cancelTopic(@Header("Authorization") String auth , @Body RequestBody body );

    //get selected fourth level
    @POST("/api/Topic/GetSelectedFourthLevel")
    Observable<ChildResponse> selectedFourth(@Header("Authorization") String auth , @Body RequestBody body );


    //post tutor profile data
    @POST("/api/account/EditTutorBaseData")
    Observable<ResultBoolean> updateTutorProfile(@Header("Authorization") String auth , @Body RequestBody body );

    //update student profile
    @POST("/api/account/EditStudentBaseData")
    Observable<ResultBoolean> updateStudentProfile(@Header("Authorization") String auth , @Body RequestBody body );


    //get Summary Data
    @POST("/api/Topic/GetTopicsSummary")
    Observable<ChildResponse> getSummary(@Header("Authorization") String auth ,@Body RequestBody body);

    //to cancel all topic
    @POST("/api/account/CancelUserAllTopics")
    Observable<ResultBoolean> cancelAllTopic(@Header("Authorization") String auth ,@Body RequestBody body);


    //get tutor list with filter
    @POST("/api/Student/TutorsList")
    Observable<TutorListArray> getTutorList(@Header("Authorization") String auth , @Body RequestBody body);


    //get all tutors
    @POST("/api/Student/TutorsList")
    Observable<TutorListArray> getTutorList(@Header("Authorization") String auth );


    //get Request Logs
    @POST("/api/Tutor/RequestLogs")
    Observable<RequestLogsArray> getRequestLogs(@Header("Authorization") String auth );

}
