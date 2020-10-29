package com.clixifi.wabell.ui.tutorReviews;

import com.clixifi.wabell.data.Response.ReviewsArray;

public interface TutorReviewsInterface {
    void onSuccess(ReviewsArray array );
    void onFail(boolean fail);
    void onConnection(boolean isConnected);

}
