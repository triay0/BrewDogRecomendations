package com.sopra.brewdogrecomendations.net;

import com.sopra.brewdogrecomendations.viewmodel.BeerModel;

import java.util.List;

import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface BrewDogApiService {

    @GET("beers")
    Single<Response<List<BeerModel>>> getBeers(@Query("food") String address);
}
