package com.clixifi.wabell.data;

import com.google.gson.annotations.SerializedName;

public class HistoryArrayItem {
    @SerializedName("PackageDuration")
    public String PackageDuration ;
    @SerializedName("ExpirationDate")
    public String ExpirationDate ;
    @SerializedName("Price")
    public double  Price;
    @SerializedName("StartDate")
    public String StartDate ;
    @SerializedName("TagLine")
    public String TagLine ;

    public String getPackageDuration() {
        return PackageDuration;
    }

    public void setPackageDuration(String packageDuration) {
        PackageDuration = packageDuration;
    }

    public String getExpirationDate() {
        return ExpirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        ExpirationDate = expirationDate;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double price) {
        Price = price;
    }

    public String getStartDate() {
        return StartDate;
    }

    public void setStartDate(String startDate) {
        StartDate = startDate;
    }

    public String getTagLine() {
        return TagLine;
    }

    public void setTagLine(String tagLine) {
        TagLine = tagLine;
    }
}
