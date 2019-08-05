package com.sopra.brewdogrecomendations.room;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.sopra.brewdogrecomendations.viewmodel.BeerModel;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface BeerDao {

    @Query("SELECT * FROM Beer WHERE food_pairing LIKE :food")
    LiveData<List<BeerModel>> getBeers(String food);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(BeerModel... beer);

    @Query("DELETE FROM Beer")
    public void destroyTable();

}