package com.sopra.brewdogrecomendations.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sopra.brewdogrecomendations.R;
import com.sopra.brewdogrecomendations.net.BrewDogApiAdapter;
import com.sopra.brewdogrecomendations.room.BeerDatabase;
import com.sopra.brewdogrecomendations.ui.adapter.BeerAdapter;
import com.sopra.brewdogrecomendations.viewmodel.BeerModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

import static com.sopra.brewdogrecomendations.Utils.getSnackbar;


public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";

    private RecyclerView beerList;
    private BeerAdapter adapter;
    private ArrayList<BeerModel> beers = new ArrayList<>();
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

        setupList();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etFood.getText().toString().isEmpty()){
                   getSnackbar(llMain, "Please check food field").show();
                    return;
                }
                if(!getBeers(BeerDatabase.getAppDatabase(getApplicationContext()), etFood.getText().toString()).isEmpty()){
                    beers.clear();
                    beers.addAll(getBeers(BeerDatabase.getAppDatabase(getApplicationContext()), etFood.getText().toString()));
                    adapter.notifyDataSetChanged();
                }else{
                                    searchBeers(etFood.getText().toString());

                }
            }
        });


        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                Collections.reverse(beers);
                adapter.notifyDataSetChanged();
            }
        });
    }

    public void setupList() {
        adapter = new BeerAdapter(beers);
        beerList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        beerList.setAdapter(adapter);

    }

    private static BeerModel addBeer(final BeerDatabase db, BeerModel beerModel) {
        db.beerDao().insertAll(beerModel);
        return beerModel;
    }


    private static ArrayList<BeerModel> getBeers(final BeerDatabase db, String query) {

        return (ArrayList<BeerModel>) db.beerDao().getBeers(query);
    }


    void searchBeers(final String food) {
        Single<Response<List<BeerModel>>> beerObservable = BrewDogApiAdapter.getApiService().getBeers(food);
        beerObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<List<BeerModel>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "OnSubscribe");

                    }

                    @Override
                    public void onSuccess(Response<List<BeerModel>> listResponse) {

                        assert listResponse.body() != null;
                        if(listResponse.body().isEmpty()){
                            getSnackbar(llMain, "No results found").show();
                            return;
                        }

                        beers.addAll(listResponse.body());

                        for (BeerModel bm : listResponse.body()) {
                            bm.setFood(food);
                            addBeer(BeerDatabase.getAppDatabase(getApplicationContext()), bm);
                        }

                        Collections.sort(beers, new Comparator<BeerModel>() {
                            @Override
                            public int compare(BeerModel beerModel, BeerModel t1) {
                                return Float.compare(beerModel.abv, t1.abv);
                            }
                        });

                        adapter.notifyDataSetChanged();

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, Objects.requireNonNull(e.getMessage()));
                        getSnackbar(llMain, e.getMessage()).show();
                    }
                });
    }
}

