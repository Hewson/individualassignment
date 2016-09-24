package com.example.hewson.individualassignment;

/**
 * Created by Hewson Tran on 24/09/2016.
 */

public class Pokemon {
    private String name, type;

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
