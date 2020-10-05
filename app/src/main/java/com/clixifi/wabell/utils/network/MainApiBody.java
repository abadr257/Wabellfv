package com.clixifi.wabell.utils.network;

import androidx.annotation.NonNull;

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

    public static RequestBody requestTopic(String Name, String RequestedBy) throws JSONException {
        JSONObject params = new JSONObject();
        params.put("Name", Name);
        params.put("RequestedBy", RequestedBy);
        return requestBody(params);
    }

    public static RequestBody tutorWorkDetailsBody(int HourPrice, ArrayList<Integer> DayIds, ArrayList<Integer> TimeIds) throws JSONException {
        JSONObject params = new JSONObject();
        params.put("HourPrice", HourPrice);
        params.put("DayIds", DayIds);
        params.put("TimeIds", TimeIds);
        return requestBody(params);
    }

    public static RequestBody tutorExpandEduBody(String Experience, String Education) throws JSONException {
        JSONObject params = new JSONObject();
        params.put("Experience", Experience);
        params.put("Education", Education);
        return requestBody(params);
    }

    public static RequestBody cancelTopicBody(int TopicId) throws JSONException {
        JSONObject params = new JSONObject();
        params.put("TopicId", TopicId);
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

    public static RequestBody addTopics(int TopicId) throws JSONException {
        JSONObject params = new JSONObject();
        params.put("TopicId", TopicId);
        return requestBody(params);
    }
    public static RequestBody cancelAllTopic(int TopicId) throws JSONException {
        JSONObject params = new JSONObject();
        params.put("TopicId", TopicId);
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
                                                     String Tagline, String Biography) throws JSONException {
        JSONObject params = new JSONObject();
        params.put("Name", Name);
        params.put("Email", Email);
        params.put("Phone", Phone);
        params.put("LocationId", LocationId);
        params.put("Experience", Experience);
        params.put("Education", Education);
        params.put("Tagline", Tagline);
        params.put("Biography", Biography);
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
