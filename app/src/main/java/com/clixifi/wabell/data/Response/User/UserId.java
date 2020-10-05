package com.clixifi.wabell.data.Response.User;

import com.google.gson.annotations.SerializedName;

public class UserId {

    @SerializedName("result")
    public String userId ;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
