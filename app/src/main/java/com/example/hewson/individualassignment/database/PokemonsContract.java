package com.example.hewson.individualassignment.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.provider.BaseColumns;

import com.example.hewson.individualassignment.model.Pokemon;

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
                    PokemonsEntry.COLUMN_NAME_ID + TEXT_TYPE + COMMA_SEP +
                    PokemonsEntry.COLUMN_NAME_NAME + TEXT_TYPE + COMMA_SEP +
                    PokemonsEntry.COLUMN_NAME_ICONURL + TEXT_TYPE + COMMA_SEP +
                    PokemonsEntry.COLUMN_NAME_URL + TEXT_TYPE + COMMA_SEP +
                    PokemonsEntry.COLUMN_NAME_TYPE1 + TEXT_TYPE + COMMA_SEP +
                    PokemonsEntry.COLUMN_NAME_TYPE2 + TEXT_TYPE + COMMA_SEP +
                    PokemonsEntry.COLUMN_NAME_WEIGHT + TEXT_TYPE + COMMA_SEP +
                    PokemonsEntry.COLUMN_NAME_HEIGHT + TEXT_TYPE + COMMA_SEP +
                    PokemonsEntry.COLUMN_NAME_LISTMOVES + TEXT_TYPE + COMMA_SEP +
                    PokemonsEntry.COLUMN_NAME_LEARNTYPE + TEXT_TYPE + COMMA_SEP +
                    PokemonsEntry.COLUMN_NAME_LEVELLEARNED + TEXT_TYPE + COMMA_SEP +
                    PokemonsEntry.COLUMN_NAME_ICON + BLOB_TYPE + " )";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

    public void restartDB() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL(PokemonsContract.SQL_CREATE_ENTRIES);
    }


    public abstract class PokemonsEntry implements BaseColumns {
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_ICONURL = "iconurl";
        public static final String COLUMN_NAME_URL = "url";
        public static final String COLUMN_NAME_TYPE1 = "type1";
        public static final String COLUMN_NAME_TYPE2 = "type2";
        public static final String COLUMN_NAME_WEIGHT = "weight";
        public static final String COLUMN_NAME_HEIGHT = "height";
        public static final String COLUMN_NAME_LISTMOVES = "listmoves";
        public static final String COLUMN_NAME_LEARNTYPE = "learntype";
        public static final String COLUMN_NAME_LEVELLEARNED = "levellearned";
        public static final String COLUMN_NAME_ICON = "icon";
    }

    public PokemonsContract(SQLiteOpenHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public long insert(Pokemon Pokemon){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PokemonsEntry.COLUMN_NAME_ID, Pokemon.getId());
        values.put(PokemonsEntry.COLUMN_NAME_NAME, Pokemon.getName());
        values.put(PokemonsEntry.COLUMN_NAME_ICONURL, Pokemon.getIconUrl());
        values.put(PokemonsEntry.COLUMN_NAME_URL, Pokemon.getUrl());
        values.put(PokemonsEntry.COLUMN_NAME_TYPE1, Pokemon.getType1());
        values.put(PokemonsEntry.COLUMN_NAME_TYPE2, Pokemon.getType2());
        values.put(PokemonsEntry.COLUMN_NAME_WEIGHT, Pokemon.getWeight());
        values.put(PokemonsEntry.COLUMN_NAME_HEIGHT, Pokemon.getHeight());
        values.put(PokemonsEntry.COLUMN_NAME_LISTMOVES, Pokemon.getListMoves());
        values.put(PokemonsEntry.COLUMN_NAME_LEARNTYPE, Pokemon.getLearnType());
        values.put(PokemonsEntry.COLUMN_NAME_LEVELLEARNED, Pokemon.getLevelLearned());
        if (Pokemon.getIcon() != null) {
            values.put(PokemonsEntry.COLUMN_NAME_ICON, BitmapConverter.getBytes(Pokemon.getIcon()));
        }
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
                PokemonsEntry.COLUMN_NAME_ICONURL,
                PokemonsEntry.COLUMN_NAME_URL,
                PokemonsEntry.COLUMN_NAME_TYPE1,
                PokemonsEntry.COLUMN_NAME_TYPE2,
                PokemonsEntry.COLUMN_NAME_TYPE2,
                PokemonsEntry.COLUMN_NAME_WEIGHT,
                PokemonsEntry.COLUMN_NAME_HEIGHT,
                PokemonsEntry.COLUMN_NAME_LISTMOVES,
                PokemonsEntry.COLUMN_NAME_LEARNTYPE,
                PokemonsEntry.COLUMN_NAME_LEVELLEARNED,
                PokemonsEntry.COLUMN_NAME_ICON
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
            pokemon.setId(cur.getString(cur.getColumnIndexOrThrow(PokemonsEntry.COLUMN_NAME_ID)));
            pokemon.setName(cur.getString(cur.getColumnIndexOrThrow(PokemonsEntry.COLUMN_NAME_NAME)));
            pokemon.setIconUrl(cur.getString(cur.getColumnIndexOrThrow(PokemonsEntry.COLUMN_NAME_ICONURL)));
            pokemon.setUrl(cur.getString(cur.getColumnIndexOrThrow(PokemonsEntry.COLUMN_NAME_URL)));
            pokemon.setType1(cur.getString(cur.getColumnIndexOrThrow(PokemonsEntry.COLUMN_NAME_TYPE1)));
            pokemon.setType2(cur.getString(cur.getColumnIndexOrThrow(PokemonsEntry.COLUMN_NAME_TYPE2)));
            pokemon.setWeight(cur.getString(cur.getColumnIndexOrThrow(PokemonsEntry.COLUMN_NAME_WEIGHT)));
            pokemon.setHeight(cur.getString(cur.getColumnIndexOrThrow(PokemonsEntry.COLUMN_NAME_HEIGHT)));
            pokemon.setListMoves(cur.getString(cur.getColumnIndexOrThrow(PokemonsEntry.COLUMN_NAME_LISTMOVES)));
            pokemon.setLearnType(cur.getString(cur.getColumnIndexOrThrow(PokemonsEntry.COLUMN_NAME_LEARNTYPE)));
            pokemon.setLevelLearned(cur.getString(cur.getColumnIndexOrThrow(PokemonsEntry.COLUMN_NAME_LEVELLEARNED)));
            if (cur.getBlob(cur.getColumnIndexOrThrow(PokemonsEntry.COLUMN_NAME_ICON)) != null){
                pokemon.setIcon((BitmapConverter.getImage(cur.getBlob(cur.getColumnIndexOrThrow(PokemonsEntry.COLUMN_NAME_ICON)))));
            }

            //pokemon.setUrl(cur.getString(cur.getColumnIndexOrThrow(PokemonsEntry.COLUMN_NAME_ICON)));
            //pokemon.setType(cur.getString(cur.getColumnIndexOrThrow(PokemonsEntry.COLUMN_NAME_TYPE)));
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
                PokemonsEntry.COLUMN_NAME_ICONURL,
                PokemonsEntry.COLUMN_NAME_URL,
                PokemonsEntry.COLUMN_NAME_TYPE1,
                PokemonsEntry.COLUMN_NAME_TYPE2,
                PokemonsEntry.COLUMN_NAME_TYPE2,
                PokemonsEntry.COLUMN_NAME_WEIGHT,
                PokemonsEntry.COLUMN_NAME_HEIGHT,
                PokemonsEntry.COLUMN_NAME_LISTMOVES,
                PokemonsEntry.COLUMN_NAME_LEARNTYPE,
                PokemonsEntry.COLUMN_NAME_LEVELLEARNED,
                PokemonsEntry.COLUMN_NAME_ICON
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
            pokemon.setId(cur.getString(cur.getColumnIndexOrThrow(PokemonsEntry.COLUMN_NAME_ID)));
            pokemon.setName(cur.getString(cur.getColumnIndexOrThrow(PokemonsEntry.COLUMN_NAME_NAME)));
            pokemon.setIconUrl(cur.getString(cur.getColumnIndexOrThrow(PokemonsEntry.COLUMN_NAME_ICONURL)));
            pokemon.setUrl(cur.getString(cur.getColumnIndexOrThrow(PokemonsEntry.COLUMN_NAME_URL)));
            pokemon.setType1(cur.getString(cur.getColumnIndexOrThrow(PokemonsEntry.COLUMN_NAME_TYPE1)));
            pokemon.setType2(cur.getString(cur.getColumnIndexOrThrow(PokemonsEntry.COLUMN_NAME_TYPE2)));
            pokemon.setWeight(cur.getString(cur.getColumnIndexOrThrow(PokemonsEntry.COLUMN_NAME_WEIGHT)));
            pokemon.setHeight(cur.getString(cur.getColumnIndexOrThrow(PokemonsEntry.COLUMN_NAME_HEIGHT)));
            pokemon.setListMoves(cur.getString(cur.getColumnIndexOrThrow(PokemonsEntry.COLUMN_NAME_LISTMOVES)));
            pokemon.setLearnType(cur.getString(cur.getColumnIndexOrThrow(PokemonsEntry.COLUMN_NAME_LEARNTYPE)));
            pokemon.setLevelLearned(cur.getString(cur.getColumnIndexOrThrow(PokemonsEntry.COLUMN_NAME_LEVELLEARNED)));
            if (cur.getBlob(cur.getColumnIndexOrThrow(PokemonsEntry.COLUMN_NAME_ICON)) != null){
                pokemon.setIcon((BitmapConverter.getImage(cur.getBlob(cur.getColumnIndexOrThrow(PokemonsEntry.COLUMN_NAME_ICON)))));
            }
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
