package com.clixifi.wabell.data.Response.RequestLogs;

import com.google.gson.annotations.SerializedName;

public class RequestLogsItem {

    @SerializedName("Id")
    public int Id;

    @SerializedName("Type")
    public String Type;
    @SerializedName("Date")
    public String Date;
    @SerializedName("Content")
    public String Content;
    @SerializedName("TutorId")
    public String TutorId;
    @SerializedName("TutorFullName")
    public String TutorFullName;
    @SerializedName("TutorImage")
    public String TutorImage;
    @SerializedName("StudentId")
    public String StudentId;
    @SerializedName("StudentFullName")
    public String StudentFullName;
    @SerializedName("StudentImage")
    public String  StudentImage;
    @SerializedName("IsFromTutor")
    public boolean IsFromTutor;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getTutorId() {
        return TutorId;
    }

    public void setTutorId(String tutorId) {
        TutorId = tutorId;
    }

    public String getTutorFullName() {
        return TutorFullName;
    }

    public void setTutorFullName(String tutorFullName) {
        TutorFullName = tutorFullName;
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

    public String getStudentFullName() {
        return StudentFullName;
    }

    public void setStudentFullName(String studentFullName) {
        StudentFullName = studentFullName;
    }

    public String getStudentImage() {
        return StudentImage;
    }

    public void setStudentImage(String studentImage) {
        StudentImage = studentImage;
    }

    public boolean isFromTutor() {
        return IsFromTutor;
    }

    public void setFromTutor(boolean fromTutor) {
        IsFromTutor = fromTutor;
    }
}
