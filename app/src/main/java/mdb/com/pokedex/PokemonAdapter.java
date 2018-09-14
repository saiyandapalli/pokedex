package mdb.com.pokedex;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.module.AppGlideModule;

import org.w3c.dom.Text;

import java.util.List;

public class PokemonAdapter extends RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder> {
    private Context context;
    private List<Pokemon> data;

    PokemonAdapter(Context context, List<Pokemon> pokemonList) {
        this.data = pokemonList;
        this.context = context;
    }

    public void setData(List<Pokemon> pokemonList) {
        this.data = pokemonList;
        notifyDataSetChanged();
    }


    @Override
    public PokemonViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        //TODO: Make this a different layout depending on whether or not we are in List View or Card View
        View v = LayoutInflater.from(context).inflate(R.layout.rowview_pokemon, viewGroup, false);
        return new PokemonViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PokemonAdapter.PokemonViewHolder pokemonViewHolder, int i) {
        Pokemon p = data.get(i);

        pokemonViewHolder.nameText.setText(p.getName());
        pokemonViewHolder.speciesText.setText(p.getSpecies());
        pokemonViewHolder.typeText.setText(p.getTypes().toString());

        //As a placeholder while the image loads
        pokemonViewHolder.pokeImageView.setImageResource(android.R.mipmap.sym_def_app_icon);
        Log.d("Loading Image", "Loading Image with URL: "+p.getImageUrl());
        Glide.with(context).load(p.getImageUrl()).into(pokemonViewHolder.pokeImageView);
    }

    public int getItemCount() {
        return data.size();
    }

    class PokemonViewHolder extends RecyclerView.ViewHolder {
        private TextView nameText;
        private TextView speciesText;
        private TextView typeText;

        private ImageView pokeImageView;

        PokemonViewHolder(View itemView) {
            super(itemView);
            nameText = itemView.findViewById(R.id.nameText);
            speciesText = itemView.findViewById(R.id.speciesText);
            typeText = itemView.findViewById(R.id.typesText);
            pokeImageView = itemView.findViewById(R.id.pokeImageView);
        }
    }


}
