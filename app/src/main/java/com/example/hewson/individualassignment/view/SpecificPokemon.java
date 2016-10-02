package com.example.hewson.individualassignment.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hewson.individualassignment.R;
import com.example.hewson.individualassignment.application.MainActivity;
import com.example.hewson.individualassignment.database.DBHelper;
import com.example.hewson.individualassignment.database.PokemonAccess;
import com.example.hewson.individualassignment.model.Pokemon;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SpecificPokemon extends AppCompatActivity {
    private static final String TAG = SpecificPokemon.class.getName();
    private static String listSeparator = "__,__";

    private DBHelper dbHelper;
    private PokemonAccess pokemonAccess;
    private TextView name, idNumber, type1, type2, weight, height, listMoves, learnType, levelLearned;
    private ImageView icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific_pokemon);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        dbHelper = new DBHelper(this);
        pokemonAccess = new PokemonAccess(dbHelper);

        Intent intent = getIntent();
        int id = intent.getIntExtra("id", 1);
        System.out.println("THE INTENT WAS " + id);
        Pokemon pokemon = pokemonAccess.getAll().get(id);
        Log.d(TAG, "pokemon is" + pokemon);

        name = (TextView) findViewById(R.id.name);
        name.setText(pokemon.getName());

        idNumber = (TextView) findViewById(R.id.id);
        idNumber.setText(pokemon.getId());

        type1 = (TextView) findViewById(R.id.type1);
        type1.setText("Type: " + pokemon.getType1());
        type2 = (TextView) findViewById(R.id.type2);
        type2.setVisibility(View.GONE);
        if (pokemon.getType2() != null){
            type2.setText("Type: " + pokemon.getType2());
            type2.setVisibility(View.VISIBLE);
        }

        icon = (ImageView) findViewById(R.id.icon);
        icon.setImageBitmap(pokemon.getIcon());

        weight = (TextView) findViewById(R.id.weight);
        weight.setText("Weight: " + pokemon.getWeight());

        height = (TextView) findViewById(R.id.height);
        height.setText("Height: " + pokemon.getHeight());

        listMoves = (TextView) findViewById(R.id.listMoves);
        ArrayList<String> arrayMoves = convertStringToList(pokemon.getListMoves());
        listMoves.setText("Moves: " + arrayMoves);

        learnType = (TextView) findViewById(R.id.learnType);
        ArrayList<String> arrayLearnType = convertStringToList(pokemon.getLearnType());
        learnType.setText("Method of learning: " + arrayLearnType);

        levelLearned = (TextView) findViewById(R.id.levelLearned);
        ArrayList<String> arrayLevelLearned = convertStringToList(pokemon.getLevelLearned());
        levelLearned.setText("Method of learning: " + arrayLevelLearned);
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
}
