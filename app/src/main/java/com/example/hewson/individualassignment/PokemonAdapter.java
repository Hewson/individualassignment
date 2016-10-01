package com.example.hewson.individualassignment;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.example.hewson.individualassignment.database.DBHelper;
import com.example.hewson.individualassignment.database.PokemonAccess;
import com.example.hewson.individualassignment.model.Pokemon;

import java.util.ArrayList;
import java.util.List;

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
        public TextView name, type, id;
        public ImageView thumbnail;

        public ViewHolder(View v) {
            super(v);
            v.setOnClickListener(this);
            id = (TextView) v.findViewById(R.id.id);
            name = (TextView) v.findViewById(R.id.name);
            type = (TextView) v.findViewById(R.id.type);
            thumbnail = (ImageView) v.findViewById(R.id.thumbnail);
        }

        @Override
        public void onClick(View view) {
            clickListener.itemClicked(view, getAdapterPosition());
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
        holder.id.setText(Integer.toString(pokemon.getId()));
        holder.name.setText(pokemon.getName());
//        holder.type.setText(pokemon.printArrayList(pokemon.getType()));
        holder.thumbnail.setImageBitmap(pokemon.getIcon());
    }

//    private Pokemon loadThumbnails(String urlThumbnail, final Pokemon pokemon) {
//        imageLoader.get(urlThumbnail, new ImageLoader.ImageListener() {
//            @Override
//            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
//                pokemon.setIcon(response.getBitmap());
//            }
//
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        });
//        return pokemon;
//    }

    // Return the size of your dataset (invoked by the layout manager)
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
