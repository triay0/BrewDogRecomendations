package com.sopra.brewdogrecomendations.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.ToggleButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sopra.brewdogrecomendations.R;
import com.sopra.brewdogrecomendations.data.local.entity.Beer;
import com.sopra.brewdogrecomendations.ui.adapter.BeerAdapter;
import com.sopra.brewdogrecomendations.viewmodel.MainActivityViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.sopra.brewdogrecomendations.Utils.getSnackbar;


public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";

    private MainActivityViewModel model;

    private RecyclerView beerList;
    private BeerAdapter adapter;
    private ArrayList<Beer> beersArray = new ArrayList<>();
    Button btn;
    EditText etFood;
    ToggleButton sw;
    private ConstraintLayout llMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        beerList = findViewById(R.id.rvBeers);
        btn = findViewById(R.id.btnInput);
        etFood = findViewById(R.id.etFood);
        sw = findViewById(R.id.switch1);
        llMain = findViewById(R.id.constraintLayout);

        model = ViewModelProviders.of(this).get(MainActivityViewModel.class);

        model.getBeers().observe(MainActivity.this, new Observer<List<Beer>>() {
            @Override
            public void onChanged(@Nullable List<Beer> beers) {

//                Toast.makeText(MainActivity.this, "size"+beers.size() , Toast.LENGTH_SHORT).show();
                Collections.sort(beers, new Comparator<Beer>() {
                    @Override
                    public int compare(Beer beer, Beer t1) {
                        return Float.compare(beer.abv, t1.abv);
                    }
                });

//                --livedata model vm rxjava dagger

                beersArray.addAll(beers);
            }
        });

        setupList();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etFood.getText().toString().isEmpty()) {
                    getSnackbar(llMain, "Please check food field").show();
                    return;
                }
                model.getBeers();

            }
        });

//Aixo al view model
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Collections.reverse(beersArray);
                adapter.notifyDataSetChanged();
            }
        });
    }

    public void setupList() {
        adapter = new BeerAdapter(beersArray);
        beerList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        beerList.setAdapter(adapter);
    }

}

