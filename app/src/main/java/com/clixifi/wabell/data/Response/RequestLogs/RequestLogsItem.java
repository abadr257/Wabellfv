package com.clixifi.wabell.data.Response.RequestLogs;

import com.google.gson.annotations.SerializedName;

public class RequestLogsItem {

    @SerializedName("FromUserName")
    public String FromUserName;

    @SerializedName("FromUserImage")
    public String FromUserImage;
    @SerializedName("FromUserPhoneNumber")
    public String FromUserPhoneNumber;
    @SerializedName("FromUserTagline")
    public String FromUserTagline;
    @SerializedName("FromUserId")
    public String FromUserId;
    @SerializedName("Date")
    public String Date;
    @SerializedName("Time")
    public String Time;
    @SerializedName("Type")
    public String Type;
    @SerializedName("FirebaseRoomId")
    public String FirebaseRoomId;
    @SerializedName("Body")
    public String  Body;
    @SerializedName("UserFirebaseId")
    public String  UserFirebaseId;

    @SerializedName("IsOnline")
    public boolean isOnline ;

    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }

    public String getFromUserName() {
        return FromUserName;
    }

    public void setFromUserName(String fromUserName) {
        FromUserName = fromUserName;
    }

    public String getFromUserImage() {
        return FromUserImage;
    }

    public void setFromUserImage(String fromUserImage) {
        FromUserImage = fromUserImage;
    }

    public String getFromUserPhoneNumber() {
        return FromUserPhoneNumber;
    }

    public void setFromUserPhoneNumber(String fromUserPhoneNumber) {
        FromUserPhoneNumber = fromUserPhoneNumber;
    }

    public String getFromUserTagline() {
        return FromUserTagline;
    }

    public void setFromUserTagline(String fromUserTagline) {
        FromUserTagline = fromUserTagline;
    }

    public String getFromUserId() {
        return FromUserId;
    }

    public void setFromUserId(String fromUserId) {
        FromUserId = fromUserId;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getFirebaseRoomId() {
        return FirebaseRoomId;
    }

    public void setFirebaseRoomId(String firebaseRoomId) {
        FirebaseRoomId = firebaseRoomId;
    }

    public String getBody() {
        return Body;
    }

    public void setBody(String body) {
        Body = body;
    }

    public String getUserFirebaseId() {
        return UserFirebaseId;
    }

    public void setUserFirebaseId(String userFirebaseId) {
        UserFirebaseId = userFirebaseId;
    }
}
