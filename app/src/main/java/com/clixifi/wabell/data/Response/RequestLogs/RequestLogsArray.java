package com.clixifi.wabell.data.Response.RequestLogs;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class RequestLogsArray {
    @SerializedName("result")
    public ArrayList<RequestLogsItem> result ;

    public ArrayList<RequestLogsItem> getResult() {
        return result;
    }

    public void setResult(ArrayList<RequestLogsItem> result) {
        this.result = result;
    }
}
