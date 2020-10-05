package com.clixifi.wabell.data.Response.expandedu;

import com.google.gson.annotations.SerializedName;

public class TutorData {
    @SerializedName("result")
    public boolean added ;

    public boolean isAdded() {
        return added;
    }

    public void setAdded(boolean added) {
        this.added = added;
    }
}
