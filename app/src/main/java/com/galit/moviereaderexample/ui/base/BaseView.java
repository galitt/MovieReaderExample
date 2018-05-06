package com.galit.moviereaderexample.ui.base;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.StringRes;

/**
 * Created by galit on 01/05/2018.
 */

public interface BaseView {

    void setLoadingInProgress(boolean inProgress);
    void setNoData();
    void showError(@StringRes int msg);
    Context getContext();
}
