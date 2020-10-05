package com.clixifi.wabell.data.Response.topicId;

import com.google.gson.annotations.SerializedName;

public class TopicsIds {

    @SerializedName("Id")
    public int Id ;
    @SerializedName("TitleAr")
    public String TitleAr ;
    @SerializedName("TitleEn")
    public String TitleEn ;
    @SerializedName("IsMarked")
    public boolean IsMarked ;
    @SerializedName("ChildsCount")
    public int ChildsCount ;
    @SerializedName("SubTopics")
    public String  SubTopics;


    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getTitleAr() {
        return TitleAr;
    }

    public void setTitleAr(String titleAr) {
        TitleAr = titleAr;
    }

    public String getTitleEn() {
        return TitleEn;
    }

    public void setTitleEn(String titleEn) {
        TitleEn = titleEn;
    }

    public boolean isMarked() {
        return IsMarked;
    }

    public void setMarked(boolean marked) {
        IsMarked = marked;
    }

    public int getChildsCount() {
        return ChildsCount;
    }

    public void setChildsCount(int childsCount) {
        ChildsCount = childsCount;
    }

    public String getSubTopics() {
        return SubTopics;
    }

    public void setSubTopics(String subTopics) {
        SubTopics = subTopics;
    }
}
