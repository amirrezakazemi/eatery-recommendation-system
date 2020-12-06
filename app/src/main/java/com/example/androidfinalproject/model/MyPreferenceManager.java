package com.example.androidfinalproject.model;

import android.content.Context;
import android.content.SharedPreferences;

public class MyPreferenceManager {
    private SharedPreferences sharedPreferences;

    public MyPreferenceManager(Context context){
        sharedPreferences = context.getSharedPreferences("user last Location", Context.MODE_PRIVATE);
    }

    public void saveLat(double lat) {
        sharedPreferences.edit().putString("lat", String.valueOf(lat)).apply();
    }

    public void saveLong(double longitude) {
        sharedPreferences.edit().putString("long", String.valueOf(longitude)).apply();
    }

    public String loadLat() {
        return sharedPreferences.getString("lat", null);
    }

    public String loadLong() {
        return sharedPreferences.getString("long", null);
    }


}
