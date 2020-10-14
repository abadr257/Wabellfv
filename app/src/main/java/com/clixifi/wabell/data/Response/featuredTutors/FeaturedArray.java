package com.clixifi.wabell.data.Response.featuredTutors;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class FeaturedArray {

    @SerializedName("result")
    public ArrayList<FeaturedItem> items ;


    public ArrayList<FeaturedItem> getItems() {
        return items;
    }

    public void setItems(ArrayList<FeaturedItem> items) {
        this.items = items;
    }
}
