package com.clixifi.wabell.data;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GetCertificates {
    @SerializedName("media")
    public ArrayList<CertificatesItem> media ;

    public ArrayList<CertificatesItem> getMedia() {
        return media;
    }

    public void setMedia(ArrayList<CertificatesItem> media) {
        this.media = media;
    }
}
