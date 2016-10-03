package com.example.hewson.individualassignment.database;

import android.database.sqlite.SQLiteOpenHelper;

import com.example.hewson.individualassignment.model.Pokemon;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hewson Tran on 26/09/2016.
 * Adapted from code written by Morgan Xu (2016). This method is a class that implements methods written by the PokemonsContract class.
 * It performs standard CRUD operations by taking in objects or variables
 */

public class PokemonAccess implements DbAccess {
    private final PokemonsContract PokemonsContract;

    //instantiates an SQLiteOpenHelper which is used to manage DB interactions
    public PokemonAccess(SQLiteOpenHelper sqLiteOpenHelper) {
        this.PokemonsContract = new PokemonsContract(sqLiteOpenHelper);
    }

    //overriding methods in the DBACess interface
    @Override
    public List<Pokemon> getAll() {
        return PokemonsContract.getPokemons();
    }

    //standard CRUD contract methods
    public void insertPokemon(Pokemon pokemon) {
        PokemonsContract.insert(pokemon);
    }

    public void deletePokemon(int id) {
        PokemonsContract.delete(id);
    }

    public void deleteAll() {
        PokemonsContract.restartDB();
    }

    public void deletePokemons(int... ids) {
        for (int id : ids) {
            PokemonsContract.delete(id);
        }
    }
}
