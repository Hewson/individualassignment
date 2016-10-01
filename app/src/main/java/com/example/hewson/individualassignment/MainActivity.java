package com.example.hewson.individualassignment;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.hewson.individualassignment.database.DBHelper;
import com.example.hewson.individualassignment.database.PokemonAccess;
import com.example.hewson.individualassignment.model.Pokemon;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements PokemonAdapter.ClickListener {
    private RecyclerView mRecyclerView;
    private List<Pokemon> pokemonList;
    private RecyclerView.LayoutManager mLayoutManager;
    private PokemonAdapter mAdapter;
    private DBHelper dbHelper;
    private PokemonAccess pokemonAccess;
    private VolleySingleton volleySingleton;
    private ImageLoader imageLoader;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new DBHelper(this);
        pokemonAccess = new PokemonAccess(dbHelper);
        pokemonList = new ArrayList<>();
        volleySingleton = VolleySingleton.getInstance();
        imageLoader = volleySingleton.getImageLoader();
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler1);
        mAdapter = new PokemonAdapter(this, pokemonAccess.getAll());
        mAdapter.setClickListener(this);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new PokemonDivider(this, LinearLayoutManager.VERTICAL));
        mRecyclerView.setAdapter(mAdapter);
        if (isOnline()) {
            requestPokemonName("https://pokeapi.co/api/v2/pokemon/?limit=20?offset=5");
        }
    }

    protected void updateDisplay() {
        mAdapter.notifyDataSetChanged();
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
                        myPokemon.setId(i + 1);
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
                    JSONObject mySprite = response.getJSONObject("sprites");
                    String iconUrl = mySprite.getString("front_default");
                    pokemon.setIconUrl(iconUrl);
                    JSONArray myTypes = response.getJSONArray("types");
                    ArrayList<String> types = new ArrayList<>();
                    for (int i = myTypes.length() - 1; i >= 0; i--) {
                        JSONObject slot = myTypes.getJSONObject(i);
                        JSONObject type = slot.getJSONObject("type");
                        String name = capitaliser(type.getString("name"));
                        types.add(name);
                    }
                    pokemon.setType(types);

                    imageLoader.get(iconUrl, new ImageLoader.ImageListener() {
                        @Override
                        public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                            pokemon.setIcon(response.getBitmap());
                        }

                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    });
                    pokemonList.add(pokemon);
                    mAdapter.setPokemon(pokemonList);
                    pokemonAccess.insertPokemon(pokemon);
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

    public void viewSpecificPokemon(View view) {
        Intent intent = new Intent(this, SpecificPokemon.class);
        startActivity(intent);
    }

    @Override
    public void itemClicked(View v, int position) {
        startActivity(new Intent(this, SpecificPokemon.class));
    }
}
