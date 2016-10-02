package com.example.hewson.individualassignment.model;

import android.graphics.Bitmap;

import java.util.ArrayList;

/**
 * Created by Hewson Tran on 24/09/2016.
 */

public class Pokemon {
    private String id, name, iconUrl, url, type1, type2, weight, height, listMoves, learnType, levelLearned;
    private Bitmap icon;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getType1() {
        return type1;
    }

    public void setType1(String type1) {
        this.type1 = type1;
    }

    public String getType2() {
        return type2;
    }

    public void setType2(String type2) {
        this.type2 = type2;
    }

    public Bitmap getIcon() {
        return icon;
    }

    public void setIcon(Bitmap icon) {
        this.icon = icon;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getListMoves() {
        return listMoves;
    }

    public void setListMoves(String listMoves) {
        this.listMoves = listMoves;
    }

    public String getLearnType() {
        return learnType;
    }

    public void setLearnType(String learnType) {
        this.learnType = learnType;
    }

    public String getLevelLearned() {
        return levelLearned;
    }

    public void setLevelLearned(String levelLearned) {
        this.levelLearned = levelLearned;
    }

    public Pokemon() {

    }

    public Pokemon(String id, String name, String iconUrl, String url, String type1, String type2, Bitmap icon) {
        this.id = id;
        this.name = name;
        this.iconUrl = iconUrl;
        this.url = url;
        this.type1 = type1;
        this.type2 = type2;
        this.icon = icon;
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
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", iconUrl='" + iconUrl + '\'' +
                ", url='" + url + '\'' +
                ", type1='" + type1 + '\'' +
                ", type2='" + type2 + '\'' +
                ", weight='" + weight + '\'' +
                ", height='" + height + '\'' +
                ", listMoves='" + listMoves + '\'' +
                ", learnType='" + learnType + '\'' +
                ", levelLearned='" + levelLearned + '\'' +
                ", icon=" + icon +
                '}';
    }
}
