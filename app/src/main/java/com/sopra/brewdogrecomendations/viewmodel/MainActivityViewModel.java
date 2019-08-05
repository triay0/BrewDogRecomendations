package com.sopra.brewdogrecomendations.viewmodel;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.sopra.brewdogrecomendations.net.BrewDogApiAdapter;
import com.sopra.brewdogrecomendations.room.BeerDao;
import com.sopra.brewdogrecomendations.room.BeerDatabase;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class MainActivityViewModel extends AndroidViewModel {

    // Create a LiveData with a String
//    private LiveData<List<BeerModel>> beers;
    private MutableLiveData<List<BeerModel>> beersMutable ;

    private BeerDatabase beerDatabase;
    private BeerDao beerDao;


    public MainActivityViewModel(Application application) {
        super(application);

        beerDatabase = BeerDatabase.getAppDatabase(application);

        beerDao = beerDatabase.beerDao();

//        beers = beerDao.getBeers("*");
        beersMutable = new MutableLiveData<>();

    }


    public MutableLiveData<List<BeerModel>> getBeers() {
        return beersMutable;
    }

    //Get beers from API and put value to beersMutable
    public void getBeers(final String food) {
        Single<Response<List<BeerModel>>> beerObservable = BrewDogApiAdapter.getApiService().getBeers(food);

        beerObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<List<BeerModel>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
//                        Log.d(TAG, "OnSubscribe");

                    }

                    @Override
                    public void onSuccess(Response<List<BeerModel>> listResponse) {

                        beersMutable.postValue(listResponse.body());
                        //Insert every result to database
                        for (BeerModel bm : listResponse.body()) {
                            bm.setFood(food);
                            insertBeer(bm);
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        beersMutable.postValue(new ArrayList<BeerModel>());

                    }
                });
    }


    private void insertBeer(BeerModel beerModel) {
        new insertAsyncTask(beerDao).execute(beerModel);
    }

    private static class insertAsyncTask extends AsyncTask<BeerModel, Void, Void> {

        private BeerDao mAsyncTaskDao;

        insertAsyncTask(BeerDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final BeerModel... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
}
