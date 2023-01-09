package com.example.ronlulwi_205857394;

import android.content.Context;
import android.content.SharedPreferences;

public class MySharedPreferences {

    private static final String APP_DB_FILE = "APP_DB_FILE";
    private static MySharedPreferences mySharedPreferences = null;
    private SharedPreferences sharedPreferences;

    public static MySharedPreferences getInstance(){return mySharedPreferences;}

    public static  void init(Context context){
        if(mySharedPreferences == null)
            mySharedPreferences = new MySharedPreferences(context);
    }

    private MySharedPreferences(Context context){
        sharedPreferences = context.getSharedPreferences(APP_DB_FILE, Context.MODE_PRIVATE);
    }

    public void putString(String key, String value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String getString(String key, String def) {
        return sharedPreferences.getString(key, def);
    }


}
