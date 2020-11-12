package com.clixifi.wabell.data.Response.favMasters;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;


public class FavMastersItem {

    @SerializedName("EngTopics")
    public ArrayList<String> EngTopics;

    @SerializedName("ArTopics")
    public ArrayList<String> ArTopics;

    @SerializedName("Id")
    public int Id;
    @SerializedName("TutorId")
    public String tutorId;
    @SerializedName("TutorName")
    public String TutorName;
    @SerializedName("TutorEmail")
    public String TutorEmail;
    @SerializedName("TutorImage")
    public String TutorImage;
    @SerializedName("StudentId")
    public String StudentId;
    @SerializedName("StudentName")
    public String StudentName;
    @SerializedName("StudentEmail")
    public String StudentEmail;
    @SerializedName("StudentImage")
    public String StudentImage;
    @SerializedName("Date")
    public String Date;
    @SerializedName("TutorIsFeatured")
    public boolean TutorIsFeatured;

    @SerializedName("TutorIsOnline")
    public boolean TutorIsOnline;

    @SerializedName("TutorIsPending")
    public boolean TutorIsPending;

    @SerializedName("TutorBiography")
    public String TutorBiography;

    @SerializedName("TagLine")
    public String TagLine;

    @SerializedName("TutorLocation")
    public String TutorLocation;

    @SerializedName("DistrictId")
    public int DistrictId;

    @SerializedName("Rank")
    public int Rank;

    @SerializedName("ViewsCount")
    public int ViewsCount;

    @SerializedName("RankCount")
    public int RankCount;

    @SerializedName("HourPrice")
    public double HourPrice ;

    @SerializedName("IsOnline")
    public boolean IsOnline ;

    public String getTagLine() {
        return TagLine;
    }

    public void setTagLine(String tagLine) {
        TagLine = tagLine;
    }

    public boolean isOnline() {
        return IsOnline;
    }

    public void setOnline(boolean online) {
        IsOnline = online;
    }

    public double getHourPrice() {
        return HourPrice;
    }

    public void setHourPrice(double hourPrice) {
        HourPrice = hourPrice;
    }

    public int getRankCount() {
        return RankCount;
    }

    public void setRankCount(int rankCount) {
        RankCount = rankCount;
    }

    public int getViewsCount() {
        return ViewsCount;
    }

    public void setViewsCount(int viewsCount) {
        ViewsCount = viewsCount;
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

    public boolean isTutorIsFeatured() {
        return TutorIsFeatured;
    }

    public void setTutorIsFeatured(boolean tutorIsFeatured) {
        TutorIsFeatured = tutorIsFeatured;
    }

    public boolean isTutorIsOnline() {
        return TutorIsOnline;
    }

    public void setTutorIsOnline(boolean tutorIsOnline) {
        TutorIsOnline = tutorIsOnline;
    }

    public boolean isTutorIsPending() {
        return TutorIsPending;
    }

    public void setTutorIsPending(boolean tutorIsPending) {
        TutorIsPending = tutorIsPending;
    }

    public String getTutorBiography() {
        return TutorBiography;
    }

    public void setTutorBiography(String tutorBiography) {
        TutorBiography = tutorBiography;
    }

    public String getTutorLocation() {
        return TutorLocation;
    }

    public void setTutorLocation(String tutorLocation) {
        TutorLocation = tutorLocation;
    }

    public int getDistrictId() {
        return DistrictId;
    }

    public void setDistrictId(int districtId) {
        DistrictId = districtId;
    }

    public int getRank() {
        return Rank;
    }

    public void setRank(int rank) {
        Rank = rank;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getTutorId() {
        return tutorId;
    }

    public void setTutorId(String tutorId) {
        this.tutorId = tutorId;
    }

    public String getTutorName() {
        return TutorName;
    }

    public void setTutorName(String tutorName) {
        TutorName = tutorName;
    }

    public String getTutorEmail() {
        return TutorEmail;
    }

    public void setTutorEmail(String tutorEmail) {
        TutorEmail = tutorEmail;
    }

    public String getTutorImage() {
        return TutorImage;
    }

    public void setTutorImage(String tutorImage) {
        TutorImage = tutorImage;
    }

    public String getStudentId() {
        return StudentId;
    }

    public void setStudentId(String studentId) {
        StudentId = studentId;
    }

    public String getStudentName() {
        return StudentName;
    }

    public void setStudentName(String studentName) {
        StudentName = studentName;
    }

    public String getStudentEmail() {
        return StudentEmail;
    }

    public void setStudentEmail(String studentEmail) {
        StudentEmail = studentEmail;
    }

    public String getStudentImage() {
        return StudentImage;
    }

    public void setStudentImage(String studentImage) {
        StudentImage = studentImage;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }
}
