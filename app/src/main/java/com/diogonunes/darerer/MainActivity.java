package com.diogonunes.darerer;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = "MainActivity";
    private List<DiceBox> _diceBoxes;
    private RecyclerView _RV;
    private FloatingActionButton _btnFAB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initializeData();
        initializeViews();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initializeData() {
        // Dummy test data
        _diceBoxes = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            _diceBoxes.add(new DiceBox("Title " + i, "Description about the dice box " + i, "Kindness"));
        }
    }

    private void initializeViews() {
        _btnFAB = (FloatingActionButton) findViewById(R.id.fab);

        _RV = (RecyclerView) findViewById(R.id.rv);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        _RV.setLayoutManager(llm);
        _RV.setHasFixedSize(true);

        RVAdapter adapter = new RVAdapter(_diceBoxes);
        _RV.setAdapter(adapter);
    }

    // Event Handling
}
