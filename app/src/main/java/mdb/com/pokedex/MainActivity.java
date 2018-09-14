package mdb.com.pokedex;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.ActionMenuItem;
import android.support.v7.view.menu.ActionMenuItemView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private boolean isGridView = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // TODO: Replace with Pokedex
        //Some dummy pokemon just to test the recycler view
        Pokemon pikachu = new Pokemon("Pikachu", 100, 20, 50, "", 100, 20, 50, "Rat", 50, 200, new ArrayList<>(Arrays.asList("Electric")));
        Pokemon bulbasaur = new Pokemon("Bulbasaur", 101, 50, 20, "", 100, 20, 50, "Monster", 50, 200, new ArrayList<>(Arrays.asList("Grass", "Poison")));
        Pokemon squirtle = new Pokemon("Squirtle", 102, 50, 30, "", 100, 20, 50, "Water thing", 50, 200, new ArrayList<>(Arrays.asList("Water")));
        Pokemon charmander = new Pokemon("Charmander", 103, 50, 10, "", 100, 20, 50, "Dragon", 50, 200, new ArrayList<>(Arrays.asList("Fire")));
        Pokemon charizard = new Pokemon("Charizard", 104, 90, 50, "", 100, 20, 50, "Dragon", 50, 200, new ArrayList<>(Arrays.asList("Fire", "Dragon")));
        Pokemon caterpie = new Pokemon("Caterpie", 105, 10, 50, "", 100, 20, 50, "Bug", 50, 200, new ArrayList<>(Arrays.asList("Bug", "Poison")));

        ArrayList<Pokemon> pokedex = new ArrayList<>(Arrays.asList(pikachu, bulbasaur, squirtle, charmander, charizard, caterpie));

        PokemonAdapter pokeAdapter = new PokemonAdapter(getApplicationContext(), pokedex);
        recyclerView.setAdapter(pokeAdapter);
    }

    ///Method which creates the options menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        MenuItem mSearch = menu.findItem(R.id.action_search);

        SearchView mSearchView = (SearchView) mSearch.getActionView();
        //Binds a listener to the search view so we can detect when something is typed in
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

    //Method called when a Menu Item is clicked
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.action_filter:
                Intent filterIntent = new Intent(getApplicationContext(), FilterActivity.class);
                startActivityForResult(filterIntent, 1);
                break;
            case R.id.action_layout_toggle:
                isGridView = !isGridView;
                supportInvalidateOptionsMenu();
                ActionMenuItemView toggle = findViewById(R.id.action_layout_toggle);

                RecyclerView recycler = findViewById(R.id.recycler);
                if (isGridView) {
                    StaggeredGridLayoutManager sglm = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
                    recycler.setLayoutManager(sglm);
                    //TODO: This line is supposed to switch the icon, but it doesn't work
                    item.setIcon(android.R.drawable.ic_dialog_dialer);
                } else {
                    recycler.setLayoutManager(new LinearLayoutManager(this));
                    //TODO: This line is supposed to switch the icon, but it doesn't work
                    item.setIcon(android.R.drawable.ic_menu_sort_by_size);
                }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {
                String minAttack = data.getStringExtra("minAttack");
                String minDefense = data.getStringExtra("minDefense");
                String minHealth = data.getStringExtra("minHealth");

                Log.d("Filter","Filter: Min Attack="+minAttack+"; Min Defense="+minDefense+"; Min Health="+minHealth);

                HashMap<String, Boolean> categories = (HashMap<String, Boolean>) data.getSerializableExtra("categories");
                Log.d("Filter Categories", categories.toString());
            }
        }
    }
}
