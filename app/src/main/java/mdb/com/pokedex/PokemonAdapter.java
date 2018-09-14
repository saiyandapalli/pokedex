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

import java.util.List;

public class PokemonAdapter extends RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder> {
    private Context context;
    private List<Pokemon> data;

    private boolean isGridLayout;

    PokemonAdapter(Context context, List<Pokemon> pokemonList) {
        this.data = pokemonList;
        this.context = context;
        this.isGridLayout = false;
    }

    public void setData(List<Pokemon> pokemonList) {
        this.data = pokemonList;
        notifyDataSetChanged();
    }


    @Override
    public PokemonViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        //TODO: Make this a different layout depending on whether or not we are in List View or Card View
        int layoutId = (isGridLayout) ? R.layout.gridview_pokemon : R.layout.rowview_pokemon;
        View v = LayoutInflater.from(context).inflate(layoutId, viewGroup, false);
        return new PokemonViewHolder(v, isGridLayout);
    }

    @Override
    public void onBindViewHolder(@NonNull PokemonAdapter.PokemonViewHolder pokemonViewHolder, int i) {
        Pokemon p = data.get(i);

        pokemonViewHolder.nameText.setText(p.getName());
        if (!isGridLayout) {
            pokemonViewHolder.speciesText.setText(p.getSpecies());

            String typeString = "Type: ";
            for (String type : p.getTypes()) {
                typeString += type+", ";
            }
            typeString = typeString.substring(0, typeString.length()-2);
            pokemonViewHolder.typeText.setText(typeString);
        }

        Log.d("Loading Image", "Loading Image with URL: "+p.getImageUrl());
        Glide.with(context).load(p.getImageUrl()).error(
                Glide.with(context).load(android.R.mipmap.sym_def_app_icon)).into(pokemonViewHolder.pokeImageView);
    }

    public int getItemCount() {
        return data.size();
    }
    public void switchLayout() {
        isGridLayout = !isGridLayout;
        notifyDataSetChanged();
    }

    class PokemonViewHolder extends RecyclerView.ViewHolder {
        private TextView nameText;
        private TextView speciesText;
        private TextView typeText;

        private ImageView pokeImageView;

        PokemonViewHolder(View itemView, boolean isGridCard) {
            super(itemView);
            if (!isGridCard) {
                nameText = itemView.findViewById(R.id.nameText);
                speciesText = itemView.findViewById(R.id.speciesText);
                typeText = itemView.findViewById(R.id.typesText);
                pokeImageView = itemView.findViewById(R.id.pokeImageView);
            } else {
                nameText = itemView.findViewById(R.id.gridPokemonName);
                pokeImageView = itemView.findViewById(R.id.gridPokemonImage);
            }
        }
    }


}
