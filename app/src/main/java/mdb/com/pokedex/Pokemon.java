package mdb.com.pokedex;

public class Pokemon {
    //{"#":"460","Attack":"92","Defense":"75","FlavorText":"","HP":"90","Sp. Atk":"92","Sp. Def":"85",
    // "Species":"Frost Tree Pok\u00e9mon","Speed":"60","Total":"494","Type":["Grass","Ice"]}
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
    private String[] types;
    private String imageUrl;

    public Pokemon(String name, int id, int attack, int defense, String flavorText, int hp, int spAtk, int spDef, String species, int speed, int total, String[] types) {
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
        imageUrl = "http://img.pokemondb.net/artwork/"+name.toLowerCase()+".jpg";
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

    public String[] getTypes() {
        return types;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
