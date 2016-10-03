package com.example.hewson.individualassignment.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.provider.BaseColumns;

import com.example.hewson.individualassignment.model.Pokemon;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hewson Tran on 26/09/2016.
 * Adapted from code written by Morgan Xu (2016). Class used to directly interact with the database including
 * basic CRUD operations and creation/deletion of tables and columns
 */

public final class PokemonsContract {
    //declaration of datatypes and constants
    public static final String TABLE_NAME = "Pokemons";
    private final SQLiteOpenHelper dbHelper;
    private static final String TEXT_TYPE = " TEXT";
    private static final String INT_TYPE = " INT";
    private static final String BLOB_TYPE = " BLOB";
    private static final String COMMA_SEP = ",";

    //string that is built as a CREATE statement for the tables
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
                    PokemonsEntry.COLUMN_NAME_ABILITY1 + TEXT_TYPE + COMMA_SEP +
                    PokemonsEntry.COLUMN_NAME_ABILITY2 + TEXT_TYPE + COMMA_SEP +
                    PokemonsEntry.COLUMN_NAME_ABILITY3 + TEXT_TYPE + COMMA_SEP +
                    PokemonsEntry.COLUMN_NAME_HP + TEXT_TYPE + COMMA_SEP +
                    PokemonsEntry.COLUMN_NAME_SPEED + TEXT_TYPE + COMMA_SEP +
                    PokemonsEntry.COLUMN_NAME_SDEFENSE + TEXT_TYPE + COMMA_SEP +
                    PokemonsEntry.COLUMN_NAME_SATTACK + TEXT_TYPE + COMMA_SEP +
                    PokemonsEntry.COLUMN_NAME_DEFENSE + TEXT_TYPE + COMMA_SEP +
                    PokemonsEntry.COLUMN_NAME_ATTACK + TEXT_TYPE + COMMA_SEP +
                    PokemonsEntry.COLUMN_NAME_ICON + BLOB_TYPE + " )";

    //string that is built to DELETE tables
    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

