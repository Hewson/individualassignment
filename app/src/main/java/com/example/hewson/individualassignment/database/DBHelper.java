package com.example.hewson.individualassignment.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Hewson Tran on 26/09/2016.
 * Adapted from code written by Morgan Xu (2016) to manage the database object with methods for
 * its creation and a method to update it when its underlying structure changes
 */

public class DBHelper extends SQLiteOpenHelper {
    //standard declaration of database constants
    public static final int DATABASE_VERSION = 96;
    public static final String DATABASE_NAME = "pokedex.db";

    //constructor for the DBHelper which instantiates the object
    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //method called to create the database
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(PokemonsContract.SQL_CREATE_ENTRIES);
    }

    //method called when the structure of the database is changed
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(PokemonsContract.SQL_DELETE_ENTRIES);
        onCreate(sqLiteDatabase);
    }

    //method called when the structure of the database is changed
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
