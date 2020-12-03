package com.clixifi.wabell.data;

import com.google.gson.annotations.SerializedName;

public class GetResultString {
    @SerializedName("result")
    public String resultString;

    public String getResultString() {
        return resultString;
    }

    public void setResultString(String resultString) {
        this.resultString = resultString;
    }
}
