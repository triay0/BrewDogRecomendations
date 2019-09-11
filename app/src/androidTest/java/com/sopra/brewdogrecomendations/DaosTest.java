package com.sopra.brewdogrecomendations;


import androidx.lifecycle.LiveData;
import androidx.room.Room;
import androidx.test.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;

import com.sopra.brewdogrecomendations.data.local.BeerDatabase;
import com.sopra.brewdogrecomendations.data.local.entity.Beer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static junit.framework.TestCase.assertNotNull;

@RunWith(AndroidJUnit4.class)
public class DaosTest {


    private BeerDatabase beerDatabase;

    @Before
    public void init() {
        //The database will be created in system memory,
        beerDatabase = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(), BeerDatabase.class).build();
    }

    @After
    public void uninit() {
        beerDatabase.close();
    }

    //Check if BeewModel is saved correctly
    @Test
    public void saveBeerModel() {
        Beer entity = new Beer();

        entity.setId(1000);
        entity.setName("Estrella Damm");
        entity.setTagline("LoremIpsum");
        entity.setFood("pescao");

        beerDatabase.beerDao().insert(entity);
        LiveData<List<Beer>> articlesList = beerDatabase.beerDao().getBeers("pescao");
        assertNotNull(articlesList);
    }
}


//Afegir m'es tests per l'objecte BeerData, retrofit
