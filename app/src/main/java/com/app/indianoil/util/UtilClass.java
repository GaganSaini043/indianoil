package com.app.indianoil.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.app.indianoil.modal.ResultModal;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class UtilClass
{

    public static String date="date";
    public static String time="time";
    public static String rocode="rocode";
    public static String stockstart="stockstart";
    public static String stockend="stockend";
    public static String receipt="receipt";
    public static String msprice="msprice";
    public static String hsdprice="hsdprice";
    public static String lastpricechange="lastpricechange";
    public static String ms_du1="ms_du1";
    public static String ms_du2="ms_du2";
    public static String hsd_du1="hsd_du1";
    public static String hsd_du2="hsd_du2";
    public static String resultlist="resultlist";
    public static String resultlistrocode="resultlistrocode";
    public static String showdatalist="showdatalist";
    public static String columnname="columnname";
    public static String roname="roname";

    public static void saveArrayList(ArrayList<ResultModal> list, String key, Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString(key, json);
        editor.apply();     // This line is IMPORTANT !!!
    }

    public static ArrayList<ResultModal> getArrayList(String key, Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String json = prefs.getString(key, null);
        Type type = new TypeToken<ArrayList<ResultModal>>() {}.getType();
        return gson.fromJson(json, type);
    }

    public static void saveArrayListrocode(ArrayList<ResultModal> list, String key, Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString(key, json);
        editor.apply();     // This line is IMPORTANT !!!
    }

    public static ArrayList<ResultModal> getArrayListrocode(String key, Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String json = prefs.getString(key, null);
        Type type = new TypeToken<ArrayList<ResultModal>>() {}.getType();
        return gson.fromJson(json, type);
    }

    public static void saveallsharedpref(Context context,String key, String token) {
        SharedPreferences mPrefs = context.getSharedPreferences("Meetn", Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        prefsEditor.putString(key, token);
        prefsEditor.commit();
    }

    public static String getallsharedpref(Context context,String key) {
        SharedPreferences mPrefs = context.getSharedPreferences("Meetn", Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        String json = mPrefs.getString(key, "");
        return json;
    }


}
