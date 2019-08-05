package com.sopra.brewdogrecomendations.room;

import androidx.room.TypeConverter;

import com.google.gson.Gson;

import java.util.ArrayList;

public class Converters {

    @TypeConverter
    public static String fromArrayList(ArrayList<String> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }
}

