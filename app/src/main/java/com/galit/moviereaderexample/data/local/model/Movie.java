package com.galit.moviereaderexample.data.local.model;

import com.galit.moviereaderexample.data.remote.model.MovieDTO;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by galit on 01/05/2018.
 */

public class Movie extends RealmObject {
    public static final String FIELD_ID = "id";


    @PrimaryKey
    private long id;
    private int voteCount;
    private boolean video;
    private float voteAverage;
    private String title;
    private float popularity;
    private String posterPath;
    private String originalLanguage;
    private String originalTitle;
    private String backdropPath;
    private boolean adult;
    private String overview;
    private String releaseDate;

    public void update(MovieDTO movieDTO){

        if(movieDTO != null){
            setVoteCount(movieDTO.vote_count);
            setVideo(movieDTO.video);
            setVoteAverage(movieDTO.vote_average);
            setTitle(movieDTO.title);
            setPopularity(movieDTO.popularity);
            setPosterPath(movieDTO.poster_path);
            setOriginalLanguage(movieDTO.original_language);
            setOriginalTitle(movieDTO.original_title);
            setBackdropPath(movieDTO.backdrop_path);
            setAdult(movieDTO.adult);
            setOverview(movieDTO.overview);
            setReleaseDate(movieDTO.release_date);
        }
    }

    public long getId() {
        return id;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public boolean isVideo() {
        return video;
    }

    public void setVideo(boolean video) {
        this.video = video;
    }

    public float getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(float voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public float getPopularity() {
        return popularity;
    }

    public void setPopularity(float popularity) {
        this.popularity = popularity;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    /**
     *
     * @return default readable date representation
     */
    public String getFormattedReleaseDate(){

        if(releaseDate == null){
            return null;
        }

        final String receivedFormat = "yyyy-MM-dd";
        final String defaultFormat = "MMM d, yyyy";

        try {
            Date date = new SimpleDateFormat(receivedFormat).parse(releaseDate);
            return new SimpleDateFormat(defaultFormat).format(date);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
