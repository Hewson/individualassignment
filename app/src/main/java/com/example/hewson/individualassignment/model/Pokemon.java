package com.example.hewson.individualassignment.model;

import android.graphics.Bitmap;

import java.util.ArrayList;

/**
 * Created by Hewson Tran on 24/09/2016.
 * Class is used to create and set Pokemon objects
 */

public class Pokemon {
    //declaration of variables
    private String id, name, iconUrl, url, type1, type2, weight, height, listMoves, learnType, levelLearned, ability1, ability2, ability3, hp, speed, sdefense, sattack, defense, attack;
    private Bitmap icon;

    //getters and setters
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

    public String getAbility1() {
        return ability1;
    }

    public void setAbility1(String ability1) {
        this.ability1 = ability1;
    }

    public String getAbility2() {
        return ability2;
    }

    public void setAbility2(String ability2) {
        this.ability2 = ability2;
    }

    public String getAbility3() {
        return ability3;
    }

    public void setAbility3(String ability3) {
        this.ability3 = ability3;
    }

    public String getHp() {
        return hp;
    }

    public void setHp(String hp) {
        this.hp = hp;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public String getSdefense() {
        return sdefense;
    }

    public void setSdefense(String sdefense) {
        this.sdefense = sdefense;
    }

    public String getSattack() {
        return sattack;
    }

    public void setSattack(String sattack) {
        this.sattack = sattack;
    }

    public String getDefense() {
        return defense;
    }

    public void setDefense(String defense) {
        this.defense = defense;
    }

    public String getAttack() {
        return attack;
    }

    public void setAttack(String attack) {
        this.attack = attack;
    }

    public Pokemon() {
        //empty constructor
    }

    //full constructor
    public Pokemon(String id, String name, String iconUrl, String url, String type1, String type2, String weight, String height, String listMoves, String learnType, String levelLearned, String ability1, String ability2, String ability3, String hp, String speed, String sdefense, String sattack, String defense, String attack, Bitmap icon) {
        this.id = id;
        this.name = name;
        this.iconUrl = iconUrl;
        this.url = url;
        this.type1 = type1;
        this.type2 = type2;
        this.weight = weight;
        this.height = height;
        this.listMoves = listMoves;
        this.learnType = learnType;
        this.levelLearned = levelLearned;
        this.ability1 = ability1;
        this.ability2 = ability2;
        this.ability3 = ability3;
        this.hp = hp;
        this.speed = speed;
        this.sdefense = sdefense;
        this.sattack = sattack;
        this.defense = defense;
        this.attack = attack;
        this.icon = icon;
    }

    //method that prints an arraylist
    public String printArrayList(ArrayList<String> types) {
        String result = "";
        for (int i = 0; i < types.size(); i++) {
            result += " " + types.get(i);
        }
        return result;
    }

    //toString override method for the Pokemon object
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
                ", ability1='" + ability1 + '\'' +
                ", ability2='" + ability2 + '\'' +
                ", ability3='" + ability3 + '\'' +
                ", hp='" + hp + '\'' +
                ", speed='" + speed + '\'' +
                ", sdefense='" + sdefense + '\'' +
                ", sattack='" + sattack + '\'' +
                ", defense='" + defense + '\'' +
                ", attack='" + attack + '\'' +
                ", icon=" + icon +
                '}';
    }
}
