package com.example.ronlulwi_205857394;

import android.app.Application;
import android.content.Context;

public class MyApp extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        MyApp.context = getApplicationContext();
        //MySPV3.init(this);
        //MySignal.init(this);
    }
    public static Context getAppContext() {
        return MyApp.context;
    }
}
