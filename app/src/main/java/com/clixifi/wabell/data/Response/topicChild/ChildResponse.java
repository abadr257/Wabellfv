package com.clixifi.wabell.data.Response.topicChild;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ChildResponse {

    @SerializedName("result")
    public ArrayList<ChildItem> subcategory;

    public ArrayList<ChildItem> getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(ArrayList<ChildItem> subcategory) {
        this.subcategory = subcategory;
    }
}
