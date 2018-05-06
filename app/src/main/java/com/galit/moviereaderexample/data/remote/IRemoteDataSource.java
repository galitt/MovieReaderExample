package com.galit.moviereaderexample.data.remote;

import com.galit.moviereaderexample.data.remote.model.MovieDTO;
import com.galit.moviereaderexample.data.remote.model.MovieListDTO;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by galit on 03/05/2018.
 */

public interface IRemoteDataSource {
    Observable<MovieListDTO> getComedies();
    Observable<MovieListDTO> getMoviesByPopularity();
    Observable<MovieListDTO> getMoviesByYear(int year);
    Observable<MovieListDTO> getMoviesForKids();
}
