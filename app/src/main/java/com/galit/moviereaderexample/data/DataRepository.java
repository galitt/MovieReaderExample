package com.galit.moviereaderexample.data;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.galit.moviereaderexample.data.local.LocalDataSourceManager;
import com.galit.moviereaderexample.data.local.model.MovieGroup;
import com.galit.moviereaderexample.data.remote.RemoteDataSourceManager;
import com.galit.moviereaderexample.utils.EMovieGroupType;
import com.galit.moviereaderexample.utils.ISharedPreferencesProvider;
import com.galit.moviereaderexample.utils.MovieReaderApplication;
import com.galit.moviereaderexample.utils.NetworkUtils;

import io.reactivex.Flowable;
import io.reactivex.Observable;

/**
 * Created by galit on 01/05/2018.
 *
 *
 * Provides centralized, consistent access to data
 * from multiple sources
 *
 */

public class DataRepository {


    private LocalDataSourceManager mLocalDataSourceManager;
    private RemoteDataSourceManager mRemoteDataSourceManager;
    private DataUpdateManager mDataUpdateManager;
    private boolean mIsInitialized = false;

    private static DataRepository instance;

    private DataRepository() {
    }


    /**
     * First call must be from Application
     * @return current instance
     */
    public static DataRepository getInstance(){
        if(instance == null){
            instance = new DataRepository();
        }

        return instance;
    }

    /**
     * Must be called from Application
     * @param appContext application context for initialization
     */
    public void init(Context appContext){
        mDataUpdateManager = new DataUpdateManager();
        mRemoteDataSourceManager = new RemoteDataSourceManager(appContext);
        mLocalDataSourceManager = new LocalDataSourceManager(appContext);
        mIsInitialized = true;
    }

    /**
     *Check for data updates
     *
     *
     * @param sharedPreferencesProvider
     * @return true if update data executed
     *
     * @throws IllegalStateException if DataRepository not initialized
     */
    public void updateData(ISharedPreferencesProvider sharedPreferencesProvider){

        if( !mIsInitialized ){
            throw new IllegalStateException("DataRepository must be initialized");
        }

        mDataUpdateManager.updateData(sharedPreferencesProvider, mLocalDataSourceManager, mRemoteDataSourceManager);
    }

    public Flowable<MovieGroup> getMovies(EMovieGroupType movieGroupType){
        return mLocalDataSourceManager.getMovies(movieGroupType);
    }
}
