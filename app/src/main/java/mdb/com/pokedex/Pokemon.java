package mdb.com.pokedex;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;

public class Pokemon implements Comparable, Parcelable {
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

    protected Pokemon(Parcel in) {
        name = in.readString();
        id = in.readInt();
        attack = in.readInt();
        defense = in.readInt();
        flavorText = in.readString();
        hp = in.readInt();
        spAtk = in.readInt();
        spDef = in.readInt();
        species = in.readString();
        speed = in.readInt();
        total = in.readInt();
        types = in.createStringArrayList();
        imageUrl = in.readString();
    }

    public static final Creator<Pokemon> CREATOR = new Creator<Pokemon>() {
        @Override
        public Pokemon createFromParcel(Parcel in) {
            return new Pokemon(in);
        }

        @Override
        public Pokemon[] newArray(int size) {
            return new Pokemon[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(id);
        dest.writeInt(attack);
        dest.writeInt(defense);
        dest.writeString(flavorText);
        dest.writeInt(hp);
        dest.writeInt(spAtk);
        dest.writeInt(spDef);
        dest.writeString(species);
        dest.writeInt(speed);
        dest.writeInt(total);
        dest.writeList(types);
        dest.writeString(imageUrl);
    }

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
        if (parsedName.contains("\u2640")) {
            parsedName = parsedName.substring(0, parsedName.indexOf("\u2640"))+"-m";
        } else if (parsedName.contains("\u2642")) {
            parsedName = parsedName.substring(0, parsedName.indexOf("\u2642"))+"-f";
        }

        if (parsedName.contains(" (")) {
            String originalName = parsedName.substring(0, name.indexOf(" ("));

            //TODO this gen modifier thing doesnt work compeltely
            String genModifier = "";
            if (parsedName.contains("x")) {
                genModifier += "-x";
            } else if (parsedName.contains("y")) {
                genModifier += "-y";
            }

            parsedName = originalName + "-mega"+genModifier;
        }

        imageUrl += parsedName + ".jpg";
        for (String line: this.toString().split("\n")) {
            System.out.println(line);
        }
    }

    @Override
    public String toString() {
        return "Pokemon{" +
                "name='" + name + '\'' +
                ", \nid=" + id +
                ", \nattack=" + attack +
                ", \ndefense=" + defense +
                ", \nflavorText='" + flavorText + '\'' +
                ", \nhp=" + hp +
                ", \nspAtk=" + spAtk +
                ", \nspDef=" + spDef +
                ", \nspecies='" + species + '\'' +
                ", \nspeed=" + speed +
                ", \ntotal=" + total +
                ", \ntypes=" + types +
                ", \nimageUrl='" + imageUrl + '\'' +
                '}';
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

    @Override
    public int describeContents() {
        return 0;
    }


}

//An enum to keep track of the different types of integer values stored in Pokémon
enum PointAttribute {
    ATTACK, DEFENSE, HP, SP_ATK, SP_DEF, SPEED
}
