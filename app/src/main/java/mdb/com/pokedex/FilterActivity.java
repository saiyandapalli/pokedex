package mdb.com.pokedex;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.HashMap;

//  This activity allows for the filtering of Pokemon through attributes
//  Includes two sections:
//      Top Section = min attack, min defense, min health
//      Bottom Section = selectable CATEGORIES

//  This works DIRECTLY with CategoryAdapter

//  ITEMS TO DO:
//  • Filling categories with info from previous screen – populateCategories(0
//  • Filling textviews with info from previous screen – populateTextViews()
//  • Passing data to previous screen through done button – doneTapped()

public class FilterActivity extends AppCompatActivity {

    private HashMap<String, Boolean> categories;
    private RecyclerView recyclerView;
    private CategoryAdapter adapter;
    private MenuItem doneButton;

    private EditText minAttackEditText;
    private EditText minDefenseEditText;
    private EditText minHealthEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        getSupportActionBar().setTitle("Filter");

        configureViews();

        // TODO: Edit these methods to fill the categories with a list provided from previous screen
        // This is the data from the most recent "Filter" –
        populateCategories();
        populateTextViews();

        setupRecyclerView();
    }

    private void populateTextViews() {
        minAttackEditText.setText("0");
        minDefenseEditText.setText("0");
        minHealthEditText.setText("0");
    }

    private void configureViews() {
        minAttackEditText = findViewById(R.id.minAttackEditText);
        minDefenseEditText = findViewById(R.id.minDefenseEditText);
        minHealthEditText = findViewById(R.id.minHealthEditText);
    }

    // Replace with appropriate code to populate categories (probably from intent or from singleton)
    private void populateCategories() {
        categories = new HashMap<>();
        for (String type : Pokedex.getSharedInstance().getPokeTypes()) {
            categories.put(type, false);
        }
    }

    private void setupRecyclerView() {
        recyclerView = findViewById(R.id.selectCategoryRecyclerView);
        StaggeredGridLayoutManager sglm = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(sglm);

        adapter = new CategoryAdapter(FilterActivity.this, categories);
        recyclerView.setAdapter(adapter);
    }

    // Adds the done button to the toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        doneButton = menu.add(Menu.NONE, 1000, Menu.NONE, "Done");
        doneButton.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return super.onCreateOptionsMenu(menu);
    }

    // Called when done button is tapped
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item == doneButton) {
            doneTapped();
        }
        return super.onOptionsItemSelected(item);
    }

    private void doneTapped() {
        System.out.println("DONE TAPPED!");

        Intent intent = new Intent();
        intent.putExtra("minAttack", minAttackEditText.getText().toString());
        intent.putExtra("minDefense", minDefenseEditText.getText().toString());
        intent.putExtra("minHealth", minHealthEditText.getText().toString());
        intent.putExtra("categories", adapter.categories);

        setResult(RESULT_OK, intent);

        finish();
    }
}
