package com.clixifi.wabell.data.Response;

import com.google.gson.annotations.SerializedName;

public class ResultBoolean {
    @SerializedName("result")
    public boolean result ;

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }
}