    //method that drops the table and creates a new table. Used for the restart function
    public void restartDB() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL(PokemonsContract.SQL_CREATE_ENTRIES);
    }

    //class that declares the columns in the table
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
        public static final String COLUMN_NAME_ABILITY1 = "ability1";
        public static final String COLUMN_NAME_ABILITY2 = "ability2";
        public static final String COLUMN_NAME_ABILITY3 = "ability3";
        public static final String COLUMN_NAME_HP = "hp";
        public static final String COLUMN_NAME_SPEED = "speed";
        public static final String COLUMN_NAME_SDEFENSE = "specialdefense";
        public static final String COLUMN_NAME_SATTACK = "specialattack";
        public static final String COLUMN_NAME_DEFENSE = "defense";
        public static final String COLUMN_NAME_ATTACK = "attack";
        public static final String COLUMN_NAME_ICON = "icon";
    }

    //method that sets the DBHelper used
    public PokemonsContract(SQLiteOpenHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    //method that inserts Pokemon
    public long insert(Pokemon Pokemon) {
        //opens the connection the DB
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        //sets the values into their respective column
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
        values.put(PokemonsEntry.COLUMN_NAME_ABILITY1, Pokemon.getAbility1());
        values.put(PokemonsEntry.COLUMN_NAME_ABILITY2, Pokemon.getAbility2());
        values.put(PokemonsEntry.COLUMN_NAME_ABILITY3, Pokemon.getAbility3());
        values.put(PokemonsEntry.COLUMN_NAME_HP, Pokemon.getHp());
        values.put(PokemonsEntry.COLUMN_NAME_SPEED, Pokemon.getSpeed());
        values.put(PokemonsEntry.COLUMN_NAME_SDEFENSE, Pokemon.getSdefense());
        values.put(PokemonsEntry.COLUMN_NAME_SATTACK, Pokemon.getSattack());
        values.put(PokemonsEntry.COLUMN_NAME_DEFENSE, Pokemon.getDefense());
        values.put(PokemonsEntry.COLUMN_NAME_ATTACK, Pokemon.getAttack());

        //bitmap utility used to convert bitmap to BLOB bye array
        if (Pokemon.getIcon() != null) {
            values.put(PokemonsEntry.COLUMN_NAME_ICON, BitmapUtility.getBytes(Pokemon.getIcon()));
        }

        //inserts the row and closes the DB while giving the rowID as the response
        long newRowId;
        newRowId = db.insert(TABLE_NAME, null, values);
        db.close();

        return newRowId;
    }

    //gets list of pokemon from the DB
    public List<Pokemon> getPokemons() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        //declares the columns to query
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
                PokemonsEntry.COLUMN_NAME_ABILITY1,
                PokemonsEntry.COLUMN_NAME_ABILITY2,
                PokemonsEntry.COLUMN_NAME_ABILITY3,
                PokemonsEntry.COLUMN_NAME_HP,
                PokemonsEntry.COLUMN_NAME_SPEED,
                PokemonsEntry.COLUMN_NAME_SDEFENSE,
                PokemonsEntry.COLUMN_NAME_SATTACK,
                PokemonsEntry.COLUMN_NAME_DEFENSE,
                PokemonsEntry.COLUMN_NAME_ATTACK,
                PokemonsEntry.COLUMN_NAME_ICON
        };

        //Sort order
        String sortOrder = PokemonsEntry.COLUMN_NAME_ID;

        //declaration of a cursor object
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

        //moving through the columns and setting the values for them onto a Pokemon object
        while (cur.moveToNext()) {
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
            pokemon.setAbility1(cur.getString(cur.getColumnIndexOrThrow(PokemonsEntry.COLUMN_NAME_ABILITY1)));
            pokemon.setAbility2(cur.getString(cur.getColumnIndexOrThrow(PokemonsEntry.COLUMN_NAME_ABILITY2)));
            pokemon.setAbility3(cur.getString(cur.getColumnIndexOrThrow(PokemonsEntry.COLUMN_NAME_ABILITY3)));
            pokemon.setHp(cur.getString(cur.getColumnIndexOrThrow(PokemonsEntry.COLUMN_NAME_HP)));
            pokemon.setSpeed(cur.getString(cur.getColumnIndexOrThrow(PokemonsEntry.COLUMN_NAME_SPEED)));
            pokemon.setSdefense(cur.getString(cur.getColumnIndexOrThrow(PokemonsEntry.COLUMN_NAME_SDEFENSE)));
            pokemon.setSattack(cur.getString(cur.getColumnIndexOrThrow(PokemonsEntry.COLUMN_NAME_SATTACK)));
            pokemon.setDefense(cur.getString(cur.getColumnIndexOrThrow(PokemonsEntry.COLUMN_NAME_DEFENSE)));
            pokemon.setAttack(cur.getString(cur.getColumnIndexOrThrow(PokemonsEntry.COLUMN_NAME_ATTACK)));

            //bitmap utility used to convert a BLOB bye array back to a bitmap
            if (cur.getBlob(cur.getColumnIndexOrThrow(PokemonsEntry.COLUMN_NAME_ICON)) != null) {
                pokemon.setIcon((BitmapUtility.getImage(cur.getBlob(cur.getColumnIndexOrThrow(PokemonsEntry.COLUMN_NAME_ICON)))));
            }
            //that specific pokemon to arraylist
            Pokemons.add(pokemon);
        }

        cur.close();
        db.close();
        //return arraylist
        return Pokemons;
    }

    //deletes an entry
    public void delete(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        //WHERE ID = ?
        String selection = PokemonsEntry.COLUMN_NAME_ID + " = ?";

        //All the different IDs its equal to
        String[] selectionArgs = {String.valueOf(id)};

        db.delete(TABLE_NAME, selection, selectionArgs);

        db.close();
    }

    //gets individual pokemon based on ID
    public Pokemon getPokemon(int id) {
        //opened connection to DB
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selection = PokemonsEntry.COLUMN_NAME_ID + " = ?";

        String[] selectionArgs = {String.valueOf(id)};

        //declares the columns to query
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
                PokemonsEntry.COLUMN_NAME_ABILITY1,
                PokemonsEntry.COLUMN_NAME_ABILITY2,
                PokemonsEntry.COLUMN_NAME_ABILITY3,
                PokemonsEntry.COLUMN_NAME_HP,
                PokemonsEntry.COLUMN_NAME_SPEED,
                PokemonsEntry.COLUMN_NAME_SDEFENSE,
                PokemonsEntry.COLUMN_NAME_SATTACK,
                PokemonsEntry.COLUMN_NAME_DEFENSE,
                PokemonsEntry.COLUMN_NAME_ATTACK,
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
        if (cur.moveToNext()) {

            //instantiates and pokemon and sets the value from it based on values from the DB
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
            pokemon.setAbility1(cur.getString(cur.getColumnIndexOrThrow(PokemonsEntry.COLUMN_NAME_ABILITY1)));
            pokemon.setAbility2(cur.getString(cur.getColumnIndexOrThrow(PokemonsEntry.COLUMN_NAME_ABILITY2)));
            pokemon.setAbility3(cur.getString(cur.getColumnIndexOrThrow(PokemonsEntry.COLUMN_NAME_ABILITY3)));
            pokemon.setHp(cur.getString(cur.getColumnIndexOrThrow(PokemonsEntry.COLUMN_NAME_HP)));
            pokemon.setSpeed(cur.getString(cur.getColumnIndexOrThrow(PokemonsEntry.COLUMN_NAME_SPEED)));
            pokemon.setSdefense(cur.getString(cur.getColumnIndexOrThrow(PokemonsEntry.COLUMN_NAME_SDEFENSE)));
            pokemon.setSattack(cur.getString(cur.getColumnIndexOrThrow(PokemonsEntry.COLUMN_NAME_SATTACK)));
            pokemon.setDefense(cur.getString(cur.getColumnIndexOrThrow(PokemonsEntry.COLUMN_NAME_DEFENSE)));
            pokemon.setAttack(cur.getString(cur.getColumnIndexOrThrow(PokemonsEntry.COLUMN_NAME_ATTACK)));

            //bitmap utility used to convert a BLOB byte array back to a bitmap
            if (cur.getBlob(cur.getColumnIndexOrThrow(PokemonsEntry.COLUMN_NAME_ICON)) != null) {
                pokemon.setIcon((BitmapUtility.getImage(cur.getBlob(cur.getColumnIndexOrThrow(PokemonsEntry.COLUMN_NAME_ICON)))));
            }
        }
        cur.close();
        db.close();
        return pokemon;
    }
}
