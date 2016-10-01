package com.example.hewson.individualassignment.database;

import android.database.sqlite.SQLiteOpenHelper;

import com.example.hewson.individualassignment.model.Pokemon;

import java.util.List;

/**
 * Created by Hewson Tran on 26/09/2016.
 */

public class PokemonAccess implements DbAccess {
    private final PokemonsContract PokemonsContract;

    public PokemonAccess(SQLiteOpenHelper sqLiteOpenHelper) {
        this.PokemonsContract = new PokemonsContract(sqLiteOpenHelper);
    }

    //Example of overriding interface
    @Override
    public List<Pokemon> getAll() {
        return PokemonsContract.getPokemons();
    }

    //Example standard contract methods
    public void insertPokemon(Pokemon pokemon){
        PokemonsContract.insert(pokemon);
    }

    public void deletePokemon(int id){
        PokemonsContract.delete(id);
    }

    //Example of extra methods
    public void insertPokemons(List<Pokemon> Pokemons){
        for(Pokemon pokemon : Pokemons){
            PokemonsContract.insert(pokemon);
        }
    }

    public void deletePokemons(int... ids){
        for(int id : ids){
            PokemonsContract.delete(id);
        }
    }

    public boolean hasPokemon(int id){
        return PokemonsContract.getPokemon(id) != null;
    }
}
