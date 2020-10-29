package com.clixifi.wabell.data.Response;

import com.google.gson.annotations.SerializedName;

public class AddReviews {
    @SerializedName("Id")
    public int id ;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
