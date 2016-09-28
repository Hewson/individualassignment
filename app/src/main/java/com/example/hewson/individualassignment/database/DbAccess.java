package com.example.hewson.individualassignment.database;

import com.example.hewson.individualassignment.model.Pokemon;

import java.util.List;

/**
 * Created by Hewson Tran on 26/09/2016.
 */

public interface DbAccess {
    List<Pokemon> getAll();
}
