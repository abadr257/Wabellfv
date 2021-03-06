package com.clixifi.wabell.data.Response.TutorProfileData;

import com.clixifi.wabell.data.Response.ImageUrl;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class TutorProfileForStudent {
    @SerializedName("Biography")
    public String Biography;
    @SerializedName("HourPrice")
    public String HourPrice;
    @SerializedName("ViewsCount")
    public int ViewsCount;
    @SerializedName("CallsCount")
    public int CallsCount;
    @SerializedName("Name")
    public String Name;
    @SerializedName("Location")
    public String Location;
    @SerializedName("Rank")
    public int Rank;
    @SerializedName("RankCount")
    public int RankCount;
    @SerializedName("ProfilePicture")
    public String ProfilePicture;

    @SerializedName("Tagline")
    public String Tagline;
    @SerializedName("ArTopics")
    public ArrayList<String> ArTopics;

    @SerializedName("PhoneNumber")
    public String PhoneNumber;


    @SerializedName("UserFirebaseId")
    public String UserFirebaseId;

    @SerializedName("EngTopics")
    public ArrayList<String> EngTopics;

    @SerializedName("Files")
    public ArrayList<ImageUrl> Files;

    @SerializedName("Experience")
    public String Experience;

    @SerializedName("IsFavorite")
    public boolean IsFavorite;

    @SerializedName("IsFeatured")  //IsOnline
    public boolean IsFeatured;
    @SerializedName("IsOnline")  //IsOnline
    public boolean IsOnline;

    @SerializedName("IsSubscribed")
    public boolean IsSubscribe;

    @SerializedName("Education")
    public String Education;


    @SerializedName("AvailableDaysText")
    public String AvailableDaysText;


    @SerializedName("AvailableTimesText")
    public String AvailableTimesText;

    public String getTagline() {
        return Tagline;
    }

    public void setTagline(String tagline) {
        Tagline = tagline;
    }

    public boolean isOnline() {
        return IsOnline;
    }

    public void setOnline(boolean online) {
        IsOnline = online;
    }

    public String getUserFirebaseId() {
        return UserFirebaseId;
    }

    public void setUserFirebaseId(String userFirebaseId) {
        UserFirebaseId = userFirebaseId;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public boolean isFeatured() {
        return IsFeatured;
    }

    public void setFeatured(boolean featured) {
        IsFeatured = featured;
    }

    public boolean isSubscribe() {
        return IsSubscribe;
    }

    public void setSubscribe(boolean subscribe) {
        IsSubscribe = subscribe;
    }

    public String getAvailableDaysText() {
        return AvailableDaysText;
    }

    public void setAvailableDaysText(String availableDaysText) {
        AvailableDaysText = availableDaysText;
    }

    public String getAvailableTimesText() {
        return AvailableTimesText;
    }

    public void setAvailableTimesText(String availableTimesText) {
        AvailableTimesText = availableTimesText;
    }

    public boolean isFavorite() {
        return IsFavorite;
    }

    public void setFavorite(boolean favorite) {
        IsFavorite = favorite;
    }

    public ArrayList<String> getArTopics() {
        return ArTopics;
    }

    public void setArTopics(ArrayList<String> arTopics) {
        ArTopics = arTopics;
    }

    public ArrayList<String> getEngTopics() {
        return EngTopics;
    }

    public void setEngTopics(ArrayList<String> engTopics) {
        EngTopics = engTopics;
    }

    public ArrayList<ImageUrl> getFiles() {
        return Files;
    }

    public void setFiles(ArrayList<ImageUrl> files) {
        Files = files;
    }

    public String getExperience() {
        return Experience;
    }

    public void setExperience(String experience) {
        Experience = experience;
    }

    public String getEducation() {
        return Education;
    }

    public void setEducation(String education) {
        Education = education;
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

    public int getCallsCount() {
        return CallsCount;
    }

    public void setCallsCount(int callsCount) {
        CallsCount = callsCount;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
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

    public int getRankCount() {
        return RankCount;
    }

    public void setRankCount(int rankCount) {
        RankCount = rankCount;
    }

    public String getProfilePicture() {
        return ProfilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        ProfilePicture = profilePicture;
    }
}
