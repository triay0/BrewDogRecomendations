package com.sopra.brewdogrecomendations.data.remote.api;

import com.sopra.brewdogrecomendations.data.local.entity.Beer;

import java.util.List;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface BeerApiService {

    @GET("beers")
    Call<List<Beer>> getBeers(@Query("food") String food);
}
