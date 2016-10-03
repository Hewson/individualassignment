package com.example.hewson.individualassignment.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hewson.individualassignment.R;
import com.example.hewson.individualassignment.controller.MoveAdapter;
import com.example.hewson.individualassignment.database.DBHelper;
import com.example.hewson.individualassignment.database.PokemonAccess;
import com.example.hewson.individualassignment.model.Pokemon;
import com.example.hewson.individualassignment.network.VolleySingleton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.hewson.individualassignment.view.MainActivity.BACK_ICON;
import static com.example.hewson.individualassignment.view.MainActivity.DEFAULT_ICON;
import static com.example.hewson.individualassignment.view.MainActivity.ENDPOINT;
import static com.example.hewson.individualassignment.view.MainActivity.LIMIT;
import static com.example.hewson.individualassignment.view.MainActivity.SHINY_ICON;
import static com.example.hewson.individualassignment.view.MainActivity.URL;

/**
 * Created by Hewson Tran on 26/09/2016.
 * This class is used to declare and manage the specific pokemon activity
 */

public class SpecificPokemon extends AppCompatActivity {
    //standard declaration of variables
    private static final String TAG = SpecificPokemon.class.getName();
    private static String listSeparator = "__,__";
    private List<String> moveList;
    private List<Pokemon> myPokemonList;

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private MoveAdapter mAdapter;

    private DBHelper dbHelper;
    private PokemonAccess pokemonAccess;
    private VolleySingleton volleySingleton;

    private TextView name, idNumber, type1, type2, weight, height, listMoves, learnType, levelLearned, ability1, ability2, ability3, abilityTag, typeTag, hp, speed, sdefense, sattack, defense, attack;
    private ImageView icon;

