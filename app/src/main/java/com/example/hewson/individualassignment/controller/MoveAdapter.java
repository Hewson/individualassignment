package com.example.hewson.individualassignment.controller;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hewson.individualassignment.R;
import com.example.hewson.individualassignment.database.DBHelper;
import com.example.hewson.individualassignment.database.PokemonAccess;
import com.example.hewson.individualassignment.model.Pokemon;
import com.example.hewson.individualassignment.network.VolleySingleton;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by Hewson Tran on 3/10/2016.
 */

public class MoveAdapter extends RecyclerView.Adapter<MoveAdapter.ViewHolder> {
    private List<Pokemon> myPokemonList = new ArrayList<>();
    private List<String> moveList = new ArrayList<>();
    private LayoutInflater inflater;
    private VolleySingleton volleySingleton;
    private Context context;
    private DBHelper dbHelper;
    private PokemonAccess pokemonAccess;

    public MoveAdapter(Context context, List<Pokemon> dataset) {
        dbHelper = new DBHelper(context);
        pokemonAccess = new PokemonAccess(dbHelper);
        inflater = LayoutInflater.from(context);
        volleySingleton = VolleySingleton.getInstance();
        this.context = context;
        this.myPokemonList = pokemonAccess.getAll();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView move, tutor, level;

        public ViewHolder(View v) {
            super(v);
            v.setOnClickListener(this);
            move = (TextView) v.findViewById(R.id.move);
            level = (TextView) v.findViewById(R.id.level);
            tutor = (TextView) v.findViewById(R.id.tutor);
        }

        @Override
        public void onClick(View view) {
        }
    }

    @Override
    public MoveAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.moves, parent, false);
        MoveAdapter.ViewHolder vh = new MoveAdapter.ViewHolder(v);
        return vh;
    }

    public void setMoves(List<String> move) {
        this.moveList = move;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(final MoveAdapter.ViewHolder holder, int position) {
        Pokemon pokemon = myPokemonList.get(position);
        Log.d(TAG, "onBindViewHolder: " + pokemon);
        holder.move.setText("nothing");
        holder.level.setText("something");
        holder.tutor.setText("tutor");
        //set stuff
        //holder.id.setText(pokemon.getId());
    }

    @Override
    public int getItemCount() {
        return moveList.size();
    }
}
