package com.clixifi.wabell.data.Response.TutorList;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class TutorItem {
    @SerializedName("EngTopics")
    public ArrayList<String> EngTopics;
    @SerializedName("ArTopics")
    public ArrayList<String> ArTopics;
    @SerializedName("IsFeatured")
    public boolean IsFeatured;
    @SerializedName("IsOnline")
    public boolean IsOnline;
    @SerializedName("IsPending")
    public boolean IsPending;
    @SerializedName("Biography")
    public String  Biography;
    @SerializedName("TagLine")
    public String  TagLine;
    @SerializedName("Id")
    public String  Id;

    @SerializedName("HourPrice")
    public String HourPrice;
    @SerializedName("ViewsCount")
    public int ViewsCount;
    @SerializedName("Name")
    public String Name;
    @SerializedName("ProfilePicture")
    public String ProfilePicture;
    @SerializedName("RankCount")
    public int RankCount;
    @SerializedName("Location")
    public String  Location;
    @SerializedName("FromUserFirebaseId")
    public String  FromUserFirebaseId;
    //Rank
    @SerializedName("Rank")
    public int  Rank;

    public String getFromUserFirebaseId() {
        return FromUserFirebaseId;
    }

    public void setFromUserFirebaseId(String fromUserFirebaseId) {
        FromUserFirebaseId = fromUserFirebaseId;
    }

    public String getTagLine() {
        return TagLine;
    }

    public void setTagLine(String tagLine) {
        TagLine = tagLine;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public ArrayList<String> getEngTopics() {
        return EngTopics;
    }

    public void setEngTopics(ArrayList<String> engTopics) {
        EngTopics = engTopics;
    }

    public ArrayList<String> getArTopics() {
        return ArTopics;
    }

    public void setArTopics(ArrayList<String> arTopics) {
        ArTopics = arTopics;
    }

    public boolean isFeatured() {
        return IsFeatured;
    }

    public void setFeatured(boolean featured) {
        IsFeatured = featured;
    }

    public boolean isOnline() {
        return IsOnline;
    }

    public void setOnline(boolean online) {
        IsOnline = online;
    }

    public boolean isPending() {
        return IsPending;
    }

    public void setPending(boolean pending) {
        IsPending = pending;
    }

    public String getBiography() {
        return Biography;
    }

    public void setBiography(String biography) {
        Biography = biography;
    }

    public String  getHourPrice() {
        return HourPrice;
    }

    public void setHourPrice(String hourPrice) {
        HourPrice = hourPrice;
    }

    public int getViewsCount() {
        return ViewsCount;
    }

    public void setViewsCount(int viewsCount) {
        ViewsCount = viewsCount;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getProfilePicture() {
        return ProfilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        ProfilePicture = profilePicture;
    }

    public int getRankCount() {
        return RankCount;
    }

    public void setRankCount(int rankCount) {
        RankCount = rankCount;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public int getRank() {
        return Rank;
    }

    public void setRank(int rank) {
        Rank = rank;
    }
}
