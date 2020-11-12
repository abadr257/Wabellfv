package com.clixifi.wabell.data;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CallsArray {

    @SerializedName("result")
    public ArrayList<CallsArrayItem> result ;

    public ArrayList<CallsArrayItem> getResult() {
        return result;
    }

    public void setResult(ArrayList<CallsArrayItem> result) {
        this.result = result;
    }
}
