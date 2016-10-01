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

    public PokemonAdapter(Context context, List<Pokemon> dataset) {
        inflater = LayoutInflater.from(context);
        volleySingleton = VolleySingleton.getInstance();
        imageLoader = volleySingleton.getImageLoader();
        myPokemonList = dataset;
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

    public PokemonAdapter(List<Pokemon> myPokemonList) {
        this.myPokemonList = myPokemonList;
    }

    @Override
    public PokemonAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        Pokemon pokemon = myPokemonList.get(position);
        holder.id.setText(Integer.toString(pokemon.getId()));
        holder.name.setText(pokemon.getName());
        holder.type.setText(pokemon.printArrayList(pokemon.getType()));
        String urlThumb = pokemon.getIconUrl();
        loadThumbnails(urlThumb, pokemon);
        holder.thumbnail.setImageBitmap(pokemon.getIcon());
        /*
        NEED TO
        CHECK THIS
        */
        //String urlThumb = pokemon.getIconUrl();
        //loadThumbnails(urlThumb, holder);
        //loadThumbnails("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1.png", holder);
    }

//    private void loadThumbnails(String urlThumbnail, final ViewHolder holder) {
    private Pokemon loadThumbnails(String urlThumbnail, final Pokemon pokemon) {
        imageLoader.get(urlThumbnail, new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                //holder.thumbnail.setImageBitmap(response.getBitmap());
                pokemon.setIcon(response.getBitmap());
            }

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        return pokemon;
    }

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
