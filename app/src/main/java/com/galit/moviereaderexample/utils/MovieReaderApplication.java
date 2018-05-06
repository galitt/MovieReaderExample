package com.galit.moviereaderexample.utils;

import android.app.Application;
import android.content.SharedPreferences;

import com.galit.moviereaderexample.data.DataRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by galit on 01/05/2018.
 */

public class MovieReaderApplication extends Application implements ISharedPreferencesProvider {

    public static final String PREFS_MOVIE_READER = "PREFS_MOVIE_READER";

    @Override
    public void onCreate() {
        super.onCreate();
        initAppManagers();
    }

    private void initAppManagers(){
        NetworkUtils.init(this);
        DataRepository.getInstance().init(this);
        DataRepository.getInstance().updateData(this);
    }

    @Override
    public SharedPreferences provideSharedPreferencesToRead() {
        return getSharedPreferences(PREFS_MOVIE_READER, MODE_PRIVATE);
    }

    @Override
    public SharedPreferences.Editor provideSharedPreferencesEditor() {
        return getSharedPreferences(PREFS_MOVIE_READER, MODE_PRIVATE).edit();
    }
}
