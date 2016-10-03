package com.example.hewson.individualassignment.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hewson.individualassignment.R;
import com.example.hewson.individualassignment.application.MainActivity;
import com.example.hewson.individualassignment.controller.MoveAdapter;
import com.example.hewson.individualassignment.controller.PokemonAdapter;
import com.example.hewson.individualassignment.database.DBHelper;
import com.example.hewson.individualassignment.database.PokemonAccess;
import com.example.hewson.individualassignment.model.Pokemon;
import com.example.hewson.individualassignment.network.VolleySingleton;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SpecificPokemon extends AppCompatActivity {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific_pokemon);

        //toolbar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        //db
        dbHelper = new DBHelper(this);
        pokemonAccess = new PokemonAccess(dbHelper);

        //recycler view
        moveList = new ArrayList<>();
        volleySingleton = VolleySingleton.getInstance();
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler2);
        mAdapter = new MoveAdapter(this, myPokemonList);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);
        for (int i = 0; i < 10; i++) {
            moveList.add(pokemonAccess.getAll().get(i).getHeight());
        }
        mAdapter.setMoves(moveList);

        Intent intent = getIntent();
        int id = intent.getIntExtra("id", 1);
        Pokemon pokemon = pokemonAccess.getAll().get(id);

        name = (TextView) findViewById(R.id.name);
        name.setText(pokemon.getName());

        idNumber = (TextView) findViewById(R.id.id);
        idNumber.setText(pokemon.getId());

        typeTag = (TextView) findViewById(R.id.typeTag);

        type1 = (TextView) findViewById(R.id.type1);
        type1.setText(pokemon.getType1());
        type2 = (TextView) findViewById(R.id.type2);
        type2.setVisibility(View.GONE);
        if (pokemon.getType2() != null){
            type2.setText(pokemon.getType2());
            type2.setVisibility(View.VISIBLE);
        }

        icon = (ImageView) findViewById(R.id.icon);
        icon.setImageBitmap(pokemon.getIcon());

        weight = (TextView) findViewById(R.id.weight);
        weight.setText("Weight: " + pokemon.getWeight());

        height = (TextView) findViewById(R.id.height);
        height.setText("Height: " + pokemon.getHeight());

        /*listMoves = (TextView) findViewById(R.id.listMoves);
        ArrayList<String> arrayMoves = convertStringToList(pokemon.getListMoves());
        listMoves.setText("Moves: " + arrayMoves);

        learnType = (TextView) findViewById(R.id.learnType);
        ArrayList<String> arrayLearnType = convertStringToList(pokemon.getLearnType());
        learnType.setText("Method of learning: " + arrayLearnType);

        levelLearned = (TextView) findViewById(R.id.levelLearned);
        ArrayList<String> arrayLevelLearned = convertStringToList(pokemon.getLevelLearned());
        levelLearned.setText("Level Learned: " + arrayLevelLearned);*/

        abilityTag = (TextView) findViewById(R.id.abilityTag);

        ability1 = (TextView) findViewById(R.id.ability1);
        ability1.setVisibility(View.GONE);
        if (pokemon.getAbility1() != null) {
            ability1.setText(pokemon.getAbility1());
            ability1.setVisibility(View.VISIBLE);
        }


        ability2 = (TextView) findViewById(R.id.ability2);
        ability2.setVisibility(View.GONE);
        if (pokemon.getAbility2() != null) {
            ability2.setText(pokemon.getAbility2());
            ability2.setVisibility(View.VISIBLE);
        }

        ability3 = (TextView) findViewById(R.id.ability3);
        ability3.setVisibility(View.GONE);
        if (pokemon.getAbility3() != null) {
            ability3.setText(pokemon.getAbility3());
            ability3.setVisibility(View.VISIBLE);
        }

        hp = (TextView) findViewById(R.id.hp);
        hp.setText("HP: " + pokemon.getHp());

        speed = (TextView) findViewById(R.id.speed);
        speed.setText("Speed: " + pokemon.getSpeed());

        sdefense = (TextView) findViewById(R.id.sdefense);
        sdefense.setText("Special Defense: " + pokemon.getSdefense());

        sattack = (TextView) findViewById(R.id.sattack);
        sattack.setText("Special Attack: " + pokemon.getSattack());

        defense = (TextView) findViewById(R.id.defense);
        defense.setText("Defense: " + pokemon.getDefense());

        attack = (TextView) findViewById(R.id.attack);
        attack.setText("Attack: " + pokemon.getAttack());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh:
                Toast.makeText(this, "Refreshing", Toast.LENGTH_LONG)
                        .show();
                break;
            case R.id.action_settings:
                Toast.makeText(this, "You selected Settings", Toast.LENGTH_LONG)
                        .show();
                break;
            default:
                break;
        }

        return true;
    }

    public static ArrayList<String> convertStringToList(String string) {
        ArrayList<String> list = new ArrayList<String>(Arrays.asList(string.split(listSeparator)));
        return list;
    }

    protected void updateDisplay() {
        mAdapter.notifyDataSetChanged();
    }


}
