package mdb.com.pokedex;

import android.support.annotation.NonNull;
import java.util.ArrayList;

public class Pokemon implements Comparable {
    private String name;
    private int id;
    private int attack;
    private int defense;
    private String flavorText;
    private int hp;
    private int spAtk;
    private int spDef;
    private String species;
    private int speed;
    private int total;
    private ArrayList<String> types;
    private String imageUrl;

    Pokemon(String name, int id, int attack, int defense, String flavorText, int hp, int spAtk, int spDef, String species, int speed, int total, ArrayList<String> types) {
        this.name = name;
        this.id = id;
        this.attack = attack;
        this.defense = defense;
        this.flavorText = flavorText;
        this.hp = hp;
        this.spAtk = spAtk;
        this.spDef = spDef;
        this.species = species;
        this.speed = speed;
        this.total = total;
        this.types = types;

        imageUrl = "https://img.pokemondb.net/artwork/";

        //TODO: Better parsing of the URL to virtually guarantee the image is found
        String parsedName = name.toLowerCase();
        if (parsedName.indexOf(" (") != -1) {
            String originalName = parsedName.substring(0, name.indexOf(" ("));

            //TODO this gen modifier thing doesnt work compeltely
            String genModifier = "";
            if (parsedName.indexOf("X") != -1) {
                genModifier = "-x";
            } else if (parsedName.indexOf("Y") != -1) {
                genModifier = "-y";
            }

            parsedName = originalName + "-mega"+genModifier;
        }

        imageUrl += parsedName + ".jpg";
    }

    public int getPointValue(PointAttribute attr) {
        switch (attr) {
            case ATTACK:
                return attack;
            case DEFENSE:
                return defense;
            case HP:
                return hp;
            case SP_ATK:
                return  spAtk;
            case SP_DEF:
                return spDef;
            case SPEED:
                return speed;
        }
        return 0;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public int getAttack() {
        return attack;
    }

    public int getDefense() {
        return defense;
    }

    public String getFlavorText() {
        return flavorText;
    }

    public int getHp() {
        return hp;
    }

    public int getSpAtk() {
        return spAtk;
    }

    public int getSpDef() {
        return spDef;
    }

    public String getSpecies() {
        return species;
    }

    public int getSpeed() {
        return speed;
    }

    public int getTotal() {
        return total;
    }

    public ArrayList<String> getTypes() {
        return types;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    @Override
    public int compareTo(@NonNull Object o) {
        if (o instanceof Pokemon) {
            return this.id - ((Pokemon) o).getId();
        } else {
            return -1;
        }
    }
}

//An enum to keep track of the different types of integer values stored in Pokémon
enum PointAttribute {
    ATTACK, DEFENSE, HP, SP_ATK, SP_DEF, SPEED
}
