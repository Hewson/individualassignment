package com.example.hewson.individualassignment.controller;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.example.hewson.individualassignment.R;
import com.example.hewson.individualassignment.database.DBHelper;
import com.example.hewson.individualassignment.database.PokemonAccess;
import com.example.hewson.individualassignment.model.Pokemon;
import com.example.hewson.individualassignment.network.VolleySingleton;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by Hewson Tran on 24/09/2016.
 * This class is the main adapter for the recyclerview in the main activity
 * It is responsible for handling the data that goes from the database to the recyclerview
 */

public class PokemonAdapter extends RecyclerView.Adapter<PokemonAdapter.ViewHolder> {
    //declaration of variables
    private List<Pokemon> myPokemonList = new ArrayList<>();
    private LayoutInflater inflater;
    private VolleySingleton volleySingleton;
    private ImageLoader imageLoader;
    private Context context;
    private ClickListener clickListener;
    private DBHelper dbHelper;
    private PokemonAccess pokemonAccess;

    //constructor for the adapter that instantiates the database, layoutmanager, imageloader, context and Pokemon list
    public PokemonAdapter(Context context, List<Pokemon> dataset) {
        dbHelper = new DBHelper(context);
        pokemonAccess = new PokemonAccess(dbHelper);
        inflater = LayoutInflater.from(context);
        volleySingleton = VolleySingleton.getInstance();
        imageLoader = volleySingleton.getImageLoader();
        myPokemonList = pokemonAccess.getAll();
        this.context = context;
    }

    //viewholder declaration by declaring the items in the viewholder
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView name, type, id, type1, type2;
        public ImageView thumbnail;

        public ViewHolder(View v) {
            super(v);
            v.setOnClickListener(this);
            id = (TextView) v.findViewById(R.id.id);
            name = (TextView) v.findViewById(R.id.name);
            type1 = (TextView) v.findViewById(R.id.type1);
            type2 = (TextView) v.findViewById(R.id.type2);
            thumbnail = (ImageView) v.findViewById(R.id.thumbnail);
        }

