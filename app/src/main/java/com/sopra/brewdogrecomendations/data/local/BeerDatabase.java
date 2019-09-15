package com.sopra.brewdogrecomendations.data.local;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.sopra.brewdogrecomendations.data.local.dao.BeerDao;
import com.sopra.brewdogrecomendations.data.local.entity.Beer;


@Database(entities = {Beer.class}, version = 1, exportSchema = false)
public abstract class BeerDatabase extends RoomDatabase {

    private static BeerDatabase INSTANCE;

    public abstract BeerDao beerDao();

    public static BeerDatabase getAppDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(), BeerDatabase.class, "beer-database")
                            .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}