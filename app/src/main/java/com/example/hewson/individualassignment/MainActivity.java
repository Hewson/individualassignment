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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.hewson.individualassignment.database.Pokemon;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
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
        if (isOnline()) {
            System.out.println("i'm here");
            testPokemon();
            System.out.println("i'm also here");
        }
    }

    protected void updateDisplay() {
        mAdapter.notifyDataSetChanged();
    }

    protected void testPokemon() {
        String url = "https://pokeapi.co/api/v2/pokemon/";
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d("something", response.toString());

                try {
                    // Parsing json object response
                    // response will be a json object
                    String name = response.getString("count");
                    JSONArray myArray = response.getJSONArray("results");
                    Log.d("something", "onResponse: " + myArray.toString());
                    for (int i = 0; i < myArray.length(); i++) {
                        JSONObject pokemon = (JSONObject) myArray.get(i);
                        String pokeName = pokemon.getString("name");
                    }

//                    JSONObject phone = response.getJSONObject("phone");
//                    String home = phone.getString("home");
//                    String mobile = phone.getString("mobile");

                        System.out.println("something");

                }
                    catch(JSONException e){
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(),
                                "Error: " + e.getMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                }
            }

            ,new Response.ErrorListener()

            {

                @Override
                public void onErrorResponse (VolleyError error){
                VolleyLog.d("something", "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
            }
            }

            );
//        JsonObjectRequest jsonRequest = new JsonObjectRequest
//                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        System.out.println(response);
//                        try {
//                            response = response.getJSONObject("args");
//                            String site = response.getString("site"),
//                                    network = response.getString("network");
//                            System.out.println("Site: "+site+"\nNetwork: "+network);
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }, new Response.ErrorListener() {
//
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        error.printStackTrace();
//                    }
//                });

            Volley.newRequestQueue(this).

            add(jsonObjReq);
        }

    protected void requestPokemon() {
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                "http://pokeapi.co/api/v2/pokemon/", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println("got here");
                Log.d("Something", response.toString());
                try {
                    Log.d("something", "onResponse: " + response.getJSONObject("results"));
//                            response = response.getJSONObject("count");
                    Log.d("DEEBUG", "onResponse: " + response);
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("DEEBUG", "onResponse: " + e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {
                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("something", "onErrorResponse: " + e.getMessage());
                System.out.println("didn't get here");
            }
        });
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jsonObjReq);
        System.out.println("did you get here?");
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
