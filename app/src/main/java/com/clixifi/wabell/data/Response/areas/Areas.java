package com.clixifi.wabell.data.Response.areas;

import com.clixifi.wabell.data.Response.cities.CityItem;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Areas {
    @SerializedName("Location")
    private ArrayList<AreasItem> areas;

    public ArrayList<AreasItem> getAreas() {
        return areas;
    }

    public void setAreas(ArrayList<AreasItem> areas) {
        this.areas = areas;
    }
}
