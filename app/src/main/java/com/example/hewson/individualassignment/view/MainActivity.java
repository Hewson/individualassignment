package com.example.hewson.individualassignment.view;

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
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.hewson.individualassignment.controller.PokemonAdapter;
import com.example.hewson.individualassignment.R;
import com.example.hewson.individualassignment.database.DBHelper;
import com.example.hewson.individualassignment.database.PokemonAccess;
import com.example.hewson.individualassignment.model.Pokemon;
import com.example.hewson.individualassignment.network.VolleySingleton;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hewson Tran on 28/09/2016.
 * <p>
 * This application utilises the pokeapi.co Pokemon API, recyclerview, cardview, SQLite databasing and volley.
 * <p>
 * This class is used as the main class which creates the main Pokedex activity and performs JSON requests for the data
 */

public class MainActivity extends AppCompatActivity implements PokemonAdapter.ClickListener {
    //declaring constants and variables
    private static final String TAG = MainActivity.class.getName();
    public static final String URL = "https://pokeapi.co/api/v2/pokemon/";
    public static final String LIMIT = "?limit=";
    public static final String ENDPOINT = "13";
    public static final String DEFAULT_ICON = "front_default";
    public static final String SHINY_ICON = "front_shiny";
    public static final String BACK_ICON = "back_default";
    private static String listSeparator = "__,__";

    //recycler view declaration
    private List<Pokemon> pokemonList;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private PokemonAdapter mAdapter;

    //db declaration
    private DBHelper dbHelper;
    private PokemonAccess pokemonAccess;
    private VolleySingleton volleySingleton;
    private ImageLoader imageLoader;

    //general declaration
    private Context context;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //toolbar setup
        Toolbar myToolbar = (Toolbar) findViewById(R.id.mytoolbar);
        setSupportActionBar(myToolbar);

        //db setup
        dbHelper = new DBHelper(this);
        pokemonAccess = new PokemonAccess(dbHelper);

        //recycler view setup
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
        mRecyclerView.setAdapter(mAdapter);

