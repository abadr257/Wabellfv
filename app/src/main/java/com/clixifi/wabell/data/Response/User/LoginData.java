package com.clixifi.wabell.data.Response.User;

import com.clixifi.wabell.data.Response.topic.Topics;
import com.clixifi.wabell.data.Response.topicId.TopicsIds;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class LoginData {
    @SerializedName("FavoriteTutors")
    private String FavoriteTutors;
    @SerializedName("Id")
    private String UserId;
    @SerializedName("UserType")
    private String UserType;
    @SerializedName("Token")
    private String Token;
    @SerializedName("Name")
    private String Name;
    @SerializedName("UserName")
    private String UserName;
    @SerializedName("Email")
    private String Email;
    @SerializedName("EmailConfirmed")
    private boolean EmailConfirmed;
    @SerializedName("PhoneNumber")
    private String PhoneNumber;
    @SerializedName("PhoneNumberConfirmed")
    private boolean PhoneNumberConfirmed;
    @SerializedName("ProfilePicture")
    private String ProfilePicture;
    @SerializedName("Birthdate")
    private String Birthdate;
    @SerializedName("Location")
    private String Location;
    @SerializedName("RankCount")
    private int RankCount;
    @SerializedName("Rank")
    private int Rank;
    @SerializedName("IsArchived")
    private boolean IsArchived;
    @SerializedName("IsBlocked")
    private boolean IsBlocked;
    @SerializedName("IsOnline")
    private boolean IsOnline;
    @SerializedName("CityId")
    private int CityId;
    @SerializedName("CountryId")
    private int CountryId;
    @SerializedName("Topics")
    private ArrayList<Topics> Topics;
    @SerializedName("TopicsIds")
    private ArrayList<TopicsIds> topicsIds;


    @SerializedName("IsFeatured")
    private boolean IsFeatured;
    @SerializedName("IsPending")
    private boolean IsPending;
    @SerializedName("IsSubscribe")
    private boolean IsSubscribe;

    public boolean isFeatured() {
        return IsFeatured;
    }

    public void setFeatured(boolean featured) {
        IsFeatured = featured;
    }

    public boolean isPending() {
        return IsPending;
    }

    public void setPending(boolean pending) {
        IsPending = pending;
    }

    public boolean isSubscribe() {
        return IsSubscribe;
    }

    public void setSubscribe(boolean subscribe) {
        IsSubscribe = subscribe;
    }

    public String getFavoriteTutors() {
        return FavoriteTutors;
    }

    public void setFavoriteTutors(String favoriteTutors) {
        FavoriteTutors = favoriteTutors;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getUserType() {
        return UserType;
    }

    public void setUserType(String userType) {
        UserType = userType;
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public boolean isEmailConfirmed() {
        return EmailConfirmed;
    }

    public void setEmailConfirmed(boolean emailConfirmed) {
        EmailConfirmed = emailConfirmed;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public boolean isPhoneNumberConfirmed() {
        return PhoneNumberConfirmed;
    }

    public void setPhoneNumberConfirmed(boolean phoneNumberConfirmed) {
        PhoneNumberConfirmed = phoneNumberConfirmed;
    }

    public String getProfilePicture() {
        return ProfilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        ProfilePicture = profilePicture;
    }

    public String getBirthdate() {
        return Birthdate;
    }

    public void setBirthdate(String birthdate) {
        Birthdate = birthdate;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public int getRankCount() {
        return RankCount;
    }

    public void setRankCount(int rankCount) {
        RankCount = rankCount;
    }

    public int getRank() {
        return Rank;
    }

    public void setRank(int rank) {
        Rank = rank;
    }

    public boolean isArchived() {
        return IsArchived;
    }

    public void setArchived(boolean archived) {
        IsArchived = archived;
    }

    public boolean isBlocked() {
        return IsBlocked;
    }

    public void setBlocked(boolean blocked) {
        IsBlocked = blocked;
    }

    public boolean isOnline() {
        return IsOnline;
    }

    public void setOnline(boolean online) {
        IsOnline = online;
    }

    public int getCityId() {
        return CityId;
    }

    public void setCityId(int cityId) {
        CityId = cityId;
    }

    public int getCountryId() {
        return CountryId;
    }

    public void setCountryId(int countryId) {
        CountryId = countryId;
    }

    public ArrayList<com.clixifi.wabell.data.Response.topic.Topics> getTopics() {
        return Topics;
    }

    public void setTopics(ArrayList<com.clixifi.wabell.data.Response.topic.Topics> topics) {
        Topics = topics;
    }

    public ArrayList<TopicsIds> getTopicsIds() {
        return topicsIds;
    }

    public void setTopicsIds(ArrayList<TopicsIds> topicsIds) {
        this.topicsIds = topicsIds;
    }
}
