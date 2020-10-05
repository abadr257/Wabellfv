package com.clixifi.wabell.data.Response.cities;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Cities {
    @SerializedName("Location")
    private ArrayList<CityItem> cityItemArrayList;

    public ArrayList<CityItem> getLocation() {
        return cityItemArrayList;
    }

    public void setLocation(ArrayList<CityItem> location) {
        this.cityItemArrayList = location;
    }
}
