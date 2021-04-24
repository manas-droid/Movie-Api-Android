package com.example.movieapp;

import android.app.Application;
import android.content.Context;

public class MyApp extends Application {

    private static MyApp instance;
    public static Context context;

    public static MyApp getInstance() {
        return instance;
    }

    public static Context getContext() {
        return context;
    }

    @Override
    public void onCreate() {
        instance = this;
        super.onCreate();
    }
}
