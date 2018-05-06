package com.galit.moviereaderexample.data.local;

/**
 * Created by galit on 01/05/2018.
 */

public interface ILocalDataSourceResultListener {

    void onSuccess();
    void onError(Throwable error);
}
