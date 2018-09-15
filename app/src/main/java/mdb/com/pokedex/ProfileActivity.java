package mdb.com.pokedex;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class ProfileActivity extends AppCompatActivity {

    private Pokemon pokemon;

    private TextView nameView;
    private TextView attack;
    private TextView defense;
    private TextView health;
    private TextView spAttack;
    private TextView spDefense;
    private TextView speed;
    private TextView total;
    private ImageView imageView;
    private Button onlineSearchButton;
    private MenuItem doneButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // This gets the pokemon object!
        Bundle data = getIntent().getExtras();
        pokemon = (Pokemon) data.getParcelable("pokemon_pass");

        // Setup views
        attack = findViewById(R.id.attackText);
        defense = findViewById(R.id.defenseText);
        health = findViewById(R.id.hpText);
        spAttack = findViewById(R.id.spAtkText);
        spDefense = findViewById(R.id.spDefText);
        speed = findViewById(R.id.speedText);
        total = findViewById(R.id.totalText);
        imageView = findViewById(R.id.profileImageView);
        nameView = findViewById(R.id.profileName);

        getSupportActionBar().setTitle("Profile");

        onlineSearchButton = findViewById(R.id.searchGoogleButton);

        setupTextViews();
        setupButton();

        Log.d("PokeURl", pokemon.getImageUrl());
        System.out.println(pokemon.getImageUrl());
        Glide.with(this).load(pokemon.getImageUrl()).error(
                Glide.with(this).load(android.R.mipmap.sym_def_app_icon)).into(imageView);
    }

    private void setupTextViews() {
        nameView.setText(pokemon.getName());
        attack.setText(Integer.toString(pokemon.getAttack()));
        defense.setText(Integer.toString(pokemon.getDefense()));
        health.setText(Integer.toString(pokemon.getHp()));
        spAttack.setText(Integer.toString(pokemon.getSpAtk()));
        spDefense.setText(Integer.toString(pokemon.getSpDef()));
        speed.setText(Integer.toString(pokemon.getSpeed()));
        total.setText(Integer.toString(pokemon.getTotal()));
    }

    private void setupButton() {
        onlineSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = pokemon.getName();
                String escapedQuery = null;
                if (query == null) {
                    Toast.makeText(ProfileActivity.this, "This link does not exist!", Toast.LENGTH_LONG).show();
                    return;
                }
                try {
                    escapedQuery = URLEncoder.encode(query, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    Toast.makeText(ProfileActivity.this, "This link does not exist!", Toast.LENGTH_LONG).show();
                    return;
                }
                Uri uri = Uri.parse("http://www.google.com/#q=" + escapedQuery);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
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
        finish();
    }
}
