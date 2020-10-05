package com.clixifi.wabell.data.Response.User;

import com.google.gson.annotations.SerializedName;

public class ResultForProfile<T> {
    @SerializedName("result")
    public T result;
}
