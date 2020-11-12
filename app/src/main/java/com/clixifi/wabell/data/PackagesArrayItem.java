package com.clixifi.wabell.data;

import com.google.gson.annotations.SerializedName;

public class PackagesArrayItem {

    @SerializedName("Id")
    public int Id ;
    @SerializedName("PackageDuration")
    public String PackageDuration ;
    @SerializedName("Price")
    public double  Price;
    @SerializedName("TagLine")
    public String TagLine ;

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

    public double getPrice() {
        return Price;
    }

    public void setPrice(double price) {
        Price = price;
    }

    public String getTagLine() {
        return TagLine;
    }

    public void setTagLine(String tagLine) {
        TagLine = tagLine;
    }
}
