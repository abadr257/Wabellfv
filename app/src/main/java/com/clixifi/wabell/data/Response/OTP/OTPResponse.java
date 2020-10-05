package com.clixifi.wabell.data.Response.OTP;

import com.google.gson.annotations.SerializedName;

public class OTPResponse {

    @SerializedName("code")
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
