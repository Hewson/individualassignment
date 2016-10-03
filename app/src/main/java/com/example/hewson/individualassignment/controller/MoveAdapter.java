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
import static com.example.hewson.individualassignment.view.SpecificPokemon.convertStringToList;

/**
 * Created by Hewson Tran on 3/10/2016.
 * This is the adapter for the recycler view in the detailed pokemon view
 */

public class MoveAdapter extends RecyclerView.Adapter<MoveAdapter.ViewHolder> {
    private List<Pokemon> myPokemonList = new ArrayList<>();
    private List<String> moveList;
    List<String> levelList;
    List<String> tutorList;
    private LayoutInflater inflater;
    private VolleySingleton volleySingleton;
    private Context context;
    private DBHelper dbHelper;
    private PokemonAccess pokemonAccess;
    int id;

    //constructor for the adapter
    public MoveAdapter(Context context, List<Pokemon> dataset, int id) {
        dbHelper = new DBHelper(context);
        pokemonAccess = new PokemonAccess(dbHelper);
        inflater = LayoutInflater.from(context);
        volleySingleton = VolleySingleton.getInstance();
        this.context = context;
        this.myPokemonList = pokemonAccess.getAll();
        this.id = id;
    }

    //creates the view holder and instantiates the items inside it
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView move, tutor, level;

        public ViewHolder(View v) {
            super(v);

            //sets the click listener for each viewholder in the recycler view
            v.setOnClickListener(this);
            move = (TextView) v.findViewById(R.id.move);
            level = (TextView) v.findViewById(R.id.level);
            tutor = (TextView) v.findViewById(R.id.tutor);
        }

        @Override
        public void onClick(View view) {
        }
    }

    //method that is called when a viewholder is created and inflates it with the moves XML file
    @Override
    public MoveAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.moves, parent, false);
        MoveAdapter.ViewHolder vh = new MoveAdapter.ViewHolder(v);
        return vh;
    }

    //method that sets the moves in the activity and updates the adapter on updated data
    public void setMoves(List<String> move) {
        this.moveList = move;
        notifyDataSetChanged();
    }

    /*
    * called when an item in the adapter is binded to the recyclerview
    * converts the long strings from the database separated by a __,__ into arraylists
    */
    @Override
    public void onBindViewHolder(final MoveAdapter.ViewHolder holder, int position) {
        Pokemon pokemon = myPokemonList.get(id);
        moveList = convertStringToList(pokemon.getListMoves());
        levelList = convertStringToList(pokemon.getLevelLearned());
        tutorList = convertStringToList(pokemon.getLearnType());

        //sets the move, level and tutor from the arraylists
        holder.move.setText(moveList.get(position));
        Log.d(TAG, "onBindViewHolder: " + moveList.get(position));
        holder.level.setText(levelList.get(position));
        Log.d(TAG, "onBindViewHolder: " + levelList.get(position));
        holder.tutor.setText(tutorList.get(position));
        Log.d(TAG, "onBindViewHolder: " + tutorList.get(position));
    }

    //returns the number of viewholders in the recyclerview
    @Override
    public int getItemCount() {
        return moveList.size();
    }
}
