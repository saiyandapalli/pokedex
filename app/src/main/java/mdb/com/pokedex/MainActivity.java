package mdb.com.pokedex;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.ActionMenuItemView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import static java.lang.Integer.parseInt;

public class MainActivity extends AppCompatActivity {

    private boolean isGridView = false;
    private PokemonAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Pokedex.getSharedInstance().loadPokedex(getJSON());

        mAdapter = new PokemonAdapter(getApplicationContext(), Pokedex.getSharedInstance().getPokedex());
        recyclerView.setAdapter(mAdapter);
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
                try {
                    int id = Integer.parseInt(s);
                    Pokemon pokemon = Pokedex.getSharedInstance().getPokemonWithId(id);
                    mAdapter.setData(Collections.singletonList(pokemon));
                } catch (Exception ex) {
                    mAdapter.setData(Pokedex.getSharedInstance().filterByName(s));
                }

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
                int minAttack = parseInt(data.getStringExtra("minAttack"));
                int minDefense = parseInt(data.getStringExtra("minDefense"));
                int minHealth = parseInt(data.getStringExtra("minHealth"));

                Pokedex dex = Pokedex.getSharedInstance();

                Bounds defaultAttackBounds = dex.getDefaultAttackBounds();
                Bounds defaultDefBounds = dex.getDefaultDefenseBounds();
                Bounds defaultHealthBounds = dex.getDefaultHealthBounds();
                Bounds defaultSpAtkBounds = dex.getDefaultSpAtkBounds();
                Bounds defaultSpDefBounds = dex.getDefaultDefenseBounds();
                Bounds defaultSpeedBounds = dex.getDefaultSpeedBounds();

                ArrayList<Pokemon> filtered = Pokedex.getSharedInstance().pointFilter(
                        new Bounds(minAttack, defaultAttackBounds.getMaximum()),
                        new Bounds(minDefense, defaultDefBounds.getMaximum()),
                        new Bounds(minHealth, defaultHealthBounds.getMaximum()),
                        defaultSpAtkBounds, defaultSpDefBounds, defaultSpeedBounds);

                Log.d("Filter","Filter: Min Attack="+minAttack+"; Min Defense="+minDefense+"; Min Health="+minHealth);

                HashMap<String, Boolean> categories = (HashMap<String, Boolean>) data.getSerializableExtra("categories");
                ArrayList<String> filterCategories = new ArrayList<>();
                for (String key : categories.keySet()) {
                    if (categories.get(key)) {
                        filterCategories.add(key);
                    }
                }

                if (filterCategories.size() > 0)
                    filtered = Pokedex.getSharedInstance().filterByTypes(filterCategories, filtered);

                Collections.sort(filtered);
                mAdapter.setData(filtered);
            }
        }
    }

    private String getJSON() {
        String json;
        try {
            InputStream is = getAssets().open("pokeData.json");

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

}
