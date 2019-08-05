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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public float getAbv() {
        return abv;
    }

    public void setAbv(float abv) {
        this.abv = abv;
    }

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
