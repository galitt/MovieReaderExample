package com.galit.moviereaderexample.data.remote;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Set;

/**
 * Created by galit on 01/05/2018.
 */

public class TMDBAPI {
    private static Logger mLogger = LoggerFactory.getLogger(TMDBAPI.class.getSimpleName());

    private static final String API_KEY = "1cc47c4124f8a486b2c884ae333b31ab";

    //urls
    public static final String URL_BASE = "http://api.themoviedb.org/3";
    public static final String URL_BASE_IMAGES = "https://image.tmdb.org/t/p/w500";
    public static final String URL_DISCOVER_MOVIE = "/discover/movie?";


    //keys
    private static final String PARAM_KEY_API_KEY = "&api_key=";
    public static final String PARAM_KEY_RELEASE_YEAR = "&primary_release_year=";
    public static final String PARAM_KEY_SORT_BY = "&sort_by=";
    public static final String PARAM_KEY_GENRES = "&with_genres=";
    public static final String PARAM_KEY_CERT_COUNTRY = "&certification_country=";
    public static final String PARAM_KEY_CERT_LTE = "&certification.lte=";




    //static key values
    public static final String PARAM_VAL_VOTE_AVERAGE_DESC = "vote_average.desc";
    public static final String PARAM_VAL_POPULARITY_DESC = "popularity.desc";
    public static final String PARAM_VAL_REVENUE_DESC = "revenue.desc";
    public static final String PARAM_VAL_GENRES_COMEDY = "35";
    public static final String PARAM_VAL_US = "US";
    public static final String PARAM_VAL_CERT_LTE = "G";



    /**
     *
     * @param params
     * @return
     */
    public static String getDiscoverMovieUrl(HashMap<String, String> params){

        String result = URL_BASE + URL_DISCOVER_MOVIE;
        if(params != null && !params.isEmpty()){

            Set<String> keySet = params.keySet();

            for(String key : keySet){
                result = result + key + params.get(key);
            }
        }

        mLogger.info("getDiscoverMovieUrl " + result + PARAM_KEY_API_KEY + API_KEY);
        return result + PARAM_KEY_API_KEY + API_KEY;
    }

    public static String getMoviesByYearUrl(int year){
        HashMap<String, String> params = new HashMap<>();
        params.put(PARAM_KEY_RELEASE_YEAR, String.valueOf(year));
        params.put(PARAM_KEY_SORT_BY, PARAM_VAL_VOTE_AVERAGE_DESC);

        return getDiscoverMovieUrl(params);
    }

    public static String getMoviesByPopularityUrl(){
        HashMap<String, String> params = new HashMap<>();
        params.put(PARAM_KEY_SORT_BY, PARAM_VAL_POPULARITY_DESC);

        return getDiscoverMovieUrl(params);
    }

    public static String getComediesUrl(){
        HashMap<String, String> params = new HashMap<>();
        params.put(PARAM_KEY_GENRES, PARAM_VAL_GENRES_COMEDY);
        params.put(PARAM_KEY_SORT_BY, PARAM_VAL_REVENUE_DESC);

        return getDiscoverMovieUrl(params);
    }

    public static String getMoviesForKidsUrl(){
        HashMap<String, String> params = new HashMap<>();
        params.put(PARAM_KEY_CERT_COUNTRY, PARAM_VAL_US);
        params.put(PARAM_KEY_CERT_LTE, PARAM_VAL_CERT_LTE);
        params.put(PARAM_KEY_SORT_BY, PARAM_VAL_POPULARITY_DESC);

        return getDiscoverMovieUrl(params);
    }
}
