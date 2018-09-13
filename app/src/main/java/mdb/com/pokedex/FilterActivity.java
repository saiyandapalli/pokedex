package mdb.com.pokedex;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

//  This activity allows for the filtering of Pokemon through attributes
//  Includes two sections:
//      Top Section = min attack, min defense, min health
//      Bottom Section = selectable CATEGORIES

public class FilterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
    }
}
