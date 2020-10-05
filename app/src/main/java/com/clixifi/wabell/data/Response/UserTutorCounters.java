package com.clixifi.wabell.data.Response;

import com.google.gson.annotations.SerializedName;

public class UserTutorCounters {

    @SerializedName("ViewsCount")
    public int ViewsCount ;

    @SerializedName("SimilarMastersCount")
    public int SimilarMastersCount ;

    @SerializedName("FavoritedCount")
    public int FavoritedCount ;

    @SerializedName("CallsCount")
    public int CallsCount ;

    public int getViewsCount() {
        return ViewsCount;
    }

    public void setViewsCount(int viewsCount) {
        ViewsCount = viewsCount;
    }

    public int getSimilarMastersCount() {
        return SimilarMastersCount;
    }

    public void setSimilarMastersCount(int similarMastersCount) {
        SimilarMastersCount = similarMastersCount;
    }

    public int getFavoritedCount() {
        return FavoritedCount;
    }

    public void setFavoritedCount(int favoritedCount) {
        FavoritedCount = favoritedCount;
    }

    public int getCallsCount() {
        return CallsCount;
    }

    public void setCallsCount(int callsCount) {
        CallsCount = callsCount;
    }
}
