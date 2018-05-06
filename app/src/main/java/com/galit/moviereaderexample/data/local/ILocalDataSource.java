package com.galit.moviereaderexample.data.local;

import com.galit.moviereaderexample.data.local.model.MovieGroup;
import com.galit.moviereaderexample.data.remote.model.MovieDTO;
import com.galit.moviereaderexample.utils.EMovieGroupType;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;

/**
 * Created by galit on 01/05/2018.
 *
 *
 */

public interface ILocalDataSource {

    Flowable<MovieGroup> getMovies(EMovieGroupType movieGroupType);
    void saveMovies(final EMovieGroupType movieGroupType, final List<MovieDTO> movieDTOList, String imagesPath);
    void clearData();
}
