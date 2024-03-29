package com.sopra.brewdogrecomendations.data.repository;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.sopra.brewdogrecomendations.data.local.BeerDatabase;
import com.sopra.brewdogrecomendations.data.local.dao.BeerDao;
import com.sopra.brewdogrecomendations.data.local.entity.Beer;
import com.sopra.brewdogrecomendations.data.remote.BrewDogApiAdapter;
import com.sopra.brewdogrecomendations.data.remote.api.BeerApiService;

import java.util.List;
import java.util.concurrent.Executor;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BeerRepository {

    private BeerDao beerDao;
    private BeerApiService beerApiService;

//    private LiveData<List<Beer>> allBeers;


    public BeerRepository(Application application) {
        this.beerDao = BeerDatabase.getAppDatabase(application).beerDao();
        this.beerApiService = BrewDogApiAdapter.getApiService();

//        allBeers = beerDao.getAllBeers();
//        MutableLiveData<>();
    }


    public LiveData<List<Beer>> getBeers(final String food) {
        final MutableLiveData<List<Beer>> responseLiveData = new MutableLiveData<>();
        LiveData<List<Beer>> catched = beerDao.getBeers(food);
        if (catched.getValue() != null) {
            Log.d("REPOUU", "NOt null catched "+catched.getValue());

            responseLiveData.postValue(catched.getValue());
            return responseLiveData;
        }

        beerApiService.getBeers(food).enqueue(new Callback<List<Beer>>() {
            @Override
            public void onResponse(Call<List<Beer>> call, final Response<List<Beer>> response) {
                Log.d("REPOUU", response.body().toString());
                if (response.isSuccessful()) {
                     new Executor() {
                        @Override
                        public void execute(Runnable runnable) {
                            for (Beer beer : response.body()
                 ) {

                            beerDao.insert( beer);

                   }
                        }
                    };

//
                    responseLiveData.setValue(response.body());


                }

            }

            @Override
            public void onFailure(Call<List<Beer>> call, Throwable t) {
                Log.d("REPOUU", "Fails");
                //allBeers.setValue(null);

            }
        });
        return responseLiveData;
    }

}