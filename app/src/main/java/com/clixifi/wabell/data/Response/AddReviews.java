package com.clixifi.wabell.data.Response;

import com.google.gson.annotations.SerializedName;

public class AddReviews {
    @SerializedName("message")
    public String message ;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
