package com.clixifi.wabell.ui.tutorReviews;

import android.content.Context;
import android.util.Log;

import com.clixifi.wabell.data.Response.AddFav.AddFavorite;
import com.clixifi.wabell.data.Response.ReviewsArray;
import com.clixifi.wabell.utils.StaticMethods;
import com.clixifi.wabell.utils.network.ConnectionListener;
import com.clixifi.wabell.utils.network.ConnectionResponse;
import com.clixifi.wabell.utils.network.MainApi;
import com.clixifi.wabell.utils.network.MainApiBody;

import okhttp3.RequestBody;

import static android.content.ContentValues.TAG;

public class TutorReviewsPresenter {
    TutorReviewsInterface reviews ;
    String token ;
    public TutorReviewsPresenter(TutorReviewsInterface reviews) {
        this.reviews = reviews;
    }

    public void getReviews(Context context , String tutorId){
        boolean net = StaticMethods.isConnectingToInternet(context);
        if(!net){
            reviews.onConnection(true);
        }else {

            if(StaticMethods.userRegisterResponse != null){
                token ="Bearer "+ StaticMethods.userRegisterResponse.Data.getToken();
            }else {
                token = "Bearer " +StaticMethods.userData.getToken();
            }
            RequestBody body = null ;
            try {
                body = MainApiBody.getReviews(tutorId);
            }catch (Exception e){

            }
            Log.e(TAG, "getReviews: "+tutorId );
            MainApi.getReviewsOfTutor(token, body, new ConnectionListener<ReviewsArray>() {
                @Override
                public void onSuccess(ConnectionResponse<ReviewsArray> connectionResponse) {
                    if(connectionResponse.data != null){
                        reviews.onSuccess(connectionResponse.data);
                    }else {
                        reviews.onFail(true);
                    }
                }

                @Override
                public void onFail(Throwable throwable) {
                    reviews.onFail(true);
                    Log.e(TAG, "onFail: GetRanks "+throwable.toString() );
                }
            });
        }
    }
}
