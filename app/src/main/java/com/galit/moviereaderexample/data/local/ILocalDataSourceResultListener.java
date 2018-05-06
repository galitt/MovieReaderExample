package com.galit.moviereaderexample.data.local;

/**
 * Created by galit on 01/05/2018.
 *
 * Local data operations callback
 */

public interface ILocalDataSourceResultListener {

    /**
     * local data updated successfully
     */
    void onSuccess();

    /**
     * local data update failed
     * @param error
     */
    void onError(Throwable error);
}
