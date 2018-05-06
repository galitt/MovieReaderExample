package com.galit.moviereaderexample.utils;

import android.content.SharedPreferences;

/**
 * Created by galit on 01/05/2018.
 */

public interface ISharedPreferencesProvider {

    SharedPreferences provideSharedPreferencesToRead();
    SharedPreferences.Editor provideSharedPreferencesEditor();
}
