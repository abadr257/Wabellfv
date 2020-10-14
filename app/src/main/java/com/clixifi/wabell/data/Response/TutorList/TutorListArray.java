package com.clixifi.wabell.data.Response.TutorList;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class TutorListArray {
    @SerializedName("result")
    public ArrayList<TutorItem> result ;

    public ArrayList<TutorItem> getResult() {
        return result;
    }

    public void setResult(ArrayList<TutorItem> result) {
        this.result = result;
    }
}
