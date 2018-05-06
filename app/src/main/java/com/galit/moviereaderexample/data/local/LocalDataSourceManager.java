package com.galit.moviereaderexample.data.local;

import android.content.Context;

import com.galit.moviereaderexample.data.local.model.Movie;
import com.galit.moviereaderexample.data.local.model.MovieGroup;
import com.galit.moviereaderexample.data.remote.model.MovieDTO;
import com.galit.moviereaderexample.utils.EMovieGroupType;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;
import io.realm.RealmResults;

/**
 * Created by galit on 01/05/2018.
 */

public class LocalDataSourceManager implements ILocalDataSource {

    public LocalDataSourceManager(Context context) {
        Realm.init(context);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                .name(MovieReaderRealmMigration.SCHEMA_NAME)
                .schemaVersion(MovieReaderRealmMigration.SCHEMA_VERSION)
                .migration(new MovieReaderRealmMigration())
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);
    }

    private MovieGroup getResetOrCreatedMovieGroup(Realm realm, EMovieGroupType movieGroupType){
        MovieGroup movieGroup = Realm.getDefaultInstance().where(MovieGroup.class).equalTo(MovieGroup.FIELD_NAME, movieGroupType.getKey()).findFirst();

        if(movieGroup == null){
            movieGroup = Realm.getDefaultInstance().createObject(MovieGroup.class, movieGroupType.getKey());

        }else {
            movieGroup.clear();
        }

        return movieGroup;
    }

    private void saveGroupMovies(Realm realm, MovieGroup movieGroup, List<MovieDTO> movieDTOList, String imagesPath){
        for (MovieDTO movieDTO : movieDTOList) {
            Movie existingMovie = Realm.getDefaultInstance().where(Movie.class).equalTo(Movie.FIELD_ID, movieDTO.id).findFirst();

            if(existingMovie != null && existingMovie.isValid()){
                movieGroup.addMovie(existingMovie);

            }else{

                Movie newMovie = realm.createObject(Movie.class, movieDTO.id);
                newMovie.update(movieDTO);
                newMovie.setPosterPath(imagesPath + movieDTO.poster_path);
                movieGroup.addMovie(newMovie);
            }
        }
    }

    @Override
    public Flowable<MovieGroup> getMovies(EMovieGroupType movieGroupType){

        Realm realm = Realm.getDefaultInstance();
       try{

            return (Flowable)realm.where(MovieGroup.class).equalTo(MovieGroup.FIELD_NAME, movieGroupType.getKey())
                    .findFirstAsync()
                    .asFlowable();
        }finally {
            realm.close();
        }
    }

    @Override
    public void saveMovies(final EMovieGroupType movieGroupType, final List<MovieDTO> movieDTOList, final String imagesPath){

        if(movieDTOList != null){

            Realm realm = Realm.getDefaultInstance();
            try {

                realm.executeTransaction(new Realm.Transaction() {

                    @Override
                    public void execute(Realm realm) {
                        MovieGroup movieGroup = getResetOrCreatedMovieGroup(realm, movieGroupType);
                        saveGroupMovies(realm, movieGroup, movieDTOList, imagesPath);
                        realm.insertOrUpdate(movieGroup);
                    }
                });

            }finally {
                realm.close();
            }

        }

    }

    @Override
    public void clearData(){

        Realm realm = Realm.getDefaultInstance();
        try{

            realm.executeTransaction(new Realm.Transaction() {

                @Override
                public void execute(Realm realm) {
                    realm.deleteAll();
                }
            });
        }finally {
            realm.close();
        }
    }
}
