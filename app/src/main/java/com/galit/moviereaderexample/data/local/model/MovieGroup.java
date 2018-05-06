package com.galit.moviereaderexample.data.local.model;

import android.support.annotation.StringRes;

import com.galit.moviereaderexample.utils.EMovieGroupType;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by galit on 01/05/2018.
 *
 * Represents many to many relation between movies and groups
 * defined by {@link EMovieGroupType}
 */

public class MovieGroup extends RealmObject {

    public static final String FIELD_NAME = "groupName";

    @PrimaryKey
    private String groupName;
    private RealmList<Movie> groupMovies = new RealmList<>();

    public String getGroupName() {
        return groupName;
    }

    public EMovieGroupType getGroupType(){
        return EMovieGroupType.valueOf(groupName);
    }

    /**
     * add movie to relayion
     * @param movie
     */
    public void addMovie(Movie movie){
        if(movie != null && movie.isValid()){
            groupMovies.add(movie);
        }
    }

    /**
     *
     * @param maxCount up to X first movie's images will be provided
     * @return list of mage urls
     */
    public List<String> getBestMoviesImages(int maxCount){

        List<String> result = new ArrayList<>();
        if(maxCount <= 0){
            maxCount = groupMovies.size();
        }

        int i = 0;
        while(i < groupMovies.size() && i < maxCount){
            result.add(groupMovies.get(i).getPosterPath());
            i++;
        }

        return result;
    }

    /**
     * Clears objects from relation
     */
    public void clear(){
        groupMovies.clear();
    }


}
