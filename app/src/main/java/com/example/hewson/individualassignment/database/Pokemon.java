package com.example.hewson.individualassignment.database;

import android.graphics.Bitmap;

/**
 * Created by Hewson Tran on 24/09/2016.
 */

public class Pokemon {
    private int id;
    private String name, type;
    private Bitmap icon;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Bitmap getIcon() {
        return icon;
    }

    public void setIcon(Bitmap icon) {
        this.icon = icon;
    }



    public Pokemon() {
    }

    public Pokemon(String name, String type) {

        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