    //method called when the activity is created
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific_pokemon);

        //receiving intent
        Intent intent = getIntent();
        int id = intent.getIntExtra("id", 1);

        //toolbar declaration
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        //db declaration and get the specific pokemon from the ID passed from the intent
        dbHelper = new DBHelper(this);
        pokemonAccess = new PokemonAccess(dbHelper);
        Pokemon pokemon = pokemonAccess.getAll().get(id);

        //recycler view declaration
        moveList = new ArrayList<>();
        volleySingleton = VolleySingleton.getInstance();
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler2);
        mAdapter = new MoveAdapter(this, myPokemonList, id);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);

        //sets the content of the moveList and updates the adapter
        moveList = convertStringToList(pokemon.getListMoves());
        mAdapter.setMoves(moveList);
        updateDisplay();

        //set the value of items from the XML
        name = (TextView) findViewById(R.id.name);
        name.setText(pokemon.getName());

        //set the value of items from the XML
        idNumber = (TextView) findViewById(R.id.id);
        idNumber.setText(pokemon.getId());

        //set the value of items from the XML
        typeTag = (TextView) findViewById(R.id.typeTag);

        //set the value of items from the XML
        type1 = (TextView) findViewById(R.id.type1);
        type1.setText(pokemon.getType1());

        //set the text colour depending on the type
        switch (pokemon.getType1()) {
            case "Normal":
                type1.setTextColor(ContextCompat.getColor(this, R.color.normal));
                break;
            case "Fire":
                type1.setTextColor(ContextCompat.getColor(this, R.color.fire));
                break;
            case "Water":
                type1.setTextColor(ContextCompat.getColor(this, R.color.water));
                break;
            case "Electric":
                type1.setTextColor(ContextCompat.getColor(this, R.color.electric));
                break;
            case "Grass":
                type1.setTextColor(ContextCompat.getColor(this, R.color.grass));
                break;
            case "Ice":
                type1.setTextColor(ContextCompat.getColor(this, R.color.normal));
                break;
            case "Fighting":
                type1.setTextColor(ContextCompat.getColor(this, R.color.fighting));
                break;
            case "Poison":
                type1.setTextColor(ContextCompat.getColor(this, R.color.poison));
                break;
            case "Ground":
                type1.setTextColor(ContextCompat.getColor(this, R.color.ground));
                break;
            case "Flying":
                type1.setTextColor(ContextCompat.getColor(this, R.color.flying));
                break;
            case "Psychic":
                type1.setTextColor(ContextCompat.getColor(this, R.color.psychic));
                break;
            case "Bug":
                type1.setTextColor(ContextCompat.getColor(this, R.color.bug));
                break;
            case "Rock":
                type1.setTextColor(ContextCompat.getColor(this, R.color.rock));
                break;
            case "Ghost":
                type1.setTextColor(ContextCompat.getColor(this, R.color.ghost));
                break;
            case "Dragon":
                type1.setTextColor(ContextCompat.getColor(this, R.color.dragon));
                break;
            case "Dark":
                type1.setTextColor(ContextCompat.getColor(this, R.color.dark));
                break;
            case "Steel":
                type1.setTextColor(ContextCompat.getColor(this, R.color.steel));
                break;
            case "Fairy":
                type1.setTextColor(ContextCompat.getColor(this, R.color.fairy));
                break;
            default:
                break;
        }

        //set the value of items from the XML
        type2 = (TextView) findViewById(R.id.type2);
        type2.setVisibility(View.GONE);
        if (pokemon.getType2() != null) {
            type2.setText(pokemon.getType2());

            //set text colour depending on type
            switch (pokemon.getType2()) {
                case "Normal":
                    type2.setTextColor(ContextCompat.getColor(this, R.color.normal));
                    break;
                case "Fire":
                    type2.setTextColor(ContextCompat.getColor(this, R.color.fire));
                    break;
                case "Water":
                    type2.setTextColor(ContextCompat.getColor(this, R.color.water));
                    break;
                case "Electric":
                    type2.setTextColor(ContextCompat.getColor(this, R.color.electric));
                    break;
                case "Grass":
                    type2.setTextColor(ContextCompat.getColor(this, R.color.grass));
                    break;
                case "Ice":
                    type2.setTextColor(ContextCompat.getColor(this, R.color.normal));
                    break;
                case "Fighting":
                    type2.setTextColor(ContextCompat.getColor(this, R.color.fighting));
                    break;
                case "Poison":
                    type2.setTextColor(ContextCompat.getColor(this, R.color.poison));
                    break;
                case "Ground":
                    type2.setTextColor(ContextCompat.getColor(this, R.color.ground));
                    break;
                case "Flying":
                    type2.setTextColor(ContextCompat.getColor(this, R.color.flying));
                    break;
                case "Psychic":
                    type2.setTextColor(ContextCompat.getColor(this, R.color.psychic));
                    break;
                case "Bug":
                    type2.setTextColor(ContextCompat.getColor(this, R.color.bug));
                    break;
                case "Rock":
                    type2.setTextColor(ContextCompat.getColor(this, R.color.rock));
                    break;
                case "Ghost":
                    type2.setTextColor(ContextCompat.getColor(this, R.color.ghost));
                    break;
                case "Dragon":
                    type2.setTextColor(ContextCompat.getColor(this, R.color.dragon));
                    break;
                case "Dark":
                    type2.setTextColor(ContextCompat.getColor(this, R.color.dark));
                    break;
                case "Steel":
                    type2.setTextColor(ContextCompat.getColor(this, R.color.steel));
                    break;
                case "Fairy":
                    type2.setTextColor(ContextCompat.getColor(this, R.color.fairy));
                    break;
                default:
                    break;
            }
            type2.setVisibility(View.VISIBLE);
        }

        //set the value of items from the XML
        icon = (ImageView) findViewById(R.id.icon);
        icon.setImageBitmap(pokemon.getIcon());

        //set the value of items from the XML
        weight = (TextView) findViewById(R.id.weight);
        weight.setText(pokemon.getWeight());

        //set the value of items from the XML
        height = (TextView) findViewById(R.id.height);
        height.setText(pokemon.getHeight());

        //set the value of items from the XML
        abilityTag = (TextView) findViewById(R.id.abilityTag);

        //set the value of items from the XML
        ability1 = (TextView) findViewById(R.id.ability1);
        ability1.setVisibility(View.GONE);
        if (pokemon.getAbility1() != null) {
            ability1.setText(pokemon.getAbility1());
            ability1.setVisibility(View.VISIBLE);
        }

        //set the value of items from the XML
        ability2 = (TextView) findViewById(R.id.ability2);
        ability2.setVisibility(View.GONE);
        if (pokemon.getAbility2() != null) {
            ability2.setText(pokemon.getAbility2());
            ability2.setVisibility(View.VISIBLE);
        }

        //set the value of items from the XML
        ability3 = (TextView) findViewById(R.id.ability3);
        ability3.setVisibility(View.GONE);
        if (pokemon.getAbility3() != null) {
            ability3.setText(pokemon.getAbility3());
            ability3.setVisibility(View.VISIBLE);
        }

        //set the value of items from the XML
        hp = (TextView) findViewById(R.id.hp);
        hp.setText(pokemon.getHp());

        //set the value of items from the XML
        speed = (TextView) findViewById(R.id.speed);
        speed.setText(pokemon.getSpeed());

        //set the value of items from the XML
        sdefense = (TextView) findViewById(R.id.sdefense);
        sdefense.setText(pokemon.getSdefense());

        //set the value of items from the XML
        sattack = (TextView) findViewById(R.id.sattack);
        sattack.setText(pokemon.getSattack());

        //set the value of items from the XML
        defense = (TextView) findViewById(R.id.defense);
        defense.setText(pokemon.getDefense());

        //set the value of items from the XML
        attack = (TextView) findViewById(R.id.attack);
        attack.setText(pokemon.getAttack());
    }

    //inflates the menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    //defines the paths after menu items are selected
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        MainActivity mainActivity = new MainActivity();
        switch (item.getItemId()) {
            case R.id.action_refresh:
                Toast.makeText(this, "Please go back to the main page to refresh", Toast.LENGTH_LONG).show();
                break;

            case R.id.shiny_refresh:
                Toast.makeText(this, "Please go back to the main page to refresh", Toast.LENGTH_LONG).show();
                break;

            case R.id.back_refresh:
                Toast.makeText(this, "Please go back to the main page to refresh", Toast.LENGTH_LONG).show();
                break;

            case R.id.default_refresh:
                Toast.makeText(this, "Please go back to the main page to refresh", Toast.LENGTH_LONG).show();
                break;

            default:
                break;
        }

        return true;
    }

    //converts strings to an arraylist
    public static ArrayList<String> convertStringToList(String string) {
        ArrayList<String> list = new ArrayList<String>(Arrays.asList(string.split(listSeparator)));
        return list;
    }

    //updates the adapter
    protected void updateDisplay() {
        mAdapter.notifyDataSetChanged();
    }
}
