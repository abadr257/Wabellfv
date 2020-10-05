package com.clixifi.wabell.data.Response.favMasters;

import com.google.gson.annotations.SerializedName;


public class FavMastersItem {

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