        //onClick override method that tells the console which viewholder in the recyclerview was clicked
        @Override
        public void onClick(View view) {
            clickListener.itemClicked(view, getAdapterPosition());
            int position = getAdapterPosition();
            Log.d(TAG, "onClick: " + position);
        }
    }

    //creates the viewholder and inflates it onto the recyclerview
    @Override
    public PokemonAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    //sets the pokemonlist with the given list and updates the listener that the data has been changed
    public void setPokemon(List<Pokemon> pokemon) {
        this.myPokemonList = pokemon;
        notifyDataSetChanged();
    }

    //method that binds the data to the viewholder from the database
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Pokemon pokemon = myPokemonList.get(position);
        holder.id.setText(pokemon.getId());
        holder.name.setText(pokemon.getName());

        //switch that sets the background colour depending on the type of the pokemon
        switch (pokemon.getType1()) {
            case "Normal":
                holder.type1.setBackgroundColor(ContextCompat.getColor(context, R.color.normal));
                break;
            case "Fire":
                holder.type1.setBackgroundColor(ContextCompat.getColor(context, R.color.fire));
                break;
            case "Water":
                holder.type1.setBackgroundColor(ContextCompat.getColor(context, R.color.water));
                break;
            case "Electric":
                holder.type1.setBackgroundColor(ContextCompat.getColor(context, R.color.electric));
                break;
            case "Grass":
                holder.type1.setBackgroundColor(ContextCompat.getColor(context, R.color.grass));
                break;
            case "Ice":
                holder.type1.setBackgroundColor(ContextCompat.getColor(context, R.color.normal));
                break;
            case "Fighting":
                holder.type1.setBackgroundColor(ContextCompat.getColor(context, R.color.fighting));
                break;
            case "Poison":
                holder.type1.setBackgroundColor(ContextCompat.getColor(context, R.color.poison));
                break;
            case "Ground":
                holder.type1.setBackgroundColor(ContextCompat.getColor(context, R.color.ground));
                break;
            case "Flying":
                holder.type1.setBackgroundColor(ContextCompat.getColor(context, R.color.flying));
                break;
            case "Psychic":
                holder.type1.setBackgroundColor(ContextCompat.getColor(context, R.color.psychic));
                break;
            case "Bug":
                holder.type1.setBackgroundColor(ContextCompat.getColor(context, R.color.bug));
                break;
            case "Rock":
                holder.type1.setBackgroundColor(ContextCompat.getColor(context, R.color.rock));
                break;
            case "Ghost":
                holder.type1.setBackgroundColor(ContextCompat.getColor(context, R.color.ghost));
                break;
            case "Dragon":
                holder.type1.setBackgroundColor(ContextCompat.getColor(context, R.color.dragon));
                break;
            case "Dark":
                holder.type1.setBackgroundColor(ContextCompat.getColor(context, R.color.dark));
                break;
            case "Steel":
                holder.type1.setBackgroundColor(ContextCompat.getColor(context, R.color.steel));
                break;
            case "Fairy":
                holder.type1.setBackgroundColor(ContextCompat.getColor(context, R.color.fairy));
                break;
            default:
                break;
        }
        holder.type1.setText(pokemon.getType1());

        //if a second type exists create it and give it a colour
        if (pokemon.getType2() != null) {
            holder.type2.setText(pokemon.getType2());

            //switch that sets the background colour depending on the type of the pokemon
            switch (pokemon.getType2()) {
                case "Normal":
                    holder.type2.setBackgroundColor(ContextCompat.getColor(context, R.color.normal));
                    break;
                case "Fire":
                    holder.type2.setBackgroundColor(ContextCompat.getColor(context, R.color.fire));
                    break;
                case "Water":
                    holder.type2.setBackgroundColor(ContextCompat.getColor(context, R.color.water));
                    break;
                case "Electric":
                    holder.type2.setBackgroundColor(ContextCompat.getColor(context, R.color.electric));
                    break;
                case "Grass":
                    holder.type2.setBackgroundColor(ContextCompat.getColor(context, R.color.grass));
                    break;
                case "Ice":
                    holder.type2.setBackgroundColor(ContextCompat.getColor(context, R.color.normal));
                    break;
                case "Fighting":
                    holder.type2.setBackgroundColor(ContextCompat.getColor(context, R.color.fighting));
                    break;
                case "Poison":
                    holder.type2.setBackgroundColor(ContextCompat.getColor(context, R.color.poison));
                    break;
                case "Ground":
                    holder.type2.setBackgroundColor(ContextCompat.getColor(context, R.color.ground));
                    break;
                case "Flying":
                    holder.type2.setBackgroundColor(ContextCompat.getColor(context, R.color.flying));
                    break;
                case "Psychic":
                    holder.type2.setBackgroundColor(ContextCompat.getColor(context, R.color.psychic));
                    break;
                case "Bug":
                    holder.type2.setBackgroundColor(ContextCompat.getColor(context, R.color.bug));
                    break;
                case "Rock":
                    holder.type2.setBackgroundColor(ContextCompat.getColor(context, R.color.rock));
                    break;
                case "Ghost":
                    holder.type2.setBackgroundColor(ContextCompat.getColor(context, R.color.ghost));
                    break;
                case "Dragon":
                    holder.type2.setBackgroundColor(ContextCompat.getColor(context, R.color.dragon));
                    break;
                case "Dark":
                    holder.type2.setBackgroundColor(ContextCompat.getColor(context, R.color.dark));
                    break;
                case "Steel":
                    holder.type2.setBackgroundColor(ContextCompat.getColor(context, R.color.steel));
                    break;
                case "Fairy":
                    holder.type2.setBackgroundColor(ContextCompat.getColor(context, R.color.fairy));
                    break;
                default:
                    break;
            }
            holder.type2.setVisibility(View.VISIBLE);
        } else {
            holder.type2.setVisibility(View.GONE);
        }
        //sets the icon for the pokemon taken from the object as a bitmap
        holder.thumbnail.setImageBitmap(pokemon.getIcon());
    }

    //returns number of viewholders
    @Override
    public int getItemCount() {
        return myPokemonList.size();
    }

    //interface that is used to declare an itemclicked method
    public interface ClickListener {
        public void itemClicked(View v, int position);
    }

    //method that sets a clicklistener object
    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }


}
