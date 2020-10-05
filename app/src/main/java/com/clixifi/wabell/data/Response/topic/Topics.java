package com.clixifi.wabell.data.Response.topic;

import com.clixifi.wabell.data.Response.topicId.TopicsIds;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Topics {

    @SerializedName("result")
    public ArrayList<TopicsIds> category ;

    public ArrayList<TopicsIds> getCategory() {
        return category;
    }

    public void setCategory(ArrayList<TopicsIds> category) {
        this.category = category;
    }
}
