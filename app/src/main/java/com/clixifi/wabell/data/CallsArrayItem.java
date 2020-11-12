package com.clixifi.wabell.data;

import com.google.gson.annotations.SerializedName;

public class CallsArrayItem {
    @SerializedName("FromUserName")
    public String FromUserName ;
    @SerializedName("FromUserImage")
    public String FromUserImage ;
    @SerializedName("FromUserPhoneNumber")
    public String FromUserPhoneNumber ;
    @SerializedName("FromUserTagline")
    public String FromUserTagline ;
    @SerializedName("FromUserId")
    public String FromUserId ;
    @SerializedName("Date")
    public String Date ;
    @SerializedName("Time")
    public String Time ;
    @SerializedName("Type")
    public String Type ;
    @SerializedName("FirebaseRoomId")
    public String FirebaseRoomId ;
    @SerializedName("ToUserFirebaseId")
    public String ToUserFirebaseId ;
    @SerializedName("FromUserFirebaseId")
    public String FromUserFirebaseId ;
    @SerializedName("IsOnline")
    public boolean IsOnline ;

    public boolean isOnline() {
        return IsOnline;
    }

    public void setOnline(boolean online) {
        IsOnline = online;
    }

    public String getToUserFirebaseId() {
        return ToUserFirebaseId;
    }

    public void setToUserFirebaseId(String toUserFirebaseId) {
        ToUserFirebaseId = toUserFirebaseId;
    }

    public String getFromUserFirebaseId() {
        return FromUserFirebaseId;
    }

    public void setFromUserFirebaseId(String fromUserFirebaseId) {
        FromUserFirebaseId = fromUserFirebaseId;
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
}
