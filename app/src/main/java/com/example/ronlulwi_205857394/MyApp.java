package com.example.ronlulwi_205857394;

import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MyApp extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        MyApp.context = getApplicationContext();
        MySharedPreferences.init(this);
    }
    public static Context getAppContext() {
        return MyApp.context;
    }
}
