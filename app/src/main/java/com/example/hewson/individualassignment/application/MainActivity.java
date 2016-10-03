package com.example.hewson.individualassignment.application;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.hewson.individualassignment.controller.PokemonAdapter;
import com.example.hewson.individualassignment.R;
import com.example.hewson.individualassignment.database.DBHelper;
import com.example.hewson.individualassignment.database.PokemonAccess;
import com.example.hewson.individualassignment.model.Pokemon;
import com.example.hewson.individualassignment.network.VolleySingleton;
import com.example.hewson.individualassignment.view.PokemonDivider;
import com.example.hewson.individualassignment.view.SpecificPokemon;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements PokemonAdapter.ClickListener {
    private static final String TAG = MainActivity.class.getName();
    public static final String URL = "https://pokeapi.co/api/v2/pokemon/";
    public static final String ENDPOINT = "?limit=10";
    private static String listSeparator = "__,__";

    private List<Pokemon> pokemonList;

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private PokemonAdapter mAdapter;

    private DBHelper dbHelper;
    private PokemonAccess pokemonAccess;
    private VolleySingleton volleySingleton;
    private ImageLoader imageLoader;

    private Context context;
    private ProgressBar progressBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //toolbar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.mytoolbar);
        setSupportActionBar(myToolbar);

        //db
        dbHelper = new DBHelper(this);
        pokemonAccess = new PokemonAccess(dbHelper);

        //recycler view
        pokemonList = new ArrayList<>();
        volleySingleton = VolleySingleton.getInstance();
        imageLoader = volleySingleton.getImageLoader();
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler1);
        progressBar = (ProgressBar) findViewById(R.id.progress);
        mAdapter = new PokemonAdapter(this, pokemonList);
        mAdapter.setClickListener(this);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        //mRecyclerView.addItemDecoration(new PokemonDivider(this, LinearLayoutManager.VERTICAL));
        mRecyclerView.setAdapter(mAdapter);

        if (pokemonAccess.getAll().isEmpty() && isOnline()) {
            Log.d(TAG, "onCreate: " + "doing json request");
            Toast.makeText(this, "Please wait for the data to be downloaded...", Toast.LENGTH_SHORT).show();
            requestPokemonName(URL + ENDPOINT);
            Log.d(TAG, "all pokemon in db after json request: " + pokemonAccess.getAll().toString());
            updateDisplay();

        } else {
            Log.d(TAG, "onCreate: " + "loading from db");
            Toast.makeText(this, "Retrieving Pokedex from database...", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "all pokemon in db: " + pokemonAccess.getAll().toString());
            updateDisplay();
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // action with ID action_refresh was selected
            case R.id.action_refresh:
                Toast.makeText(this, "Refreshing! Please wait for the data to be downloaded...", Toast.LENGTH_LONG)
                        .show();
                int[] intArray = new int[pokemonAccess.getAll().size()];
                for (int i = 0; i < pokemonAccess.getAll().size(); i++) {
                    intArray[i] = i;
                }
                pokemonAccess.deleteAll();
                requestPokemonName(URL + ENDPOINT);
                updateDisplay();
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

    protected void requestPokemonName(String url) {
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray myArray = response.getJSONArray("results");
                    String pokeUrl;
                    for (int i = 0; i < myArray.length(); i++) {
                        Pokemon myPokemon = new Pokemon();
                        JSONObject pokemonObj = (JSONObject) myArray.get(i);
                        pokeUrl = pokemonObj.getString("url");
                        String pokeName = capitaliser(pokemonObj.getString("name"));
                        myPokemon.setName(pokeName);
                        myPokemon.setUrl(pokeUrl);
                        String id = "#" + String.format("%03d", i + 1);
                        myPokemon.setId(id);
                        requestPokemonUrl(pokeUrl, myPokemon);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),
                            "Error: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError e) {
                VolleyLog.d("can't request name", "Error: " + e.getMessage());
                e.printStackTrace();
            }
        });
        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(60000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance().getRequestQueue().add(jsonObjReq);
    }

    public void requestPokemonUrl(String pokeUrl, final Pokemon pokemon) {
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, pokeUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    progressBar.setVisibility(View.VISIBLE);

                    //parsing height and weight
                    String height = response.getString("height");
                    pokemon.setHeight(height);
                    String weight = response.getString("weight");
                    pokemon.setWeight(weight);

                    //parsing types
                    JSONArray myTypes = response.getJSONArray("types");
                    int counter = 0;
                    for (int i = myTypes.length() - 1; i >= 0; i--) {
                        counter++;
                        JSONObject slot = myTypes.getJSONObject(i);
                        JSONObject type = slot.getJSONObject("type");
                        String name = capitaliser(type.getString("name"));
                        if (counter == 1) {
                            pokemon.setType1(name);
                        } else if (counter == 2) {
                            pokemon.setType2(name);
                        } else {

                        }
                    }

                    //parsing abilities
                    JSONArray myAbilities = response.getJSONArray("abilities");
                    int counterAbilities = 0;
                    for (int i = 0; i < myAbilities.length(); i++) {
                        counterAbilities++;
                        JSONObject abilityNumber = myAbilities.getJSONObject(i);
                        JSONObject ability = abilityNumber.getJSONObject("ability");
                        String abilityName = capitaliser(ability.getString("name"));
                        if (counterAbilities == 1) {
                            pokemon.setAbility1(abilityName);
                        } else if (counterAbilities == 2) {
                            pokemon.setAbility2(abilityName);
                        } else if (counterAbilities == 3) {
                            pokemon.setAbility3(abilityName);
                        } else {
                        }
                    }

                    //parsing base stats
                    JSONArray myStats = response.getJSONArray("stats");
                    for (int i = 0; i < myStats.length(); i++) {
                        JSONObject statObject = myStats.getJSONObject(i);
                        JSONObject statType = statObject.getJSONObject("stat");
                        String statName = statType.getString("name");
                        String statLevel = statObject.getString("base_stat");
                        switch (statName) {
                            case "speed": pokemon.setSpeed(statLevel);
                                break;
                            case "special-defense": pokemon.setSdefense(statLevel);
                                break;
                            case "special-attack": pokemon.setSattack(statLevel);
                                break;
                            case "defense": pokemon.setDefense(statLevel);
                                break;
                            case "attack": pokemon.setAttack(statLevel);
                                break;
                            case "hp": pokemon.setHp(statLevel);
                                break;
                            default: break;
                        }


                    }

                    //parsing moves
                    JSONArray myMoves = response.getJSONArray("moves");
                    ArrayList<String> listMoves = new ArrayList<>();
                    ArrayList<String> listLearnType = new ArrayList<>();
                    ArrayList<String> listLevelLearned = new ArrayList<>();
                    for (int i = 0; i < myMoves.length(); i++) {
                        JSONObject moveNumber = myMoves.getJSONObject(i);
                        JSONArray version = moveNumber.getJSONArray("version_group_details");
                        JSONObject versionNumber = version.getJSONObject(0);
                        JSONObject learnMethod = versionNumber.getJSONObject("move_learn_method");
                        String learnType = capitaliser(learnMethod.getString("name"));
                        listLearnType.add(learnType);
                        String levelLearned = versionNumber.getString("level_learned_at");
                        listLevelLearned.add(levelLearned);
                        JSONObject moveObject = moveNumber.getJSONObject("move");
                        String move = capitaliser(moveObject.getString("name"));
                        listMoves.add(move);

                        pokemon.setListMoves(convertListToString(listMoves));
                        pokemon.setLearnType(convertListToString(listLearnType));
                        pokemon.setLevelLearned(convertListToString(listLevelLearned));
                    }

                    //parsing icons
                    JSONObject mySprite = response.getJSONObject("sprites");
                    String iconUrl = mySprite.getString("front_default");
                    pokemon.setIconUrl(iconUrl);
                    imageLoader.get(iconUrl, new ImageLoader.ImageListener() {
                        @Override
                        public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                            pokemon.setIcon(response.getBitmap());
                            if (pokemon.getIcon() != null) {
                                pokemonAccess.insertPokemon(pokemon);
                                Log.d(TAG, "inserting: "+ pokemon.toString());
                                progressBar.setVisibility(View.GONE);
                            }
                            mAdapter.setPokemon(pokemonAccess.getAll());
                            updateDisplay();
                        }

                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError e) {
                e.printStackTrace();
            }
        }
        );
        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(60000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance().getRequestQueue().add(jsonObjReq);
    }

    protected boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }

    private String capitaliser(String word) {
        String[] words = word.split(" ");
        StringBuilder sb = new StringBuilder();
        if (words[0].length() > 0) {
            sb.append(Character.toUpperCase(words[0].charAt(0)) + words[0].subSequence(1, words[0].length()).toString().toLowerCase());
            for (int i = 1; i < words.length; i++) {
                sb.append(" ");
                sb.append(Character.toUpperCase(words[i].charAt(0)) + words[i].subSequence(1, words[i].length()).toString().toLowerCase());
            }
        }
        return sb.toString();
    }

    @Override
    public void itemClicked(View v, int position) {
        Intent intent = new Intent(this, SpecificPokemon.class);
        intent.putExtra("id", position);
        startActivity(intent);
    }

    protected void updateDisplay() {
        mAdapter.notifyDataSetChanged();
    }

    public static String convertListToString(ArrayList<String> listOfStrings) {
        StringBuffer stringBuffer = new StringBuffer();
        for (String string : listOfStrings) {
            stringBuffer.append(string).append(listSeparator);
        }
        int lastIndex = stringBuffer.lastIndexOf(listSeparator);
        stringBuffer.delete(lastIndex, lastIndex + listSeparator.length() + 1);
        return stringBuffer.toString();
    }


}
