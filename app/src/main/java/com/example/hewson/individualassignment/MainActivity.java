package com.example.hewson.individualassignment;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.hewson.individualassignment.model.Pokemon;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Pokemon> myPokemonList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    //private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private PokemonAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler1);
        //mAdapter = new PokemonAdapter(this);
        mAdapter = new PokemonAdapter(this, myPokemonList);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        // specify an adapter (see also next example)
        mRecyclerView.addItemDecoration(new PokemonDivider(this, LinearLayoutManager.VERTICAL));
        mRecyclerView.setAdapter(mAdapter);


        if (isOnline()) {
            requestPokemonName("https://pokeapi.co/api/v2/pokemon/?limit=5");
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
                    String pokeUrl = "";
                    for (int i = 0; i < myArray.length(); i++) {
                        Pokemon myPokemon = new Pokemon();
                        JSONObject pokemon = (JSONObject) myArray.get(i);
                        String pokeName = capitaliser(pokemon.getString("name"));
                        pokeUrl = pokemon.getString("url");
                        myPokemon.setName(pokeName);
                        myPokemon.setUrl(pokeUrl);
                        myPokemon.setId(i + 1);
                        myPokemonList.add(requestPokemonUrl(pokeUrl, myPokemon));
                        updateDisplay();
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
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("can't request name", "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue queue = VolleySingleton.getmInstance().getmRequestQueue();
        queue.add(jsonObjReq);
    }

    public Pokemon requestPokemonUrl(String pokeUrl, final Pokemon pokemon) {
        JsonObjectRequest newRequest = new JsonObjectRequest(Request.Method.GET, pokeUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    System.out.println("did u get here");
                    JSONObject mySprite = response.getJSONObject("sprites");
                    String iconUrl = mySprite.getString("front_default");
                    Log.d("something", "onResponse: " + iconUrl);
                    pokemon.setIconUrl(iconUrl);
                    System.out.println("the icon url is being set " + iconUrl);

                } catch (JSONException e) {
                    System.out.println(e.getMessage());
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),
                            "Error: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("could not load sprites", "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

        );
        RequestQueue queue = VolleySingleton.getmInstance().getmRequestQueue();
        queue.add(newRequest);
        return pokemon;
    }

//    protected void requestPokemon(String url) {
//        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//                try {
//                    JSONObject mySprite = response.getJSONObject("sprites");
//                    String iconUrl = mySprite.getString("front_default");
//                    Log.d("something", "onResponse: " + iconUrl);
//                    Pokemon myPokemon = new Pokemon();
//                    myPokemon.setIconUrl(iconUrl);
//                    myPokemonList.add(myPokemon);
//                    System.out.println(myPokemonList.toString());
//                    updateDisplay();
//                } catch (JSONException e) {
//                    System.out.println(e.getMessage());
//                    e.printStackTrace();
//                    Toast.makeText(getApplicationContext(),
//                            "Error: " + e.getMessage(),
//                            Toast.LENGTH_LONG).show();
//                }
//            }
//        }, new Response.ErrorListener() {
//
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                VolleyLog.d("something", "Error: " + error.getMessage());
//                Toast.makeText(getApplicationContext(),
//                        error.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        }
//
//        );
//        RequestQueue queue = VolleySingleton.getmInstance().getmRequestQueue();
//        queue.add(jsonObjReq);
//    }

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

}
