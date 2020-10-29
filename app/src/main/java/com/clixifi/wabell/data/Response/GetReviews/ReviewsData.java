package com.clixifi.wabell.data.Response.GetReviews;

import com.google.gson.annotations.SerializedName;

public class ReviewsData {
    @SerializedName("Id")
    public int Id ;
    @SerializedName("FromUserId")
    public String FromUserId ;
    @SerializedName("FromUserName")
    public String FromUserName ;
    @SerializedName("FromUserEmail")
    public String FromUserEmail ;
    @SerializedName("FromUserImage")
    public String FromUserImage ;
    @SerializedName("ToUserId")
    public String ToUserId ;
    @SerializedName("ToUserName")
    public String  ToUserName ;
    @SerializedName("ToUserEmail")
    public String  ToUserEmail ;
    @SerializedName("ToUserImage")
    public String  ToUserImage ;
    @SerializedName("Value")
    public int Value ;
    @SerializedName("Comment")
    public String Comment ;
    @SerializedName("Date")
    public String Date ;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getFromUserId() {
        return FromUserId;
    }

    public void setFromUserId(String fromUserId) {
        FromUserId = fromUserId;
    }

    public String getFromUserName() {
        return FromUserName;
    }

    public void setFromUserName(String fromUserName) {
        FromUserName = fromUserName;
    }

    public String getFromUserEmail() {
        return FromUserEmail;
    }

    public void setFromUserEmail(String fromUserEmail) {
        FromUserEmail = fromUserEmail;
    }

    public String getFromUserImage() {
        return FromUserImage;
    }

    public void setFromUserImage(String fromUserImage) {
        FromUserImage = fromUserImage;
    }

    public String getToUserId() {
        return ToUserId;
    }

    public void setToUserId(String toUserId) {
        ToUserId = toUserId;
    }

    public String getToUserName() {
        return ToUserName;
    }

    public void setToUserName(String toUserName) {
        ToUserName = toUserName;
    }

    public String getToUserEmail() {
        return ToUserEmail;
    }

    public void setToUserEmail(String toUserEmail) {
        ToUserEmail = toUserEmail;
    }

    public String getToUserImage() {
        return ToUserImage;
    }

    public void setToUserImage(String toUserImage) {
        ToUserImage = toUserImage;
    }

    public int getValue() {
        return Value;
    }

    public void setValue(int value) {
        Value = value;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }
}
