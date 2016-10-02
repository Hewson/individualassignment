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
 */

public class PokemonAdapter extends RecyclerView.Adapter<PokemonAdapter.ViewHolder> {
    private List<Pokemon> myPokemonList = new ArrayList<>();
    private LayoutInflater inflater;
    private VolleySingleton volleySingleton;
    private ImageLoader imageLoader;
    private Context context;
    private ClickListener clickListener;
    private DBHelper dbHelper;
    private PokemonAccess pokemonAccess;

    public PokemonAdapter(Context context, List<Pokemon> dataset) {
        dbHelper = new DBHelper(context);
        pokemonAccess = new PokemonAccess(dbHelper);
        inflater = LayoutInflater.from(context);
        volleySingleton = VolleySingleton.getInstance();
        imageLoader = volleySingleton.getImageLoader();
        myPokemonList = pokemonAccess.getAll();
        this.context = context;
    }

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

        @Override
        public void onClick(View view) {
            clickListener.itemClicked(view, getAdapterPosition());
            int position = getAdapterPosition();
            Log.d(TAG, "onClick: " + position);
        }
    }

    @Override
    public PokemonAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    public void setPokemon(List<Pokemon> pokemon) {
        this.myPokemonList = pokemon;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Pokemon pokemon = myPokemonList.get(position);
        //myPokemonList.get(position);
        holder.id.setText(pokemon.getId());
        holder.name.setText(pokemon.getName());
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
        if (pokemon.getType2() != null) {
            holder.type2.setText(pokemon.getType2());
            switch (pokemon.getType2()) {
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
            holder.type2.setVisibility(View.VISIBLE);
        }
        else {
            holder.type2.setVisibility(View.GONE);
        }
        holder.thumbnail.setImageBitmap(pokemon.getIcon());
    }

    @Override
    public int getItemCount() {
        return myPokemonList.size();
    }

    public interface ClickListener {
        public void itemClicked(View v, int position);
    }

    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }


}
