package com.example.ronlulwi_205857394;

import android.content.Context;
import android.content.SharedPreferences;

public class MySharedPreferences {

    private static final String DB_FILE = "DB_FILE";
    private static MySharedPreferences mySharedPreferences = null;
    private SharedPreferences sharedPreferences;

    public static MySharedPreferences getInstance(){return mySharedPreferences;}

    public static  void init(Context context){
        if(mySharedPreferences == null)
            mySharedPreferences = new MySharedPreferences(context);
    }

    private MySharedPreferences(Context context){
        sharedPreferences = context.getSharedPreferences(DB_FILE, Context.MODE_PRIVATE);
    }

    public void putFloat(String key, float value){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat(key, value);
        editor.apply();
    }

    public Float getFloat(String key, float value){ return sharedPreferences.getFloat(key, value); }

    public void putInt(String key, int value){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public int getInt(String key, int value){ return sharedPreferences.getInt(key, value); }






}