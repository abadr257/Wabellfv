package com.clixifi.wabell.helpers.prefs;

import android.content.Context;
import android.content.SharedPreferences;

import com.clixifi.wabell.data.Response.User.LoginData;
import com.clixifi.wabell.utils.StaticMethods;

import com.google.gson.Gson;



import static com.clixifi.wabell.helpers.prefs.PrefKeys.App_WABELL;


/**
 * Created by Ahmed Ashraf in 24 / 9 / 2020 .
 *
 */
public class PrefUtils {


    public static final String Is_First_open = "firstopen",
            User_data = "Userdata",User_Skills = "UserSkills", User_category = "UserCat",Status_User = "status_user",
            Language_List = "languagelist", Language_Selected = "languageselected",
            Country_List = "countrylist", Country_Selected = "Countryselected";

    public static final int  User_Singin = 1 ,
            User_Singout = 0 ,  User_Verify = 2 , userSkills = 3 , userCategory = 4 ;


    public static void saveOpenStatus(boolean indexlang, Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences(App_WABELL, Context.MODE_PRIVATE).edit();
        editor.putBoolean(Is_First_open, indexlang);
        editor.commit();
    }

    public static boolean getOpenStatus(Context context) {
        String lang = "";
        boolean status = context.getSharedPreferences(App_WABELL, Context.MODE_PRIVATE).getBoolean(Is_First_open, false);
        if (!status) {
            return true;
        }else {
            saveOpenStatus(true,context);
            return false;
        }
    }

    public static void saveUserinformation(Context context, LoginData consumerResponse, int status ) {
        SharedPreferences sharedpreferences = context.getSharedPreferences(App_WABELL, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        Gson gson = new Gson();
        String Userdatavalue = gson.toJson(consumerResponse);
        editor.putString(User_data, Userdatavalue);
        editor.putInt(Status_User,status);
        editor.commit();
    }


    public static Boolean getUserformation(Context context) {
        SharedPreferences sharedpreferences = context.getSharedPreferences(App_WABELL, Context.MODE_PRIVATE);
        if (sharedpreferences.getInt(Status_User, User_Singout) != User_Singout){
            StaticMethods.userData = new LoginData();
            String userdatavalue = sharedpreferences.getString(User_data, null);
            StaticMethods.userData =  new Gson().fromJson(userdatavalue, LoginData.class);
            StaticMethods.User_Status = sharedpreferences.getInt(Status_User, User_Singout);
            StaticMethods.auth = StaticMethods.userData.getToken();
            return  true;
        }else {
            return  false;
        }
    }

    public static  void  SignOut_User(Context context){
        SharedPreferences sharedpreferences = context.getSharedPreferences(App_WABELL, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putInt(Status_User,User_Singout);
        editor.commit();
    }

    public static  int User_Status(Context context){
        SharedPreferences sharedpreferences = context.getSharedPreferences(App_WABELL, Context.MODE_PRIVATE);
        return   sharedpreferences.getInt(Status_User, User_Verify);
    }


}
