package mdb.com.pokedex;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class FilterTests {
    private Pokemon pikachu = new Pokemon("Pikachu", 100, 20, 50, "", 100, 20, 50, "", 50, 200, new String[0]);
    private Pokemon bulbasaur = new Pokemon("Bulbasaur", 101, 50, 20, "", 100, 20, 50, "", 50, 200, new String[0]);
    private Pokemon squirtle = new Pokemon("Squirtle", 102, 50, 30, "", 100, 20, 50, "", 50, 200, new String[0]);
    private Pokemon charmander = new Pokemon("Charmander", 103, 50, 10, "", 100, 20, 50, "", 50, 200, new String[0]);
    private Pokemon charizard = new Pokemon("Charizard", 104, 90, 50, "", 100, 20, 50, "", 50, 200, new String[0]);
    private Pokemon caterpie = new Pokemon("Caterpie", 105, 10, 50, "", 100, 20, 50, "", 50, 200, new String[0]);

    private Pokedex constructPokedex() {
        Pokedex dex = Pokedex.getSharedInstance();
        dex.getPokedex().add(pikachu);
        dex.getPokedex().add(bulbasaur);
        dex.getPokedex().add(squirtle);
        dex.getPokedex().add(charmander);
        dex.getPokedex().add(charizard);
        dex.getPokedex().add(caterpie);
        return dex;
    }

    @Test
    public void nameRegex() {
        String regex = "^(c).*$";
        assertEquals(true, "Charizard".toLowerCase().matches(regex));
        assertEquals(false, "Apple".toLowerCase().matches(regex));

        regex = "^(ch).*$";
        assertEquals(true, "Charizard".toLowerCase().matches(regex));
        assertEquals(false, "Caterpie".toLowerCase().matches(regex));
    }

    @Test
    //Tests that the pokedex can filter by name
    public void name_filter_c() {
        Pokedex dex = constructPokedex();

        ArrayList<Pokemon> filtered = dex.filterByName("C");
        String[] correct = {"Charmander", "Charizard", "Caterpie"};
        assertEquals(correct.length, filtered.size());
        for (int i=0; i<filtered.size(); i++) {
            assertEquals(correct[i], filtered.get(i).getName());
        }

        filtered = dex.filterByName("Ch");
        String[] correct1 = {"Charmander", "Charizard"};
        assertEquals(correct1.length, filtered.size());
        for (int i=0; i<filtered.size(); i++) {
            assertEquals(correct1[i], filtered.get(i).getName());
        }
    }

    @Test
    //Tests the getPokemonById function
    public void id_filter() {
        Pokedex dex = constructPokedex();

        assertEquals(pikachu, dex.getPokemonWithId(100));
        assertEquals(bulbasaur, dex.getPokemonWithId(101));
        assertEquals(squirtle, dex.getPokemonWithId(102));
        assertEquals(null, dex.getPokemonWithId(0));
    }

    @Test
    public void attack_filter() {
        Pokedex dex = constructPokedex();

        Bounds attackBounds = new Bounds(50, 50);
        Bounds defenseBounds = new Bounds(0, 100);
        Bounds hpBounds = new Bounds(0, 100);
        Bounds spAtkBounds = new Bounds(0, 100);
        Bounds spDefBounds = new Bounds(0, 100);
        Bounds speedBounds = new Bounds(0, 100);

        Pokemon[] correct = {bulbasaur, squirtle, charmander};
        ArrayList<Pokemon> filtered = dex.pointFilter(attackBounds, defenseBounds, hpBounds, spAtkBounds, spDefBounds, speedBounds);

        assertEquals(correct.length, filtered.size());
        for (int i=0; i<filtered.size(); i++) {
            assertEquals(correct[i], filtered.get(i));
        }

        defenseBounds = new Bounds(20, 30);
        Pokemon[] correct1 = {bulbasaur, squirtle};
        filtered = dex.pointFilter(attackBounds, defenseBounds, hpBounds, spAtkBounds, spDefBounds, speedBounds);
        assertEquals(correct1.length, filtered.size());
        for (int i=0; i<filtered.size(); i++) {
            assertEquals(correct1[i], filtered.get(i));
        }
    }
}