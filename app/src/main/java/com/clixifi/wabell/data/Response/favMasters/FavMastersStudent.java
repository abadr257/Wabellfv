package com.clixifi.wabell.data.Response.favMasters;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class FavMastersStudent {
    @SerializedName("result")
    ArrayList<FavMastersItem> favMasters ;


    public ArrayList<FavMastersItem> getFavMasters() {
        return favMasters;
    }

    public void setFavMasters(ArrayList<FavMastersItem> favMasters) {
        this.favMasters = favMasters;
    }
}
