package com.example.hewson.individualassignment.parsers;

import com.example.hewson.individualassignment.database.Pokemon;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hewson on 9/26/2016.
 */

public class PokemonParser {
    public static List<Pokemon> parseFeed(String content) {
//        try {
//            JSONArray myArray = new JSONArray(content);
//            List<Pokemon> pokemonList = new ArrayList<>();
//            for (int i = 0; i < myArray.length(); i++) {
//                JSONObject object = myArray.getJSONObject(i);
//                Pokemon pokemon = new Pokemon();
//                pokemon.setName(object.getString("name"));
//                pokemon.setType(object.getString("types"));
//                pokemonList.add(pokemon);
//            }
//            return pokemonList;
//        } catch (JSONException e) {
//            e.printStackTrace();
//            return null;
//        }
        try {
            JSONObject object = new JSONObject(content);
            List<Pokemon> pokemonList = new ArrayList<>();
            Pokemon pokemon = new Pokemon();
            pokemon.setName(object.getString("name"));
            pokemonList.add(pokemon);
            return pokemonList;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }


    }
}
