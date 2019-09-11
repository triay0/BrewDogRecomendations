package com.sopra.brewdogrecomendations.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.sopra.brewdogrecomendations.data.local.entity.Beer;
import com.sopra.brewdogrecomendations.data.repository.BeerRepository;

import java.util.List;

public class MainActivityViewModel extends AndroidViewModel {

    // Create a LiveData with a String
//    private LiveData<List<Beer>> beers;

    private LiveData<List<Beer>> beersMutable;

    private BeerRepository beerRepository;
//    private BeerDao beerDao;


    public MainActivityViewModel(Application application) {
        super(application);

        beerRepository = new BeerRepository(application);
        beersMutable = beerRepository.getBeers("pizza");

    }


    public LiveData<List<Beer>> getBeers() {
        return beersMutable;
    }


}
