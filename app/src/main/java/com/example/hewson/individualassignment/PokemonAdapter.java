package com.example.hewson.individualassignment;

import android.content.Context;
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

import java.util.List;

/**
 * Created by Hewson Tran on 24/09/2016.
 */

public class PokemonAdapter extends RecyclerView.Adapter<PokemonAdapter.ViewHolder> {
    private List<Pokemon> myPokemonList;
    private LayoutInflater inflater;
    private VolleySingleton volleySingleton;
    private ImageLoader imageLoader;

    public PokemonAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        volleySingleton = VolleySingleton.getmInstance();
        imageLoader = volleySingleton.getmImageLoader();
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
        public TextView name, type;
        public ImageView thumbnail;

        public ViewHolder(View v) {
            super(v);
            v.setOnClickListener(this);
            name = (TextView) v.findViewById(R.id.name);
            type = (TextView) v.findViewById(R.id.type);
            thumbnail = (ImageView) v.findViewById(R.id.thumbnail);
        }

        @Override
        public void onClick(View view) {
            Toast.makeText(view.getContext(), "You clicked that", Toast.LENGTH_SHORT).show();
        }
    }

    public void add(int position, Pokemon pokemon) {
        myPokemonList.add(position, pokemon);
        notifyItemInserted(position);
    }

    public void remove(String item) {
        int position = myPokemonList.indexOf(item);
        myPokemonList.remove(position);
        notifyItemRemoved(position);
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public PokemonAdapter(List<Pokemon> myDataset) {
        myPokemonList = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public PokemonAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                        int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        Pokemon pokemon = myPokemonList.get(position);
        holder.name.setText(pokemon.getName());
        holder.type.setText(pokemon.getType());

        /*
        NEED TO
        CHECK THIS
        */

        String urlThumb = pokemon.getIconUrl();
        loadThumbnails(urlThumb, holder);
    }

    private void loadThumbnails (String urlThumbnail, final ViewHolder holder){
        imageLoader.get(urlThumbnail, new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                holder.thumbnail.setImageBitmap(response.getBitmap());
            }

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return myPokemonList.size();
    }

}
