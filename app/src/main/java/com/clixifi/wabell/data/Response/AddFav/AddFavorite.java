package com.clixifi.wabell.data.Response.AddFav;

import com.google.gson.annotations.SerializedName;

public class AddFavorite {
    @SerializedName("TutorId")
    public String TutorId ;

    public String getTutorId() {
        return TutorId;
    }

    public void setTutorId(String tutorId) {
        TutorId = tutorId;
    }
}
