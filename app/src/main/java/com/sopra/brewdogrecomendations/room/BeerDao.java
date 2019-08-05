package com.sopra.brewdogrecomendations.room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.sopra.brewdogrecomendations.viewmodel.BeerModel;

import java.util.List;

@Dao
public interface BeerDao {

    @Query("SELECT * FROM Beer WHERE food_pairing LIKE :food")
    List<BeerModel> getBeers(String food);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(BeerModel... beer);

    @Query("DELETE FROM Beer")
    public void destroyTable();

}