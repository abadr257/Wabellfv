package com.clixifi.wabell.data.Response;

import com.google.gson.annotations.SerializedName;

public class ImageUrl {
    @SerializedName("FilePath")
    public String FilePath ;

    public String getFilePath() {
        return FilePath;
    }

    public void setFilePath(String filePath) {
        FilePath = filePath;
    }
}
