package com.clixifi.wabell.data;

import com.google.gson.annotations.SerializedName;

public class HistoryArrayItem {
    @SerializedName("PackageDuration")
    public String PackageDuration ;

    @SerializedName("PackageDurationAr")
    public String PackageDurationAr ;


    @SerializedName("ExpirationDate")
    public String ExpirationDate ;
    @SerializedName("Price")
    public String  Price;
    @SerializedName("StartDate")
    public String StartDate ;
    @SerializedName("TagLine")
    public String TagLine ;

    @SerializedName("Title")
    public String Title ;
    @SerializedName("TitleAr")
    public String TitleAr ;

    public String getTitleAr() {
        return TitleAr;
    }

    public void setTitleAr(String titleAr) {
        TitleAr = titleAr;
    }

    public String getPackageDurationAr() {
        return PackageDurationAr;
    }

    public void setPackageDurationAr(String packageDurationAr) {
        PackageDurationAr = packageDurationAr;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

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

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
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
