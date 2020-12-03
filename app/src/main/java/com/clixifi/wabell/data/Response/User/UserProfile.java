package com.clixifi.wabell.data.Response.User;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class UserProfile {
    @SerializedName("Experience")
    public String Experience;
    @SerializedName("Education")
    public String Education;
    @SerializedName("Biography")
    public String Biography;
    @SerializedName("HourPrice")
    public String HourPrice;
    @SerializedName("Tagline")
    public String Tagline;
    @SerializedName("IsSubscribe")
    public boolean IsSubscribe;
    @SerializedName("AvailableDaysText")
    public String AvailableDays;
    @SerializedName("AvailableTimesText")
    public String AvailableTimes;
    @SerializedName("UserType")
    public String UserType;
    @SerializedName("City")
    public String City;
    @SerializedName("Area")
    public String Area;
    @SerializedName("EngTopics")
    public ArrayList<String> EngTopics;
    @SerializedName("ArTopics")
    public ArrayList<String> ArTopics;
    //ArAvailableDaysText
    @SerializedName("ArAvailableDaysText")
    public String ArAvailableDaysText;
    @SerializedName("ArAvailableTimesText")
    public String ArAvailableTimesText;

    @SerializedName("Name")
    public String Name;
    @SerializedName("UserName")
    public String UserName;
    @SerializedName("Email")
    public String Email;
    @SerializedName("PhoneNumber")
    public String PhoneNumber;
    @SerializedName("ProfilePicture")
    public String ProfilePicture;
    @SerializedName("Location")
    public String Location;
    @SerializedName("Address")
    public String Address;
    @SerializedName("TopicsText")
    public String Topics;

    @SerializedName("DistrictId")
    public int DistrictId;
    @SerializedName("CityId")
    public int CityId ;

    public int getCityId() {
        return CityId;
    }

    public void setCityId(int cityId) {
        CityId = cityId;
    }

    @SerializedName("Files")
    public ArrayList<String> Files;

    public int getDistrictId() {
        return DistrictId;
    }

    public void setDistrictId(int districtId) {
        DistrictId = districtId;
    }

    public String getArAvailableTimesText() {
        return ArAvailableTimesText;
    }

    public void setArAvailableTimesText(String arAvailableTimesText) {
        ArAvailableTimesText = arAvailableTimesText;
    }

    public String getArAvailableDaysText() {
        return ArAvailableDaysText;
    }

    public void setArAvailableDaysText(String arAvailableDaysText) {
        ArAvailableDaysText = arAvailableDaysText;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getArea() {
        return Area;
    }

    public void setArea(String area) {
        Area = area;
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

    public ArrayList<String> getFiles() {
        return Files;
    }

    public void setFiles(ArrayList<String> files) {
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

    public String getHourPrice() {
        return HourPrice;
    }

    public void setHourPrice(String hourPrice) {
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
