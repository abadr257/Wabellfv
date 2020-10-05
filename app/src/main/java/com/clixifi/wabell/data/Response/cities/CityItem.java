package com.clixifi.wabell.data.Response.cities;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CityItem {
    @SerializedName("Id")
    private int Id;
    @SerializedName("ParentId")
    private String ParentId;
    @SerializedName("Parent")
    private String Parent;
    @SerializedName("Code2")
    private String Code2;
    @SerializedName("Code3")
    private String Code3;
    @SerializedName("Name")
    private String Name;
    @SerializedName("Nationality")
    private String Nationality;
    @SerializedName("Language")
    private String Language;
    @SerializedName("Currency")
    private String Currency;
    @SerializedName("PhoneCode")
    private String PhoneCode;
    @SerializedName("SubLocations")
    private String SubLocations;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getParentId() {
        return ParentId;
    }

    public void setParentId(String parentId) {
        ParentId = parentId;
    }

    public String getParent() {
        return Parent;
    }

    public void setParent(String parent) {
        Parent = parent;
    }

    public String getCode2() {
        return Code2;
    }

    public void setCode2(String code2) {
        Code2 = code2;
    }

    public String getCode3() {
        return Code3;
    }

    public void setCode3(String code3) {
        Code3 = code3;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getNationality() {
        return Nationality;
    }

    public void setNationality(String nationality) {
        Nationality = nationality;
    }

    public String getLanguage() {
        return Language;
    }

    public void setLanguage(String language) {
        Language = language;
    }

    public String getCurrency() {
        return Currency;
    }

    public void setCurrency(String currency) {
        Currency = currency;
    }

    public String getPhoneCode() {
        return PhoneCode;
    }

    public void setPhoneCode(String phoneCode) {
        PhoneCode = phoneCode;
    }

    public String getSubLocations() {
        return SubLocations;
    }

    public void setSubLocations(String subLocations) {
        SubLocations = subLocations;
    }
}
