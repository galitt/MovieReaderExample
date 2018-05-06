package com.galit.moviereaderexample.data;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.galit.moviereaderexample.data.local.ILocalDataSource;
import com.galit.moviereaderexample.data.local.ILocalDataSourceResultListener;
import com.galit.moviereaderexample.data.remote.IRemoteDataSource;
import com.galit.moviereaderexample.data.remote.TMDBAPI;
import com.galit.moviereaderexample.data.remote.model.MovieDTO;
import com.galit.moviereaderexample.data.remote.model.MovieListDTO;
import com.galit.moviereaderexample.utils.EMovieGroupType;
import com.galit.moviereaderexample.utils.ISharedPreferencesProvider;
import com.galit.moviereaderexample.utils.NetworkUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Calendar;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by galit on 03/05/2018.
 */

public class DataUpdateManager {

    public static final String PREFS_MOVIE_READER_KEY_TIMESTAMP = "PREFS_MOVIE_READER_KEY_TIMESTAMP";
    private static final long DATA_UPDATE_DELAY = 86400; //1 day in sec


    private ISharedPreferencesProvider mSharedPreferencesProvider;
    private final Logger mLogger = LoggerFactory.getLogger(getClass().getSimpleName());

    /**
     *Check for data updates
     *
     *
     * @param sharedPreferencesProvider
     * @return true if update data executed
     *
     * @throws IllegalStateException if DataRepository not initialized
     */
    public void updateData(ISharedPreferencesProvider sharedPreferencesProvider, ILocalDataSource localDataSourceManager, IRemoteDataSource remoteDataSourceManager){

        validate(sharedPreferencesProvider, localDataSourceManager, remoteDataSourceManager);
        mSharedPreferencesProvider = sharedPreferencesProvider;

        if(isNeedToUpdate()){

            EMovieGroupType[] groupTypes = EMovieGroupType.values();
            for(EMovieGroupType type : groupTypes){
                updateMovies(type, localDataSourceManager, remoteDataSourceManager);
            }

        }
    }

    /**
     *
     * @param sharedPreferencesProvider
     * @param localDataSourceManager
     * @param remoteDataSourceManager
     */
    private void validate(ISharedPreferencesProvider sharedPreferencesProvider, ILocalDataSource localDataSourceManager, IRemoteDataSource remoteDataSourceManager){
        if(sharedPreferencesProvider == null){
            throw new IllegalStateException("SharedPreferencesProvider not provided");
        }

        if(localDataSourceManager == null){
            throw new IllegalStateException("LocalDataSourceManager not provided");
        }

        if(remoteDataSourceManager == null){
            throw new IllegalStateException("RemoteDataSourceManager not provided");
        }
    }

    /**
     *
     * @return
     */
    private boolean isNeedToUpdate(){

        if( !NetworkUtils.isNetworkConnected() ){
            return false;
        }

        long timestampDiff = getCurrentTimestamp() - getLastDataUpdateTimestamp();

        return timestampDiff > DATA_UPDATE_DELAY;
    }

    /**
     *
     * @return
     */
    private long getLastDataUpdateTimestamp(){

        SharedPreferences prefs = mSharedPreferencesProvider.provideSharedPreferencesToRead();
        return prefs.getLong(PREFS_MOVIE_READER_KEY_TIMESTAMP, 0);
    }

    /**
     *
     */
    private void setLastDataUpdateTimestamp(){

        SharedPreferences.Editor editor = mSharedPreferencesProvider.provideSharedPreferencesEditor();
        editor.putLong(PREFS_MOVIE_READER_KEY_TIMESTAMP, getCurrentTimestamp());
        editor.apply();
    }

    /**
     *
     * @return current time in seconds
     */
    private long getCurrentTimestamp(){
        return System.currentTimeMillis()/1000;
    }

    private void updateMovies(final EMovieGroupType type, final ILocalDataSource localDataSourceManager, IRemoteDataSource remoteDataSourceManager){

        remoteDataSourceManager.getMoviesByYear((Calendar.getInstance().get(Calendar.YEAR) - 1))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<MovieListDTO>() {

                               @Override
                               public void accept(MovieListDTO movieDTOList) throws Exception {
                                   if(movieDTOList != null && movieDTOList.results != null) {
                                       final List<MovieDTO> results = movieDTOList.results;
                                       mLogger.info("movies count " + results.size());
                                       localDataSourceManager.clearData();

                                       localDataSourceManager.saveMovies(type, results, TMDBAPI.URL_BASE_IMAGES);
                                       setLastDataUpdateTimestamp();
                                   }

                               }
                           }, new Consumer<Throwable>() {
                               @Override
                               public void accept(@NonNull Throwable throwable) throws Exception {
                                   mLogger.error("updateLastYearMovies failed ", throwable);
                               }
                           }
                );

    }

//    private void updateLastYearMovies(final ILocalDataSource localDataSourceManager, IRemoteDataSource remoteDataSourceManager){
//
//        remoteDataSourceManager.getMoviesByYear((Calendar.getInstance().get(Calendar.YEAR) - 1))
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<MovieListDTO>() {
//
//                    @Override
//                    public void onSubscribe(Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(MovieListDTO movieDTOList) {
//                        if(movieDTOList != null && movieDTOList.results != null) {
//                            final List<MovieDTO> results = movieDTOList.results;
//                            mLogger.info("updateLastYearMovies count " + results.size());
//                            localDataSourceManager.clearData(new ILocalDataSourceResultListener() {
//                                @Override
//                                public void onSuccess() {
//
//                                    if (results != null && !results.isEmpty()) {
//                                        localDataSourceManager.saveMovies(EMovieGroupType.LAST_YEAR, results, null);
//                                        setLastDataUpdateTimestamp();
//                                    }
//                                }
//
//                                @Override
//                                public void onError(Throwable error) {
//
//                                    mLogger.error("updateLastYearMovies failed", error);
//                                }
//                            });
//                        }
//                    }
//
//                    @Override
//                    public void onError(Throwable throwable) {
//                        mLogger.error("updateLastYearMovies failed ", throwable);
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//
//                });
//
//    }
}
