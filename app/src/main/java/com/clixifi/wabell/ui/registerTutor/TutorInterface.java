package com.clixifi.wabell.ui.registerTutor;

import com.clixifi.wabell.data.Response.User.RegisterData;
import com.clixifi.wabell.data.Response.User.UserResponse;
import com.clixifi.wabell.data.Response.areas.AreasItem;
import com.clixifi.wabell.data.Response.cities.CityItem;

import java.util.ArrayList;

public interface TutorInterface {
    void onSuccess(UserResponse<RegisterData> data) ;
    void onFail(boolean fail , String error);
    void onNoConnection(boolean noConnection);
    void onCity(ArrayList<CityItem> cityItems);
    void onArea(ArrayList<AreasItem> areasItems);
}
