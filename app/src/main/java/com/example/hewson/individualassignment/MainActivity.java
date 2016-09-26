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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.hewson.individualassignment.database.Pokemon;


import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity{
    private List<Pokemon> myPokemonList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler1);
        mAdapter = new PokemonAdapter(myPokemonList);
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
        for (int i = 1; i < 20; i++) {
            requestPokemon("http://pokeapi.co/api/v2/pokemon/" + i + "/");
        }
//        updateDisplay();
//        if (isOnline()) {
//            requestPokemon("http://pokeapi.co/api/v2/pokemon/1/");
//        }
    }

    protected void updateDisplay() {
        mAdapter.notifyDataSetChanged();
    }

    protected void requestPokemon(String uri) {
//        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET,
//                uri, JSONObject,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        try {
//                            myPokemonList = PokemonParser.parseFeed(response.getJSONArray("name"));
//                            updateDisplay();
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError e) {
//                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                });
        StringRequest stringRequest = new StringRequest(Request.Method.GET, uri,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            Pokemon pokemon = new Pokemon();
                            pokemon.setName(object.getString("name"));
                            pokemon.setType("something");
                            Log.d("DEEBUG", "onResponse: " + object.getString("name"));
                            myPokemonList.add(pokemon);
                            updateDisplay();
//                          myPokemonList = PokemonParser.parseFeed(response);
//                            updateDisplay();
                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.d("DEEBUG", "onResponse: " + e.getMessage());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {
                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);
        queue.start();
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
}
