package com.galit.moviereaderexample.data.remote;

import android.content.Context;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.BuildConfig;
import com.androidnetworking.interceptors.HttpLoggingInterceptor;
import com.galit.moviereaderexample.data.remote.model.MovieDTO;
import com.galit.moviereaderexample.data.remote.model.MovieListDTO;
import com.rx2androidnetworking.Rx2AndroidNetworking;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;

/**
 * Created by galit on 01/05/2018.
 */

public class RemoteDataSourceManager implements IRemoteDataSource {
    private static final long NETWORK_TIMEOUT = 30;

    public RemoteDataSourceManager(Context context) {

        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(NETWORK_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(NETWORK_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(NETWORK_TIMEOUT, TimeUnit.SECONDS)
                .build();

        AndroidNetworking.initialize(context, okHttpClient);

        //enable logs
        if ( BuildConfig.DEBUG) {
            AndroidNetworking.enableLogging(HttpLoggingInterceptor.Level.BODY);
        }
    }

    @Override
    public Observable<MovieListDTO> getComedies() {
        return Rx2AndroidNetworking.get(TMDBAPI.getComediesUrl())
                .build()
                .getObjectObservable(MovieListDTO.class);
    }

    @Override
    public Observable<MovieListDTO> getMoviesByPopularity() {
        return Rx2AndroidNetworking.get(TMDBAPI.getMoviesByPopularityUrl())
                .build()
                .getObjectObservable(MovieListDTO.class);
    }

    @Override
    public Observable<MovieListDTO> getMoviesByYear(int year) {
        return Rx2AndroidNetworking.get(TMDBAPI.getMoviesByYearUrl(year))
                .build()
                .getObjectObservable(MovieListDTO.class);
    }

    @Override
    public Observable<MovieListDTO> getMoviesForKids() {
        return Rx2AndroidNetworking.get(TMDBAPI.getMoviesForKidsUrl())
                .build()
                .getObjectObservable(MovieListDTO.class);
    }
}
