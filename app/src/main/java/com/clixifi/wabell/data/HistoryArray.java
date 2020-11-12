package com.clixifi.wabell.data;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class HistoryArray {
    @SerializedName("result")
    ArrayList<HistoryArrayItem> result ;

    public ArrayList<HistoryArrayItem> getResult() {
        return result;
    }

    public void setResult(ArrayList<HistoryArrayItem> result) {
        this.result = result;
    }
}
