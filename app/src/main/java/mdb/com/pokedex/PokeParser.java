package mdb.com.pokedex;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


public class PokeParser {
    private ArrayList<Pokemon> pokemon;
    private Set<String> pokeTypes;

    PokeParser(String json) {
        pokemon = new ArrayList<>();
        pokeTypes = new HashSet<>();

        try {
            parseJSON(json);
        } catch (JSONException e) {
            Log.e("JSONError", e.getMessage());
        }
    }

    private void parseJSON(String json) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        Iterator<String> pokemonKeys = jsonObject.keys();
        while (pokemonKeys.hasNext()) {
            String key = pokemonKeys.next();
            if (jsonObject.get(key) instanceof JSONObject ) {
                JSONObject pokeObject = new JSONObject(jsonObject.get(key).toString());

                int id = pokeObject.getInt("#");
                int attack = pokeObject.getInt("Attack");
                int defense = pokeObject.getInt("Defense");

                String flavorText = pokeObject.getString("FlavorText");

                int hp = pokeObject.getInt("HP");
                int spAtk = pokeObject.getInt("Sp. Atk");
                int spDef = pokeObject.getInt("Sp. Def");

                String species = pokeObject.getString("Species");

                int speed = pokeObject.getInt("Speed");
                int total = pokeObject.getInt("Total");

                ArrayList<String> types = new ArrayList<>();
                JSONArray jsontypes = pokeObject.getJSONArray("Type");
                for(int i = 0; i < jsontypes.length(); i++){
                    String type = jsontypes.getString(i);
                    pokeTypes.add(type);
                    types.add(type);
                }

                Pokemon poke = new Pokemon(key, id, attack, defense, flavorText, hp, spAtk, spDef, species, speed, total, types);
                pokemon.add(poke);
            }
        }
    }

    public ArrayList<Pokemon> getPokemon() {
        return pokemon;
    }

    public Set<String> getPokeTypes() {
        return pokeTypes;
    }
}

