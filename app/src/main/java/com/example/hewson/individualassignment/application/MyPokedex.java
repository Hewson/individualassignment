package com.example.hewson.individualassignment.application;

import android.app.Application;
import android.content.Context;

/**
 * Created by Hewson Tran on 28/09/2016.
 */

public class MyPokedex extends Application {
    private static MyPokedex mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }
    public static MyPokedex getInstance(){
        return mInstance;
    }
    public static Context getAppContext() {
        return mInstance.getApplicationContext();
    }
}
