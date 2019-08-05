package com.sopra.brewdogrecomendations.net;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class BrewDogApiAdapter {


        private static BrewDogApiService API_SERVICE;

        public static BrewDogApiService getApiService () {

            if(API_SERVICE == null){
                Retrofit adapter = new Retrofit.Builder()
                        .baseUrl(ApiConstants.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .build();

                API_SERVICE = adapter.create(BrewDogApiService.class);
            }

            return API_SERVICE;

        }

}
