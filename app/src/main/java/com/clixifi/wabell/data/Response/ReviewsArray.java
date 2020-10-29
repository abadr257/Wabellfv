package com.clixifi.wabell.data.Response;

import com.clixifi.wabell.data.Response.GetReviews.ReviewsData;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ReviewsArray {
    @SerializedName("result")
    public ArrayList<ReviewsData> reviewsData;

    public ArrayList<ReviewsData> getReviewsData() {
        return reviewsData;
    }

    public void setReviewsData(ArrayList<ReviewsData> reviewsData) {
        this.reviewsData = reviewsData;
    }
}
