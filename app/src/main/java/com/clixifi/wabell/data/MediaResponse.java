package com.clixifi.wabell.data;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MediaResponse {
    @SerializedName("ImgFilePaths")
    public ArrayList<String> ImgFilePaths;

    public ArrayList<String> getImgFilePaths() {
        return ImgFilePaths;
    }

    public void setImgFilePaths(ArrayList<String> imgFilePaths) {
        ImgFilePaths = imgFilePaths;
    }
}
