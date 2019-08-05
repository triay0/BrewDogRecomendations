package com.sopra.brewdogrecomendations.viewmodel;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

@Entity(tableName = "beer")
public class BeerModel {
    @SerializedName("id")
    @PrimaryKey
    public int id;
    @ColumnInfo(name = "name")
    @SerializedName("name")
    public String name;
    @ColumnInfo(name = "tagline")
    @SerializedName("tagline")
    public String tagline;
    @ColumnInfo(name = "description")
    @SerializedName("description")
    public String description;
    @ColumnInfo(name = "image_url")
    @SerializedName("image_url")
    public String image_url;
    @ColumnInfo(name = "abv")
    @SerializedName("abv")
    public float abv;

    public String getFood() {
        return food;
    }

    public void setFood(String food) {
        this.food = food;
    }

    @ColumnInfo(name = "food_pairing")
    //@SerializedName("food_pairing")
    public String food;

}
