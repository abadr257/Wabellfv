package com.clixifi.wabell.utils.network;

import androidx.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class MainApiBody {
    private static final String JSON_TYPE = "application/json";

    @NonNull
    private static RequestBody requestBody(JSONObject jsonBody) {
        return RequestBody.create(MediaType.parse(JSON_TYPE), jsonBody.toString());
    }

    public static RequestBody loginBody(String userName, String pass) throws JSONException {
        JSONObject params = new JSONObject();
        params.put("UserName", userName);
        params.put("Password", pass);
        return requestBody(params);
    }
    public static RequestBody unFavBody(String tutorId, String studentId) throws JSONException {
        JSONObject params = new JSONObject();
        params.put("StudentId", studentId);
        params.put("TutorId", tutorId);
        return requestBody(params);
    }
    public static RequestBody registerBody(String Email, String Phone,
                                           String Password, String Name, int LocationId, String UserType) throws JSONException {
        JSONObject params = new JSONObject();
        params.put("Email", Email);
        params.put("Phone", Phone);
        params.put("Password", Password);
        params.put("Name", Name);
        params.put("LocationId", LocationId);
        params.put("UserType", UserType);
        return requestBody(params);
    }


    public static RequestBody areasBody(int ParentId) throws JSONException {
        JSONObject params = new JSONObject();
        params.put("ParentId", ParentId);
        return requestBody(params);
    }

    public static RequestBody getTutorProfile(String  Id , boolean isViewed) throws JSONException {
        JSONObject params = new JSONObject();
        params.put("Id", Id);
        params.put("IsViewed", isViewed);
        return requestBody(params);
    }

    public static RequestBody getReviews(String  Id) throws JSONException {
        JSONObject params = new JSONObject();
        params.put("Id", Id);
        return requestBody(params);
    }

    public static RequestBody requestTopic(String Name, String RequestedBy) throws JSONException {
        JSONObject params = new JSONObject();
        params.put("Name", Name);
        params.put("RequestedBy", RequestedBy);
        return requestBody(params);
    }

    public static RequestBody filterBody(String  FromHourPrice , String ToHourPrice ,
                                         int Rating ,int CountryId ,
                                         int CityId ,boolean Price) throws JSONException {
        JSONObject params = new JSONObject();
        // object for budget
        JSONObject Budget = new JSONObject();
        Budget.put("FromHourPrice",FromHourPrice);
        Budget.put("ToHourPrice",ToHourPrice);
        // object for Distance
        JSONObject Distance = new JSONObject();
        Distance.put("CountryId",CountryId);
        Distance.put("CityId",CityId);
        // object for SortBy
        JSONObject SortBy = new JSONObject();
        SortBy.put("Price",Price);

        // to send all this in body like this ,,
        /*{
            "Budget":{
            "FromHourPrice": 5.00,
                    "ToHourPrice":20.00
        },
            "Rating" : 2,
                "Distance":{
            "CountryId":1,
                    "CityId":2
        },
            "SortBy":{
            "Price":true
        }
        }*/


        if(!FromHourPrice.isEmpty() && !ToHourPrice.isEmpty() ){
            params.put("Budget", Budget);
        }
        if(CityId > 0){
            params.put("Distance", Distance);
        }
        if(Rating > 0){
            params.put("Rating", Rating);
        }
        if(Price){
            params.put("SortBy", SortBy);
        }

        return requestBody(params);
    }

    public static RequestBody tutorWorkDetailsBody(double HourPrice, JSONArray DayIds, JSONArray TimeIds , String Id) throws JSONException {
        JSONObject params = new JSONObject();
        params.put("HourPrice", HourPrice);
        params.put("DayIds", DayIds);
        params.put("TimeIds", TimeIds);
        params.put("Id", Id);
        return requestBody(params);
    }
    public static RequestBody getTutorListInSearch(double HourPrice, JSONArray DayIds, JSONArray TimeIds) throws JSONException {
        JSONObject params = new JSONObject();
        /*params.put("HourPrice", HourPrice);
        params.put("DayIds", DayIds);
        params.put("TimeIds", TimeIds);*/
        return requestBody(params);
    }
    public static RequestBody tutorExpandEduBody(String Experience, String Education) throws JSONException {
        JSONObject params = new JSONObject();
        params.put("Experience", Experience);
        params.put("Education", Education);
        return requestBody(params);
    }

    public static RequestBody cancelTopicBody(int TopicId , String Id) throws JSONException {
        JSONObject params = new JSONObject();
        params.put("TopicId", TopicId);
        params.put("Id", Id);
        return requestBody(params);
    }

    public static RequestBody fourthLevelBody(int ParentId) throws JSONException {
        JSONObject params = new JSONObject();
        params.put("ParentId", ParentId);
        return requestBody(params);
    }



    public static RequestBody summaryBody(int ParentId) throws JSONException {
        JSONObject params = new JSONObject();
        params.put("ParentId", ParentId);
        return requestBody(params);
    }

    public static RequestBody addTopics(int TopicId , String Id) throws JSONException {
        JSONObject params = new JSONObject();
        params.put("TopicId", TopicId);
        params.put("Id", Id);
        return requestBody(params);
    }

    public static RequestBody cancelAllTopic(int TopicId ,String Id) throws JSONException {
        JSONObject params = new JSONObject();
        params.put("TopicId", TopicId);
        params.put("Id", Id);
        return requestBody(params);
    }

    public static RequestBody tutorBioBody(String Tagline, String Biography) throws JSONException {
        JSONObject params = new JSONObject();
        params.put("Tagline", Tagline);
        params.put("Biography", Biography);
        return requestBody(params);
    }

    public static RequestBody tutorProfileUpdateBody(String Name, String Email,
                                                     String Phone, int LocationId,
                                                     String Experience, String Education,
                                                     String Tagline, String Biography , String  id) throws JSONException {
        JSONObject params = new JSONObject();
        params.put("Name", Name);
        params.put("Email", Email);
        params.put("Phone", Phone);
        params.put("LocationId", LocationId);
        params.put("Experience", Experience);
        params.put("Education", Education);
        params.put("Tagline", Tagline);
        params.put("Biography", Biography);
        params.put("Id", id);
        return requestBody(params);
    }

    public static RequestBody studentUpdateProfileBody(String Name, String Email,
                                                       String Phone, int LocationId) throws JSONException {
        JSONObject params = new JSONObject();
        params.put("Name", Name);
        params.put("Email", Email);
        params.put("Phone", Phone);
        params.put("LocationId", LocationId);
        return requestBody(params);
    }


    public static RequestBody setNewPassBody(String userId, String newPass) throws JSONException {
        JSONObject params = new JSONObject();
        params.put("Id", userId);
        params.put("NewPassword", newPass);
        return requestBody(params);
    }
}
