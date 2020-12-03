package com.clixifi.wabell.data.Response.User;

import com.google.gson.annotations.SerializedName;

public class UserResponse<T> {
    @SerializedName("Status")
    public String Status;
    @SerializedName("Message")
    public String Message;
    @SerializedName("Data")
    public T Data;
    @SerializedName("DataProfile")
    public T DataProfile;
    @SerializedName("Errors")
    public T Errors;


}
