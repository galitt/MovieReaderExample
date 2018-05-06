package com.galit.moviereaderexample.utils;

import android.content.Context;
import android.support.annotation.StringRes;

import com.galit.moviereaderexample.R;

import java.util.Calendar;

/**
 * Created by galit on 01/05/2018.
 */

public enum EMovieGroupType {

    MOST_POPULAR(R.string.title_movie_group_popular),
    LAST_YEAR(R.string.title_movie_group_last_year),
    COMEDY(R.string.title_movie_group_comedy),
    KIDS(R.string.title_movie_group_kids)

    ;

    public static final int MOVIE_GROUP_COUNT = 4;

    private @StringRes int mTitle;

    private EMovieGroupType(@StringRes int title) {
        this.mTitle = title;
    }

    public String getTitle(Context context) {

        String title = context.getResources().getString(mTitle);

        if(equals(EMovieGroupType.LAST_YEAR)){
            return String.format(title, (Calendar.getInstance().get(Calendar.YEAR) - 1));
        }

        return title;
    }

    public String getKey() {
        return toString();
    }
}
