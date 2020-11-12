package com.clixifi.wabell.data;

import com.google.gson.annotations.SerializedName;

public class WhatsAppResponse {

    @SerializedName("result")
    public String result ;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
