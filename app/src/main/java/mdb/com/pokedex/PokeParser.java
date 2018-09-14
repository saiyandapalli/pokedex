package mdb.com.pokedex;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;


public class PokeParser {
    public static ArrayList<Pokemon> parseJSON(String json) throws JSONException {
        ArrayList<Pokemon> pokelist = new ArrayList<>();
        JSONObject jsonObject = new JSONObject(json);
        Iterator<String> pokemonKeys = jsonObject.keys();
        while (pokemonKeys.hasNext()) {
            String key = pokemonKeys.next();
            if (jsonObject.get(key) instanceof JSONObject ) {
                JSONObject pokemon = new JSONObject(jsonObject.get(key).toString());

                int id = pokemon.getInt("#");
                int attack = pokemon.getInt("Attack");
                int defense = pokemon.getInt("Defense");

                String flavorText = pokemon.getString("FlavorText");

                int hp = pokemon.getInt("HP");
                int spAtk = pokemon.getInt("Sp. Atk");
                int spDef = pokemon.getInt("Sp. Def");

                String species = pokemon.getString("Species");

                int speed = pokemon.getInt("Speed");
                int total = pokemon.getInt("Total");

                ArrayList<String> types = new ArrayList<>();
                JSONArray jsontypes = pokemon.getJSONArray("Type");
                for(int i = 0; i < jsontypes.length(); i++){
                    types.add(jsontypes.getString(i));
                }

                Pokemon poke = new Pokemon(key, id, attack, defense, flavorText, hp, spAtk, spDef, species, speed, total, types);
                pokelist.add(poke);
            }
        }
        return pokelist;
    }
}