        //if database is empty and is online: make a JSON request
        if (pokemonAccess.getAll().isEmpty() && isOnline()) {
            Log.d(TAG, "onCreate: " + "doing json request");
            Toast.makeText(this, "Please wait for the data to be downloaded...", Toast.LENGTH_SHORT).show();
            requestPokemonName(URL + LIMIT + ENDPOINT, DEFAULT_ICON);
            updateDisplay();

        }
        //if database is not empty: load from database and set a progress bar
        else {
            Log.d(TAG, "onCreate: " + "loading from db");
            Toast.makeText(this, "Retrieving Pokedex from database...", Toast.LENGTH_SHORT).show();
            updateDisplay();
            progressBar.setVisibility(View.GONE);
        }
    }

    //inflate the options menu from the menu.XML
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    //a method which acts as a switch to decide on what happens when each menu item is selected
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh:
                //if refresh item selected: make a JSON request, add it to the database and update the adapter
                Toast.makeText(this, "Refreshing! Please wait for the data to be downloaded...", Toast.LENGTH_LONG).show();
                pokemonAccess.deleteAll();
                requestPokemonName(URL + LIMIT + ENDPOINT, DEFAULT_ICON);
                updateDisplay();
                break;

            case R.id.shiny_refresh:
                //if refresh item selected: make a JSON request, add it to the database and update the adapter
                Toast.makeText(this, "Refreshing! Please wait for the data to be downloaded...", Toast.LENGTH_LONG).show();
                pokemonAccess.deleteAll();
                requestPokemonName(URL + LIMIT + ENDPOINT, SHINY_ICON);
                updateDisplay();
                break;

            case R.id.back_refresh:
                //if refresh item selected: make a JSON request, add it to the database and update the adapter
                Toast.makeText(this, "Refreshing! Please wait for the data to be downloaded...", Toast.LENGTH_LONG).show();
                pokemonAccess.deleteAll();
                requestPokemonName(URL + LIMIT + ENDPOINT, BACK_ICON);
                updateDisplay();
                break;

            case R.id.default_refresh:
                //if refresh item selected: make a JSON request, add it to the database and update the adapter
                Toast.makeText(this, "Refreshing! Please wait for the data to be downloaded...", Toast.LENGTH_LONG).show();
                pokemonAccess.deleteAll();
                requestPokemonName(URL + LIMIT + ENDPOINT, DEFAULT_ICON);
                updateDisplay();
                break;

            default:
                break;
        }
        return true;
    }

    //JSON volley request which looks for the list of pokemon and includes a variable called iconType which declares which type of bitmap will be selected from the pokeAPI database
    protected void requestPokemonName(String url, final String iconType) {
        //standard JSON request
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray myArray = response.getJSONArray("results");
                    String pokeUrl;

                    //parsing the JSON response
                    for (int i = 0; i < myArray.length(); i++) {
                        Pokemon pokemon = new Pokemon();
                        JSONObject pokemonObj = (JSONObject) myArray.get(i);
                        pokeUrl = pokemonObj.getString("url");
                        String pokeName = capitaliser(pokemonObj.getString("name"));

                        //set values for the Pokemon
                        pokemon.setName(pokeName);
                        pokemon.setUrl(pokeUrl);

                        //formats the string to look like #001 with leading 0s
                        String id = "#" + String.format("%03d", i + 1);

                        //set values for the Pokemon
                        pokemon.setId(id);

                        //with the specific URL given from the response, make another JSON request for more details
                        requestPokemonUrl(pokeUrl, pokemon, iconType);
                    }
                } catch (JSONException e) {

                    //print to console and to a toast that there was an eerror
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),
                            "Error: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError e) {
                //print error if response doesn't come back or has an error
                e.printStackTrace();
            }
        });

        //changes the timeout for the response to 60 seconds (for slow connections)
        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(60000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        //add the request to the application requestQueue
        VolleySingleton.getInstance().getRequestQueue().add(jsonObjReq);
    }

    //method makes another JSON request for specific pokemon details
    public void requestPokemonUrl(String pokeUrl, final Pokemon pokemon, final String iconType) {
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, pokeUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    progressBar.setVisibility(View.VISIBLE);

                    //parsing and setting height and weight
                    String height = response.getString("height");
                    pokemon.setHeight(height);
                    String weight = response.getString("weight");
                    pokemon.setWeight(weight);

                    //parsing and setting types
                    JSONArray myTypes = response.getJSONArray("types");
                    int counter = 0;
                    for (int i = myTypes.length() - 1; i >= 0; i--) {
                        counter++;
                        JSONObject slot = myTypes.getJSONObject(i);
                        JSONObject type = slot.getJSONObject("type");
                        String name = capitaliser(type.getString("name"));

                        //checks if there's a second type and set it
                        if (counter == 1) {
                            pokemon.setType1(name);
                        } else if (counter == 2) {
                            pokemon.setType2(name);
                        } else {

                        }
                    }

                    //parsing and setting abilities
                    JSONArray myAbilities = response.getJSONArray("abilities");
                    int counterAbilities = 0;
                    for (int i = 0; i < myAbilities.length(); i++) {
                        counterAbilities++;
                        JSONObject abilityNumber = myAbilities.getJSONObject(i);
                        JSONObject ability = abilityNumber.getJSONObject("ability");
                        String abilityName = capitaliser(ability.getString("name"));

                        //checks how many abilities there are and sets the correct variable
                        if (counterAbilities == 1) {
                            pokemon.setAbility1(abilityName);
                        } else if (counterAbilities == 2) {
                            pokemon.setAbility2(abilityName);
                        } else if (counterAbilities == 3) {
                            pokemon.setAbility3(abilityName);
                        } else {
                        }
                    }

                    //parsing and setting base stats
                    JSONArray myStats = response.getJSONArray("stats");
                    for (int i = 0; i < myStats.length(); i++) {
                        JSONObject statObject = myStats.getJSONObject(i);
                        JSONObject statType = statObject.getJSONObject("stat");
                        String statName = statType.getString("name");
                        String statLevel = statObject.getString("base_stat");

                        //sets the stat level based on its type
                        switch (statName) {
                            case "speed":
                                pokemon.setSpeed(statLevel);
                                break;
                            case "special-defense":
                                pokemon.setSdefense(statLevel);
                                break;
                            case "special-attack":
                                pokemon.setSattack(statLevel);
                                break;
                            case "defense":
                                pokemon.setDefense(statLevel);
                                break;
                            case "attack":
                                pokemon.setAttack(statLevel);
                                break;
                            case "hp":
                                pokemon.setHp(statLevel);
                                break;
                            default:
                                break;
                        }


                    }

                    //parsing and setting moves
                    JSONArray myMoves = response.getJSONArray("moves");

                    //lists that are temporarily used to store the values which will then be converted to strings and inserted into the DB
                    ArrayList<String> listMoves = new ArrayList<>();
                    ArrayList<String> listLearnType = new ArrayList<>();
                    ArrayList<String> listLevelLearned = new ArrayList<>();
                    for (int i = 0; i < myMoves.length(); i++) {
                        JSONObject moveNumber = myMoves.getJSONObject(i);
                        JSONArray version = moveNumber.getJSONArray("version_group_details");
                        JSONObject versionNumber = version.getJSONObject(0);

                        //parse values and add to arraylists
                        JSONObject learnMethod = versionNumber.getJSONObject("move_learn_method");
                        String learnType = capitaliser(learnMethod.getString("name"));
                        listLearnType.add(learnType);
                        String levelLearned = versionNumber.getString("level_learned_at");
                        listLevelLearned.add(levelLearned);
                        JSONObject moveObject = moveNumber.getJSONObject("move");
                        String move = capitaliser(moveObject.getString("name"));
                        listMoves.add(move);

                        //convertListToString method converts the lists into strings and adds them into a variable which will be added to the DB
                        pokemon.setListMoves(convertListToString(listMoves));
                        pokemon.setLearnType(convertListToString(listLearnType));
                        pokemon.setLevelLearned(convertListToString(listLevelLearned));
                    }

                    //parsing and setting icons
                    JSONObject mySprite = response.getJSONObject("sprites");
                    String iconUrl = mySprite.getString(iconType);
                    pokemon.setIconUrl(iconUrl);

                    //request the image from the URL
                    imageLoader.get(iconUrl, new ImageLoader.ImageListener() {
                        @Override
                        public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                            pokemon.setIcon(response.getBitmap());

                            //if the icon isn't empty add the Pokemon to the database
                            if (pokemon.getIcon() != null) {
                                pokemonAccess.insertPokemon(pokemon);
                                progressBar.setVisibility(View.GONE);
                            }

                            //update the adapter and the display
                            mAdapter.setPokemon(pokemonAccess.getAll());
                            updateDisplay();
                        }

                        @Override
                        public void onErrorResponse(VolleyError e) {
                            e.printStackTrace();
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
        //changes the timeout for the response to 60 seconds (for slow connections)
        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(60000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        //add the request to the application requestQueue
        VolleySingleton.getInstance().getRequestQueue().add(jsonObjReq);
    }

    //checks if device has connectivity
    protected boolean isOnline() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }

    //method that capitalises the first character in a string
    private String capitaliser(String str) {
        String[] word = str.split(" ");
        StringBuilder stringBuilder = new StringBuilder();
        if (word[0].length() > 0) {
            stringBuilder.append(Character.toUpperCase(word[0].charAt(0)) + word[0].subSequence(1, word[0].length()).toString().toLowerCase());
            for (int i = 1; i < word.length; i++) {
                stringBuilder.append(" ");
                stringBuilder.append(Character.toUpperCase(word[i].charAt(0)) + word[i].subSequence(1, word[i].length()).toString().toLowerCase());
            }
        }
        return stringBuilder.toString();
    }

    //method that listens for clicks and creates an intent and passes it to the next activity
    @Override
    public void itemClicked(View v, int position) {
        Intent intent = new Intent(this, SpecificPokemon.class);
        //passes the ID of the pokemon clicked
        intent.putExtra("id", position);
        startActivity(intent);
    }

    //tells the adapter data has changed
    protected void updateDisplay() {
        mAdapter.notifyDataSetChanged();
    }

    //method that converts a list to strings
    public static String convertListToString(ArrayList<String> strings) {
        StringBuffer stringBuffer = new StringBuffer();
        for (String string : strings) {
            stringBuffer.append(string).append(listSeparator);
        }
        int last = stringBuffer.lastIndexOf(listSeparator);
        stringBuffer.delete(last, last + listSeparator.length() + 1);
        return stringBuffer.toString();
    }
}
