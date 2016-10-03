package com.example.hewson.individualassignment.application;

import android.app.Application;
import android.content.Context;

/**
 * Created by Hewson Tran on 28/09/2016.
 *
 * This application utilises the pokeapi.co Pokemon API, recyclerview, cardview, SQLite databasing and volley.
 * It features a recyclerview on the main page for Pokemon and a recyclerview on the detailed page to look at moves.
 * Features logic to determine if DB is empty and will make a JSON call. If it is not, it will load from the DB.
 * Refresh buttons refresh the Pokedex and Pokemon list and also allow the user to switch the type of icons displayed in the Pokedex (shiny, back, front).
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
