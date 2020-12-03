package com.clixifi.wabell.data;

import com.google.gson.annotations.SerializedName;

public class PaymentResponse {
    @SerializedName("CheckOutId")
    public String CheckOutId ;

    @SerializedName("description")
    public String description ;


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCode() {
        return CheckOutId;
    }

    public void setCode(String code) {
        this.CheckOutId = code;
    }

}
