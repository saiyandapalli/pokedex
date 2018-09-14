package mdb.com.pokedex;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

public class PokemonAdapter extends RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder> {
    private Context context;
    private List<Pokemon> data;

    PokemonAdapter(Context context, List<Pokemon> memberList) {
        this.data = memberList;
        this.context = context;
    }


    @Override
    public PokemonViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.rowview_pokemon, viewGroup, false);
        return new PokemonViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PokemonAdapter.PokemonViewHolder pokemonViewHolder, int i) {
        Pokemon p = data.get(i);

        pokemonViewHolder.nameText.setText(p.getName());
        pokemonViewHolder.speciesText.setText(p.getSpecies());
        pokemonViewHolder.typeText.setText(p.getTypes().toString());
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
