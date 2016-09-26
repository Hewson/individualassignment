package com.example.hewson.individualassignment;

import android.app.ListActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.hewson.individualassignment.parsers.PokemonParser;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ListActivity {
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
        Pokemon pokemon = new Pokemon("Fairy Type", "Clefairy");
        for (int i = 0; i < 100; i++) {
            myPokemonList.add(pokemon);
        }
        updateDisplay();
        requestPokemon("http://services.hanselandpetal.com/feeds/flowers.json");
    }

    protected void updateDisplay() {
        mAdapter.notifyDataSetChanged();
    }

    protected void requestPokemon(String uri) {
        StringRequest request = new StringRequest(uri,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        myPokemonList = PokemonParser.parseFeed(response);
                        updateDisplay();
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError e) {
                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }
}
