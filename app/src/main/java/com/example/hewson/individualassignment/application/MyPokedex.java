package com.example.hewson.individualassignment.application;

import android.app.Application;
import android.content.Context;

/**
 * Created by Hewson Tran on 28/09/2016.
 *
 * This application utilises the pokeapi.co Pokemon API, recyclerview, cardview, SQLite databasing and volley.
 *
 * This class is used to provide methods such as getting the application context and application itself
 */

public class MyPokedex extends Application {
    private static MyPokedex mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static MyPokedex getInstance() {
        return mInstance;
    }

    //returns application context
    public static Context getAppContext() {
        return mInstance.getApplicationContext();
    }
}
