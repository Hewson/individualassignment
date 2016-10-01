package com.example.hewson.individualassignment.model;

import android.graphics.Bitmap;

import java.util.ArrayList;

/**
 * Created by Hewson Tran on 24/09/2016.
 */

public class Pokemon {
    private int id;
    private String name, iconUrl, url;
    public ArrayList<String> type;
    private Bitmap icon;

    public Pokemon() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getType() {
        return type;
    }

    public void setType(ArrayList<String> type) {
        this.type = type;
    }

    public Bitmap getIcon() {
        return icon;
    }

    public void setIcon(Bitmap icon) {
        this.icon = icon;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String printArrayList(ArrayList<String> types) {
        String result = "";
        for (int i = 0; i < types.size(); i++) {
            result += " " + types.get(i);
        }
        return result;
    }

    @Override
    public String toString() {
        return "Pokemon{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", iconUrl='" + iconUrl + '\'' +
                ", url='" + url + '\'' +
                ", type=" + type +
                ", icon=" + icon +
                '}';
    }
}
