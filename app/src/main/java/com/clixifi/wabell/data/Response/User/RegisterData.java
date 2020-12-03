package com.clixifi.wabell.data.Response.User;

import com.clixifi.wabell.data.Response.topic.Topics;
import com.clixifi.wabell.data.Response.topicId.TopicsIds;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class RegisterData {
    @SerializedName("Id")
    private String UserId;
    @SerializedName("Email")
    private String Email;
    @SerializedName("Name")
    private String Name;
    @SerializedName("PhoneNumber")
    private String PhoneNumber;
    @SerializedName("LocationId")
    private String LocationId;
    @SerializedName("Location")
    private String Location;
    @SerializedName("Address")
    private String Address;
    @SerializedName("IsArchived")
    private String IsArchived;
    @SerializedName("IsBlocked")
    private String IsBlocked;
    @SerializedName("ProfilePicture")
    private String ProfilePicture;
    @SerializedName("ApprovedStatusId")
    private String ApprovedStatusId;
    @SerializedName("ApprovedStatus")
    private String ApprovedStatus;


    @SerializedName("FacebookUrl")
    private String FacebookUrl;
    @SerializedName("TwitterUrl")
    private String TwitterUrl ;


    @SerializedName("LinkedInUrl")
    private String LinkedInUrl;
    @SerializedName("Birthdate")
    private String Birthdate;
    @SerializedName("IsOnline")
    private String IsOnline;
    @SerializedName("OfflineUntil")
    private String OfflineUntil;

    @SerializedName("Token")
    private String Token;
    @SerializedName("EmailConfirmed")
    private String EmailConfirmed;

    @SerializedName("PhoneNumberConfirmed")
    private String PhoneNumberConfirmed;


    @SerializedName("UserName")
    private String UserName ;

    @SerializedName("UserType")
    private String type ;

    @SerializedName("Exception")
    private String Exception ;

    @SerializedName("User")
    private String User ;

    public String getException() {
        return Exception;
    }

    public void setException(String exception) {
        Exception = exception;
    }

    public String getUser() {
        return User;
    }

    public void setUser(String user) {
        User = user;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getLocationId() {
        return LocationId;
    }

    public void setLocationId(String locationId) {
        LocationId = locationId;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getIsArchived() {
        return IsArchived;
    }

    public void setIsArchived(String isArchived) {
        IsArchived = isArchived;
    }

    public String getIsBlocked() {
        return IsBlocked;
    }

    public void setIsBlocked(String isBlocked) {
        IsBlocked = isBlocked;
    }

    public String getProfilePicture() {
        return ProfilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        ProfilePicture = profilePicture;
    }

    public String getApprovedStatusId() {
        return ApprovedStatusId;
    }

    public void setApprovedStatusId(String approvedStatusId) {
        ApprovedStatusId = approvedStatusId;
    }

    public String getApprovedStatus() {
        return ApprovedStatus;
    }

    public void setApprovedStatus(String approvedStatus) {
        ApprovedStatus = approvedStatus;
    }

    public String getFacebookUrl() {
        return FacebookUrl;
    }

    public void setFacebookUrl(String facebookUrl) {
        FacebookUrl = facebookUrl;
    }

    public String getTwitterUrl() {
        return TwitterUrl;
    }

    public void setTwitterUrl(String twitterUrl) {
        TwitterUrl = twitterUrl;
    }

    public String getLinkedInUrl() {
        return LinkedInUrl;
    }

    public void setLinkedInUrl(String linkedInUrl) {
        LinkedInUrl = linkedInUrl;
    }

    public String getBirthdate() {
        return Birthdate;
    }

    public void setBirthdate(String birthdate) {
        Birthdate = birthdate;
    }

    public String getIsOnline() {
        return IsOnline;
    }

    public void setIsOnline(String isOnline) {
        IsOnline = isOnline;
    }

    public String getOfflineUntil() {
        return OfflineUntil;
    }

    public void setOfflineUntil(String offlineUntil) {
        OfflineUntil = offlineUntil;
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }

    public String getEmailConfirmed() {
        return EmailConfirmed;
    }

    public void setEmailConfirmed(String emailConfirmed) {
        EmailConfirmed = emailConfirmed;
    }

    public String getPhoneNumberConfirmed() {
        return PhoneNumberConfirmed;
    }

    public void setPhoneNumberConfirmed(String phoneNumberConfirmed) {
        PhoneNumberConfirmed = phoneNumberConfirmed;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }
}
