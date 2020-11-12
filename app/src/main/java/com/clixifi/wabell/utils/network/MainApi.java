package com.clixifi.wabell.utils.network;


import com.clixifi.wabell.data.AddReviewObject;
import com.clixifi.wabell.data.CallsArray;
import com.clixifi.wabell.data.CurrentPackages;
import com.clixifi.wabell.data.DeleteCertificates;
import com.clixifi.wabell.data.GetCertificates;
import com.clixifi.wabell.data.HistoryArray;
import com.clixifi.wabell.data.MediaResponse;
import com.clixifi.wabell.data.PackagesArray;
import com.clixifi.wabell.data.PaymentResponse;
import com.clixifi.wabell.data.Response.AddFav.AddFavorite;
import com.clixifi.wabell.data.Response.AddReviews;
import com.clixifi.wabell.data.Response.GetReviews.ReviewsData;
import com.clixifi.wabell.data.Response.OTP.OTPResponse;
import com.clixifi.wabell.data.Response.RequestLogs.RequestLogsArray;
import com.clixifi.wabell.data.Response.ResultBoolean;
import com.clixifi.wabell.data.Response.ReviewsArray;
import com.clixifi.wabell.data.Response.TutorList.TutorListArray;
import com.clixifi.wabell.data.Response.TutorProfileData.TutorProfileForStudent;
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
import com.clixifi.wabell.data.WhatsAppResponse;
import com.clixifi.wabell.utils.StaticMethods;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainApi {
    private static MainApiInterface getApi() {
        return getApi(MainApiInterface.class);
    }

    public static <T> T getApi(Class<T> aClass) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS).addInterceptor(interceptor).build();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(StaticMethods.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();


        return retrofit.create(aClass);
    }

    public static void LoginApi(RequestBody body, final ConnectionListener<UserResponse<LoginData>> connectionListener) {
        getApi().login(body).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UserResponse<LoginData>>() {
                    @Override
                    public void onError(Throwable e) {
                        connectionListener.onFail(e);
                    }

                    @Override
                    public void onComplete() {
                    }

                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(UserResponse<LoginData> userResponse) {
                        ConnectionResponse<UserResponse<LoginData>> response = new ConnectionResponse<>();
                        response.data = userResponse;
                        connectionListener.onSuccess(response);
                    }
                });
    }

    public static void RegisterApi(RequestBody body, final ConnectionListener<UserResponse<RegisterData>> connectionListener) {
        getApi().register(body).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UserResponse<RegisterData>>() {
                    @Override
                    public void onError(Throwable e) {
                        connectionListener.onFail(e);
                    }

                    @Override
                    public void onComplete() {
                    }

                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(UserResponse<RegisterData> userResponse) {
                        ConnectionResponse<UserResponse<RegisterData>> response = new ConnectionResponse<>();
                        response.data = userResponse;
                        connectionListener.onSuccess(response);
                    }
                });
    }

    public static void CitiesApi(int body, final ConnectionListener<Cities> connectionListener) {
        getApi().cities(body).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Cities>() {
                    @Override
                    public void onError(Throwable e) {
                        connectionListener.onFail(e);
                    }

                    @Override
                    public void onComplete() {
                    }

                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(Cities userResponse) {
                        ConnectionResponse<Cities> response = new ConnectionResponse<>();
                        response.data = userResponse;
                        connectionListener.onSuccess(response);
                    }
                });
    }

    public static void AreasApi(int body, final ConnectionListener<Areas> connectionListener) {
        getApi().areas(body).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Areas>() {
                    @Override
                    public void onError(Throwable e) {
                        connectionListener.onFail(e);
                    }

                    @Override
                    public void onComplete() {
                    }

                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(Areas userResponse) {
                        ConnectionResponse<Areas> response = new ConnectionResponse<>();
                        response.data = userResponse;
                        connectionListener.onSuccess(response);
                    }
                });
    }

    public static void addToFav(String token, String tutorId, final ConnectionListener<AddFavorite> connectionListener) {
        getApi().addToFavorite(token, tutorId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<AddFavorite>() {
                    @Override
                    public void onError(Throwable e) {
                        connectionListener.onFail(e);
                    }

                    @Override
                    public void onComplete() {
                    }

                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(AddFavorite userResponse) {
                        ConnectionResponse<AddFavorite> response = new ConnectionResponse<>();
                        response.data = userResponse;
                        connectionListener.onSuccess(response);
                    }
                });
    }
    public static void getReviewsOfTutor(String token, RequestBody tutorId, final ConnectionListener<ReviewsArray> connectionListener) {
        getApi().getReviews(token, tutorId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ReviewsArray>() {
                    @Override
                    public void onError(Throwable e) {
                        connectionListener.onFail(e);
                    }

                    @Override
                    public void onComplete() {
                    }

                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(ReviewsArray userResponse) {
                        ConnectionResponse<ReviewsArray> response = new ConnectionResponse<>();
                        response.data = userResponse;
                        connectionListener.onSuccess(response);
                    }
                });
    }
    public static void deleteFav(String token, RequestBody body, final ConnectionListener<ResultBoolean> connectionListener) {
        getApi().deleteFav(token, body).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResultBoolean>() {
                    @Override
                    public void onError(Throwable e) {
                        connectionListener.onFail(e);
                    }

                    @Override
                    public void onComplete() {
                    }

                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(ResultBoolean userResponse) {
                        ConnectionResponse<ResultBoolean> response = new ConnectionResponse<>();
                        response.data = userResponse;
                        connectionListener.onSuccess(response);
                    }
                });
    }
    public static void addRequestLog(String token, RequestBody body, final ConnectionListener<ResultBoolean> connectionListener) {
        getApi().addRequestLogs(token, body).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResultBoolean>() {
                    @Override
                    public void onError(Throwable e) {
                        connectionListener.onFail(e);
                    }

                    @Override
                    public void onComplete() {
                    }

                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(ResultBoolean userResponse) {
                        ConnectionResponse<ResultBoolean> response = new ConnectionResponse<>();
                        response.data = userResponse;
                        connectionListener.onSuccess(response);
                    }
                });
    }
    public static void getPackagesApi(String token, final ConnectionListener<PackagesArray> connectionListener) {
        getApi().getPackages(token).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<PackagesArray>() {
                    @Override
                    public void onError(Throwable e) {
                        connectionListener.onFail(e);
                    }

                    @Override
                    public void onComplete() {
                    }

                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(PackagesArray userResponse) {
                        ConnectionResponse<PackagesArray> response = new ConnectionResponse<>();
                        response.data = userResponse;
                        connectionListener.onSuccess(response);
                    }
                });
    }

    public static void getHistoryPackages(String token, final ConnectionListener<HistoryArray> connectionListener) {
        getApi().getPackagesHistory(token).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HistoryArray>() {
                    @Override
                    public void onError(Throwable e) {
                        connectionListener.onFail(e);
                    }

                    @Override
                    public void onComplete() {
                    }

                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(HistoryArray userResponse) {
                        ConnectionResponse<HistoryArray> response = new ConnectionResponse<>();
                        response.data = userResponse;
                        connectionListener.onSuccess(response);
                    }
                });
    }
    public static void getCurrentPackagesApi(String token, final ConnectionListener<ResultForProfile<CurrentPackages>> connectionListener) {
        getApi().getCurrentPackages(token).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResultForProfile<CurrentPackages>>() {
                    @Override
                    public void onError(Throwable e) {
                        connectionListener.onFail(e);
                    }

                    @Override
                    public void onComplete() {
                    }

                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(ResultForProfile<CurrentPackages> userResponse) {
                        ConnectionResponse<ResultForProfile<CurrentPackages>> response = new ConnectionResponse<>();
                        response.data = userResponse;
                        connectionListener.onSuccess(response);
                    }
                });
    }
    public static void payPackage(String token,RequestBody body, final ConnectionListener<ResultForProfile<PaymentResponse>> connectionListener) {
        getApi().payPackage(token , body).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResultForProfile<PaymentResponse>>() {
                    @Override
                    public void onError(Throwable e) {
                        connectionListener.onFail(e);
                    }

                    @Override
                    public void onComplete() {
                    }

                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(ResultForProfile<PaymentResponse> userResponse) {
                        ConnectionResponse<ResultForProfile<PaymentResponse>> response = new ConnectionResponse<>();
                        response.data = userResponse;
                        connectionListener.onSuccess(response);
                    }
                });
    }
    public static void getRequestCalls(String token, final ConnectionListener<RequestLogsArray> connectionListener) {
        getApi().getStudentCalls(token).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RequestLogsArray>() {
                    @Override
                    public void onError(Throwable e) {
                        connectionListener.onFail(e);
                    }

                    @Override
                    public void onComplete() {
                    }

                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(RequestLogsArray userResponse) {
                        ConnectionResponse<RequestLogsArray> response = new ConnectionResponse<>();
                        response.data = userResponse;
                        connectionListener.onSuccess(response);
                    }
                });
    }

    public static void getWhatsAppNum(String token, final ConnectionListener<WhatsAppResponse> connectionListener) {
        getApi().getWhatsApp(token).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<WhatsAppResponse>() {
                    @Override
                    public void onError(Throwable e) {
                        connectionListener.onFail(e);
                    }

                    @Override
                    public void onComplete() {
                    }

                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(WhatsAppResponse userResponse) {
                        ConnectionResponse<WhatsAppResponse> response = new ConnectionResponse<>();
                        response.data = userResponse;
                        connectionListener.onSuccess(response);
                    }
                });
    }

    public static void getRequestMessages(String token, final ConnectionListener<CallsArray> connectionListener) {
        getApi().getMessages(token).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CallsArray>() {
                    @Override
                    public void onError(Throwable e) {
                        connectionListener.onFail(e);
                    }

                    @Override
                    public void onComplete() {
                    }

                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(CallsArray userResponse) {
                        ConnectionResponse<CallsArray> response = new ConnectionResponse<>();
                        response.data = userResponse;
                        connectionListener.onSuccess(response);
                    }
                });
    }

    public static void verificationCode(String token, final ConnectionListener<OTPResponse> connectionListener) {
        getApi().getverificationCode(token).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<OTPResponse>() {
                    @Override
                    public void onError(Throwable e) {
                        connectionListener.onFail(e);
                    }

                    @Override
                    public void onComplete() {
                    }

                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(OTPResponse userResponse) {
                        ConnectionResponse<OTPResponse> response = new ConnectionResponse<>();
                        response.data = userResponse;
                        connectionListener.onSuccess(response);
                    }
                });
    }

    public static void reviewStudents(String token, String studentId, int rate, String comment, final ConnectionListener<AddReviews> connectionListener) {
        getApi().addReviews(token, studentId, rate, comment).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<AddReviews>() {
                    @Override
                    public void onError(Throwable e) {
                        connectionListener.onFail(e);
                    }

                    @Override
                    public void onComplete() {
                    }

                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(AddReviews userResponse) {
                        ConnectionResponse<AddReviews> response = new ConnectionResponse<>();
                        response.data = userResponse;
                        connectionListener.onSuccess(response);
                    }
                });
    }

    public static void updateTutorProfile(String token, RequestBody body, final ConnectionListener<ResultBoolean> connectionListener) {
        getApi().updateTutorProfile(token, body).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResultBoolean>() {
                    @Override
                    public void onError(Throwable e) {
                        connectionListener.onFail(e);
                    }

                    @Override
                    public void onComplete() {
                    }

                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(ResultBoolean userResponse) {
                        ConnectionResponse<ResultBoolean> response = new ConnectionResponse<>();
                        response.data = userResponse;
                        connectionListener.onSuccess(response);
                    }
                });
    }

    public static void setIfOffline(String token, boolean isOnline, String date, final ConnectionListener<ResultBoolean> connectionListener) {
        getApi().setOnline(token, isOnline, date).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResultBoolean>() {
                    @Override
                    public void onError(Throwable e) {
                        connectionListener.onFail(e);
                    }

                    @Override
                    public void onComplete() {
                    }

                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(ResultBoolean userResponse) {
                        ConnectionResponse<ResultBoolean> response = new ConnectionResponse<>();
                        response.data = userResponse;
                        connectionListener.onSuccess(response);
                    }
                });
    }

    public static void getTutorProfile(String token, RequestBody body, final ConnectionListener<TutorProfileForStudent> connectionListener) {
        getApi().getTutorProfile(token, body).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<TutorProfileForStudent>() {
                    @Override
                    public void onError(Throwable e) {
                        connectionListener.onFail(e);
                    }

                    @Override
                    public void onComplete() {
                    }

                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(TutorProfileForStudent userResponse) {
                        ConnectionResponse<TutorProfileForStudent> response = new ConnectionResponse<>();
                        response.data = userResponse;
                        connectionListener.onSuccess(response);
                    }
                });
    }

    public static void updateStudentProfile(String token, RequestBody body, final ConnectionListener<ResultBoolean> connectionListener) {
        getApi().updateStudentProfile(token, body).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResultBoolean>() {
                    @Override
                    public void onError(Throwable e) {
                        connectionListener.onFail(e);
                    }

                    @Override
                    public void onComplete() {
                    }

                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(ResultBoolean userResponse) {
                        ConnectionResponse<ResultBoolean> response = new ConnectionResponse<>();
                        response.data = userResponse;
                        connectionListener.onSuccess(response);
                    }
                });
    }

    public static void cancelAll(String token, RequestBody body, final ConnectionListener<ResultBoolean> connectionListener) {
        getApi().cancelAllTopic(token, body).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResultBoolean>() {
                    @Override
                    public void onError(Throwable e) {
                        connectionListener.onFail(e);
                    }

                    @Override
                    public void onComplete() {
                    }

                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(ResultBoolean userResponse) {
                        ConnectionResponse<ResultBoolean> response = new ConnectionResponse<>();
                        response.data = userResponse;
                        connectionListener.onSuccess(response);
                    }
                });
    }

    public static void getProfileData(String token, final ConnectionListener<ResultForProfile<UserResponse<UserProfile>>> connectionListener) {
        getApi().getUserProfile(token).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResultForProfile<UserResponse<UserProfile>>>() {
                    @Override
                    public void onError(Throwable e) {
                        connectionListener.onFail(e);
                    }

                    @Override
                    public void onComplete() {
                    }

                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(ResultForProfile<UserResponse<UserProfile>> userResponse) {
                        ConnectionResponse<ResultForProfile<UserResponse<UserProfile>>> response = new ConnectionResponse<>();
                        response.data = userResponse;
                        connectionListener.onSuccess(response);
                    }
                });
    }
    public static void uploadProfileTutor(String AccountId ,String category, boolean fromUi, List<MultipartBody.Part> body , final ConnectionListener<MediaResponse> connectionListener) {
        getApi().uploadMedia( AccountId,category,false ,body).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MediaResponse>() {
                    @Override
                    public void onError(Throwable e) {
                        connectionListener.onFail(e);
                    }

                    @Override
                    public void onComplete() {
                    }

                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(MediaResponse userResponse) {
                        ConnectionResponse<MediaResponse> response = new ConnectionResponse<>();
                        response.data = userResponse;
                        connectionListener.onSuccess(response);
                    }
                });
    }
    //getFeaturedMasters
    public static void getFeatured(String token, final ConnectionListener<FeaturedArray> connectionListener) {
        getApi().getFeaturedMasters(token).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<FeaturedArray>() {
                    @Override
                    public void onError(Throwable e) {
                        connectionListener.onFail(e);
                    }

                    @Override
                    public void onComplete() {
                    }

                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(FeaturedArray userResponse) {
                        ConnectionResponse<FeaturedArray> response = new ConnectionResponse<>();
                        response.data = userResponse;
                        connectionListener.onSuccess(response);
                    }
                });
    }

    public static void workApi(String token, RequestBody body, final ConnectionListener<ResultBoolean> connectionListener) {
        getApi().tutorWorkDetails(token, body).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResultBoolean>() {
                    @Override
                    public void onError(Throwable e) {
                        connectionListener.onFail(e);
                    }

                    @Override
                    public void onComplete() {
                    }

                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(ResultBoolean userResponse) {
                        ConnectionResponse<ResultBoolean> response = new ConnectionResponse<>();
                        response.data = userResponse;
                        connectionListener.onSuccess(response);
                    }
                });
    }

    public static void expAndEduApi(String token, RequestBody body, final ConnectionListener<ResultBoolean> connectionListener) {
        getApi().tutorExp(token, body).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResultBoolean>() {
                    @Override
                    public void onError(Throwable e) {
                        connectionListener.onFail(e);
                    }

                    @Override
                    public void onComplete() {
                    }

                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(ResultBoolean userResponse) {
                        ConnectionResponse<ResultBoolean> response = new ConnectionResponse<>();
                        response.data = userResponse;
                        connectionListener.onSuccess(response);
                    }
                });
    }

    public static void BioApi(String token, RequestBody body, final ConnectionListener<ResultBoolean> connectionListener) {
        getApi().tutorBio(token, body).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResultBoolean>() {
                    @Override
                    public void onError(Throwable e) {
                        connectionListener.onFail(e);
                    }

                    @Override
                    public void onComplete() {
                    }

                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(ResultBoolean userResponse) {
                        ConnectionResponse<ResultBoolean> response = new ConnectionResponse<>();
                        response.data = userResponse;
                        connectionListener.onSuccess(response);
                    }
                });
    }

    public static void sendVerify(String email, String code, final ConnectionListener<UserId> connectionListener) {
        getApi().sendVerificationCode(email, code).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UserId>() {
                    @Override
                    public void onError(Throwable e) {
                        connectionListener.onFail(e);
                    }

                    @Override
                    public void onComplete() {
                    }

                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(UserId userResponse) {
                        ConnectionResponse<UserId> response = new ConnectionResponse<>();
                        response.data = userResponse;
                        connectionListener.onSuccess(response);
                    }
                });
    }

    public static void getTopics(String token, final ConnectionListener<Topics> connectionListener) {
        getApi().getTopicsCategory(token).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Topics>() {
                    @Override
                    public void onError(Throwable e) {
                        connectionListener.onFail(e);
                    }

                    @Override
                    public void onComplete() {
                    }

                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(Topics userResponse) {
                        ConnectionResponse<Topics> response = new ConnectionResponse<>();
                        response.data = userResponse;
                        connectionListener.onSuccess(response);
                    }
                });
    }

    public static void getSelectedFouth(String token, RequestBody body, final ConnectionListener<ChildResponse> connectionListener) {
        getApi().selectedFourth(token, body).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ChildResponse>() {
                    @Override
                    public void onError(Throwable e) {
                        connectionListener.onFail(e);
                    }

                    @Override
                    public void onComplete() {
                    }

                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(ChildResponse userResponse) {
                        ConnectionResponse<ChildResponse> response = new ConnectionResponse<>();
                        response.data = userResponse;
                        connectionListener.onSuccess(response);
                    }
                });
    }

    public static void getTopicsChild(String token, RequestBody body, final ConnectionListener<ChildResponse> connectionListener) {
        getApi().getChildOfTopics(token, body).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ChildResponse>() {
                    @Override
                    public void onError(Throwable e) {
                        connectionListener.onFail(e);
                    }

                    @Override
                    public void onComplete() {
                    }

                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(ChildResponse userResponse) {
                        ConnectionResponse<ChildResponse> response = new ConnectionResponse<>();
                        response.data = userResponse;
                        connectionListener.onSuccess(response);
                    }
                });
    }

    public static void getOTPForegtPass(String userName, final ConnectionListener<UserId> connectionListener) {
        getApi().getCodeForgetPass(userName).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UserId>() {
                    @Override
                    public void onError(Throwable e) {
                        connectionListener.onFail(e);
                    }

                    @Override
                    public void onComplete() {
                    }

                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(UserId userResponse) {
                        ConnectionResponse<UserId> response = new ConnectionResponse<>();
                        response.data = userResponse;
                        connectionListener.onSuccess(response);
                    }
                });
    }

    public static void setNewPassword(RequestBody body, final ConnectionListener<UserResponse<RegisterData>> connectionListener) {
        getApi().newPassword(body).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UserResponse<RegisterData>>() {
                    @Override
                    public void onError(Throwable e) {
                        connectionListener.onFail(e);
                    }

                    @Override
                    public void onComplete() {
                    }

                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(UserResponse<RegisterData> userResponse) {
                        ConnectionResponse<UserResponse<RegisterData>> response = new ConnectionResponse<>();
                        response.data = userResponse;
                        connectionListener.onSuccess(response);
                    }
                });
    }

    public static void getTutorCounters(String token, int body, final ConnectionListener<UserTutorCounters> connectionListener) {
        getApi().getTutorCounters(token, body).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UserTutorCounters>() {
                    @Override
                    public void onError(Throwable e) {
                        connectionListener.onFail(e);
                    }

                    @Override
                    public void onComplete() {
                    }

                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(UserTutorCounters userResponse) {
                        ConnectionResponse<UserTutorCounters> response = new ConnectionResponse<>();
                        response.data = userResponse;
                        connectionListener.onSuccess(response);
                    }
                });
    }

    public static void requestTopic(String token, RequestBody body, final ConnectionListener<RequestTopic> connectionListener) {
        getApi().requestTopic(token, body).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RequestTopic>() {
                    @Override
                    public void onError(Throwable e) {
                        connectionListener.onFail(e);
                    }

                    @Override
                    public void onComplete() {
                    }

                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(RequestTopic userResponse) {
                        ConnectionResponse<RequestTopic> response = new ConnectionResponse<>();
                        response.data = userResponse;
                        connectionListener.onSuccess(response);
                    }
                });
    }

    public static void getFavMasters(String token, final ConnectionListener<FavMastersStudent> connectionListener) {
        getApi().getFavMasters(token).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<FavMastersStudent>() {
                    @Override
                    public void onError(Throwable e) {
                        connectionListener.onFail(e);
                    }

                    @Override
                    public void onComplete() {
                    }

                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(FavMastersStudent userResponse) {
                        ConnectionResponse<FavMastersStudent> response = new ConnectionResponse<>();
                        response.data = userResponse;
                        connectionListener.onSuccess(response);
                    }
                });
    }

    public static void getTutorList(String token, final ConnectionListener<TutorListArray> connectionListener) {
        getApi().getTutorList(token).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<TutorListArray>() {
                    @Override
                    public void onError(Throwable e) {
                        connectionListener.onFail(e);
                    }

                    @Override
                    public void onComplete() {
                    }

                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(TutorListArray userResponse) {
                        ConnectionResponse<TutorListArray> response = new ConnectionResponse<>();
                        response.data = userResponse;
                        connectionListener.onSuccess(response);
                    }
                });
    }


    public static void getRequestsLogs(String token, final ConnectionListener<RequestLogsArray> connectionListener) {
        getApi().getRequestLogs(token).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RequestLogsArray>() {
                    @Override
                    public void onError(Throwable e) {
                        connectionListener.onFail(e);
                    }

                    @Override
                    public void onComplete() {
                    }

                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(RequestLogsArray userResponse) {
                        ConnectionResponse<RequestLogsArray> response = new ConnectionResponse<>();
                        response.data = userResponse;
                        connectionListener.onSuccess(response);
                    }
                });
    }

    public static void getTutorList(String token, RequestBody body, final ConnectionListener<TutorListArray> connectionListener) {
        getApi().getTutorList(token, body).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<TutorListArray>() {
                    @Override
                    public void onError(Throwable e) {
                        connectionListener.onFail(e);
                    }

                    @Override
                    public void onComplete() {
                    }

                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(TutorListArray userResponse) {
                        ConnectionResponse<TutorListArray> response = new ConnectionResponse<>();
                        response.data = userResponse;
                        connectionListener.onSuccess(response);
                    }
                });
    }

    public static void cancelTopic(String token, RequestBody body, final ConnectionListener<ResultBoolean> connectionListener) {
        getApi().cancelTopic(token, body).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResultBoolean>() {
                    @Override
                    public void onError(Throwable e) {
                        connectionListener.onFail(e);
                    }

                    @Override
                    public void onComplete() {
                    }

                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(ResultBoolean userResponse) {
                        ConnectionResponse<ResultBoolean> response = new ConnectionResponse<>();
                        response.data = userResponse;
                        connectionListener.onSuccess(response);
                    }
                });
    }

    public static void summaryApi(String token, RequestBody body, final ConnectionListener<ChildResponse> connectionListener) {
        getApi().getSummary(token, body).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ChildResponse>() {
                    @Override
                    public void onError(Throwable e) {
                        connectionListener.onFail(e);
                    }

                    @Override
                    public void onComplete() {
                    }

                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(ChildResponse userResponse) {
                        ConnectionResponse<ChildResponse> response = new ConnectionResponse<>();
                        response.data = userResponse;
                        connectionListener.onSuccess(response);
                    }
                });
    }

    public static void addTopic(String token, RequestBody body, final ConnectionListener<ResultBoolean> connectionListener) {
        getApi().addTopics(token, body).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResultBoolean>() {
                    @Override
                    public void onError(Throwable e) {
                        connectionListener.onFail(e);
                    }

                    @Override
                    public void onComplete() {
                    }

                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(ResultBoolean userResponse) {
                        ConnectionResponse<ResultBoolean> response = new ConnectionResponse<>();
                        response.data = userResponse;
                        connectionListener.onSuccess(response);
                    }
                });
    }


    //delete Certificates


    public static void deleteCer(String token,int file , final ConnectionListener<DeleteCertificates> connectionListener) {
        getApi().deleteCertificates(token , file).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DeleteCertificates>() {
                    @Override
                    public void onError(Throwable e) {
                        connectionListener.onFail(e);
                    }

                    @Override
                    public void onComplete() {
                    }

                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(DeleteCertificates userResponse) {
                        ConnectionResponse<DeleteCertificates> response = new ConnectionResponse<>();
                        response.data = userResponse;
                        connectionListener.onSuccess(response);
                    }
                });
    }
    public static void getCer(String token, final ConnectionListener<GetCertificates> connectionListener) {
        getApi().getCertificates(token ).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GetCertificates>() {
                    @Override
                    public void onError(Throwable e) {
                        connectionListener.onFail(e);
                    }

                    @Override
                    public void onComplete() {
                    }

                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(GetCertificates userResponse) {
                        ConnectionResponse<GetCertificates> response = new ConnectionResponse<>();
                        response.data = userResponse;
                        connectionListener.onSuccess(response);
                    }
                });
    }

}
