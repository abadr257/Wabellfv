package com.clixifi.wabell.data;

import com.google.gson.annotations.SerializedName;

public class PackagesArrayItem {

    @SerializedName("Id")
    public int Id ;
    @SerializedName("PackageDuration")
    public String PackageDuration ;
    @SerializedName("PackageDurationAr")
    public String PackageDurationAr ;
    @SerializedName("Price")
    public String  Price;
    @SerializedName("TagLine")
    public String TagLine ;
    @SerializedName("Title")
    public String Title ;  //TitleAr

    @SerializedName("TitleAr")
    public String TitleAr ;

    public String getTitleAr() {
        return TitleAr;
    }

    public String getPackageDurationAr() {
        return PackageDurationAr;
    }

    public void setPackageDurationAr(String packageDurationAr) {
        PackageDurationAr = packageDurationAr;
    }

    public void setTitleAr(String titleAr) {
        TitleAr = titleAr;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getPackageDuration() {
        return PackageDuration;
    }

    public void setPackageDuration(String packageDuration) {
        PackageDuration = packageDuration;
    }

    public String  getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getTagLine() {
        return TagLine;
    }

    public void setTagLine(String tagLine) {
        TagLine = tagLine;
    }
}
