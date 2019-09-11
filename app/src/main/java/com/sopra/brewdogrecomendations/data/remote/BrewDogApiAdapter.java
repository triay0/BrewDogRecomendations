package com.sopra.brewdogrecomendations.data.remote;

import com.sopra.brewdogrecomendations.data.remote.api.BeerApiService;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class BrewDogApiAdapter {


        private static BeerApiService API_SERVICE;

        public static BeerApiService getApiService () {

            if(API_SERVICE == null){
                Retrofit adapter = new Retrofit.Builder()
                        .baseUrl(ApiConstants.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .build();

                API_SERVICE = adapter.create(BeerApiService.class);
            }

            return API_SERVICE;

        }

}
