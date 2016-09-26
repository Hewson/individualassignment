package com.example.hewson.individualassignment.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.BaseColumns;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hewson Tran on 26/09/2016.
 */

public final class PokemonsContract {
    public static final String TABLE_NAME = "Pokemons";
    private final SQLiteOpenHelper dbHelper;
    private static final String TEXT_TYPE = " TEXT";
    private static final String INT_TYPE = " INT";
    private static final String BLOB_TYPE = " BLOB";
    private static final String COMMA_SEP = ",";

    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    PokemonsEntry._ID + " INTEGER PRIMARY KEY," +
                    PokemonsEntry.COLUMN_NAME_ID + INT_TYPE + COMMA_SEP +
                    PokemonsEntry.COLUMN_NAME_NAME + TEXT_TYPE + COMMA_SEP +
                    PokemonsEntry.COLUMN_NAME_TYPE + TEXT_TYPE + " )";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

    public abstract class PokemonsEntry implements BaseColumns {
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_TYPE = "img";
    }

    public PokemonsContract(SQLiteOpenHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public long insert(Pokemon Pokemon){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(PokemonsEntry.COLUMN_NAME_ID, Pokemon.getId());
        values.put(PokemonsEntry.COLUMN_NAME_NAME, Pokemon.getName());;
        values.put(PokemonsEntry.COLUMN_NAME_TYPE, Pokemon.getType());

        long newRowId;
        newRowId = db.insert(TABLE_NAME, null, values);
        db.close();

        return newRowId;
    }

    public List<Pokemon> getPokemons(){
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        //Columns to query
        String[] columns = {
                PokemonsEntry._ID,
                PokemonsEntry.COLUMN_NAME_ID,
                PokemonsEntry.COLUMN_NAME_NAME,
                PokemonsEntry.COLUMN_NAME_TYPE
        };

        //Sort order
        String sortOrder = PokemonsEntry.COLUMN_NAME_ID;

        Cursor cur = db.query(
                TABLE_NAME,  // The table to query
                columns,                               // The columns to return
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );

        List<Pokemon> Pokemons = new ArrayList<>();

        while (cur.moveToNext()){
            Pokemon pokemon = new Pokemon();
            pokemon.setId(cur.getInt(cur.getColumnIndexOrThrow(PokemonsEntry.COLUMN_NAME_ID)));
            pokemon.setName(cur.getString(cur.getColumnIndexOrThrow(PokemonsEntry.COLUMN_NAME_NAME)));
            pokemon.setType(cur.getString(cur.getColumnIndexOrThrow(PokemonsEntry.COLUMN_NAME_TYPE)));
            Pokemons.add(pokemon);
        }

        cur.close();
        db.close();
        return Pokemons;
    }

    public void delete(int id){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        //WHERE ID = ?
        String selection = PokemonsEntry.COLUMN_NAME_ID + " = ?";

        //All the different IDs its equal to
        String[] selectionArgs = {String.valueOf(id)};

        db.delete(TABLE_NAME, selection, selectionArgs);

        db.close();
    }

    public Pokemon getPokemon(int id){
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selection = PokemonsEntry.COLUMN_NAME_ID + " = ?";

        String[] selectionArgs = {String.valueOf(id)};

        //Columns to query
        String[] columns = {
                PokemonsEntry._ID,
                PokemonsEntry.COLUMN_NAME_ID,
                PokemonsEntry.COLUMN_NAME_NAME,
                PokemonsEntry.COLUMN_NAME_TYPE
        };

        Cursor cur = db.query(
                TABLE_NAME,  // The table to query
                columns,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );
        Pokemon pokemon = null;
        if(cur.moveToNext()){
            pokemon = new Pokemon();
            pokemon.setId(cur.getInt(cur.getColumnIndexOrThrow(PokemonsEntry.COLUMN_NAME_ID)));
            pokemon.setName(cur.getString(cur.getColumnIndexOrThrow(PokemonsEntry.COLUMN_NAME_NAME)));
            pokemon.setType(cur.getString(cur.getColumnIndexOrThrow(PokemonsEntry.COLUMN_NAME_TYPE)));
        }
        cur.close();
        db.close();
        return pokemon;
    }

    public static byte[] getBitmapAsByteArray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        return outputStream.toByteArray();
    }
}
