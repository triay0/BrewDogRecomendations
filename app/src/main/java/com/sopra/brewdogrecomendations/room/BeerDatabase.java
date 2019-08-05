package com.sopra.brewdogrecomendations.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.sopra.brewdogrecomendations.viewmodel.BeerModel;

@Database(entities = {BeerModel.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})

public abstract class BeerDatabase extends RoomDatabase {

    private static BeerDatabase INSTANCE;

    public abstract BeerDao beerDao();

    public static BeerDatabase getAppDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(), BeerDatabase.class, "beer-database")
                            // allow queries on the main thread.
                            // Don't do this on a real app! See PersistenceBasicSample for an example.
                            .allowMainThreadQueries()///////////////////////////////////////////////////////////////////////
                            .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}