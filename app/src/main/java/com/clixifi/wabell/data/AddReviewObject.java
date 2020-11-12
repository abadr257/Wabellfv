package com.clixifi.wabell.data;

import com.google.gson.annotations.SerializedName;

public class AddReviewObject<T> {
    @SerializedName("result")
    public T result;

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
