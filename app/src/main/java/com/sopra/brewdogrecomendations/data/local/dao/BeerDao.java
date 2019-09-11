package com.sopra.brewdogrecomendations.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.sopra.brewdogrecomendations.data.local.entity.Beer;

import java.util.List;

@Dao
public interface BeerDao {

    @Query("SELECT * FROM Beer")
    LiveData<List<Beer>> getAllBeers();

    @Query("SELECT * FROM Beer WHERE food_pairing LIKE :food")
    LiveData<List<Beer>> getBeers(String food);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Beer... beer);

    @Query("DELETE FROM Beer")
    public void destroyTable();

}