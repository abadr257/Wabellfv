package com.clixifi.wabell.data;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PackagesArray {
    @SerializedName("result")
    ArrayList<PackagesArrayItem> result ;

    public ArrayList<PackagesArrayItem> getResult() {
        return result;
    }

    public void setResult(ArrayList<PackagesArrayItem> result) {
        this.result = result;
    }
}
