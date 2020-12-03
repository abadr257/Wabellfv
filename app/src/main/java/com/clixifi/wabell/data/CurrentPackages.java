package com.clixifi.wabell.data;

import com.google.gson.annotations.SerializedName;

public class CurrentPackages {

    @SerializedName("PackageDuration")
    public String PackageDuration ;
    @SerializedName("Price")
    public String  Price ;
    @SerializedName("ExpirationDate")
    public String  ExpirationDate ;
    @SerializedName("StartDate")
    public String StartDate ;
    @SerializedName("TagLine")
    public String TagLine ;

    @SerializedName("remainDays")
    public int remainDays ;

    @SerializedName("PackageDurationAr")
    public String PackageDurationAr;

    public String getPackageDurationAr() {
        return PackageDurationAr;
    }

    public void setPackageDurationAr(String packageDurationAr) {
        PackageDurationAr = packageDurationAr;
    }

    public int getRemainDays() {
        return remainDays;
    }

    public void setRemainDays(int remainDays) {
        this.remainDays = remainDays;
    }

    public String getPackageDuration() {
        return PackageDuration;
    }

    public void setPackageDuration(String packageDuration) {
        PackageDuration = packageDuration;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getExpirationDate() {
        return ExpirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        ExpirationDate = expirationDate;
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
