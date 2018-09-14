package mdb.com.pokedex;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // TODO: Replace with Pokedex
        Pokemon pikachu = new Pokemon("Pikachu", 100, 20, 50, "", 100, 20, 50, "Rat", 50, 200, new ArrayList<>(Arrays.asList("Electric")));
        Pokemon bulbasaur = new Pokemon("Bulbasaur", 101, 50, 20, "", 100, 20, 50, "Monster", 50, 200, new ArrayList<>(Arrays.asList("Grass", "Poison")));
        Pokemon squirtle = new Pokemon("Squirtle", 102, 50, 30, "", 100, 20, 50, "Water thing", 50, 200, new ArrayList<>(Arrays.asList("Water")));
        Pokemon charmander = new Pokemon("Charmander", 103, 50, 10, "", 100, 20, 50, "Dragon", 50, 200, new ArrayList<>(Arrays.asList("Fire")));
        Pokemon charizard = new Pokemon("Charizard", 104, 90, 50, "", 100, 20, 50, "Dragon", 50, 200, new ArrayList<>(Arrays.asList("Fire", "Dragon")));
        Pokemon caterpie = new Pokemon("Caterpie", 105, 10, 50, "", 100, 20, 50, "Bug", 50, 200, new ArrayList<>(Arrays.asList("Bug", "Poison")));

        ArrayList<Pokemon> pokedex = new ArrayList<>(Arrays.asList(pikachu, bulbasaur, squirtle, charmander, charizard, caterpie));

        PokemonAdapter pokeAdapter = new PokemonAdapter(getApplicationContext(), pokedex);
        recyclerView.setAdapter(pokeAdapter);

        Toolbar mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        MenuItem mSearch = menu.findItem(R.id.action_search);

        SearchView mSearchView = (SearchView) mSearch.getActionView();
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Log.d("Submit", "Query is: "+s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                Log.d("Change", "Query is: "+s);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}
