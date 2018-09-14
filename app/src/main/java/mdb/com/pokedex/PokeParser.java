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

    private Bounds defaultAttackBounds;
    private Bounds defaultDefenseBounds;
    private Bounds defaultHealthBounds;
    private Bounds defaultSpAtkBounds;
    private Bounds defaultSpDefBounds;
    private Bounds defaultSpeedBounds;

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

        int maxHealth = 0;
        int maxAtk = 0;
        int maxDef = 0;
        int maxSpAtk = 0;
        int maxSpeed = 0;
        int maxSpDef = 0;

        while (pokemonKeys.hasNext()) {
            String key = pokemonKeys.next();
            if (jsonObject.get(key) instanceof JSONObject ) {
                JSONObject pokeObject = new JSONObject(jsonObject.get(key).toString());

                int id = pokeObject.getInt("#");

                int attack = pokeObject.getInt("Attack");
                maxAtk = Math.max(attack, maxAtk);

                int defense = pokeObject.getInt("Defense");
                maxDef = Math.max(defense, maxDef);

                String flavorText = pokeObject.getString("FlavorText");

                int hp = pokeObject.getInt("HP");
                maxHealth = Math.max(hp, maxHealth);

                int spAtk = pokeObject.getInt("Sp. Atk");
                maxSpAtk = Math.max(spAtk, maxSpAtk);

                int spDef = pokeObject.getInt("Sp. Def");
                maxSpDef = Math.max(spDef, maxSpDef);

                String species = pokeObject.getString("Species");

                int speed = pokeObject.getInt("Speed");
                maxSpeed = Math.max(speed, maxSpeed);

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

        defaultAttackBounds = new Bounds(0, maxAtk);
        defaultDefenseBounds = new Bounds(0, maxDef);
        defaultHealthBounds = new Bounds(0, maxHealth);
        defaultSpAtkBounds = new Bounds(0, maxSpAtk);
        defaultSpDefBounds = new Bounds(0, maxSpDef);
        defaultSpeedBounds = new Bounds(0, maxSpDef);
    }

    public ArrayList<Pokemon> getPokemon() {
        return pokemon;
    }

    public Set<String> getPokeTypes() {
        return pokeTypes;
    }

    public Bounds getDefaultAttackBounds() {
        return defaultAttackBounds;
    }

    public Bounds getDefaultDefenseBounds() {
        return defaultDefenseBounds;
    }

    public Bounds getDefaultHealthBounds() {
        return defaultHealthBounds;
    }

    public Bounds getDefaultSpAtkBounds() {
        return defaultSpAtkBounds;
    }

    public Bounds getDefaultSpDefBounds() {
        return defaultSpDefBounds;
    }

    public Bounds getDefaultSpeedBounds() {
        return defaultSpeedBounds;
    }
}

