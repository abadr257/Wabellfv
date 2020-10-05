package com.clixifi.wabell.data.Response.requestTopic;

import com.google.gson.annotations.SerializedName;

public class RequestTopic {

    @SerializedName("Id")
    public int id ;

    @SerializedName("Name")
    public String  Name ;

    @SerializedName("Date")
    public String  Date ;

    @SerializedName("RequestedBy")
    public String  RequestedBy ;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getRequestedBy() {
        return RequestedBy;
    }

    public void setRequestedBy(String requestedBy) {
        RequestedBy = requestedBy;
    }
}
