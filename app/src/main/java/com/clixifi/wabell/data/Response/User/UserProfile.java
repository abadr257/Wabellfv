package com.clixifi.wabell.data.Response.User;

import com.google.gson.annotations.SerializedName;

public class UserProfile {
    @SerializedName("Experience")
    public String Experience ;
    @SerializedName("Education")
    public String Education ;
    @SerializedName("Biography")
    public String Biography ;
    @SerializedName("HourPrice")
    public double HourPrice ;
    @SerializedName("Tagline")
    public String Tagline ;
    @SerializedName("IsSubscribe")
    public boolean IsSubscribe ;
    @SerializedName("AvailableDaysText")
    public String AvailableDays ;
    @SerializedName("AvailableTimesText")
    public String AvailableTimes ;
    @SerializedName("UserType")
    public String UserType ;
    @SerializedName("Name")
    public String Name ;
    @SerializedName("UserName")
    public String  UserName ;
    @SerializedName("Email")
    public String Email ;
    @SerializedName("PhoneNumber")
    public String PhoneNumber ;
    @SerializedName("ProfilePicture")
    public String ProfilePicture ;
    @SerializedName("Location")
    public String Location ;
    @SerializedName("Address")
    public String Address ;
    @SerializedName("TopicsText")
    public String Topics ;

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

    public double getHourPrice() {
        return HourPrice;
    }

    public void setHourPrice(double hourPrice) {
        HourPrice = hourPrice;
    }

    public String getTagline() {
        return Tagline;
    }

    public void setTagline(String tagline) {
        Tagline = tagline;
    }

    public boolean isSubscribe() {
        return IsSubscribe;
    }

    public void setSubscribe(boolean subscribe) {
        IsSubscribe = subscribe;
    }

    public String getAvailableDays() {
        return AvailableDays;
    }

    public void setAvailableDays(String availableDays) {
        AvailableDays = availableDays;
    }

    public String getAvailableTimes() {
        return AvailableTimes;
    }

    public void setAvailableTimes(String availableTimes) {
        AvailableTimes = availableTimes;
    }

    public String getUserType() {
        return UserType;
    }

    public void setUserType(String userType) {
        UserType = userType;
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

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getProfilePicture() {
        return ProfilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        ProfilePicture = profilePicture;
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

    public String getTopics() {
        return Topics;
    }

    public void setTopics(String topics) {
        Topics = topics;
    }
}
