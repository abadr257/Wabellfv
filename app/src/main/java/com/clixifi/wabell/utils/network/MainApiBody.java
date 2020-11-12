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

    public static RequestBody loginBody(String userName, String pass, String fireToken) throws JSONException {
        JSONObject params = new JSONObject();
        params.put("UserName", userName);
        params.put("Password", pass);
        params.put("FirebaseToken", fireToken);
        return requestBody(params);
    }

    public static RequestBody unFavBody(String tutorId, String studentId) throws JSONException {
        JSONObject params = new JSONObject();
        params.put("StudentId", studentId);
        params.put("TutorId", tutorId);
        return requestBody(params);
    }

    public static RequestBody registerBody(String Email, String Phone,
                                           String Password, String Name, int LocationId, String UserType, String UserFirebaseId) throws JSONException {
        JSONObject params = new JSONObject();
        params.put("Email", Email);
        params.put("Phone", Phone);
        params.put("Password", Password);
        params.put("Name", Name);
        params.put("LocationId", LocationId);
        params.put("UserType", UserType);
        params.put("UserFirebaseId", UserFirebaseId);//UserFirebaseId
        return requestBody(params);
    }

    public static RequestBody addRequestLog(String type, String toUID,
                                            String froUId, int roomID, String body) throws JSONException {
        JSONObject params = new JSONObject();
        params.put("Type", type);
        params.put("ToUserId", toUID);
        params.put("FromUserId", froUId);
        params.put("FirebaseRoomId", roomID);
        params.put("Body", body);
        return requestBody(params);
    }


    public static RequestBody areasBody(int ParentId) throws JSONException {
        JSONObject params = new JSONObject();
        params.put("ParentId", ParentId);
        return requestBody(params);
    }

    public static RequestBody getTutorProfile(String Id, boolean isViewed) throws JSONException {
        JSONObject params = new JSONObject();
        params.put("Id", Id);
        params.put("IsViewed", isViewed);
        return requestBody(params);
    }

    public static RequestBody getReviews(String Id) throws JSONException {
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

    public static RequestBody filterBody(String FromHourPrice, String ToHourPrice,
                                         int Rating, int CountryId,
                                         int CityId, boolean Price, String name) throws JSONException {
        JSONObject params = new JSONObject();
        // object for budget
        JSONObject Budget = new JSONObject();
        Budget.put("FromHourPrice", FromHourPrice);
        Budget.put("ToHourPrice", ToHourPrice);
        // object for Distance

        // object for SortBy
        JSONObject SortBy = new JSONObject();
        SortBy.put("Price", Price);

        if (!FromHourPrice.isEmpty() && !ToHourPrice.isEmpty()) {
            params.put("Budget", Budget);
        }
        JSONObject Distance = new JSONObject();
        if (CountryId > 0) {
            Distance.put("CountryId", CountryId);
        } else if (CityId > 0) {
            Distance.put("CityId", CityId);
        }
        params.put("Distance", Distance);

        if (Rating > 0) {
            params.put("Rating", Rating);
        }
        if (Price) {
            params.put("SortBy", SortBy);
        }
        if (!name.isEmpty()) {
            params.put("Name", name);
        }

        return requestBody(params);
    }

    public static RequestBody tutorWorkDetailsBody(double HourPrice, JSONArray DayIds, JSONArray TimeIds, String Id) throws JSONException {
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

    public static RequestBody paymentBody(String amount, String cardnumber, String cardholder, String expiryMonth, String expiryYear, String cardcvv, int PackageId) throws JSONException {
        JSONObject params = new JSONObject();
        params.put("amount", amount);
        params.put("cardnumber", cardnumber);
        params.put("cardholder", cardholder);
        params.put("expiryMonth", expiryMonth);
        params.put("expiryYear", expiryYear);
        params.put("cardcvv", cardcvv);
        params.put("PackageId", PackageId);
        return requestBody(params);
    }

    public static RequestBody cancelTopicBody(int TopicId, String Id) throws JSONException {
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

    public static RequestBody addTopics(int TopicId, String Id) throws JSONException {
        JSONObject params = new JSONObject();
        params.put("TopicId", TopicId);
        params.put("Id", Id);
        return requestBody(params);
    }

    public static RequestBody cancelAllTopic(int TopicId, String Id) throws JSONException {
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
                                                     String Tagline, String Biography, String id) throws JSONException {
        JSONObject params = new JSONObject();
        params.put("Id", id);
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
