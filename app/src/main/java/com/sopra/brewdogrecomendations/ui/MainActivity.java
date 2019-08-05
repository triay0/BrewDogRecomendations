package com.sopra.brewdogrecomendations.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sopra.brewdogrecomendations.R;
import com.sopra.brewdogrecomendations.ui.adapter.BeerAdapter;
import com.sopra.brewdogrecomendations.viewmodel.BeerModel;
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
    private ArrayList<BeerModel> beersArray = new ArrayList<>();
    Button btn;
    EditText etFood;
    Switch sw;
    private LinearLayout llMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        beerList = findViewById(R.id.rvBeers);
        btn = findViewById(R.id.btnInput);
        etFood = findViewById(R.id.etFood);
        sw = findViewById(R.id.switch1);
        llMain = findViewById(R.id.llMain);

        model = ViewModelProviders.of(this).get(MainActivityViewModel.class);

        model.getBeers().observe(MainActivity.this, new Observer<List<BeerModel>>() {
            @Override
            public void onChanged(@Nullable List<BeerModel> beers) {
                Collections.sort(beers, new Comparator<BeerModel>() {
                    @Override
                    public int compare(BeerModel beerModel, BeerModel t1) {
                        return Float.compare(beerModel.abv, t1.abv);
                    }
                });

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
                model.getBeers(etFood.getText().toString());

            }
        });


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

