package com.clixifi.wabell.data;

import com.google.gson.annotations.SerializedName;

public class DeleteCertificates {

    @SerializedName("deleted")
    public boolean deleted ;


    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
