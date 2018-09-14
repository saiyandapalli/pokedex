package mdb.com.pokedex;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/*
A singleton class to store an array list of all Pokemon.
Handles filtering the array list and searching it for
 */
public class Pokedex {
    private static Pokedex sharedInstance;
    private ArrayList<Pokemon> pokedex;
    private Set<String> pokeTypes;

    private Bounds defaultAttackBounds;
    private Bounds defaultDefenseBounds;
    private Bounds defaultHealthBounds;
    private Bounds defaultSpAtkBounds;
    private Bounds defaultSpDefBounds;
    private Bounds defaultSpeedBounds;

    private Pokedex() {
        /*
        The constructor is private because it should only be called within Pokedex
        */

        pokedex = new ArrayList<>();
        pokeTypes = new HashSet<>();
    }

    public static Pokedex getSharedInstance() {
        if (sharedInstance == null) {
            sharedInstance = new Pokedex();
        }

        return sharedInstance;
    }


    private Predicate<Pokemon> pointPredicate(final PointAttribute attribute, final Bounds bounds) {
        return new Predicate<Pokemon>() {
            @Override
            public boolean apply(@NonNull Pokemon input) {
                return bounds.isValid(input.getPointValue(attribute));
            }
        };
    }

    public ArrayList<Pokemon> pointFilter(Bounds attackBounds, Bounds defenseBounds, Bounds hpBounds, Bounds spAtkBounds, Bounds spDefBounds, Bounds speedBounds) {
        //Filter the collection based on all the predicates
        Collection<Pokemon> filtered = Collections2.filter(pokedex,
                Predicates.and(pointPredicate(PointAttribute.ATTACK, attackBounds),
                        Predicates.and(pointPredicate(PointAttribute.DEFENSE, defenseBounds),
                                Predicates.and(pointPredicate(PointAttribute.HP, hpBounds)),
                                Predicates.and(pointPredicate(PointAttribute.SP_ATK, spAtkBounds)),
                                Predicates.and(pointPredicate(PointAttribute.SP_DEF, spDefBounds)))));
        return Lists.newArrayList(filtered);
    }

    public ArrayList<Pokemon> filterByTypes(final ArrayList<String> types) {
        Predicate<Pokemon> typePredicate = new Predicate<Pokemon>() {
            @Override
            public boolean apply(@NonNull Pokemon input) {
                for (String type : types) {
                    if (input.getTypes().contains(type)) {
                        return true;
                    }
                }
                return false;
            }
        };

        return Lists.newArrayList(Collections2.filter(pokedex, typePredicate));
    }

    public ArrayList<Pokemon> filterByTypes(final ArrayList<String> types, ArrayList<Pokemon> pokemon) {
        Predicate<Pokemon> typePredicate = new Predicate<Pokemon>() {
            @Override
            public boolean apply(@NonNull Pokemon input) {
                for (String type : types) {
                    if (input.getTypes().contains(type)) {
                        return true;
                    }
                }
                return false;
            }
        };

        return Lists.newArrayList(Collections2.filter(pokemon, typePredicate));
    }

    public ArrayList<Pokemon> filterByName(final String predicate) {
        //Define a predicate which checks if the name starts with the input string
        Predicate<Pokemon> namePredicate = new Predicate<Pokemon>() {
            @Override
            public boolean apply(Pokemon input) {
                return input.getName().toLowerCase().matches("^("+predicate.toLowerCase()+").*$");
            }
        };

        return Lists.newArrayList(Collections2.filter(pokedex, namePredicate));
    }

    public Pokemon getPokemonWithId(final int id) {
        Predicate<Pokemon> idPredicate = new Predicate<Pokemon>() {
            @Override
            public boolean apply(Pokemon input) {
                return input.getId() == id;
            }
        };

        Collection<Pokemon> pokeCollection= Collections2.filter(pokedex, idPredicate);
        if (pokeCollection.iterator().hasNext()) {
            return pokeCollection.iterator().next();
        } else {
            return null;
        }
    }

    public void loadPokedex(String json) {
        PokeParser parser = new PokeParser(json);

        pokedex = parser.getPokemon();
        pokeTypes = parser.getPokeTypes();

        defaultAttackBounds = parser.getDefaultAttackBounds();
        defaultDefenseBounds = parser.getDefaultDefenseBounds();
        defaultHealthBounds = parser.getDefaultHealthBounds();
        defaultSpAtkBounds = parser.getDefaultSpAtkBounds();
        defaultSpDefBounds = parser.getDefaultSpDefBounds();
        defaultSpeedBounds = parser.getDefaultSpeedBounds();

        Collections.sort(pokedex);
    }

    public ArrayList<Pokemon> getPokedex() {
        return pokedex;
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

class Bounds {
    private int minimum;
    private int maximum;

    Bounds(int min, int max) {
        minimum = min;
        maximum = max;
    }

    public boolean isValid(int value) {
        return value >= minimum && value <= maximum;
    }

    public int getMinimum() {
        return minimum;
    }

    public int getMaximum() {
        return maximum;
    }
}

