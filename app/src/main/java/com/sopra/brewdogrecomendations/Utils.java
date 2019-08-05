package com.sopra.brewdogrecomendations;

import android.view.View;

import com.google.android.material.snackbar.Snackbar;

public class Utils {

    public static Snackbar getSnackbar(View v, String msg){

        return Snackbar
                .make(v, msg, Snackbar.LENGTH_LONG);
    }
}
